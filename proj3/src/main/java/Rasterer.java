import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final static double LargestLonPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / 256;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        Map<String, Object> results = new HashMap<>();

        double ullat = params.get("ullat");
        double ullon = params.get("ullon");
        double lrlat = params.get("lrlat");
        double lrlon = params.get("lrlon");
        double w = params.get("w");
        double h = params.get("h");


        //calculate depth
        double lonpp = (lrlon - ullon) / w;
        int depth = depth(lonpp, w);
        results.put("depth", depth);

        //getfilenames
        int[] indices = getIndices(depth, ullon, lrlon, ullat, lrlat);
        //System.out.println(Arrays.toString(indices));
        String[][] files = getGrid(depth, indices);
        //printFiles(files);
        results.put("render_grid", files);

        //getBoundings
        double[] bounds = getBounding(depth, indices);
        results.put("raster_ul_lon", bounds[0]);
        results.put("raster_lr_lon", bounds[1]);
        results.put("raster_lr_lat", bounds[2]);
        results.put("raster_ul_lat", bounds[3]);

        //set query_success
        results.put("query_success", true);

        System.out.println(results);
        return results;
    }

    private void printFiles(String[][] files) {
        for (String[] file : files) {
            System.out.println(Arrays.toString(file));
        }
    }

    private int depth(double londpp, double w) {
        int depth = 0;
        double currlondpp = LargestLonPP;
        while (currlondpp >= londpp && depth < 7) {
            currlondpp /= 2;
            depth++;
        }
        return depth;
    }

    //get x start from indices[0] to indices[1], get y from indices[2] to indices [3]
    private int[] getIndices(int depth, double ullon, double lrlon, double ullat, double lrlat) {
        int[] indices = new int[4]; // will be startX, endX, startY, endY
        int range = (int) Math.pow(2, depth);
        double wide = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / range;
        double high = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / range;
        double lon = MapServer.ROOT_ULLON;
        double lat = MapServer.ROOT_ULLAT;
        boolean startX = false;
        boolean endX = false;
        boolean startY = false;
        boolean endY = false;
        for (int i = 0; i < range; i++) {
            if (!startX && lon >= ullon) {
                if (i != 0) {
                    indices[0] = i - 1;
                }
                startX = true;
            }
            if (!endX && lon + wide >= lrlon) {
                indices[1] = i;
                endX = true;
            }
            if (!startY && lat <= ullat) {
                if (i != 0) {
                    indices[2] = i - 1;
                }
                startY = true;
            }
            if (!endY && lat - high <= lrlat) {
                indices[3] = i;
                endY = true;
            }
            lat -= high;
            lon += wide;
        }

        // check if query box is larger than the root field
        // lower case will be handle above
        if (lrlon > MapServer.ROOT_LRLON) {
            indices[1] = range - 1;
        }
        if (ullat > MapServer.ROOT_ULLAT) {
            indices[3] = range - 1;
        }
        return indices;
    }

    //get the bounings value store in array
    private double[] getBounding(int depth, int[] indices) {
        int range = (int) Math.pow(2, depth);
        double wide = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / range;
        double high = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / range;
        double[] bounding = new double[4];
        bounding[0] = MapServer.ROOT_ULLON + indices[0] * wide;
        bounding[1] = MapServer.ROOT_ULLON + indices[1] * wide;
        bounding[0] = MapServer.ROOT_LRLAT + indices[2] * wide;
        bounding[1] = MapServer.ROOT_LRLAT + indices[3] * wide;
        return bounding;
    }

    //convert indices to filenames
    private String[][] getGrid(int depth, int[] indices) {
        int w = indices[1] - indices[0] + 1;
        int h = indices[3] - indices[2] + 1;
        String[][] grid = new String[h][w];
        for (int i = indices[2]; i <= indices[3]; i++) {
            for (int j = indices[0]; j <= indices[1]; j++) {
                String file = "d" + depth + "_x" + j + "_y" + i + ".png";
                grid[i - indices[2]][j - indices[0]] = file;
            }
        }
        return grid;
    }


}
