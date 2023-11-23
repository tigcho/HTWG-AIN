package aufgabe5;

public class PyTree {

    private static final double min = 0.001;

    public static void main(String[] args) {
        double x = 0.6;
        double y = 0.05;

        StdDraw.show(0);
        StdDraw.setCanvasSize(800, 800);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenRadius(0.002);
        PyTree1(x, y, 0, 0.15);
        // PyTree2(x, y, 0, 0.10);
        StdDraw.show(0);
    }

    static void PyTree1(double x, double y, double alpha, double size) {
        if (size < min) {
            StdDraw.setPenColor(StdDraw.GREEN);
        } else {
            StdDraw.setPenColor(StdDraw.ORANGE);
        }

        double delta = 0.5;

        double s = size * Math.sin(alpha);
        double c = size * Math.cos(alpha);

        double xB = x + c;
        double yB = y + s;

        double xC = x + c - s;
        double yC = y + s + c;

        double xD = x - s;
        double yD = y + c;

        double u = size * Math.cos(delta);
        double v = size * Math.sin(delta);
        double xE = xD + u * Math.cos(alpha + delta);
        double yE = yD + u * Math.sin(alpha + delta);

        // Drawing Rectangles
        // StdDraw.line(x, y, xB, yB); // A -> B
        StdDraw.line(xB, yB, xC, yC); // B -> C
        // StdDraw.line(xC, yC, xD, yD); // C-> D
        StdDraw.line(xD, yD, x, y); // D -> A

        if (min < size) {
            PyTree1(xD, yD, alpha + delta, u);
            PyTree1(xE, yE, alpha + delta - 1.5, v);
        }
    }

    static void PyTree2(double x, double y, double alpha, double size) {
        double height = Math.random() * size + size;
        double beta = Math.toRadians(Math.random() * 45 + 10);

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

        double u = size * Math.cos(beta);
        double v = size * Math.sin(beta);
        double xE = xD + u * Math.cos(alpha + beta);
        double yE = yD + u * Math.sin(alpha + beta);

        // Drawing Rectangles
        // StdDraw.line(x, y, xB, yB); // A -> B
        StdDraw.line(xB, yB, xC, yC); //B -> C
        // StdDraw.line(xC, yC, xD, yD); // C-> D
        StdDraw.line(xD, yD, x, y); // D-> A

        if (min < size) {
            PyTree2(xD, yD, alpha + beta, u);
            PyTree2(xE, yE, alpha + beta - 1.5, v);
        }
    }
}
