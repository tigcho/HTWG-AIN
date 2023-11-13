// IntVar.java

package aufgabe1;

import java.util.Scanner;

public final class IntVar {
    private IntVar() { }

    /**
     * final static variable.
     */
    private static final Scanner EINGABE = new Scanner(System.in);

    /**
     * main ist der Startpunkt des Programms.
     * @param args
     */
    public static void main(final String[] args) {
        final int max = Integer.MAX_VALUE;
        final int min = Integer.MIN_VALUE;
        System.out.printf("Zwei Ganze Zahlen zwischen %d und %d eingeben: ",
                min, max);
        int x = 0;
        int y = 0;
        if (EINGABE.hasNextInt()) {
            x = EINGABE.nextInt();
        } else {
            System.out.println("Ungueltig. Nochmal eingeben: ");
        }

        if (EINGABE.hasNextInt()) {
            y = EINGABE.nextInt();
        } else {
            System.out.println("Ungueltig. Nochmal eingeben: ");
        }

        System.out.printf("%d ist oktal %o und hexa %x%n", x, x, x);
        System.out.printf("%d ist oktal %o und hexa %x%n", y, y, y);

        System.out.println("Operator eingeben: ");
        String operator = EINGABE.next();
        long result = 0;

        switch (operator) {
            case "+":
                result = x + y;
                break;
            case "-":
                result = x - y;
                break;
            case "*":
                result = x * y;
                break;
            case "/":
                result = x / y;
                break;
            case "%":
                result = x % y;
                break;
            case "==":
                System.out.printf("%d == %d ist %b%n", x, y, x == y);
                break;
            case "!=":
                System.out.printf("%d != %d ist %b%n", x, y, x != y);
                break;
            case "<":
                System.out.printf("%d < %d ist %b%n", x, y, x < y);
                break;
            case "<=":
                System.out.printf("%d <= %d ist %b%n", x, y, x <= y);
                break;
            case ">":
                System.out.printf("%d > %d ist %b%n", x, y, x > y);
                break;
            case ">=":
                System.out.printf("%d >= %d ist %b%n", x, y, x >= y);
                break;
            default:
                System.out.println("Ungueltiger Rechenoperator!");
                break;
        }
        if (result > min && result < max) {
            System.out.println("Ergebnis ist darstellbar!");
        } else {
            System.out.println("Ergebnis ist nicht darstellbar!");
        }
        System.out.printf("Ergebnis: %d%n", result);
    }
}

