package bibliothek;

public class Buch {
    private String name;
    private Person entleiher;

    public Buch(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Person getEntleiher() {
        return entleiher;
    }

    public boolean wirdAusgeliehen(Person p) {
        if (entleiher != null) {
            return false;
        } else if (entleiher == null) {
            entleiher = p;
            p.leihtAus(this);
        }
        return true;
    }

    public boolean wirdZurueckGegeben(){
        if (entleiher == null){
            return false;
        } else {
            entleiher.gibtZurueck(this);
            entleiher = null;
            return true;
        }
    }

    public void print() {
        System.out.print("Name: " + name);
        if (entleiher == null) {
            System.out.print("----- Ein Geist kann kein Entleiher sein!");
        } else {
            System.out.print("; Entleiher:  " + entleiher.getName());
        }
        System.out.println();
    }
}
