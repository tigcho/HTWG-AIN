package bibliothek;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private final String name;
    private final List<Buch> ausgelieheneBuecher;

    public Person(String name) {
        this.name = name;
        this.ausgelieheneBuecher = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean leihtAus(Buch b) {
        if (b.getEntleiher() != null)
            return false;

        for (var buch : ausgelieheneBuecher) {
            if (buch.equals(b)) {
                return false;
            }
        }
        ausgelieheneBuecher.add(b);
        b.wirdAusgeliehen(this);
        return true;
    }

    public boolean gibtZurueck(Buch b) {
        if (!ausgelieheneBuecher.contains(b))
            return false;

        ausgelieheneBuecher.remove(b);
        b.wirdZurueckGegeben();
        return true;
    }

    public void print() {
        System.out.print("Name: " + name + "; Geliehene Bücher: ");
        for (Buch b : ausgelieheneBuecher){
            System.out.print(b.getName() + " ");
        }
        System.out.println();
    }
}
