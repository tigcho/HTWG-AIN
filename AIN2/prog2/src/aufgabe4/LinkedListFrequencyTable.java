package aufgabe4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListFrequencyTable<T> extends AbstractFrequencyTable<T> {

    // TODO: Instanzvariablen festlegen
    private Node<T> begin;
    private Node<T> end;
    private int size = 0;

    @Override
    public Iterator<Element<T>> iterator() {
        return new Iterator<>() {
            Node<T> current = begin.next;

            @Override
            public boolean hasNext() {
                return current != end;
            }

            @Override
            public Element<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Element<T> element = current.element;
                current = current.next;
                return element;
            }
        };
    }


    // TODO: geschachtelte Klasse Node festlegen
    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private Element<T> element;

        public Node(Element<T> e, Node<T> n, Node<T> p) {
            this.element = e;
            this.next = n;
            this.prev = p;
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
        begin = new Node<>(null, null, null);
        end = new Node<>(null, null, begin);
        begin.next = end;
        size = 0;
    }

    @Override
    public void add(T e, int f) {
        // TODO: NullException Behandlung, ungueltige Daten ignorieren
        if (e == null || f < 1) {
            return;
        }

        // TODO: current setzen, LinkedList durchlaufen und Worter vergleichen
        Node<T> current = begin.next;
        while (current != end && !current.element.getElement().equals(e)) {
            current = current.next;
        }
        // TODO: Ende erreicht, Wort nicht gefunden
        if (current == end) {
            Element<T> newElem = new Element<T>(e, f);
            Node<T> newNode = new Node<T>(newElem, end, end.prev);
            end.prev.next = newNode;
            end.prev = newNode;
            size++;
            moveLeft(newNode);
        } else {
            // TODO: Word vorhanden
            current.element.addFrequency(f);
            moveLeft(current);
        }
    }

    @Override
    public Element<T> get(int pos) {
        // TODO: IndexOutOfBounds-Behandlung, return Position des Wortes, an dem Schleife endet
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = begin.next;
        for (int i = 0; i < pos; i++) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public int get(T e) {
        // TODO: Vergleiche Worter, Zeiger setzen, hole die Haeufigkeit dazu, ungleich end
        Node<T> current = begin.next;
        while (current != end) {
            if (current.element.getElement().equals(e)) {
                return current.element.getFrequency();
            }
            current = current.next;
        }
        return 0;
    }

    // TODO: Hilfsmethode zum Sortieren
    private void sort(Node<T> a, Node<T> b) {
        Element<T> tmp = a.element;
        a.element = b.element;
        b.element = tmp;
    }

    // TODO: Null Exception Behandlung (current.prev), Zeiger setzen und y > x pruefen, sortieren
    private void moveLeft(Node<T> node) {
        Node<T> current = node;
        while (current.prev != begin && current.element.getFrequency() > current.prev.element.getFrequency()) {
            sort(current, current.prev);
            current = current.prev;
        }
    }
}