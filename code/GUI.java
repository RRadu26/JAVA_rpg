import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GUI {
    private ArrayList<Account> accounts;
    private HashMap<CellType, ArrayList<String>> stories;
    private Grid g;
    private JFrame mapF;
    private ArrayList<JButton> grid=new ArrayList<>();
    private ImageIcon characterIcon=new ImageIcon("fisiere/character.png");
    private ImageIcon enemyIcon=new ImageIcon("fisiere/enemy.png");
    private ImageIcon shopIcon=new ImageIcon("fisiere/shop.png");
    private ImageIcon unknownIcon=new ImageIcon("fisiere/unknown.png");
    private DefaultListModel<String> itempanellist=new DefaultListModel<>();
    private int cashwon=0;
    private int enemykilled=0;
    private int xpwon=0;
    private void finish(){
        mapF.dispatchEvent(new WindowEvent(mapF, WindowEvent.WINDOW_CLOSING));
        JFrame finishF=new JFrame("Game over!");
        finishF.setBounds(200,200,600,280);
        JTextField story=new JTextField(storyText());
        JTextField cash=new JTextField("Money won: " + cashwon);
        JTextField enemy=new JTextField("Enemy killed: " + enemykilled);
        JTextField xp=new JTextField("XP obtained: " + xpwon);
        JTextField lvl=new JTextField("Character level: " + g.character.level);
        cash.setBounds(10,50,590,30);
        enemy.setBounds(10,90,590,30);
        xp.setBounds(10,130,590,30);
        lvl.setBounds(10,170,590,30);
        story.setBounds(10,10,590,30);
        story.setEditable(false);
        cash.setEditable(false);
        lvl.setEditable(false);
        xp.setEditable(false);
        enemy.setEditable(false);
        JButton exit=new JButton("Exit");
        exit.setBounds(10,210,150,40);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishF.dispatchEvent(new WindowEvent(finishF, WindowEvent.WINDOW_CLOSING));
            }
        });
        finishF.add(exit);
        finishF.add(story);
        finishF.add(lvl);
        finishF.add(xp);
        finishF.add(enemy);
        finishF.add(cash);
        finishF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        finishF.setLayout(null);
        finishF.setVisible(true);

    }
    private void move(String direction){
        if(g.current.type==CellType.empty){
            grid.get(g.current.Ox*5+g.current.Oy).setIcon(null);
        }
        else if(g.current.type==CellType.shop){
            grid.get(g.current.Ox*5+g.current.Oy).setIcon(shopIcon);
        }
        else if(g.current.type==CellType.enemy){
            grid.get(g.current.Ox*5+g.current.Oy).setIcon(enemyIcon);
        }

        g.current.visited=true;
        switch (direction) {
            case "N" -> g.goNorth();
            case "S" -> g.goSouth();
            case "E" -> g.goEast();
            case "W" -> g.goWest();
        }
        if(g.current.type==CellType.finish)
            finish();
        else if(g.current.type==CellType.shop)
            shop((Shop) g.current.current);
        else if(g.current.type==CellType.enemy)
            enemy((Enemy) g.current.current);
        grid.get(g.current.Ox*5+g.current.Oy).setIcon(characterIcon);

    }
    private String enemyAttack(Enemy enemy) {
        Random random = new Random();
        int t = random.nextInt(3);
        if (t < 2) {
            return g.character.receiveDamage(enemy.getDamage());
        }
        else {
            int rand = random.nextInt(enemy.abilities.size());
            if (enemy.ability(enemy.abilities.get(rand), g.character))
                return "You've been attacked with " + enemy.abilities.get(rand).toString() + " with " + enemy.abilities.get(rand).damage;
                return g.character.receiveDamage(enemy.getDamage());
        }
    }
    private void enemy(Enemy enemy) {
        mapF.setVisible(false);
        JFrame enemyF=new JFrame("World of marcel _battle");
        enemyF.setBounds(200,200,800,800);
        //info panel
            //stats
        JPanel infoP=new JPanel();
        infoP.setBounds(0,0,800,250);
        JTextField hp=new JTextField("HP: " + g.character.HP + "/" + g.character.maxHP);
        JTextField mana=new JTextField("Mana: " + g.character.mana + "/" + g.character.mana);
        JTextField enemyHP=new JTextField("Enemy HP: " + enemy.HP);
        JTextField enemyMana=new JTextField("Enemy Mana: " + enemy.mana);
        hp.setEditable(false);
        mana.setEditable(false);
        enemyHP.setEditable(false);
        enemyMana.setEditable(false);
        hp.setBorder(BorderFactory.createEmptyBorder());
        mana.setBorder(BorderFactory.createEmptyBorder());
        enemyHP.setBorder(BorderFactory.createEmptyBorder());
        enemyMana.setBorder(BorderFactory.createEmptyBorder());
        hp.setBounds(10,10,200,30);
        mana.setBounds(10,50,200,30);
        enemyHP.setBounds(680,10,300,30);
        enemyMana.setBounds(680,50,300,30);
            //battle info
        JTextField log=new JTextField();
        JTextField elog=new JTextField();
        log.setEditable(false);
        elog.setEditable(false);
        log.setBounds(10,90,600,30);
        elog.setBounds(190,210,600,30);
        infoP.add(log);
        infoP.add(elog);
        infoP.add(mana);
        infoP.add(hp);
        infoP.add(enemyHP);
        infoP.add(enemyMana);
        infoP.setBackground(Color.darkGray);
        infoP.setLayout(null);
        //bottomPanel
        JPanel bottomP=new JPanel();
        bottomP.setBounds(0,690,800,40);
        bottomP.setBackground(Color.darkGray);
        JButton attack=new JButton("Attack");
        JButton fireAttack=new JButton("Fire Attack");
        JButton iceAttack=new JButton("Ice Attack");
        JButton earthAttack=new JButton("Earth Attack");
        JButton usePotion=new JButton("Use potion");
        JButton exit=new JButton("Exit");
        exit.setVisible(false);
        bottomP.add(attack);
        bottomP.add(fireAttack);
        bottomP.add(earthAttack);
        bottomP.add(iceAttack);
        bottomP.add(usePotion);
        bottomP.add(exit);
        JTextField story=new JTextField(storyText());
        story.setBounds(10,730,780,30);
        story.setEditable(false);
        //list Panel
        JPanel listP=new JPanel();
        listP.setBounds(0,540,200,150);
        listP.setBackground(Color.darkGray);
        JList<String>item_list=new JList<>(itempanellist);
        item_list.setBounds(0,0,200,150);
        listP.add(item_list);
        listP.setLayout(null);
        if (enemy.HP <= 0)
            exit.setVisible(true);
            //actions
        ActionListener listener=new ActionListener() {
            private Boolean enemyDefeated=false;
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int cash = random.nextInt(5) + 8;
                int xp = random.nextInt(10) + 15;
                Boolean ea = false;
                if (!enemyDefeated) {
                    if (e.getSource().equals(attack)) {
                        log.setText(enemy.receiveDamage(g.character.getDamage()));
                        ea = true;
                    }
                    else if (e.getSource().equals(earthAttack)) {
                        Earth spell = new Earth();
                        g.character.ability(spell, enemy);
                        if (enemy.earth_protect)
                            log.setText("You've attacked the enemy with an Earth Spell who missed");
                        else
                            log.setText("You've attacked the enemy with an Earth Spell with " + spell.damage + "damage");
                        ea = true;
                    }
                    else if (e.getSource().equals(fireAttack)) {
                        Fire spell = new Fire();
                        g.character.ability(spell, enemy);
                        if (enemy.fire_protect)
                            log.setText("You've attacked the enemy with a Fire Spell who missed");
                        else
                            log.setText("You've attacked the enemy with an FIRE Spell with " + spell.damage + "damage");
                        ea = true;
                    }
                    else if (e.getSource().equals(iceAttack)) {
                        Ice spell = new Ice();
                        g.character.ability(spell, enemy);
                        if (enemy.ice_protect)
                            log.setText("You've attacked the enemy with an Ice Spell who missed");
                        else
                            log.setText("You've attacked the enemy with an Ice Spell with " + spell.damage + "damage");
                        ea = true;
                    }
                    else if(e.getSource().equals(usePotion)){
                        log.setText("You've used " + g.character.inventory.list.get((item_list.getSelectedIndex())).toString());
                        elog.setText(null);
                        g.character.inventory.list.get(item_list.getSelectedIndex()).use(g.character);
                        itempanellist.remove(item_list.getSelectedIndex());

                    }
                    if (ea)
                        elog.setText(enemyAttack(enemy));
                    enemyMana.setText("Enemy Mana: " + enemy.mana);
                    hp.setText("HP: " + g.character.HP+ "/" + g.character.maxHP);
                    enemyHP.setText("Enemy HP: " + enemy.HP);
                    mana.setText("Mana: " + g.character.mana+ "/" + g.character.maxmana);
                    if (enemy.HP <= 0) {
                        enemyDefeated = true;
                        log.setText("You've won " + cash + " money and " + xp + " xp!");
                        elog.setText("The enemy was defeated!");
                        g.character.xp += xp;
                        g.character.inventory.cash += cash;
                        g.character.levelUP();
                        exit.setVisible(true);
                        cashwon += cash;
                        xpwon += xp;
                        enemykilled +=1;
                    }
                }
                if (enemyDefeated && e.getSource().equals(exit)) {
                    mapF.setVisible(true);
                    enemyF.dispatchEvent(new WindowEvent(enemyF, WindowEvent.WINDOW_CLOSING));
                }
                if(g.character.HP <= 0)
                {
                    enemyF.dispatchEvent(new WindowEvent(enemyF, WindowEvent.WINDOW_CLOSING));
                    finish();
                }
            }
        };
        attack.addActionListener(listener);
        fireAttack.addActionListener(listener);
        iceAttack.addActionListener(listener);
        earthAttack.addActionListener(listener);
        exit.addActionListener(listener);
        usePotion.addActionListener(listener);
        enemyF.add(story);
        enemyF.add(listP);
        enemyF.add(bottomP);
        enemyF.add(infoP);
        enemyF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enemyF.setLayout(null);
        enemyF.setVisible(true);

    }
    private void shop(Shop shop) {
        //photo
        ImageIcon enemyIcon=new ImageIcon("fisiere/shop_keeper.png");
        JButton buton=new JButton(enemyIcon);
        buton.setBackground(null);
        buton.setBorder(BorderFactory.createEmptyBorder());
        buton.setBounds(0,0,320,100);
        buton.setEnabled(false);
        JPanel photo=new JPanel();
        photo.setLayout(null);
        photo.add(buton);
        photo.setLayout(null);
        photo.setBounds(470,10,320,100);
        photo.setBackground(Color.yellow);
        mapF.setVisible(false);
        JFrame shopF=new JFrame("World of marcel _shop");
        DefaultListModel<String> items=new DefaultListModel<>();
        for(int i=0;i<shop.list.size();i++)
            items.addElement(shop.list.get(i).toString() + " " +shop.list.get(i).getPrice() + "$");
        JButton buyDesiredPotion=new JButton("Buy Potion");
        JButton exit=new JButton("Exit");
        JTextField cash=new JTextField("Money: " + g.character.inventory.cash);
        JTextField weight=new JTextField("Remaining weight: " + g.character.inventory.remaining_weigth());
        JTextField story=new JTextField(storyText());
        cash.setBounds(10,90,150,30);
        weight.setBounds(170,90,300,30);
        story.setBounds(10,130,780,30);
        cash.setBorder(BorderFactory.createEmptyBorder());
        weight.setBorder(BorderFactory.createEmptyBorder());
        story.setEditable(false);
        cash.setEditable(false);
        weight.setEditable(false);
        JList<String>item_list=new JList<>(items);
        item_list.setBounds(10,10,260,80);
        buyDesiredPotion.setBounds(290,10,150,30);
        exit.setBounds(290,50,150,30);
        exit.addActionListener(e -> {
            mapF.setVisible(true);
            shopF.dispatchEvent(new WindowEvent(shopF, WindowEvent.WINDOW_CLOSING));
        });
        buyDesiredPotion.addActionListener(e -> {
            if (g.character.buy(shop.list.get(item_list.getSelectedIndex()))) {
                story.setText("You've bought a "+shop.list.get(item_list.getSelectedIndex()));
                itempanellist.addElement(shop.list.get(item_list.getSelectedIndex()).toString());
                shop.buyPotion(item_list.getSelectedIndex());
                items.remove(item_list.getSelectedIndex());
                weight.setText("Remaining weight: " + g.character.inventory.remaining_weigth());
                cash.setText("Money: " + g.character.inventory.cash);
            }
            else
                story.setText("Not enough money or inventory space to buy the desired potion!");
        });
        shopF.setBounds(200,200,800,200);
        shopF.add(story);
        shopF.add(weight);
        shopF.add(cash);
        shopF.add(exit);
        shopF.add(item_list);
        shopF.add(photo);
        shopF.add(buyDesiredPotion);
        shopF.setLayout(null);
        shopF.setVisible(true);
        shopF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private String storyText(){
        Random random=new Random();
        if(!g.current.visited)
        {
            if(g.current.type.equals(CellType.empty))
                return stories.get(CellType.empty).get(random.nextInt(stories.get(CellType.empty).size()));
            else if(g.current.type.equals(CellType.enemy))
                return stories.get(CellType.enemy).get(random.nextInt(stories.get(CellType.enemy).size()));
            else if(g.current.type.equals(CellType.shop))
                return stories.get(CellType.shop).get(random.nextInt(stories.get(CellType.shop).size()));
            else if(g.current.type.equals(CellType.finish))
                return stories.get(CellType.finish).get(random.nextInt(stories.get(CellType.finish).size()));

        }
        return null;
    }
    private void generateGrid()
    {
        Random random=new Random();
        int x,y;
        for(int i=0;i<4;i++) {
            x = random.nextInt(5);
            y = random.nextInt(5);
            if(x==0&&y==0){
                i--;
                continue;
            }
            if(x==4&&y==4){
                i--;
                continue;
            }

            g.get(y).set(x,new Cell(y,x,CellType.shop,g.character));
        }
        for(int i=0;i<2;i++) {
            x = random.nextInt(5);
            y = random.nextInt(5);
            if(x==0&&y==0){
                i--;
                continue;
            }
            if(x==4&&y==4){
                i--;
                continue;
            }
            g.get(y).set(x,new Cell(y,x,CellType.enemy,g.character));
        }
        g.get(4).set(4,new Cell(4,4,CellType.finish,g.character));
    }
    private void play(){
        ActionListener moveL;
        //item_list
        mapF=new JFrame("World of Marcel _map");
        JPanel inventoryP=new JPanel();
        JPanel mapP=new JPanel();
        JPanel statsP=new JPanel();
        JPanel storyP=new JPanel();
        statsP.setBackground(Color.DARK_GRAY);
        storyP.setBackground(Color.DARK_GRAY);
        inventoryP.setBackground(Color.GRAY);
        //inventory texts
        JList<String>item_list=new JList<>(itempanellist);
        JTextField inventorytext=new JTextField("Inventory");
        JTextField cashtext=new JTextField("Money: " + g.character.inventory.cash);
        JTextField remainingweighttext=new JTextField("Remaning Weight: " + g.character.inventory.remaining_weigth());

        inventorytext.setBackground(Color.GRAY);
        inventorytext.setBounds(60,0,140,30);
        item_list.setBounds(0,30,200,150);
        cashtext.setBounds(20,190,200,30);
        remainingweighttext.setBounds(20,220,200,30);
        remainingweighttext.setEditable(false);
        cashtext.setEditable(false);
        item_list.setEnabled(false);
        inventorytext.setEditable(false);
        inventorytext.setBorder(BorderFactory.createEmptyBorder());
        cashtext.setBorder(BorderFactory.createEmptyBorder());
        remainingweighttext.setBorder(BorderFactory.createEmptyBorder());
        cashtext.setBackground(Color.GRAY);
        remainingweighttext.setBackground(Color.GRAY);
        inventoryP.add(inventorytext);
        inventoryP.add(item_list);
        inventoryP.add(remainingweighttext);
        inventoryP.add(cashtext);
        //stats texts
        JTextField name=new JTextField();
        JTextField profession=new JTextField();
        JTextField hp=new JTextField();
        JTextField mana=new JTextField();
        JTextField xp=new JTextField();
        JTextField level=new JTextField();
        name.setText("Name: " + g.character.name);
        profession.setText(g.character.toString());
        hp.setText("HP: " + g.character.HP+ "/" + g.character.maxHP);
        mana.setText("Mana: " + g.character.mana+ "/" + g.character.maxmana);
        level.setText("Level: " + g.character.level);
        xp.setText("XP: " + g.character.xp);
        name.setBounds(50,20,200,30);
        profession.setBounds(250,20,100,30);
        hp.setBounds(350,20,100,30);
        mana.setBounds(450,20,100,30);
        level.setBounds(550,20,100,30);
        xp.setBounds(650,20,100,30);
        name.setEditable(false);
        profession.setEditable(false);
        hp.setEditable(false);
        mana.setEditable(false);
        level.setEditable(false);
        xp.setEditable(false);
        statsP.add(name);
        statsP.add(profession);
        statsP.add(hp);
        statsP.add(mana);
        statsP.add(xp);
        statsP.add(level);
        //story panel & move buttons
        JButton north=new JButton("North");
        JButton south=new JButton("South");
        JButton west=new JButton("West");
        JButton east=new JButton("East");
        storyP.add(north);
        storyP.add(south);
        storyP.add(east);
        storyP.add(west);
        north.setBounds(110,10,130,20);
        south.setBounds(260,10,130,20);
        east.setBounds(410,10,130,20);
        west.setBounds(560,10,130,20);
        storyP.setBounds(0,670,800,130);
        statsP.setBounds(0,0,800,70);
        mapP.setBounds(0,70,600,600);
        inventoryP.setBounds(600,70,200,600);
        mapF.setBounds(200,200,800,800);
        JTextArea story=new JTextArea(storyText());
        story.setBounds(10,40,780,30);
        story.setEditable(false);
        storyP.add(story);
        moveL= e -> {
            if(e.getSource().equals(north))
                move("N");
            else if(e.getSource().equals(south))
                move("S");
            else if(e.getSource().equals(east))
                move("E");
            else if(e.getSource().equals(west))
                move("W");
            story.setText(storyText());
            hp.setText("HP: " + g.character.HP+ "/" + g.character.maxHP);
            mana.setText("Mana: " + g.character.mana+ "/" + g.character.mana);
            level.setText("Level: " + g.character.level);
            xp.setText("XP: " + g.character.xp);
            cashtext.setText("Money: " + g.character.inventory.cash);
            remainingweighttext.setText("Remaning Weight: " + g.character.inventory.remaining_weigth());

        };
        north.addActionListener(moveL);
        south.addActionListener(moveL);
        east.addActionListener(moveL);
        west.addActionListener(moveL);
        //add map grid
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                JButton buton=new JButton(unknownIcon);
                buton.setEnabled(false);
                buton.setBackground(Color.green);
                buton.setBounds(i*120,j*120,120,120);
                grid.add(buton);
                mapP.add(buton);
            }
        }
        mapF.setVisible(true);
        grid.get(0).setIcon(characterIcon);
        mapF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //add panels to the frame
        mapF.add(statsP);
        mapF.add(mapP);
        mapF.add(storyP);
        mapF.add(inventoryP);
        statsP.setLayout(null);
        mapP.setLayout(null);
        mapF.setLayout(null);
        inventoryP.setLayout(null);
        storyP.setLayout(null);

    }
    private void init(ArrayList<Account> accounts, HashMap<CellType, ArrayList<String>> stories, Grid g) {
        this.accounts = accounts;
        this.stories = stories;
        this.g = g;
    }
    private void login() {
        ImageIcon enemyIcon=new ImageIcon("fisiere/world_of_marcel.png");
        JButton buton=new JButton(enemyIcon);
        buton.setBackground(null);
        buton.setBorder(BorderFactory.createEmptyBorder());
        buton.setBounds(0,0,500,200);
        buton.setEnabled(false);
        JPanel photo=new JPanel();
        photo.setLayout(null);
        photo.add(buton);
        photo.setLayout(null);
        photo.setBounds(150,500,500,200);
        JFrame loginF;
        JTextField nameB;
        JPasswordField passwordB;
        JButton loginB;
        JTextField loginText;
        JTextField nameT=new JTextField();
        JTextField passwordT=new JTextField();
        nameT.setBorder(BorderFactory.createEmptyBorder());
        passwordT.setBorder(BorderFactory.createEmptyBorder());
        nameT.setBounds(376,150,80,25);
        passwordT.setBounds(360,250,80,25);
        nameT.setText("Email");
        passwordT.setText("Password");
        loginF=new JFrame("World of marcel _login");
        loginText = new JTextField();
        loginB = new JButton("Log in");
        loginB.setBounds(325, 400, 150, 40);
        nameB = new JTextField();
        nameB.setBounds(275, 200, 250, 25);
        passwordB = new JPasswordField();
        passwordB.setBounds(275, 300, 250, 25);
        loginText.setBounds(300, 500, 200, 25);
        loginF.getContentPane().setBackground(Color.yellow);
        loginText.setEditable(false);
        nameT.setEditable(false);
        passwordT.setEditable(false);

        loginB.addActionListener(e -> {
            String email = nameB.getText();
            char[] password = passwordB.getPassword();
            for (Account account : accounts) {
                if (account.info.getCred().getEmail().equals(email)) {
                    if (account.info.getCred().getPassword().equals(String.valueOf(password))) {
                        loginF.dispatchEvent(new WindowEvent(loginF, WindowEvent.WINDOW_CLOSING));
                        choose(account);
                        return;
                    }
                }
            }
            loginText.setText("Password or email is incorrect!");

        });
        loginF.add(photo);
        loginF.add(nameT);
        loginF.add(passwordT);
        loginF.add(nameB);
        loginF.add(passwordB);
        loginF.add(loginB);
        loginF.add(loginText);
        loginF.setBounds(200,200,800,800);
        loginF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginF.setVisible(true);
    }
    private void choose(Account account) {
        JFrame chooseCharacterF=new JFrame("World of marcel _character");
        DefaultListModel<String> clist=new DefaultListModel<>();
        JButton choose=new JButton();
        choose.setText("Choose selected character");

        for(int i=0;i<account.getCharacters().size();i++) {
            clist.addElement(account.getCharacters().get(i).name
                    + " " +account.getCharacters().get(i).toString()
                    + " level " + account.getCharacters().get(i).level);
        }
        JList<String>name_list=new JList<>(clist);
        choose.addActionListener(e -> {
            g.character=account.getCharacters().get(name_list.getSelectedIndex());
            g.character.levelUP();
            chooseCharacterF.dispatchEvent(new WindowEvent(chooseCharacterF, WindowEvent.WINDOW_CLOSING));
            generateGrid();
            play();
        });
        chooseCharacterF.setBounds(200,200,400,clist.size()*35+30);
        chooseCharacterF.add(name_list);
        chooseCharacterF.add(choose);
        chooseCharacterF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chooseCharacterF.setLayout(new BoxLayout(chooseCharacterF.getContentPane(),BoxLayout.Y_AXIS));
        chooseCharacterF.setVisible(true);
    }
    GUI(ArrayList<Account> accounts, HashMap<CellType, ArrayList<String>> stories, Grid g) {
        init(accounts, stories, g);
        login();
    }
}

