package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Play implements Serializable {
    private Map map;
    private int width;
    private int height;
    private int yOffset;
    private TERenderer ter;
    private int move;
    private int waterToDrink = 3;
    private String hint = "";

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

    public void start() {
        hub();
        action();
    }

    public void load() {
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
        StdDraw.text(midWidth, drawHeight - 1, "Water need to Drink: " + waterToDrink);
        StdDraw.textRight(width - 1, drawHeight - 1, detectElements());
        StdDraw.line(0, drawHeight - 2, width, drawHeight - 2);
        StdDraw.line(0, 2, width, 2);
        StdDraw.textLeft(1, 1, hint);
        StdDraw.text(midWidth, 1, "Enter the unlocked room (O)");
        StdDraw.textRight(width - 1, 1, "Quit/Saving (Q)");
        StdDraw.show();
    }

    private void action() {
        char c = '!';
        while (c != 'Q' && c != 'q') {
            if (StdDraw.isMousePressed()) {
                ter.renderFrame(map.getTiles());
                hub();
            }
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

    public void serialize() {
        try {
            FileOutputStream fo = new FileOutputStream(new File("game.txt"));
            ObjectOutputStream oos = new ObjectOutputStream(fo);
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            return;
        }
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
        hint = "";
        if (c == 'w' || c == 'W') {
            TETile newPos = tiles[px][py + 1];
            if (newPos.equals(Tileset.FLOOR) || newPos.equals(Tileset.WATER)) {
                newPy = py + 1;
                move++;
            }
        }
        if (c == 's' || c == 'S') {
            TETile newPos = tiles[px][py - 1];
            if (newPos.equals(Tileset.FLOOR) || newPos.equals(Tileset.WATER)) {
                newPy = py - 1;
                move++;
            }
        }
        if (c == 'a' || c == 'A') {
            TETile newPos = tiles[px - 1][py];
            if (newPos.equals(Tileset.FLOOR) || newPos.equals(Tileset.WATER)) {
                newPx = px - 1;
                move++;
            }
        }
        if (c == 'd' || c == 'D') {
            TETile newPos = tiles[px + 1][py];
            if (newPos.equals(Tileset.FLOOR) || newPos.equals(Tileset.WATER)) {
                newPx = px + 1;
                move++;
            }
        }

        if (c == 'o' || c == 'O') {
            if (checkDoor(newPx, newPy, tiles)) {
                endGame();
            } else {
                hint = "No unlocked door to opened!";
            }
        }
        tiles[px][py] = Tileset.FLOOR;
        if (tiles[newPx][newPy].equals(Tileset.WATER)) {
            waterToDrink--;
            if (waterToDrink == 0) {
                unLuckDoor();
            }
        }
        tiles[newPx][newPy] = Tileset.PLAYER;
        p.setPos(newPx, newPy);
    }

    private String detectElements() {
        int x = (int) (StdDraw.mouseX());
        int y = (int) (StdDraw.mouseY() - 2);
        if (x >= width || y >= height || x <= 0 || y <= 0) {
            return "boarder";
        }
        return map.getTiles()[x][y].description();
    }

    private void unLuckDoor() {
        Position door = map.getDoor();
        int x = door.getXpos();
        int y = door.getYpos();
        map.getTiles()[x][y] = Tileset.UNLOCKED_DOOR;
    }

    private boolean checkDoor(int x, int y, TETile[][] tiles) {
        if (tiles[x + 1][y] == Tileset.UNLOCKED_DOOR) {
            return true;
        }
        if (tiles[x - 1][y] == Tileset.UNLOCKED_DOOR) {
            return true;
        }
        if (tiles[x][y + 1] == Tileset.UNLOCKED_DOOR) {
            return true;
        }
        if (tiles[x][y - 1] == Tileset.UNLOCKED_DOOR) {
            return true;
        }
        return false;

    }

    private void drawFrame(String s, int fontSize, int w, int h) {
        Font bigFont = new Font("Monaco", Font.BOLD, fontSize);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(w, h, s);
        StdDraw.show();
    }

    private void endGame() {
        StdDraw.clear(Color.BLACK);
        String cong = "You Win!\n Use " + move + " movements!";
        drawFrame(cong, 40, width / 2, height / 2);
        drawFrame("Press N to Start over!", 40, width / 2, 4);
        drawFrame("Press Q to End!", 40, width / 2, 2);
        char c = 'e';
        while (c != 'Q' && c != 'q' && c != 'N' && c != 'n') {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            c = StdDraw.nextKeyTyped();
        }
        if (c == 'Q' || c == 'q') {
            System.exit(0);
        }
        GameUI ui = new GameUI(width, height, ter);
        ui.start();
    }


}
