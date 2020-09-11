package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

public class GameUI {
    private int width;
    private int height;
    private TERenderer ter;
    int yOffset = 4;
    private char[] clickalbe = {'q', 'Q', 'n', 'N', 'l', 'L'};

    public GameUI(int w, int h, TERenderer t) {
        width = w;
        height = h;
        ter = t;
    }

    public void start() throws IOException, ClassNotFoundException {
        ter.initialize(width, height + yOffset, 0, 2);
        drawFrame("CS61B THE GAME", 30, width / 2, height * 4 / 5);
        drawFrame("New Game (N)", 20, width / 2, height * 11 / 20);
        drawFrame("Load Game (L)", 20, width / 2, height / 2);
        drawFrame("Quit (Q)", 20, width / 2, height * 9 / 20);
        drawFrame("Author: ShuFan Lin", 15, width / 2, height / 10);
        action();
    }

    private void drawFrame(String s, int fontSize, int w, int h) {
        Font bigFont = new Font("Monaco", Font.BOLD, fontSize);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(w, h, s);
        StdDraw.show();
    }

    private boolean checkClickable(char c) {
        for (char ch : clickalbe) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }

    private void action() throws IOException, ClassNotFoundException {
        char c = 'e';
        while (!checkClickable(c)) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = StdDraw.nextKeyTyped();
        }

        if (c == 'q' || c == 'Q') {
            System.exit(0);
        }
        if (c == 'n' || c == 'N') {
            long seed = askSeed();
            Random random = new Random(seed);
            Map map = new Map(width, height, random);
            TETile[][] tiles = map.getTiles();
            ter.renderFrame(tiles);
            Play play = new Play(map, width, height, yOffset, ter);
            play.start();
        }
        if (c == 'l' || c == 'L') {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("game.txt")));
                Play play = (Play) ois.readObject();
                play.load();
            } catch (Exception e) {
                System.exit(0);
            }

        }
    }

    private long askSeed() {
        drawFrame("Enter Seed:", 20, width / 2, height / 4);
        StringBuilder temp = new StringBuilder();
        char c = 'e';
        //StdDraw.isKeyPressed(KeyEvent.VK_ENTER) this code could enable enter key for stddraw
        while (c != 's' && c != 'S') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = StdDraw.nextKeyTyped();
            temp.append(c);
            fillBlack(width / 2, height / 5, width / 2.0, 0.7);
            drawFrame(temp.toString(), 20, width / 2, height / 5);
        }
        String s = temp.toString();

        if (checkIfSeed(s)) {
            return Long.parseLong(s.substring(0, s.length() - 1));
        }
        fillBlack(width / 2, height / 5, width / 2.0, 0.7);
        drawFrame("Seed's format is incorrect!", 15, width / 2, height / 6);
        return askSeed();


    }

    private void fillBlack(int x, int y, double halfW, double halfH) {
        StdDraw.setPenColor(Color.black);
        StdDraw.filledRectangle(x, y, halfW, halfH);
        StdDraw.show();
    }

    private boolean checkIfSeed(String s) {
        long seed = 0L;
        try {
            long num = Long.parseLong(s.substring(0, s.length() - 1));
            seed = seed * 10 + num;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


}
