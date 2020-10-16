public class Vertex implements Comparable {
    private long id;
    private double dist;

    public Vertex(long id, double dist) {
        this.id = id;
        this.dist = dist;
    }

    @Override
    public int compareTo(Object o) {
        Vertex n = (Vertex) o;
        double dif = this.dist - n.dist;
        return Double.compare(dif, 0.0);
    }

    public long getId() {
        return id;
    }

}
