public class Rogue extends Character{
    public Rogue(int level,int xp){
        super(90+level*9,20+level,true,false,false);
        strength=5+level;
        charisma=5+level*2;
        dexterity=10+level*3;
        inventory.max_weight=10+level*5;
        this.level=level;
        this.xp=xp;

    }
    @Override
    int getDamage(){
        return dexterity/2+strength/3+dexterity/3;
    }
    @Override
    public String toString(){
        return "Rogue";
    }

    @Override
    void levelUP() {
        super.levelUP();
        strength=5+level;
        charisma=5+level*2;
        dexterity=10+level*3;
        inventory.max_weight=10+level*5;
    }
}
