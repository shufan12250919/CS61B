import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /**
     * Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc.
     */
    private Map<Long, Node> nodes;
    private Map<String, ArrayList<Node>> namedLocation;
    private Map<String, Edge> edges;
    private Trie locations;

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            nodes = new HashMap<>();
            edges = new HashMap<>();
            namedLocation = new HashMap<>();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
            createLocationTries();
            //System.out.println(getLocationsByPrefix("m").toString());
            //getLocations("Top Dog");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
        //getLocations("Top Dog");
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        List<Long> removes = new ArrayList<>();
        for (Long id : nodes.keySet()) {
            if (nodes.get(id).getAdj().size() == 0) {
                removes.add(id);
            }
        }
        for (Long remove : removes) {
            nodes.remove(remove);
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     *
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     *
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return nodes.get(v).getAdj();
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double min = Double.MAX_VALUE;
        long id = -1;
        for (Node node : nodes.values()) {
            double dist = distance(lon, lat, node.getLon(), node.getLat());
            if (dist < min) {
                min = dist;
                id = node.getId();
            }
        }
        return id;
    }

    /**
     * Gets the longitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodes.get(v).getLon();
    }

    /**
     * Gets the latitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodes.get(v).getLat();
    }

    public void addNode(Long nodeID, Node node) {
        nodes.put(nodeID, node);
    }

    public Node getNode(long nodeID) {
        return nodes.get(nodeID);
    }

    public void addLocations(String cleanName, Node node) {
        if (namedLocation.get(cleanName) == null) {
            ArrayList<Node> list = new ArrayList<>();
            list.add(node);
            namedLocation.put(cleanName, list);
        } else {
            namedLocation.get(cleanName).add(node);
        }
    }

    public void addEdge(Long node1, Long node2, String speed, String name) {
        String edge = node1 + " " + node2;
        edges.put(edge, new Edge(node1, node2, speed, name));
    }

    public List<Edge> getEdge(Long node) {
        List<Edge> edgeList = new ArrayList<>();
        for (String ids : edges.keySet()) {
            if (ids.contains(node.toString())) {
                edgeList.add(edges.get(ids));
            }
        }
        return edgeList;
    }

    public Edge getEdge(long node, long node2) {
        List<Edge> edgeList = getEdge(node);
        for (Edge e : edgeList) {
            for (long id : e.getNodes()) {
                if (id == node2) {
                    return e;
                }
            }
        }
        return null;
    }

    private void createLocationTries() {
        locations = new Trie();
        for (List<Node> nodes : namedLocation.values()) {
            for (Node node : nodes) {
                String name = node.getLocationName();
                if (name != null) {
                    locations.insert(name);
                }
            }
        }
    }

    public List<String> getLocationsByPrefix(String prefix) {
        prefix = cleanString(prefix);
        return locations.wordsStartWith(prefix);
    }

    public List<Map<String, Object>> getLocations(String location) {
        location = cleanString(location);
        List<Map<String, Object>> result = new ArrayList<>();
        List<Node> ns = namedLocation.get(location);
        if (ns == null) {
            return null;
        }
        for (Node n : ns) {
            String name = n.getLocationName();
            if (name.toLowerCase().equals(location)) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", n.getId());
                map.put("lon", n.getLon());
                map.put("lat", n.getLat());
                map.put("name", name);
                result.add(map);
                //System.out.println(n.getId());
            }
        }
        //System.out.println(result.size());
        return result;
    }
}
