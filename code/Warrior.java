public class Warrior extends Character{
    public Warrior(int level,int xp){
        super(100+level*10,15+level,true,false,false);
        strength=10+level*3;
        charisma=5+level*2;
        dexterity=5+level;
        inventory.max_weight=10+level*5;
        this.level=level;
        this.xp=xp;

    }
    @Override
    int getDamage(){
        return strength/2+charisma/3+dexterity/3;
    }
    @Override
    public String toString(){
        return "Warrior";
    }

    @Override
    void levelUP() {
        super.levelUP();
        strength=10+level*3;
        charisma=5+level*2;
        dexterity=5+level;
        inventory.max_weight=10+level*5;
    }
}
