/*
 * class Evaluator
 * repl-Schleife: lese von der Konsole eine Ziele und 
 * werte sie als arithmetischen Ausdruck aus.
 * Da tokenizer String-Konstante zurückliefert, reicht 
 * Gleichheitsprüfung mit == aus (siehe z.B. shift-Methode).
 */
package aufgabe3;

import java.util.Scanner;
import java.util.Arrays;
import static aufgabe3.Tokenizer.*;

/**
 * Klasse zum Auswerten von arithmetischen Ausdrücken.
 */
public class Evaluator {

    private static final String ANSI_BLUE = "\u001B[34m";
    private static Object[] stack = new Object[100];	// Stack
    private static int top = -1;					// Index des obersten Kellerelements
    private static Object token;					// Aktuelles Token
    private static Tokenizer tokenizer;				// Zerlegt String-Eingabe in Tokens

    /**
     * Wertet expr als arithmetischen Ausdruck aus.
     *
     * @param expr Arthmetischer Ausdruck als String
     * @return Wert des Ausdrucks oder null, falls der Ausdruck fehlerhaft ist.
     */
    public static Double eval(String expr) {
        // Dollar in leeren Stack ablegen:
        top = -1;
        stack[++top] = DOLLAR;

        // expr in Tokens zerlegen und erstes Token abholen:
        tokenizer = new Tokenizer(expr);
        token = tokenizer.nextToken();

        while (token != null) {
            if (!reduce()) {
                if (!shift()) {
                    if (accept()) {
                        return (double) stack[top];
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    private static boolean shift() {
        if (stack[top] == DOLLAR
                && (token == KL_AUF || isVal(token))) {    // 1. Regel
            doShift();
            return true;
        } else if (isOp(stack[top])
                && (token == KL_AUF || isVal(token))) {    // 2. Regel
            doShift();
            return true;
        } else if (stack[top] == KL_AUF
                && (token == KL_AUF || isVal(token))) {    // 3. Regel
            doShift();
            return true;
        } else if (isVal(stack[top])
                && stack[top - 1] == DOLLAR
                && isOp(token)) {    // 6. Regel
            doShift();
            return true;
        } else if (isVal(stack[top])
                && stack[top - 1] == KL_AUF
                && (token == KL_ZU || isOp(token))) {    // 7. Regel
            doShift();
            return true;
        }
        if (isVal(stack[top])
            && isOp(stack[top -1])
            && isVal(stack[top - 2])
            && isOp(token)) {
            if (stack[top - 1] == PLUS && token == MULT || token == POWER) {
            doShift();
            return true;
            }
        }
        return false;
    }

    private static void doShift() {
        top++;
        stack[top] = token;
        token = tokenizer.nextToken();
    }

    private static boolean isOp(Object o) {
        return (o == PLUS || o == MULT || o == POWER);
    }

    private static boolean isVal(Object o) {
        return o instanceof Double;
    }

    private static boolean reduce() {
        if (stack[top] == KL_ZU
                && isVal(stack[top - 1])
                && stack[top - 2] == KL_AUF
                && (token == KL_ZU || isOp(token) || token == DOLLAR)) {    // 4. Regel
            doReduceKlValKl();
            return true;
        } else if (isVal(stack[top])
                && isOp(stack[top - 1])
                && isVal(stack[top - 2])
                && (token == KL_ZU || token == DOLLAR)) {    // 8. Regel
            doReduceValOpVal();
            return true;
        } else if (isVal(stack[top])
                && isOp(stack[top - 1])
                && isVal(stack[top - 2])
                && isOp(token)) {
            if (!(stack[top - 1] == PLUS && token == MULT || token == POWER)) {
                doReduceValOpVal();
                return true;
            }
        }
        return false;
    }

    private static void doReduceKlValKl() {
        Object value = stack[top - 1];
        top -= 2;
        stack[top] = value;
    }

    private static void doReduceValOpVal() {
        double stacktop = (double) stack[top];
        double stacktop2 = (double) stack[top - 2];

        if (stack[top - 1] == PLUS) {
            Object value = stacktop2 + stacktop;
            top -= 2;
            stack[top] = value;
        } else if (stack[top - 1] == MULT) {
            Object value = stacktop2 * stacktop;
            top -= 2;
            stack[top] = value;
        } else if (stack[top - 1] == POWER) {
            Object value = Math.pow(stacktop2, stacktop);
            top -= 2;
            stack[top] = value;
        }
    }

    private static boolean accept() {
        if (stack[top - 1] == DOLLAR && token == DOLLAR && isVal(stack[top])) { // 5. Regel
            return true;
        }
        return false;
    }

    /**
     * Liest von der Konsole eine Folge von Zeilen, wertet jede Zeile als
     * Ausdruck aus und gibt seinen Wert aus. (repl = read-evaluate-print-loop).
     */
    public static void repl() {
        Scanner in = new Scanner(System.in);
        System.out.print(ANSI_BLUE + ">> ");

        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals("end")) {
                System.out.println("bye!");
                break;
            }
            System.out.println(eval(line) == null ? "!!! error" : eval(line));
            System.out.print(ANSI_BLUE + ">> ");
        }
    }

    /**
     * Testprogramm.
     *
     * @param args wird nicht benutzt.
     */
    public static void main(String[] args) {
        // Zum Testen, später auskommentieren:
	String s1 = "1+2";
	String s2 = "2^10+5";
	String s3 = "5+2^10";
        String s4 = "(2+3*4+4)^2";
        String s5 = "(2+3*4+4))*2";
        String s6 = "2+3**4";
        String s7 = "2^3^2";
        String s8 = "2^2*5";
        String s9 = "1+(2+(3+(4+(5+6))))";
        String s10 = "1+2+3+4+5+6";

	System.out.println(eval(s1));	// 3.0
	System.out.println(eval(s2));	// 1029.0
        System.out.println(eval(s3));	// 1029.0
        System.out.println(eval(s4));	// 324.0
        System.out.println(eval(s5));	// null; Syntaxfehler
        System.out.println(eval(s6));	// null; Syntaxfehler
        System.out.println(eval(s7));	// 512.0
        System.out.println(eval(s8));	// 20.0
        System.out.println(eval(s9));	// 21.0
        System.out.println(eval(s10));	// 21.0

        repl();
    }
}
