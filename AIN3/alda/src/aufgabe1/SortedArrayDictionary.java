package aufgabe1;
import java.util.Comparator;  
import java.util.Iterator;
import java.util.Arrays;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {
 
    static private class Entry<K, V> {
        K key;
	V value;
	Entry(K k, V v) {
	    key = k;
	    value = v;
	};

	private static final int DEF_CAPACITY = 16;
	private int size;
	private Entry<K, V>[] data;

	@SuppressWarnings("unchecked")
	public SortedArrayDictionary() {
	    size = 0;
	    data = new Entry[DEF_CAPACITY];
	}

	public V search(K key) {
	    int i = searchKey
