package aufgabe1;

public class DictionaryTest {
	public static void main(String[] args)  {
		testSortedArrayDictionary();
		testHashDictionary();
		testBinaryTreeDictionary();
	}

	private static void testSortedArrayDictionary() {
		Dictionary<String, String> dict = new SortedArrayDictionary<>();
		testDict(dict);
	}

	private static void testHashDictionary() {
		Dictionary<String, String> dict = new HashDictionary<>(3);
		testDict(dict);
	}

	private static void testBinaryTreeDictionary() {
		Dictionary<String, String> dict = new BinaryTreeDictionary<>();
		testDict(dict);

		BinaryTreeDictionary<Integer, Integer> btd = new BinaryTreeDictionary<>();
		btd.insert(10, 0);
		btd.insert(20, 0);
		btd.insert(50, 0);
		System.out.println("insert:");
		btd.prettyPrint();

		btd.insert(40, 0);
		btd.insert(30, 0);
		System.out.println("insert:");
		btd.prettyPrint();

		btd.insert(21, 0);
		System.out.println("insert:");
		btd.prettyPrint();

		btd.insert(35, 0);
		btd.insert(45, 0);
		System.out.println("insert:");
		btd.prettyPrint();

		System.out.println("For Each Loop:");
		for (Dictionary.Entry<Integer, Integer> e : btd) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}

		btd.remove(30);
		System.out.println("remove:");
		btd.prettyPrint();

		btd.remove(35);
		btd.remove(40);
		btd.remove(45);
		System.out.println("remove:");
		btd.prettyPrint();

		btd.remove(50);
		System.out.println("remove:");
		btd.prettyPrint();

		System.out.println("For Each Loop:");
		for (Dictionary.Entry<Integer, Integer> e : btd) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
	}

	private static void testDict(Dictionary<String, String> dict) {
		System.out.println("===== New Test Case ========================");
		System.out.println("test " + dict.getClass());
		System.out.println(dict.insert("gehen", "go") == null);		// true
		String s = "gehen";
		System.out.println(dict.search(s) != null);					// true
		System.out.println(dict.search(s).equals("go"));			// true
		System.out.println(dict.insert(s, "walk").equals("go"));	// true
		System.out.println(dict.search("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen").equals("walk"));	// true
		System.out.println(dict.remove("gehen") == null); // true
		dict.insert("starten", "start");
		dict.insert("gehen", "go");
		dict.insert("schreiben", "write");
		dict.insert("reden", "say");
		dict.insert("arbeiten", "work");
		dict.insert("lesen", "read");
		dict.insert("singen", "sing");
		dict.insert("schwimmen", "swim");
		dict.insert("rennen", "run");
		dict.insert("beten", "pray");
		dict.insert("tanzen", "dance");
		dict.insert("schreien", "cry");
		dict.insert("tauchen", "dive");
		dict.insert("fahren", "drive");
		dict.insert("spielen", "play");
		dict.insert("planen", "plan");
		dict.insert("diskutieren", "discuss");
		System.out.println(dict.size());
		for (Dictionary.Entry<String, String> e : dict) {
			System.out.println(e.getKey() + ": " + e.getValue() + " search: " + dict.search(e.getKey()));
		}
	}
}