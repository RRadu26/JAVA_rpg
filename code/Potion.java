enum Type
{
    small,
    medium,
    big
}
public interface Potion {
    int weight = 5;
    int price = 0;
    int regen = 0;
    void use(Character caracter);
    int getPrice();
    int getRegen();
    int getWeight();
    String toString();
}
