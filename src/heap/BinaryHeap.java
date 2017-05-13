package heap;

import java.util.Comparator;

/**
 * @author Manoj Khanna
 */

public class BinaryHeap<E> {

    public final int c;
    public final Comparator<E> comparator;

    public Node<E>[] nodes;
    public int[] p, q;
    public int n;

    @SuppressWarnings("unchecked")
    public BinaryHeap(int c, Comparator<E> comparator) {
        this.c = c;
        this.comparator = comparator;

        nodes = new Node[c];
        p = new int[c];
        q = new int[c];
    }

    @SuppressWarnings("unchecked")
    public BinaryHeap(int c) {
        this(c, (o1, o2) -> ((Comparable<E>) o1).compareTo(o2));
    }

    @Override
    public String toString() {
        return toString(0, 0);
    }

    private int parentIndex(int i) {
        return (i - 1) / 2;
    }

    private int leftIndex(int i) {
        return 2 * i + 1;
    }

    private int rightIndex(int i) {
        return 2 * i + 2;
    }

    public String toString(int i, int d) {
        if (i >= n) {
            return "";
        }

        StringBuilder string = new StringBuilder();

        if (d > 0) {
            string.append("\n");
        }

        for (int j = 0; j < 4 * d; j++) {
            string.append(" ");
        }

        string.append(nodes[q[i]])
                .append(toString(leftIndex(i), d + 1))
                .append(toString(rightIndex(i), d + 1));

        return string.toString();
    }

    private void swap(int i, int j) {
        int x = p[q[i]];
        p[q[i]] = p[q[j]];
        p[q[j]] = x;

        int y = q[i];
        q[i] = q[j];
        q[j] = y;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int j = parentIndex(i);

            if (comparator.compare(nodes[q[i]].e, nodes[q[j]].e) >= 0) {
                break;
            }

            swap(i, j);

            i = j;
        }
    }

    private void siftDown(int i) {
        while (i < n) {
            int j = leftIndex(i), k = rightIndex(i);

            if (k < n && comparator.compare(nodes[q[j]].e, nodes[q[k]].e) >= 0) {
                j = k;
            }

            if (j >= n || comparator.compare(nodes[q[i]].e, nodes[q[j]].e) <= 0) {
                break;
            }

            swap(i, j);

            i = j;
        }
    }

    public void add(int i, E e) {
        if (nodes[i] == null) {
            nodes[i] = new Node<>(i, e);
            p[i] = n;
            q[n] = i;

            n++;

            siftUp(n - 1);
        }
    }

    public void remove(int i) {
        if (nodes[i] != null) {
            int c = comparator.compare(nodes[q[n - 1]].e, nodes[i].e);

            nodes[i] = null;
            p[q[n - 1]] = p[i];
            q[p[i]] = q[n - 1];

            n--;

            if (c < 0) {
                siftUp(p[i]);
            } else if (c > 0) {
                siftDown(p[i]);
            }
        }
    }

    public E peek() {
        if (nodes[q[0]] != null) {
            return nodes[q[0]].e;
        }

        return null;
    }

    public E poll() {
        if (nodes[q[0]] != null) {
            E e = nodes[q[0]].e;

            remove(q[0]);

            return e;
        }

        return null;
    }

    public boolean contains(int i) {
        return nodes[i] != null;
    }

    public E get(int i) {
        if (nodes[i] != null) {
            return nodes[i].e;
        }

        return null;
    }

    public void set(int i, E e) {
        if (nodes[i] != null) {
            int c = comparator.compare(e, nodes[i].e);

            nodes[i].e = e;

            if (c < 0) {
                siftUp(p[i]);
            } else if (c > 0) {
                siftDown(p[i]);
            }
        }
    }

    public static class Node<E> {

        public final int i;
        public E e;

        public Node(int i, E e) {
            this.i = i;
            this.e = e;
        }

        @Override
        public String toString() {
            return i + "(" + e + ")";
        }

    }

    private static class Example {

        public static void main(String[] args) {
            System.out.println("Binary Heap Example:");
            System.out.println("");

            BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(10);

            binaryHeap.add(0, 60);
            binaryHeap.add(1, 40);
            binaryHeap.add(2, 50);
            binaryHeap.add(3, 30);
            binaryHeap.add(4, 10);
            binaryHeap.add(5, 20);

            System.out.println("add(...):\n" + binaryHeap);
            System.out.println("");

            binaryHeap.remove(3);

            System.out.println("remove(3):\n" + binaryHeap);
            System.out.println("");

            System.out.println("peek(): " + binaryHeap.peek());
            System.out.println("");

            System.out.println("poll(): " + binaryHeap.poll() + "\n" + binaryHeap);
            System.out.println("");

            System.out.println("contains(1): " + binaryHeap.contains(1));
            System.out.println("");

            System.out.println("get(1): " + binaryHeap.get(1));
            System.out.println("");

            binaryHeap.set(0, 10);

            System.out.println("set(0, 10):\n" + binaryHeap);
        }

    }

}
