import java.io.IOException;

public class Test {

    public static void main(String[] Args) throws IOException {
        Game joc = Game.getInstance(10, 10);
        joc.run();


    }
}