package taetigkeit;

public interface Taetigkeit {
    double getTime();
    void add(Taetigkeit a);
    void remove(Taetigkeit a);
    int getAnzahl();
}
