import com.sun.source.tree.NewArrayTree;

import java.util.Random;

public abstract class Character extends Entity{
    String name;
    int Ox;
    int Oy;
    int xp;
    int level;
    int strength;
    int charisma;
    int dexterity;
    Inventory inventory= new Inventory();
    private Random random=new Random();
    public Character(int hp,int mana,boolean fp,boolean ip,boolean ep)
    {
        super(hp,mana,fp,ip,ep);
    }
    boolean buy(Potion potion) {
        if (inventory.cash >= potion.getPrice() && inventory.remaining_weigth() >= potion.getWeight()) {
            inventory.add_potion(potion);
            inventory.cash= inventory.cash-potion.getPrice();
            return true;
        }
        return false;
    }
    @Override
        String receiveDamage(int damage) {
            HP=HP-damage;
            return "Enemy attacked you with a normal attack with " + damage + " damage.";
        }

    @Override
    int getDamage() {
        return 0;

    }
    void levelUP()
    {
        if(xp>=30) {
            level += 1;
            xp-=30;
        }
    }

}
