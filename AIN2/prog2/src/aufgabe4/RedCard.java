package aufgabe4;

import java.util.Random;

public class RedCard extends Card {

    public RedCard() {
        Random zahl = new Random();
        farbe = Farbe.values()[zahl.nextInt(2)].toString();
        wert = Wert.values()[zahl.nextInt(8)].toString();
    }

    public RedCard(Farbe f, Wert w) {
        if (!(f == Farbe.HERZ || f == Farbe.KARO))
            throw new IllegalArgumentException("RedCards darf nur rote Karten haben.");
        farbe = f.toString();
        wert = w.toString();
    }
}