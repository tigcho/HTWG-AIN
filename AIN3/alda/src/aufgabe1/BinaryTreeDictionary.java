package aufgabe1;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    static private class Node<K, V> {
        K key;
        V value;
        int height;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        Node(K k, V v) {
            key = k;
            value = v;
            height = 0;
            left = null;
            right = null;
            parent = null;
        }
    }

    private Node<K, V> root = null;
    private int size = 0;
    private V oldValue = null;


    @Override
    public V insert(K key, V value) {
        root = insertR(key, value, root);

        if (root != null) {
            root.parent = null;
        }
        return oldValue;
    }

    private Node<K, V> insertR(K key, V value, Node<K, V> p) {
        if (p == null) {
            size++;
            oldValue = null;
            return new Node<>(key, value);
        }

        if (key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);
            if (p.left != null) {
                p.left.parent = p;
            }

        } else if (key.compareTo(p.key) > 0) {
            p.right = insertR(key, value, p.right);
            if (p.right != null) {
                p.right.parent = p;
            }

        } else {
            oldValue = p.value;
            p.value = value;
        }
        return p;
    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }

    private V searchR(K key, Node<K, V> p) {
        if (p == null) {
            return null;
        }

        if (key.compareTo(p.key) == 0) {
            return p.value;

        } else if (key.compareTo(p.key) < 0) {
            return searchR(key, p.left);

        } else {
            return searchR(key, p.right);
        }
    }

    @Override
    public V remove(K key) {
        root = removeR(key, root);
        if (root != null) {
            root.parent = null;
        }
        return oldValue;
    }

    private Node<K, V> removeR(K key, Node<K, V> p) {
        if (p == null) {
            oldValue = null;
            return null;
        }

        if (key.compareTo(p.key) < 0) {
            p.left = removeR(key, p.left);
            if (p.left != null) {
                p.left.parent = p;
            }

        } else if (key.compareTo(p.key) > 0) {
            p.right = removeR(key, p.right);
            if (p.right != null) {
                p.right.parent = p;
            }

        } else {
            oldValue = p.value;
            if (p.left == null || p.right == null) {
                size--;
                return (p.left == null) ? p.right : p.left;

            } else {
                Node<K, V> min = findMin(p.right);
                p.key = min.key;
                p.value = min.value;
                p.right = removeR(min.key, p.right);
            }
        }
        return p;
    }

    private Node<K, V> findMin(Node<K, V> p) {
        if (p.left == null) {
            return p;
        } else {
            return findMin(p.left);
        }
    }

    @Override
    public int size() {
        return size;
    }

    private Node<K, V> leftMostDescendant(Node<K, V> p) {
        if (p == null) {
            return null;
        }
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    private Node<K, V> parentOfRightMostAncestor(Node<K, V> p) {
        if (p == null) {
            return null;
        }

        while (p.parent != null && p.parent.right == p) {
            p = p.parent;
        }
        return p.parent;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {

            Node<K, V> p = leftMostDescendant(root);

            @Override
            public boolean hasNext() {
                return p.right != null || parentOfRightMostAncestor(p) != null;
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                p = (p.right != null) ? leftMostDescendant(p.right) : parentOfRightMostAncestor(p);
                return new Entry<>(p.key, p.value);
            }
        };
    }

    public void prettyPrint() {
        printR(0, root);
    }

    private void printR(int level, Node<K, V> p) {
        printLevel(level);

        if (p == null) {
            System.out.println("#");
        } else {
            System.out.println(p.key + " " + p.value + "^" + ((p.parent == null) ? "null" : p.parent.key));
            if (p.left != null || p.right != null) {
                printR(level + 1, p.left);
                printR(level + 1, p.right);
            }
        }
    }

    private static void printLevel(int level) {
        if (level == 0) {
            return;
        }

        for (int i = 0; i < level - 1; i++) {
            System.out.print("   ");
        }
        System.out.print("|__");
    }
}