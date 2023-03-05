import java.util.Random;

public abstract class Entity implements Element{
    int HP;
    int maxHP;
    int mana;
    int maxmana;
    boolean fire_protect;
    boolean ice_protect;
    boolean earth_protect;
    public Entity(int hp,int mana,boolean fp,boolean ip,boolean ep)
    {
        this.HP=hp;
        this.mana=mana;
        this.maxHP=hp;
        this.maxmana=mana;
        this.fire_protect=fp;
        this.ice_protect=ip;
        this.earth_protect=ep;
    }
    public void hp_regen(int val)
    {
        HP=HP+val;
        if(HP>maxHP)
            HP=maxHP;
    }
    public void mana_regen(int val)
    {
        mana=mana+val;
        if(mana>maxmana)
            mana=maxmana;
    }
    public boolean ability(Spell spell,Entity enemy)
    {
        if(spell.mana_cost<=mana)
        {
            mana-=spell.mana_cost;
            enemy.accept(spell);
            return true;
        }
        return false;
    }
    public void accept(Visitor v){
        v.visit(this);
    }
    abstract String receiveDamage(int damage);
    abstract int getDamage();

}
