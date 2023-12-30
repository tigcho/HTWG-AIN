package taetigkeit;

public class Elementar implements Taetigkeit {
    private final double time;
    private final String description;

    public Elementar (String d, double t){
        time = t;
        description = d;
    }

    @Override
    public double getTime() {
        return time;
    }

    @Override
    public void add(Taetigkeit a) {
        return;
    }

    @Override
    public void remove(Taetigkeit a) {
        return;
    }

    @Override
    public int getAnzahl() {
        return 1;
    }

    @Override
    public String toString() {
        return description + " (" + time + " min)";
    }
}