package tree;

import java.util.HashMap;

/**
 * @author Manoj Khanna
 */

public class Trie<E> {

    public final Node<E> rootNode;

    public Trie(E e) {
        rootNode = new Node<>(e, null);
    }

    public Trie() {
        this(null);
    }

    @Override
    public String toString() {
        return toString(rootNode, 0);
    }

    private String toString(Node<E> node, int d) {
        StringBuilder string = new StringBuilder();

        if (d > 0) {
            string.append("\n");
        }

        for (int i = 0; i < 4 * d; i++) {
            string.append(" ");
        }

        string.append(node);

        for (Node<E> childNode : node.childNodeMap.values()) {
            string.append(toString(childNode, d + 1));
        }

        return string.toString();
    }

    @SafeVarargs
    public final void add(E... e) {
        Node<E> node = rootNode;

        for (E ei : e) {
            node.addChild(ei);

            node = node.childNodeMap.get(ei);
        }
    }

    @SafeVarargs
    public final void remove(E... e) {
        Node<E> node = rootNode;

        for (E ei : e) {
            if (!node.childNodeMap.containsKey(ei)) {
                return;
            }

            node = node.childNodeMap.get(ei);
        }

        int i = e.length - 1;

        while (node != rootNode) {
            node = node.parentNode;

            node.removeChild(e[i]);

            i--;
        }
    }

    @SafeVarargs
    public final boolean contains(E... e) {
        Node<E> node = rootNode;

        for (E ei : e) {
            if (!node.childNodeMap.containsKey(ei)) {
                return false;
            }

            node = node.childNodeMap.get(ei);
        }

        return true;
    }

    public static class Node<E> {

        public final E e;
        public final Node<E> parentNode;
        public final HashMap<E, Node<E>> childNodeMap;

        public Node(E e, Node<E> parentNode) {
            this.e = e;
            this.parentNode = parentNode;

            childNodeMap = new HashMap<>();
        }

        @Override
        public String toString() {
            return String.valueOf(e);
        }

        public void addChild(E e) {
            if (!childNodeMap.containsKey(e)) {
                childNodeMap.put(e, new Node<>(e, this));
            }
        }

        public void removeChild(E e) {
            if (childNodeMap.containsKey(e)) {
                if (childNodeMap.get(e).childNodeMap.isEmpty()) {
                    childNodeMap.remove(e);
                }
            }
        }

    }

    private static class Example {

        public static void main(String[] args) {
            System.out.println("Trie Example:");
            System.out.println("");

            Trie<Character> trie = new Trie<>();

            for (Character[] c : new Character[][]{
                    {'a', 'b', 'c'},
                    {'i', 'j', 'k'},
                    {'i', 'j', 'p', 'q'},
                    {'x', 'y', 'z'},
            }) {
                trie.add(c);
            }

            System.out.println("add(...):\n" + trie);
            System.out.println("");

            trie.remove('i', 'j', 'p', 'q');

            System.out.println("remove(i, j, p, q):\n" + trie);
            System.out.println("");

            System.out.println("contains(x, y): " + trie.contains('x', 'y'));
        }

    }

}
