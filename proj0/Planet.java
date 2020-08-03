public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public static final double g = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double x = xxPos - p.xxPos;
        double y = yyPos - p.yyPos;
        return Math.sqrt(x * x + y * y);
    }

    public double calcForceExertedBy(Planet p) {
        double dis = calcDistance(p);
        return g * mass * p.mass / (dis * dis);
    }

    public double calcForceExertedByX(Planet p) {
        return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY(Planet p) {
        return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] list) {
        double totalForce = 0.0;
        for (int i = 0; i < list.length; i++) {
            if (!this.equals(list[i])) {
                totalForce += calcForceExertedByX(list[i]);
            }
        }
        return totalForce;
    }

    public double calcNetForceExertedByY(Planet[] list) {
        double totalForce = 0.0;
        for (int i = 0; i < list.length; i++) {
            if (!this.equals(list[i])) {
                totalForce += calcForceExertedByY(list[i]);
            }
        }
        return totalForce;
    }

    public void update(double seconds, double xforce, double yforce) {
        double xAcceleration = xforce / mass;
        double yAcceleration = yforce / mass;
        xxVel += xAcceleration * seconds;
        yyVel += yAcceleration * seconds;
        xxPos += xxVel * seconds;
        yyPos += yyVel * seconds;
    }

    public void draw() {
        String image = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, image);
    }


}
