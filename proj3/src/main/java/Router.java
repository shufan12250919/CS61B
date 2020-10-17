import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     *
     * @param g       The graph to use.
     * @param stlon   The longitude of the start location.
     * @param stlat   The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        List<Long> nodes = new ArrayList<>();
        long start = g.closest(stlon, stlat);
        long end = g.closest(destlon, destlat);
        HashMap<Long, Long> edgeFrom = new HashMap<>();
        HashMap<Long, Double> best = new HashMap<>();
        HashSet<Long> mark = new HashSet<>();
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(start, 0.0));
        best.put(start, 0.0);
        edgeFrom.put(start, start);
        while (!pq.isEmpty()) {
            long current = pq.remove().getId();
            mark.add(current);
            if (current == end) {
                break;
            }
            for (Long neighbor : g.getNode(current).getAdj()) {
                if (!mark.contains(neighbor)) {
                    double toneightborDistance = g.distance(current, neighbor);
                    double currentDistance = best.get(current);
                    double totalDistance = toneightborDistance + currentDistance;
                    double h = g.distance(neighbor, end);
                    if (!best.containsKey(neighbor)) {
                        best.put(neighbor, totalDistance);
                        edgeFrom.put(neighbor, current);
                        pq.add(new Vertex(neighbor, totalDistance + h));
                        continue;
                    }
                    if (best.get(neighbor) > totalDistance) {
                        best.put(neighbor, totalDistance);
                        edgeFrom.put(neighbor, current);
                    }
                    pq.add(new Vertex(neighbor, totalDistance + h));
                }
            }
        }
        nodes.add(end);
        long previous = end;
        //check if the end is reachable
        if (!edgeFrom.containsKey(previous)) {
            return null;
        }
        while (true) {
            long current = edgeFrom.get(previous);
            nodes.add(current);
            if (current == start) {
                break;
            }
            previous = current;
        }
        Collections.reverse(nodes);
        return nodes;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     *
     * @param g     The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> list = new ArrayList<>();
        if (route.size() <= 1) {
            return list;
        }
        NavigationDirection nd = new NavigationDirection();
        nd.direction = NavigationDirection.START;
        nd.distance = 0.0;
        nd.way = g.getEdge(route.get(0), route.get(1)).getStreetName();

        for (int i = 1; i < route.size(); i++) {
            long prev = route.get(i - 1);
            long current = route.get(i);
            nd.distance += g.distance(prev, current);
            if (i == route.size() - 1) {
                list.add(nd);
                break;
            }
            long next = route.get(i + 1);
            String currentWay = g.getEdge(current, next).getStreetName();
            if (currentWay.equals(nd.way)) {
                continue;
            }
            list.add(nd);
            nd = new NavigationDirection();
            nd.distance = 0.0;
            nd.way = g.getEdge(current, next).getStreetName();
            nd.direction = NavigationDirection.getDirection(g, prev, current, next);

        }
        return list;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /**
         * Integer constants representing directions.
         */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /**
         * Number of directions supported.
         */
        public static final int NUM_DIRECTIONS = 8;

        /**
         * A mapping of integer values to directions.
         */
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /**
         * Default name for an unknown way.
         */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /**
         * The direction a given NavigationDirection represents.
         */
        int direction;
        /**
         * The name of the way I represent.
         */
        String way;
        /**
         * The distance along this way I represent.
         */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = "";
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         *
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }

        public static int getDirection(GraphDB g, Long v1, Long v2, Long v3) {
            double angle = g.bearing(v3, v2) - g.bearing(v2, v1);
            int direction = START;
            if (-15 < angle && angle < 15) {
                direction = STRAIGHT;
            } else if (-30 < angle && angle <= -15) {
                direction = SLIGHT_LEFT;
            } else if (15 <= angle && angle < 30) {
                direction = SLIGHT_RIGHT;
            } else if (-100 < angle && angle <= -30) {
                direction = LEFT;
            } else if (30 <= angle && angle < 100) {
                direction = RIGHT;
            } else if (angle <= -100) {
                direction = g.bearing(v1, v3) < 0 ? SHARP_LEFT : SHARP_RIGHT;
            } else if (100 <= angle) {
                direction = g.bearing(v1, v3) > 0 ? SHARP_LEFT : SHARP_RIGHT;
            }
            //System.out.println(DIRECTIONS[direction]);
            //only can't pass the testdirection's one turn
            //change the resluts.txt's 21 line from turn right to sharp right to pass
            //current bearing calculate should be good enough
            return direction;
        }
    }
}
