package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Room implements Serializable {
    private TETile[][] tiles;
    private int xlen;
    private int ylen;
    private int xRL;    // x's right left position
    private int yRL;    // y's right left position
    private ArrayList<Position> spcae;
    private Random random;

    public Room(TETile[][] t, int xlen, int ylen, int xRL, int yRL, Random random) {
        tiles = t;
        this.xlen = xlen;
        this.ylen = ylen;
        this.xRL = xRL;
        this.yRL = yRL;
        this.random = random;
        buildRoom();
    }


    private void buildRoom() {
        for (int i = xRL; i < xRL + xlen; i++) {
            for (int j = yRL; j < yRL + ylen; j++) {
                tiles[i][j] = Tileset.FLOOR;
            }
        }
    }

    public Position pickRandomPosition() {
        int x = random.nextInt(xlen) + xRL;
        int y = random.nextInt(ylen) + yRL;
        return new Position(tiles, x, y);
    }

    public void buildHallWay(Room r) {
        Position start = pickRandomPosition();
        Position end = r.pickRandomPosition();
        start.makeWay(end);
    }


}
