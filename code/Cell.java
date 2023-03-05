enum CellType
{
    enemy,
    shop,
    empty,
    finish
}
public class Cell {
    int Ox;
    int Oy;
    CellType type;
    CellElement current;
    Boolean visited;
    private Character character;
    public Cell(int Oy,int Ox,CellType type,Character character)
    {
        this.Ox=Ox;
        this.Oy=Oy;
        this.type=type;
        visited=false;
        switch (type){
            case empty:
                current =new Empty();
                break;
            case enemy:
                current=new Enemy(character);
                break;
            case shop:
                current=new Shop(character);
                break;
        }
    }
}
