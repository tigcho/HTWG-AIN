package aufgabe4;

import java.util.Random;

public class BlackCard extends Card {

    public BlackCard() {
        Random random = new Random();
        farbe = Farbe.values()[random.nextInt(2)].toString();
        wert = Wert.values()[random.nextInt(8)].toString();
    }

    public BlackCard(Farbe f, Wert w) {
        if (!(f == Farbe.PIQUE || f == Farbe.KREUZ))
            throw new IllegalArgumentException("BlackCards darf nur schwarze Karten haben.");
        farbe = f.toString();
        wert = w.toString();
    }
}