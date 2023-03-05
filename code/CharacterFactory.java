public class CharacterFactory {
    public Character createCharacter(String character,int lvl, int xp)
    {
        if(character==null || character.isEmpty())
            return null;
        else if(character.equals("Warrior"))
            return new Warrior(lvl,xp);
        else if(character.equals("Mage"))
            return new Mage(lvl,xp);
        else if(character.equals("Rogue"))
            return new Rogue(lvl,xp);
        return null;
    }
}
