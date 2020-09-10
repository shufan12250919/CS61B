package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    private int width;
    private int height;
    private TETile[][] tiles;
    private Random random;
    private ArrayList<Room> rooms;
    private Position player;


    public Map(int w, int h, Random r) {
        random = r;
        width = w;
        height = h;
        tiles = new TETile[width][height];
        rooms = new ArrayList<>();
        fillWithNothing();
        fillWithRooms(random.nextInt(5) + 10);
        buildHallWay();
        buildWall();
        buildGold();
        buildPlayer();
    }

    public TETile[][] getTiles() {
        return tiles;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void fillWithNothing() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void fillWithRooms(int time) {
        while (time > 0) {
            time--;
            int x = 1 + random.nextInt(width - 3);
            int y = 1 + random.nextInt(height - 3);

            int xlen = random.nextInt((width - x) / 4 + 1) + 2;
            if (x + xlen >= width - 1) {
                xlen--;
            }
            int ylen = random.nextInt((height - y) / 4 + 1) + 2;
            if (y + ylen >= height - 1) {
                ylen--;
            }

            Room r = new Room(tiles, xlen, ylen, x, y, random);
            rooms.add(r);
        }
    }

    private void buildHallWay() {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room r1 = rooms.get(i);
            Room r2 = rooms.get(i + 1);
            r1.buildHallWay(r2);
        }
    }


    private void buildWall() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (tiles[i][j] == Tileset.FLOOR) {
                    putWallSurrounding(i, j);
                }
            }
        }
    }

    private void putWallSurrounding(int x, int y) {
        if (tiles[x][y] != Tileset.FLOOR) {
            return;
        }
        //check right, right up, right down
        if (x + 1 < width) {
            if (tiles[x + 1][y] == Tileset.NOTHING) {
                tiles[x + 1][y] = Tileset.WALL;
            }
            if (y + 1 < height) {
                if (tiles[x + 1][y + 1] == Tileset.NOTHING) {
                    tiles[x + 1][y + 1] = Tileset.WALL;
                }
            }
            if (y - 1 >= 0) {
                if (tiles[x + 1][y - 1] == Tileset.NOTHING) {
                    tiles[x + 1][y - 1] = Tileset.WALL;
                }
            }
        }
        //check left, left up, left down
        if (x - 1 >= 0) {
            if (tiles[x - 1][y] == Tileset.NOTHING) {
                tiles[x - 1][y] = Tileset.WALL;
            }
            if (y + 1 < height) {
                if (tiles[x - 1][y + 1] == Tileset.NOTHING) {
                    tiles[x - 1][y + 1] = Tileset.WALL;
                }
            }
            if (y - 1 >= 0) {
                if (tiles[x - 1][y - 1] == Tileset.NOTHING) {
                    tiles[x - 1][y - 1] = Tileset.WALL;
                }
            }
        }
        //check top
        if (y + 1 < height) {
            if (tiles[x][y + 1] == Tileset.NOTHING) {
                tiles[x][y + 1] = Tileset.WALL;
            }
        }
        //check bottom
        if (y - 1 >= 0) {
            if (tiles[x][y - 1] == Tileset.NOTHING) {
                tiles[x][y - 1] = Tileset.WALL;
            }
        }

    }

    private void buildGold() {
        int roomNum = random.nextInt(rooms.size() - 1);
        Room r = rooms.get(roomNum);
        Position p = r.pickRandomPosition();
        int x = p.getXpos();
        int y = p.getYpos();
        int direction = random.nextInt(3);
        if (direction == 1) {
            while (tiles[x][y] != Tileset.WALL) {
                x++;
            }
        } else if (direction == 2) {
            while (tiles[x][y] != Tileset.WALL) {
                x--;
            }
        } else if (direction == 3) {
            while (tiles[x][y] != Tileset.WALL) {
                y++;
            }
        } else {
            while (tiles[x][y] != Tileset.WALL) {
                y--;
            }
        }
        if (checkGold(x, y)) {
            tiles[x][y] = Tileset.LOCKED_DOOR;
        } else {
            buildGold();
        }

    }

    private boolean checkGold(int x, int y) {
        if (x - 1 >= 0 && tiles[x - 1][y] == Tileset.NOTHING) {
            return true;
        }
        if (x + 1 <= width - 1 && tiles[x + 1][y] == Tileset.NOTHING) {
            return true;
        }
        if (y - 1 >= 0 && tiles[x][y - 1] == Tileset.NOTHING) {
            return true;
        }
        if (y + 1 <= height - 1 && tiles[x][y + 1] == Tileset.NOTHING) {
            return true;
        }
        return false;

    }

    public void present() {
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(tiles);
    }

    private void buildPlayer() {
        int roomNum = random.nextInt(rooms.size() - 1);
        Room r = rooms.get(roomNum);
        player = r.pickRandomPosition();
        int x = player.getXpos();
        int y = player.getYpos();
        tiles[x][y] = Tileset.PLAYER;
    }


}
