package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Position implements Serializable {
    private TETile[][] tiles;
    private int xpos;
    private int ypos;

    public Position(TETile[][] t, int x, int y) {
        tiles = t;
        xpos = x;
        ypos = y;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setPos(int x, int y) {
        xpos = x;
        ypos = y;
    }

    public void makeWay(Position target) {
        int startX = xpos;
        int endX = target.getXpos();
        int startY = ypos;
        int endY = target.getYpos();

        while (startX != endX) {
            if (startX < endX) {
                startX++;
            } else {
                startX--;
            }
            tiles[startX][startY] = Tileset.FLOOR;
        }

        while (startY != endY) {
            if (startY < endY) {
                startY++;
            } else {
                startY--;
            }
            tiles[startX][startY] = Tileset.FLOOR;
        }

    }

}
