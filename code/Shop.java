import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Shop implements CellElement{
    ArrayList<Potion> list;
    private Character character;
    public Shop(Character character)
    {
        this.character=character;
        Random random=new Random();
        int npotion=random.nextInt(3);
        list=new ArrayList<Potion>();
        list.add(new HealthPotion());
        list.add(new ManaPotion());
        for(int i=0;i<npotion;i++)
        {
            int n=random.nextInt(2);
            if(n==0)
                list.add(new HealthPotion());
            if(n==1)
                list.add(new ManaPotion());
        }
    }
    @Override
    public char toCharacter() {
        return 'S';
    }
    public Potion buyPotion(int index)
    {
        Potion p=list.get(index);
        list.remove(index);
        return p;
    }
    @Override
    public void actionT() {
        ArrayList<String> cmds=new ArrayList<String>();
        cmds.add("B");
        cmds.add("B");
        cmds.add("X");
        System.out.println("Shopkeeper: Welcome to my shop! To buy something , press B and then the number of the desired object.");
        System.out.println("X-exit");
        Scanner scanner = new Scanner(System.in);
        int i=0;
        while(true) {
            System.out.println("Your money: " + character.inventory.cash);
            if (list.isEmpty())
                System.out.println("No items to buy for now.");
            else {
                System.out.println("  Potion Regen Price");
                for (int j = 0; j < this.list.size(); j++)
                    System.out.println(j + " " + list.get(j).toString() + " " + list.get(j).getPrice());
            }
            String cmd = scanner.nextLine();
            if (cmd.equals("P")) {
                if (cmds.get(i).equals("X"))
                    break;
                if (cmds.get(i).equals("B")) {
                    if (character.buy(list.get(0))) {
                        System.out.println("You've bought a "+list.get(0));
                        buyPotion(0);
                    }
                        else
                        System.out.println("Not enough money or inventory space to buy the desired potion!");
                } else
                    System.out.println("Invalid index.");
                i++;
            }
        }
        }
    }



