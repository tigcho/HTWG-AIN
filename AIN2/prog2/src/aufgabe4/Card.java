package aufgabe4;

import java.util.Objects;

public abstract class Card {

    public enum Farbe {
        KARO, HERZ, PIQUE, KREUZ
    }

    public enum Wert {
        SIEBEN, ACHT, NEUN, ZEHN, BUBE, DAME, KÖNIG, ASS
    }

    protected String farbe;
    protected String wert;

    public final String getFarbe() {
        return farbe;
    }

    public final String getWert() {
        return wert;
    }

    @Override
    public String toString() {
        return wert + " - " + farbe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Card other = (Card) o;
        return this.wert == other.wert && this.farbe == other.farbe;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}