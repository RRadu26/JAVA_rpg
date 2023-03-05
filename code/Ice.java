import java.util.Random;

public class Ice extends Spell{
public Ice() {
    super();
    random=new Random();
    mana_cost=random.nextInt(5)+3;
    damage=random.nextInt(6)+10;
    }
    public void visit(Entity entity){
        System.out.println("Ice attack");
        if(entity.ice_protect==false) {
            entity.HP -= damage;
            System.out.println("Ice spell damage: " + this.damage);
        }
        else
            System.out.println("Spell missed");
    }

    @Override
    public String toString() {
        return "Ice spell";
    }
}
