package aufgabe1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Measurement {

    static File file = null;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";


    public static void main(String[] args) throws FileNotFoundException {

        file = new File("/home/selin/HTWG-AIN/AIN3/alda/src/aufgabe1/dtengl.txt");

        // SortedArrayDictionary
        System.out.println(RED + "SortedArrayDictionary: " + RESET);
        Dictionary<String, String> sad = new SortedArrayDictionary<>();
        testDict(sad);

        // HashDictionary
        System.out.println(GREEN + "HashDictionary: " + RESET);
        Dictionary<String, String> hd = new HashDictionary<>(11);
        testDict(hd);

        // BinaryTreeDictionary
        System.out.println(BLUE + "BinaryTreeDictionary: " + RESET);
        Dictionary<String, String> btd = new BinaryTreeDictionary<>();
        testDict(btd);
    }

    private static void testDict(Dictionary<String, String> dict) throws FileNotFoundException {
        Scanner sc = new Scanner(file);

        System.out.println("Insert: ");

        long startTime = System.currentTimeMillis();
        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            dict.insert(parts[0], parts[1]);
            i++;
            if (i == 8000) {
                long time8000 = System.currentTimeMillis() - startTime;
                System.out.println("  8000: " + time8000 + " ms");
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("~16000: " + endTime + " ms");

        // search german words
        List<String> ger = new ArrayList<>();
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            ger.add(parts[0]);
        }

        System.out.println("Search german:");

        startTime = System.currentTimeMillis();
        i = 0;
        for (String word : ger) {
            dict.search(word);
            i++;
            if (i == 8000) {
                long time8000 = System.currentTimeMillis() - startTime;
                System.out.println("  8000: " + time8000 + " ms");
            }
        }

        endTime = System.currentTimeMillis() - startTime;
        System.out.println("~16000: " + endTime + " ms");

        // search english words
        List<String> eng = new ArrayList<>();
        sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(" ");
            eng.add(parts[1]);
        }

        System.out.println("Search english:");
        startTime = System.currentTimeMillis();
        i = 0;
        for (String word : eng) {
            dict.search(word);
            i++;
            if (i == 8000) {
                long time8000 = System.currentTimeMillis() - startTime;
                System.out.println("  8000: " + time8000 + " ms");
            }
        }
        endTime = System.currentTimeMillis() - startTime;
        System.out.println("~16000: " + endTime + " ms\n");
    }
}