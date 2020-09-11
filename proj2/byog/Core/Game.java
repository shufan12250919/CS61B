package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 45;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        GameUI ui = new GameUI(WIDTH, HEIGHT, ter);
        ui.start();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        StringCommand command = new StringCommand(input);
        long seed = command.getSeed();
        String action = command.getAction();
        if (!command.getLoad()) {
            Random r = new Random(seed);
            Map map = new Map(WIDTH, HEIGHT, r);
            Play play = new Play(map, WIDTH, HEIGHT, 4, ter);
            play.startWithCommand(action);
            //map.present();
            if (command.getStore()) {
                try {
                    play.serialize();
                } catch (Exception e) {
                    return map.getTiles();
                }

            }
            return map.getTiles();
        } else {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("./game.txt")));
                Play play = (Play) ois.readObject();
                play.startWithCommand(action);
                //play.getMap().present();
                if (command.getStore()) {
                    play.serialize();
                }
                return play.getMap().getTiles();
            } catch (ClassNotFoundException | IOException e) {
                return new TETile[0][0];
            }
        }
    }
}
