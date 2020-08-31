package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void fillwithNothing(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void addHexagon(TETile[][] tiles, int size, int startx, int starty) {
        if (size < 2) {
            return;
        }
        int width = size * 3 - 2;
        int height = size * 2;
        if (startx + width > tiles[0].length || starty + height > tiles.length) {
            return;
        }

        TETile select = randomTile();
        //lowerside
        int count = size;
        int edge = size - 1;
        for (int i = starty; i < starty + size; i++) {
            for (int j = startx + edge; j < startx + edge + count; j++) {
                tiles[j][i] = select;
            }
            edge--;
            count += 2;
        }
        //upperside
        count = size;
        edge = size - 1;
        for (int i = starty + 2 * size - 1; i >= starty + size; i--) {
            for (int j = startx + edge; j < startx + edge + count; j++) {
                tiles[j][i] = select;
            }
            edge--;
            count += 2;
        }

    }


    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(2);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOWER;
            default:
                return Tileset.NOTHING;
        }
    }

    public static void tesselation(TETile[][] tiles, int size) {
        int startx = tiles[0].length / 2 - size;
        int starty = 0;
        //mid
        addvertical(tiles, size, startx, starty, 5);
        //left
        startx = startx - 2 * size + 1;
        starty = size;
        addvertical(tiles, size, startx, starty, 4);
        startx = startx - 2 * size + 1;
        starty += size;
        addvertical(tiles, size, startx, starty, 3);
        //right
        startx = tiles[0].length / 2 - size; // reset startx
        startx = startx + 2 * size - 1;
        starty = size;
        addvertical(tiles, size, startx, starty, 4);
        startx = startx + 2 * size - 1;
        starty += size;
        addvertical(tiles, size, startx, starty, 3);

    }

    public static void addvertical(TETile[][] tiles, int size, int startx, int starty, int count) {
        for (int i = 0; i < count; i++) {
            addHexagon(tiles, size, startx, starty);
            starty += size * 2;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexTiles = new TETile[WIDTH][HEIGHT];
        fillwithNothing(hexTiles);
        //addHexagon(hexTiles, 5, 0, 0);
        tesselation(hexTiles, HEIGHT / 10);
        ter.renderFrame(hexTiles);
    }
}
