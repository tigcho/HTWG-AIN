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

    private static class MinEntry<K, V> {
        K key;
        V value;
    }
    
    private Node<K, V> root = null;
    private int size = 0;
    private V oldValue = null;

    @Override
    public V insert(K key, V value) {
        root = insertR(key, value, root); // return value of insertR is the new root
        if (root != null) { // binary tree is not empty
            root.parent = null; // root has no parent
        }
        return oldValue;
    }

    /*
    * insertR(root, newKey, newValue)

              root
              /  \
             /    \
       leftSub   rightSub

    (1) insertR(leftSub, newKey, newValue) if newKey < root.key
    (2) insertR(rightSub, newKey, newValue) if newKey > root.key
    (3) Update root.value if newKey == root.key
    * */
    private Node<K, V> insertR(K key, V value, Node<K, V> p) {
        if (p == null) {
            p = new Node(key, value);
            oldValue = null;
            size++;
        } else if (key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);

            /*
            *        A
                    / \
                   B   C      We want to set the parent pointer of
                  / \         B's left child back to B
                 D   E

            * */
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
        } else if (key.compareTo(p.key) < 0) {
            return searchR(key, p.left);
        } else if (key.compareTo(p.key) > 0) {
            return searchR(key, p.right);
        } else {
            return p.value;
        }
    }

    @Override
    public V remove(K key) {
        root = removeR(key, root);
        return oldValue;
    }

    private Node<K, V> removeR(K key, Node<K, V> p) {
        if (p == null) {
            oldValue = null;
        } else if (key.compareTo(p.key) < 0) {
            p.left = removeR(key, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = removeR(key, p.right);
        } else if (p.left == null || p.right == null) { // Node to be removed has 0 or 1 child
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right; // p is replaced by its child
        } else {
            MinEntry<K, V> min = new MinEntry<K, V>();
            p.right = getRemMinR(p.right, min); // get the smallest Node in the right subtree
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        return p;
    }

    private Node<K, V> getRemMinR(Node<K, V> p, MinEntry<K, V> min) {
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else {
            p.left = getRemMinR(p.left, min);
        }
        return p;
    }

    @Override
    public int size () {
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
            System.out.println("#"); // empty Node
        } else { 
            System.out.println(p.key + " " + p.value + "^" + ((p.parent == null) ? "null" : p.parent.key));
            if (p.left != null || p.right != null) {
                printR(level + 1, p.left);
                printR(level + 1, p.right); // increment level if child node exists
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
