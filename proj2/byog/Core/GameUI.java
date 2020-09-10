package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class GameUI {
    private int width;
    private int height;
    private TERenderer ter;
    private Random random;

    public GameUI(int w, int h, TERenderer t, Random r) {
        width = w;
        height = h;
        ter = t;
        random = r;
    }

    public void start() {
        ter.initialize(width, height);
        drawFrame("CS61B THE GAME", 30, width / 2, height * 4 / 5);
        drawFrame("New Game (N)", 20, width / 2, height * 17 / 30);
        drawFrame("Load Game (L)", 20, width / 2, height / 2);
        drawFrame("Quit (Q)", 20, width / 2, height * 13 / 30);
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

    private void action() {
        char c = 'e';
        while (c == 'e') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = StdDraw.nextKeyTyped();
        }
        if (c == 'q' || c == 'Q') {
            System.exit(0);
        } else if (c == 'n' || c == 'N') {
            Map map = new Map(width, height, random);
            TETile[][] tiles = map.getTiles();
            ter.renderFrame(tiles);
        }
    }


}
