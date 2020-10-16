public class Edge {
    private String streetName;
    private Integer speedLimit;
    private long to; //the target

    public Edge(long id, String limit, String name) {
        to = id;
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

    public long getTo() {
        return to;
    }
}
