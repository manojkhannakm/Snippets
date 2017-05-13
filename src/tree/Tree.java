package tree;

import java.util.TreeMap;

/**
 * @author Manoj Khanna
 */

public class Tree<E> {

    public final Node<E> rootNode;

    public Tree() {
        rootNode = new Node<>(0, null);
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

        for (Node<E> childNode : node.nodeMap.values()) {
            string.append(toString(childNode, d + 1));
        }

        return string.toString();
    }

    public static class Node<E> {

        public final TreeMap<Integer, Node<E>> nodeMap;

        public final int i;
        public E e;

        public Node(int i, E e) {
            this.i = i;
            this.e = e;

            nodeMap = new TreeMap<>();
        }

        @Override
        public String toString() {
            return i + "(" + e + ")";
        }

        public void add(int i, E e) {
            if (nodeMap.containsKey(i)) {
                return;
            }

            nodeMap.put(i, new Node<>(i, e));
        }

        public void remove(int i) {
            nodeMap.remove(i);
        }

        public boolean contains(int i) {
            return nodeMap.containsKey(i);
        }

        public E get(int i) {
            if (nodeMap.containsKey(i)) {
                return nodeMap.get(i).e;
            }

            return null;
        }

        public void set(int i, E e) {
            if (nodeMap.containsKey(i)) {
                nodeMap.get(i).e = e;
            }
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

                    node.add(i, c);

                    node = node.nodeMap.get(i);
                }
            }

            System.out.println("add(...):\n" + tree);
            System.out.println("");

            tree.rootNode
                    .nodeMap.get(8)
                    .nodeMap.get(9)
                    .remove(15);

            System.out.println("remove(15):\n" + tree);
            System.out.println("");

            System.out.println("contains(9): " + tree.rootNode
                    .nodeMap.get(8)
                    .contains(9));
            System.out.println("");

            System.out.println("get(24): " + tree.rootNode
                    .nodeMap.get(23)
                    .get(24));
            System.out.println("");

            tree.rootNode
                    .nodeMap.get(8)
                    .set(9, 'p');

            System.out.println("set(9, p):\n" + tree);
        }

    }

}
