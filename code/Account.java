import java.lang.reflect.Array;
import java.util.ArrayList;

public class Account {
    private ArrayList<Character> characters;
    public int played;
    public Information info;
    public Account(int played){
        this.played=played;
        characters=new ArrayList<Character>();
    }
    public void addCharacter(Character character)
    {
        characters.add(character);
    }
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public static class Information{
        private Credentials cred;
        private String name;
        private String country;
        private ArrayList<String> favouriteGames;
        private Information(InformationBuilder builder)
        {
            this.cred=builder.cred;
            this.name=builder.name;
            this.country=builder.country;
            this.favouriteGames=builder.favouriteGames;
        }

        public String getName() {
            return name;
        }
        public String getCountry(){
            return country;
        }

        public Credentials getCred() {
            return cred;
        }

        public ArrayList<String> getFavouriteGames() {
            return favouriteGames;
        }
        public static class InformationBuilder{
            private Credentials cred;
            private String name;
            private String country;
            private ArrayList<String> favouriteGames;
            public InformationBuilder(String name,Credentials cred)
            {
                this.cred=cred;
                this.name=name;
            }

            public InformationBuilder setCountry(String country) {
                this.country = country;
                return this;
            }
            public InformationBuilder setFavouriteGames(ArrayList<String> favouriteGames){
                this.favouriteGames=favouriteGames;
                return this;
            }
            public Information build() throws InformanionIncompleteException {
                if(cred==null||name==null)
                    throw new InformanionIncompleteException("No name or credentials");
                return new Information(this);
            }
        }
    }

}
