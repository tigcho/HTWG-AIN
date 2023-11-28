package aufgabe5;

public class PyTree {

    private static final double min = 0.001;
    private static final double angle = Math.toRadians(30);

    public static void main(String[] args) {
        double x = 0.6;
        double y = 0.05; // Punkt A Koordinaten

        StdDraw.show(0);
        StdDraw.setCanvasSize(800, 800);
        StdDraw.clear(StdDraw.BOOK_BLUE);
        StdDraw.setPenRadius(0.003);
        // PyTreeOne(x, y, 0, 0.15);
        PyTreeTwo(x, y, 0, 0.09);
        StdDraw.show(0);
    }

    static void PyTreeOne(double x, double y, double alpha, double size) {
        if (size < min) {
            StdDraw.setPenColor(StdDraw.GREEN);
        } else {
            StdDraw.setPenColor(StdDraw.ORANGE);
        }

        double s = size * Math.sin(alpha);
        double c = size * Math.cos(alpha);

        double xB = x + c;
        double yB = y + s;

        double xC = x + c - s;
        double yC = y + s + c;

        double xD = x - s;
        double yD = y + c;

        double u = size * Math.cos(angle);
        double v = size * Math.sin(angle);

        double xE = xD + u * Math.cos(alpha + angle);
        double yE = yD + u * Math.sin(alpha + angle);

        // StdDraw.line(x, y, xB, yB); // A -> B
        StdDraw.line(xB, yB, xC, yC); // B -> C
        // StdDraw.line(xC, yC, xD, yD); // C-> D
        StdDraw.line(xD, yD, x, y); // D -> A

        if (min < size) {
            PyTreeOne(xD, yD, alpha + angle, u);
            PyTreeOne(xE, yE, alpha + angle - 1.5, v);
        }
    }

    static void PyTreeTwo(double x, double y, double alpha, double size) {
        double height = Math.random() * size + size;
        double delta = Math.toRadians(Math.random() * 40 + 10);

        if (size < min) {
            StdDraw.setPenColor(StdDraw.GREEN);
        } else {
            StdDraw.setPenColor(StdDraw.ORANGE);
        }

        double s = size * Math.sin(alpha);
        double c = size * Math.cos(alpha);

        double r = height * Math.sin(alpha);
        double h = height * Math.cos(alpha);

        double xB = x + c;
        double yB = y + s;

        double xC = x + c - r;
        double yC = y + s + h;

        double xD = x - r;
        double yD = y + h;

        double u = size * Math.cos(delta);
        double v = size * Math.sin(delta);
        double xE = xD + u * Math.cos(alpha + delta);
        double yE = yD + u * Math.sin(alpha + delta);

        // Drawing Rectangles
        // StdDraw.line(x, y, xB, yB); // A -> B
        StdDraw.line(xB, yB, xC, yC); //B -> C
        // StdDraw.line(xC, yC, xD, yD); // C-> D
        StdDraw.line(xD, yD, x, y); // D-> A

        if (min < size) {
            PyTreeTwo(xD, yD, alpha + delta, u);
            PyTreeTwo(xE, yE, alpha + delta - 1.5, v);
        }
    }
}
