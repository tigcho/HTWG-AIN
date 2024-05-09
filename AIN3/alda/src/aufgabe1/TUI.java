package aufgabe1;

import java.io.*;
import java.util.*;

public class TUI {
    private static Dictionary<String, String> dic;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("This is a German-English Dictionary. " +
                "\nStart by using <create>. It will create one using SortedArrayDictionary by default. " +
                "\nIf you want to create a specific one, type <create> followed by the name of the Dictionary you want to create. ");

        while (true) {
            System.out.print("❯ ");
            String input = scanner.nextLine();
            commands(input);
        }
    }

    private static void commands(String command) throws Exception {

        String args[] = command.split(" ");

        switch (args[0]) {
            case "create":
                if (args.length < 2)
                    System.out.println("Please specify the type of Dictionary you want to create");
                else
                create(args);
                break;
            case "read":
                read(Integer.parseInt(args[1]), args[2]);
            case "p":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    print();
                break;
            case "s":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    search(args);
                break;
            case "i":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    insert(args);
                break;
            case "r":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    remove(args);
                break;
            case "test":
                if (dic == null)
                    System.out.println("Use 'create' to create your first Dictionary!");
                else
                    test(Integer.parseInt(args[1]));
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }

    private static void create(String[] args) {
        System.out.println("Creating new Dictionary");
        if (args[1].equals("Hash")) {
            dic = new HashDictionary<>(3);

        } else if (args[1].equals("Binary")) {
            dic = new BinaryTreeDictionary<>();

        } else {
            dic = new SortedArrayDictionary<>();
        }
    }

    private static void print() {
        for (Dictionary.Entry<String, String> v : dic)
            System.out.println(v.getKey() + ": " + v.getValue());
    }

    private static void read(int n, String path) throws IOException {
        int counter = 0;

        File selectedFile = new File(path);
        String line;

        FileReader in;
        long start = 0;
        long stop = 0;
        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            start = System.nanoTime();
            while ((line = br.readLine()) != null && (n == -1 || counter < n)) {
                String[] words = line.split(" ");
                dic.insert(words[0], words[1]);
                counter++;
            }
            stop = System.nanoTime();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long diff = stop - start;
        System.out.println("Time Reading " + (diff / 1000000) + "ms");

    }


    private static void search(String[] args) {
        try {
            System.out.println(dic.search(args[1]));
        }
        catch (NullPointerException e) {
            System.err.println("Word has not been found");
        }
    }

    private static void insert(String[] args) {
        System.out.printf("Adding %s: %s to the Dictionary\n", args[1], args[2]);
        dic.insert(args[1], args[2]);
    }

    private static void remove(String[] args) {
        System.out.printf("Removing %s from Dictionary\n", args[1]);
        dic.remove(args[1]);
    }

    private static void test(int n) {
        int counter = 0;

        File selectedFile = new File("/home/selin/HTWG-AIN/AIN3/alda/src/aufgabe1/dtengl.txt");
        String line;
        List<String> germanWords = new LinkedList<>();
        List<String> englishWords = new LinkedList<>();

        FileReader in;

        long timeB = 0;
        long timeG = 0;
        long timeE = 0;
        try {
            in = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(in);
            while ((line = br.readLine()) != null && counter < n)
            {
                String[] words = line.split(" ");
                germanWords.add(words[0]);
                englishWords.add(words[1]);
                long startB = System.nanoTime();
                dic.insert(words[0], words[1]);
                long stopB = System.nanoTime();
                timeB += (stopB - startB);
                counter++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Build time: " + (timeB / 1000000) + "ms\n");

        ListIterator<String> itG = germanWords.listIterator();

        while (itG.hasNext() == true) {
            long startG = System.nanoTime();
            dic.search(itG.next());
            long stopG = System.nanoTime();
            timeG += (stopG - startG);
        }

        System.out.printf("Search german: " + (timeG / 1000000) + "ms\n");

        ListIterator<String> itE = englishWords.listIterator();

        while (itE.hasNext() == true) {
            long startE = System.nanoTime();
            dic.search(itE.next());
            long stopE = System.nanoTime();
            timeE += (stopE - startE);
        }

        System.out.printf("Search english: " + (timeE / 1000000) + "ms\n");

    }
}