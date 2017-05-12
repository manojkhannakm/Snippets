package simple;

import java.util.Objects;

/**
 * @author Manoj Khanna
 */

public class Pair<A, B> implements Comparable<Pair<A, B>> {

    public A a;
    public B b;

    public Pair() {
    }

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public Pair(Pair<A, B> pair) {
        this(pair.a, pair.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) obj;

        return Objects.equals(a, pair.a)
                && Objects.equals(b, pair.b);
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ")";
    }

    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(Pair<A, B> o) {
        int c = ((Comparable<A>) a).compareTo(o.a);

        if (c == 0) {
            c = ((Comparable<B>) b).compareTo(o.b);
        }

        return c;
    }

    private static class Example {

        public static void main(String[] args) {
            System.out.println("Pair Example:");
            System.out.println("");

            Pair<Integer, Integer> pair1 = new Pair<>(10, 20), pair2 = new Pair<>(10, 20);

            System.out.println("(10, 20) == (10, 20): " + (pair1 == pair2));
            System.out.println("");

            System.out.println("(10, 20).hashCode() == (10, 20).hashCode(): " + (pair1.hashCode() == pair2.hashCode()));
            System.out.println("");

            System.out.println("(10, 20).equals((10, 20)): " + pair1.equals(pair2));
            System.out.println("");

            System.out.println("(10, 20).compareTo((10, 20)): " + pair1.compareTo(pair2));
        }

    }

}
