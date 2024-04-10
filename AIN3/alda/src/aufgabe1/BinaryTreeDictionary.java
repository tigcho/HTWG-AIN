package dictionary;

import java.util.Comparator;
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
        root = insertR(key, value, root); // return value of insertR is the new root
        if (root != null) { // binary tree is not empty
            root.parent = null; // root has no parent
        }
        return oldValue
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
        } else if (key.compareTo(p.right) > 0) {
            return searchR
        }
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
