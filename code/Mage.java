public class Mage extends Character{
    public Mage(int level,int xp){
        super(80+level*8,30+level,true,false,false);
        strength=5+level;
        charisma=10+level*3;
        dexterity=5+level;
        inventory.max_weight=9+level*3;
        this.level=level;
        this.xp=xp;

    }
    @Override
    int getDamage(){
        return charisma/2+dexterity/2;
    }
    @Override
    public String toString() {
        return "Mage";
    }

    @Override
    void levelUP() {
        super.levelUP();
        strength=5+level;
        charisma=10+level*3;
        dexterity=5+level;
        inventory.max_weight=9+level*3;
    }
}
