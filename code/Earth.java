import java.util.Random;

public class Earth extends Spell{
    public Earth()
    {
        random=new Random();
        mana_cost=random.nextInt(4)+3;
        damage=random.nextInt(5)+10;
    }
    public void visit(Entity entity){
        if(entity.earth_protect==false) {
            entity.HP -= damage;
            System.out.println("Earth spell damage: " + this.damage);
        }
        else
            System.out.println("Spell missed");
    }

    @Override
    public String toString() {
        return "Earth Spell";
    }
}
