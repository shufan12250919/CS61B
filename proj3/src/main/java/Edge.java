public class Edge {
    private String streetName;
    private Integer speedLimit;
    private long[] nodes = new long[2];

    public Edge(long n1, long n2, String limit, String name) {
        nodes[0] = n1;
        nodes[1] = n2;
        if (!limit.equals("")) {
            setSpeedLimit(limit);
        }
        if (!name.equals("")) {
            streetName = name;
        }
    }

    private void setSpeedLimit(String limit) {
        String speed = limit.split(" ")[0]; // ex: get "55 mph"'s 55
        speedLimit = Integer.parseInt(speed);
    }


    public String getStreetName() {
        return streetName;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public long[] getNodes() {
        return nodes;
    }
}
