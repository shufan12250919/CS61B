import java.util.HashMap;
import java.util.Map;

public class TestGetRasterer {
    public static void main(String[] args) {
        Map<String, Double> input = new HashMap<>();
        input.put("lrlon", -122.22639156874114);
        input.put("ullon", -122.24897024687533);
        input.put("w", 649.4774549567859);
        input.put("h", 407.1840746852771);
        input.put("ullat", 37.82444238853759);
        input.put("lrlat", 37.823522088551385);
        Rasterer r = new Rasterer();
        Map<String, Object> output = r.getMapRaster(input);

    }
}
