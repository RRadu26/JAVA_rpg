import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Enemy extends Entity implements CellElement {
    ArrayList<Spell> abilities;
    private Random random = new Random();
    private Character character;

    public Enemy(Character character) {
        super(0, 0, false, false, false);
        maxHP = random.nextInt(15) + 30+character.level*8;
        maxmana = random.nextInt(5) + character.level*2+10;
        HP = maxHP;
        mana = maxmana;
        boolean ra=random.nextBoolean();
        fire_protect = ra;
        earth_protect = ra;
        ice_protect = !ra;
        this.character = character;
        int nabilities = random.nextInt(3) + 2;
        this.abilities=new ArrayList<Spell>(nabilities);
        for (int i = 0; i < nabilities; i++) {
            int ran = random.nextInt(3);
            switch (ran) {
                case 0:
                    abilities.add(new Earth());
                    break;
                case 1:
                    abilities.add(new Fire());
                    break;
                case 2:
                    abilities.add(new Ice());
            }
        }
    }

    @Override
    public char toCharacter() {
        return 'E';
    }

    @Override
    public void actionT() {
        ArrayList<String> cmds = new ArrayList<String>();
        cmds.add("F");
        cmds.add("I");
        cmds.add("E");
        cmds.add("A");
        cmds.add("A");

        cmds.add("P");
        cmds.add("P");
        for(int j=0;j<20;j++)
            cmds.add("A");
        int i = 0;
        System.out.println("A-normal attack");
        System.out.println("P-potion list , then press number of the desired potion");
        System.out.println("F-fire spell");
        System.out.println("I-ice spell");
        System.out.println("E-earth spell");
        Scanner scanner = new Scanner(System.in);
        while (this.HP > 0 && this.character.HP >= 0) {
            System.out.println("Your Hp: " + character.HP + "  " + "Your mana: " + character.mana);
            System.out.println("Enemy Hp: " + this.HP + "  " + "Enemy mana: " + this.mana);
            boolean a = true;
            String cmd = scanner.nextLine();
            if (cmd.equals("P")) {
                if (cmds.get(i).equals("A"))
                    System.out.println(receiveDamage(character.getDamage()));
                else if (cmds.get(i).equals("P")) {
                    System.out.println("Potion  Regen");
                    for (int j = 0; j < character.inventory.list.size(); j++)
                        System.out.println(j + ". " + character.inventory.list.get(j));
                    System.out.println("You've used the " + character.inventory.list.get(0).toString());
                    character.inventory.list.get(0).use(character);
                    a = false;
                } else if (cmds.get(i).equals("E")) {
                    Earth spell = new Earth();
                    character.ability(spell, this);
                } else if (cmds.get(i).equals("I")) {
                    Ice spell = new Ice();
                    character.ability(spell, this);
                } else if (cmds.get(i).equals("F")) {
                    Fire spell = new Fire();
                    character.ability(spell, this);
                }
                i++;
                if (a)
                    attack();
            }
        }
            if(character.HP>0)
                character.xp+=5;
    }
    @Override
    String receiveDamage(int damage) {
        boolean miss = random.nextBoolean();
        if (miss) {
            HP = HP - damage;
            return "You've damaged the enemy with a normal attack with "+damage+" damage";
        }
            return "You've missed the normal attack";
    }

    @Override
    int getDamage() {
        int damage = random.nextInt(3+character.level) + 3;
        boolean critical = random.nextBoolean();
        if (critical)
            return damage * 2;
        return damage;
    }

    void attack() {
        System.out.println("Enemy attack");
        int t = random.nextInt(3);
        if (t < 2) {
            System.out.println(character.receiveDamage(getDamage()));
        }
        if (t == 2) {
            int rand=random.nextInt(abilities.size());
            if(!this.ability(abilities.get(rand),character))
                character.receiveDamage(getDamage());
        }
    }
}
