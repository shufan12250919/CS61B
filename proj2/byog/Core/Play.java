package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;

public class Play implements Serializable {
    private Map map;
    private int width;
    private int height;
    private int yOffset;
    private TERenderer ter;
    private int move;

    public Play(Map m, int w, int h, int yOff, TERenderer t) {
        map = m;
        width = w;
        height = h;
        yOffset = yOff;
        ter = t;
        move = 0;
    }

    public Map getMap() {
        return map;
    }

    public void start() throws IOException {
        hub();
        action();
    }

    public void load() throws IOException {
        ter.renderFrame(map.getTiles());
        hub();
        action();
    }

    private void hub() {
        int midWidth = width / 2;
        int drawHeight = height + yOffset;
        Font smallFont = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(smallFont);
        StdDraw.textLeft(1, drawHeight - 1, "Move: " + move);
        StdDraw.text(midWidth, drawHeight - 1, "Watch!");
        StdDraw.textRight(width - 1, drawHeight - 1, "Element");
        StdDraw.line(0, drawHeight - 2, width, drawHeight - 2);
        StdDraw.line(0, 2, width, 2);
        StdDraw.textRight(width - 1, 1, "Quit/Saving (Q)");
        StdDraw.show();
    }

    private void action() throws IOException {
        char c = '!';
        while (c != 'Q' && c != 'q') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = StdDraw.nextKeyTyped();
            movePlay(c);
            ter.renderFrame(map.getTiles());
            hub();

        }
        serialize();
        System.exit(0);

    }

    public void serialize() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("game.txt")));
        oos.writeObject(this);
        oos.close();
    }

    public void startWithCommand(String s) {
        for (int i = 0; i < s.length(); i++) {
            char key = s.charAt(i);
            movePlay(key);
        }
    }

    private void movePlay(char c) {
        Position p = map.getPlayer();
        TETile[][] tiles = map.getTiles();
        int px = p.getXpos();
        int py = p.getYpos();
        int newPx = px;
        int newPy = py;
        if (c == 'w' || c == 'W') {
            if (tiles[px][py + 1].equals(Tileset.FLOOR)) {
                newPy = py + 1;
                move++;
            }
        }
        if (c == 's' || c == 'S') {
            if (tiles[px][py - 1].equals(Tileset.FLOOR)) {
                newPy = py - 1;
                move++;
            }
        }
        if (c == 'a' || c == 'A') {
            if (tiles[px - 1][py].equals(Tileset.FLOOR)) {
                newPx = px - 1;
                move++;
            }
        }
        if (c == 'd' || c == 'D') {
            if (tiles[px + 1][py].equals(Tileset.FLOOR)) {
                newPx = px + 1;
                move++;
            }
        }
        tiles[px][py] = Tileset.FLOOR;
        tiles[newPx][newPy] = Tileset.PLAYER;
        p.setPos(newPx, newPy);
    }


}
