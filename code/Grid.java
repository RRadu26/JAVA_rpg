import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int length;
    int height;
    public Character character;
    public Cell current;
    private Grid(int length,int height){
        this.length =length;
        this.height=height;
    }
    public static Grid generate(int length,int height){
        Grid map = new Grid(length,height);
        for(int i=0;i<height;i++)
        {
            ArrayList<Cell>a=new ArrayList<Cell>();
            map.add(a);
        }

        return map;
    }
    public void goNorth() {
        if(character.Oy<1)
        {
            System.out.println("Miscare invalida");
            return;
        }
        character.Oy--;
        current=this.get(character.Oy).get(character.Ox);
    }
    public void goEast() {
        if(character.Ox>=length-1)
        {
            System.out.println("Miscare invalida");
            return;
        }
        character.Ox++;
        current=this.get(character.Oy).get(character.Ox);
    }
    public void goWest() {
        if(character.Ox<1)
        {
            System.out.println("Miscare invalida");
            return;
        }
        character.Ox--;
        current=this.get(character.Oy).get(character.Ox);

    }
    public void goSouth() {
        if(character.Oy>=height-1)
        {
            System.out.println("Miscare invalida");
            return;
        }
        character.Oy++;
        current=this.get(character.Oy).get(character.Ox);

    }
}
