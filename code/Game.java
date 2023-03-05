import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.json.simple.parser.ParseException;

public class Game {
    public ArrayList<Account> accounts;
    HashMap<CellType,ArrayList<String>> stories;
    Grid g;
    private Boolean running=true;
    private static Game singleInstance=null;
    private ArrayList<String> cmds;

    private void init_accounts(){
        accounts=new ArrayList<Account>();
        JSONParser jsonParser=new JSONParser();
        try(FileReader reader=new FileReader("fisiere/accounts.json")) {
            Object obj=jsonParser.parse(reader);
            JSONObject jobj=(JSONObject) obj;
            JSONArray account=(JSONArray) jobj.get("accounts");
            CharacterFactory chfa=new CharacterFactory();
            for(int i=0;i<account.size();i++) {
                JSONObject ac=(JSONObject) account.get(i);
                JSONObject cred=(JSONObject) ac.get("credentials");
                Account acc=new Account(Integer.parseInt(String.valueOf(ac.get("maps_completed"))));
                JSONArray fgames=(JSONArray) ac.get("favorite_games");
                ArrayList<String> fgamess = new ArrayList<String>();
                for(int j=0;j<fgames.size();j++){
                    fgamess.add((String)fgames.get(j));
                }
                acc.info=new Account.Information.InformationBuilder(
                        (String)ac.get("name"),new Credentials((String)cred.get("email"),(String)cred.get("password")))
                        .setCountry((String)ac.get("country")).setFavouriteGames(fgamess).build();
                accounts.add(acc);
                JSONArray charray=(JSONArray) ac.get("characters");
                for(int j=0;j<charray.size();j++)
                {
                    JSONObject cha=(JSONObject) charray.get(j);
                    Character newcharacter=chfa.createCharacter((String)cha.get("profession"),Integer.parseInt((String)cha.get("level")),Integer.parseInt(String.valueOf(cha.get("experience"))));
                    newcharacter.name=(String)cha.get("name");
                    acc.addCharacter(newcharacter);
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InformanionIncompleteException e) {
            e.printStackTrace();
        }
    }
    private void init_stories(){
        stories=new HashMap<CellType,ArrayList<String>>();
        JSONParser jsonParser=new JSONParser();
        try(FileReader reader=new FileReader("fisiere/stories.json")){
            Object obj=jsonParser.parse(reader);
            JSONObject jobj=(JSONObject) obj;
            JSONArray story=(JSONArray) jobj.get("stories");
            JSONObject st;
            ArrayList<String> empty=new ArrayList<String>();
            ArrayList<String> shop=new ArrayList<String>();
            ArrayList<String> enemy=new ArrayList<String>();
            ArrayList<String> finish=new ArrayList<String>();

            for(int i=0;i<story.size();i++) {
                st = (JSONObject) story.get(i);
                String type=(String)st.get("type");
                switch (type) {
                    case "EMPTY":
                        empty.add((String)st.get("value"));
                        break;
                    case "ENEMY":
                        enemy.add((String)st.get("value"));
                        break;

                    case "SHOP":
                        shop.add((String)st.get("value"));
                        break;
                    case "FINISH":
                        finish.add((String)st.get("value"));
                        break;
                }
            }
            stories.put(CellType.empty,empty);
            stories.put(CellType.enemy,enemy);
            stories.put(CellType.shop,shop);
            stories.put(CellType.finish,finish);


        }
        catch (FileNotFoundException e) {
        e.printStackTrace();
        }
        catch (IOException e) {
        e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void hardcoding()
    {
        cmds=new ArrayList<String>();
        cmds.add("E");
        cmds.add("E");
        cmds.add("E");
        cmds.add("E");
        cmds.add("S");
        cmds.add("S");
        cmds.add("S");
        cmds.add("S");
        g.get(0).set(3,new Cell(0,3,CellType.shop,g.character));
        g.get(1).set(3,new Cell(1,3,CellType.shop,g.character));
        g.get(2).set(0,new Cell(2,0,CellType.shop,g.character));
        g.get(3).set(4,new Cell(3,4,CellType.enemy,g.character));
        g.get(4).set(4,new Cell(4,4,CellType.finish,g.character));
    }
    private Game(int x,int y)
    {
        g=Grid.generate(x,y);
        init();
    }
    public static Game getInstance(int x,int y)
    {
        if(singleInstance==null)
            singleInstance=new Game(x,y);
        return singleInstance;
    }
    private void init() {
        for (int i = 0; i < g.height; i++) {
            for (int j = 0; j < g.length; j++)
                g.get(i).add(new Cell(i, j, CellType.empty,g.character));
        }
        g.current=g.get(0).get(0);
        init_stories();
        init_accounts();
    }
    private void chooseT()
    {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Choose an account from the list bellow by pressing the corresponding index");
        for(int i=0;i<accounts.size();i++)
            System.out.println(i+". "+accounts.get(i).info.getName());
        int index=scanner.nextInt();
        Account acc=accounts.get(index);
        System.out.println("You've chosen the account named: " + acc.info.getName());
        System.out.println("Choose a character from the list bellow by pressing the corresponding index");
        System.out.println("Character name | Profession | Level");

        ArrayList<Character> characters=acc.getCharacters();
        for(int i=0;i<characters.size();i++)
            System.out.println(i+". "+characters.get(i).name+" | "+characters.get(i).toString()+" | " + characters.get(i).level);
        index=scanner.nextInt();
        g.character=characters.get(index);
        System.out.println("You've chosen the character named: "+g.character.name);

    }
    private void print_map(boolean end)
    {
        if(!end) {
            System.out.println("E-move east");
            System.out.println("W-move west");
            System.out.println("N-move north");
            System.out.println("S-move south");
        }
        for(int i=0;i<g.height;i++) {

            for (int j = 0; j < g.length; j++) {
                if(g.get(i).get(j).equals(g.current) && g.current.type==CellType.finish){
                    System.out.print("PF");
                    continue;
                }
                if(g.get(i).get(j).equals(g.current)){
                    System.out.print("P  ");
                    continue;
                }
                if(g.get(i).get(j).visited)
                    System.out.print(g.get(i).get(j).current.toCharacter()+"  ");
                else
                    System.out.print("?  ");
            }
            System.out.println();
        }


    }
    private void moveT(String cmd)
    {
        if(cmd.equals("S"))
            g.goSouth();
        if(cmd.equals("N"))
            g.goNorth();
        if(cmd.equals("E"))
            g.goEast();
        if(cmd.equals("W"))
            g.goWest();
    }
    private void print_stories()
    {
        Random random=new Random();
        if(!g.current.visited)
        {
            if(g.current.type.equals(CellType.empty))
                System.out.println(stories.get(CellType.empty).get(random.nextInt(stories.get(CellType.empty).size())));
            else if(g.current.type.equals(CellType.enemy))
                System.out.println(stories.get(CellType.enemy).get(random.nextInt(stories.get(CellType.enemy).size())));
            else if(g.current.type.equals(CellType.shop))
                System.out.println(stories.get(CellType.shop).get(random.nextInt(stories.get(CellType.shop).size())));
            else if(g.current.type.equals(CellType.finish))
                System.out.println(stories.get(CellType.finish).get(random.nextInt(stories.get(CellType.finish).size())));

        }
    }
    private boolean actionT()
    {
        print_stories();
        g.current.visited=true;
        if(g.current.type==CellType.finish)
           return true;
        g.current.current.actionT();
        return false;
    }
    private void runT()
    {
        chooseT();
        hardcoding();
        Scanner scanner=new Scanner(System.in);
        int i=0;
        while(running) {
            if (actionT()) {
                print_map(true);
                System.out.println("You've reached the end,congratulations! :)");
                return;
            }
                if(g.character.HP<=0) {
                    System.out.println("You lost :(");
                    return;
                }
                print_map(false);
            String cmd=scanner.nextLine();
            if(cmd.equals("P"))
                moveT(cmds.get(i++));

        }
    }
    private void runG() throws IOException {
        new GUI(accounts,stories,g);
    }
    public void run() throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Press T to play in terminal or G to play in the graphic interface ");
        String mode=scanner.nextLine();
        if(mode.equals("T"))
            runT();
        else if(mode.equals("G"))
            runG();
    }

}
