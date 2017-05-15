package tree;

import java.util.TreeMap;

/**
 * @author Manoj Khanna
 */

public class Tree<E> {

    public final Node<E> rootNode;

    public Tree(E e) {
        rootNode = new Node<>(0, e, null);
    }

    public Tree() {
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

    public static class Node<E> {

        public final int i;
        public final Node<E> parentNode;
        public final TreeMap<Integer, Node<E>> childNodeMap;

        public E e;

        public Node(int i, E e, Node<E> parentNode) {
            this.i = i;
            this.e = e;
            this.parentNode = parentNode;

            childNodeMap = new TreeMap<>();
        }

        @Override
        public String toString() {
            return i + "(" + e + ")";
        }

        public void addChild(int i, E e) {
            if (!childNodeMap.containsKey(i)) {
                childNodeMap.put(i, new Node<>(i, e, this));
            }
        }

        public void removeChild(int i) {
            childNodeMap.remove(i);
        }

    }

    private static class Example {

        public static void main(String[] args) {
            System.out.println("Tree Example:");
            System.out.println("");

            Tree<Character> tree = new Tree<>();

            for (char[] s : new char[][]{
                    {'a', 'b', 'c'},
                    {'i', 'j', 'k'},
                    {'i', 'j', 'p', 'q'},
                    {'x', 'y', 'z'},
            }) {
                Node<Character> node = tree.rootNode;

                for (char c : s) {
                    int i = c - 'a';

                    node.addChild(i, c);

                    node = node.childNodeMap.get(i);
                }
            }

            System.out.println("addChild(...):\n" + tree);
            System.out.println("");

            tree.rootNode
                    .childNodeMap.get(8)
                    .childNodeMap.get(9)
                    .removeChild(15);

            System.out.println("removeChild(15):\n" + tree);
        }

    }

}
