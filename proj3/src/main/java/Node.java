import java.util.ArrayList;
import java.util.List;

public class Node {
    private long id;
    private double lon;
    private double lat;
    private List<Long> adj; // adjacent nodes' ids
    private List<Edge> edges;
    private String locationName;

    public Node(long id, double lon, double lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        adj = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addAdj(Long adjID) {
        adj.add(adjID);
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public void setLocationName(String name) {
        locationName = name;
    }

    public List<Long> getAdj() {
        return adj;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    public long getId() {
        return id;
    }

    public String getLocationName() {
        return locationName;
    }
}
