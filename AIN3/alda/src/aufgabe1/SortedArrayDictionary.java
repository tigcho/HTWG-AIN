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
	    int i = searchKey(key);
	    if (i >= 0) {
		return data[i].value;
	    } else {
		return null;
	    }
	}

	public V insert(K key, V value) {
	    int i = searchKey(key);

	    // vorhandener Eintrag wird überschrieben
	    if (i >= 0) {
		V r = data[i].value;
		data[i].value = value;
		return r;
	    }

	    // Neueintrag
	    if (data.length == size) {
		data = Arrays.copyOf(data, 2 * size);
	    }
	    data[size] = new Entry<K, V>(key, value);
	    size++;
	    return null;
	}

	public V remove(K key) {
	    int i = searchKey(key);
	    if (i == -1) {
		return null;
	    }

	    // Datensatz löschen und Lücke schließen
	    V r = data[i].value;
	    for (int j = i; j < size - 1; j++) {
		data[j] = data[j + 1];
	    }
	    data[--size] = null;
	    return r;
	}

	@Override
	public int size() {
	    return size;
	}

	private int searchKey(K key) {
	    int li = 0;
	    int re = size - 1;

	    while (re >= li) {
		int m = (li + re) / 2;
		if (key.compareTo(data[m].key) < 0) {
		    re = m - 1;
		} else if ( key.compareTo(data[m].key) > 0) {
		    li = m + 1;
		} else {
		    return m; // key gefunden
		}
		return -1; // key nicht gefunden
	    }
	}
