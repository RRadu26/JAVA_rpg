import java.util.ArrayList;

public class Inventory {
    ArrayList<Potion> list;
    int max_weight=20;
    int cash=50;
    public Inventory() {
        list=new ArrayList<Potion>();
    }
    void add_potion(Potion potion) {
            list.add(potion);
    }
    void remove_potion(Potion potion)
    {
        list.remove(potion);
    }
    int remaining_weigth()
    {
        int w=0;
        for (Potion potion : list) w += potion.weight;
        return max_weight-w;
    }
}

