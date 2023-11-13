package aufgabe2;

public class LinkedListFrequencyTable extends AbstractFrequencyTable {

    // TODO: Instanzvariablen festlegen
    private Node begin;
    private Node end;
    private int size = 0;

    // TODO: geschachtelte Klasse Node festlegen
    private static class Node {
        private Node next;
        private Node prev;
        private Word word;

        public Node(Word x, Node n, Node p) {
            word = x;
            next = n;
            prev = p;
        }
    }

    public LinkedListFrequencyTable() {
        clear();
    }

    @Override
    public int size() {
        return size;
    }


    // TODO: LinkedList mit 0 Elementen
    @Override
    public final void clear() {
        begin = new Node(null, null, null);
        end = new Node(null, null, begin);
        begin.next = end;
        size = 0;
    }

    @Override
    public void add(String w, int f) {
        // TODO: NullException Behandlung, ungueltige Daten ignorieren
        if (w == null || w.isEmpty()) {
            return;
        }

        // TODO: current setzen, LinkedList durchlaufen und Worter vergleichen
        Node current = begin.next;
        while (current != end && !current.word.getWord().equals(w)) {
            current = current.next;
        }
        // TODO: Ende erreicht, Wort nicht gefunden
        if (current == end) {
            Word newWord = new Word(w, f);
            Node newNode = new Node(newWord, end, end.prev);
            end.prev.next = newNode;
            end.prev = newNode;
            size++;
            moveLeft(newNode);
        } else {
            // TODO: Word vorhanden
            current.word.addFrequency(f);
            moveLeft(current);
        }
    }

    @Override
    public Word get(int pos) {
        // TODO: IndexOutOfBounds-Behandlung, return Position des Wortes, an dem Schleife endet
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = begin.next;
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }
        return current.word;
    }

    @Override
    public int get(String w) {
        // TODO: Vergleiche Worter, Zeiger setzen, hole die Haeufigkeit dazu, ungleich end
        Node current = begin.next;
        while (current != end) {
            if (current.word.getWord().equals(w)) {
                return current.word.getFrequency();
            }
            current = current.next;
        }
        return 0;
    }

    // TODO: Hilfsmethode zum Sortieren
    private void sort(Node a, Node b) {
        Word tmp = a.word;
        a.word = b.word;
        b.word = tmp;
    }

    // TODO: Null Exception Behandlung (current.prev), Zeiger setzen und y > x pruefen, sortieren
    private void moveLeft(Node node) {
        Node current = node;
        while (current.prev != begin && current.word.getFrequency() > current.prev.word.getFrequency()) {
            sort(current, current.prev);
            current = current.prev;
        }
    }
}