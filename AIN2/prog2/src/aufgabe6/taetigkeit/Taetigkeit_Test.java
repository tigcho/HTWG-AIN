package taetigkeit;

public class Taetigkeit_Test {
    public static void main(String[] args) {
        Taetigkeit tk1 = new Parallel();
        tk1.add(new Elementar("Linke Seitenwand montieren", 5.0));
        tk1.add(new Elementar("Rechte Seitenwand montieren", 5.0));

        Taetigkeit tk2 = new Parallel();
        tk2.add(new Elementar("Linke Türe montieren", 7.0));
        tk2.add(new Elementar("Rechte Türe mit Griff montieren", 9.0));

        Taetigkeit schrankMontage = new Seriell();
        schrankMontage.add(new Elementar("Füße an Boden montieren", 6.0));
        schrankMontage.add(tk1);
        schrankMontage.add(new Elementar("Decke montieren", 8.0));
        schrankMontage.add(tk2);

        System.out.println(schrankMontage.getTime() + " min");
        System.out.println(schrankMontage.getAnzahl());
        System.out.println(schrankMontage);
    }
}