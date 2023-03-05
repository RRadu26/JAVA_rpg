import java.util.Random;

public class Fire extends Spell{
    public Fire() {
        random=new Random();
        mana_cost=random.nextInt(5)+3;
        damage=random.nextInt(7)+10;
    }
    public void visit(Entity entity){
        System.out.println("Fire attack");
        if(entity.fire_protect==false) {
            entity.HP -= damage;
            System.out.println("Fire spell damage: " + this.damage);
        }
        else
            System.out.println("Spell missed");
    }

    @Override
    public String toString() {
        return "Fire Spell";
    }
}
