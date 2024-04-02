/*HashDictionary verwendet als Implementierung eine wie in der Vorlesung besprochene
Hashtabelle mit linear verketteten Listen. Achten Sie darauf, dass die Größe der Hashtabelle eine
Primzahl ist. Wird ein bestimmter Füllungsgrad (load factor) z.B. von 2 überschritten, dann wird
die Tabelle vergrößert, so dass die neue Größe etwa doppelt so groß und wieder eine Primzahl
ist. Die Daten werden dann sofort umkopiert.*/

import java.util.LinkedList;
import.java.util.Iterator;

public class HashDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
    private LinkedList<Entry<K, V>>[] data;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public HashDictionary(int load) {
	this.size = 0;
	this.capacity = capacity;
	this.data = new LinkedList[capacity];
    }


