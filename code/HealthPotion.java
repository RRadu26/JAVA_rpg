import java.util.Random;

public class HealthPotion implements Potion{
    private Random random=new Random();
    int price=random.nextInt(6)+11;
    int weight=5;
    int regen=price*2;
    @Override
    public void use(Character caracter) {
        caracter.hp_regen(regen);
        caracter.inventory.remove_potion(this);
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public int getRegen() {
        return this.regen;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }
    @Override
    public String toString()
    {
        return "Health Potion with "+" " + regen+" regen";
    }
}
