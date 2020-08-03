public class NBody {

    public static double readRadius(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        double r = in.readDouble();
        Planet[] list = new Planet[n];
        for (int i = 0; i < n; i++) {
            double xPos = in.readDouble();
            double yPos = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            list[i] = new Planet(xPos, yPos, xV, yV, m, img);
        }
        return list;
    }

    public static void main(String[] args) {
        //collecting data
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        //draw background
        StdDraw.setScale(-1 * radius, radius);
        String bg = "images/starfield.jpg";
        StdDraw.picture(0, 0, bg);

        //draw all planets
        for (Planet p : planets) {
            p.draw();
        }

        //creat animation
        StdDraw.enableDoubleBuffering();
        double curtime = 0.0;
        while (curtime <= T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < xForces.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, bg);
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            curtime += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }
}
