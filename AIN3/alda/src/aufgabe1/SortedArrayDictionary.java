package aufgabe1;
import java.util.Comparator;  
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

public class SortedArrayDictionary<K extends Comparable<K>, V> implements Dictionary<K, V> {
  private static final int DEF_CAPACITY = 16;
  private int size;
  private Entry<K, V>[] data;

  public SortedArrayDictionary() {
    this(DEF_CAPACITY);
  }

  public SortedArrayDictionary(int capacity) {
    size = 0;
    data = new Entry[capacity];
  }

  public V insert(K key, V value) {
    if (key == null) {
      throw new IllegalArgumentException("Key is null");
    }

    if (!(key instanceof Comparable)) {
      throw new IllegalArgumentException("Key is not comparable");
    }

    int i = searchKey(key);
      if (i != -1) {
        V r = data[i].getValue();
          data[i].setValue(value);
          return r;
      }

      if (data.length == size) {
          reallocate();
      }

      for (i = size - 1; i >= 0 && key.compareTo(data[i].getKey()) < 0; i--) {
          data[i + 1] = data[i];
      }

      data[i + 1] = new Entry<K, V>(key, value);
      size++;
      return null;
  }

  public V search(K key) {
    if (key == null) {
      throw new IllegalArgumentException("Key is null");
    }

    int i = searchKey(key);
    if (i != -1) {
      return data[i].getValue();
    }
    return null;
  }

  public V remove(K key) {
      int i = searchKey(key);
      if (i != -1) {
        V r = data[i].getValue();
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j + 1];
        }
        data[--size] = null;
        return r;
      }
      return null;
  }

  public int size() {
      return size;
  }

  private int searchKey(K key) {
    int li = 0;
    int re = size - 1;

    while (li <= re) {
      int m = (li + re) / 2;
      int comp = key.compareTo(data[m].getKey());
     
      if (comp == 0) {
        return m;
      } else if (comp < 0) {
        re = m - 1;
      } else {
        li = m + 1;
      }
    }
    return -1;
  }

  private void reallocate() {
    Entry<K, V>[] old = data;
    data = new Entry[2 * old.length];
    System.arraycopy(old, 0, data, 0, size);
  }

  @Override
    public Iterator<Entry<K, V>> iterator() {
        return new SortedArrayDictionaryIterator();
    }

    private class SortedArrayDictionaryIterator implements Iterator<Entry<K, V>> {
        private int cursor;

        public SortedArrayDictionaryIterator() {
            this.cursor = 0;
        }

        public boolean hasNext() {
            return this.cursor < SortedArrayDictionary.this.size;
        }

        public Entry<K, V> next() {
            if(this.hasNext()) {
                return data[cursor++];
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
