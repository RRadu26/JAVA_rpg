import java.util.Random;

public abstract class Spell implements Visitor{
    int damage;
    int mana_cost;
    protected Random random;
    abstract public void visit(Entity entity);
    abstract public String toString();
}
