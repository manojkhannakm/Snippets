package simple;

import java.util.Objects;

/**
 * @author Manoj Khanna
 */

public class Interval<E extends Number & Comparable<E>> implements Comparable<Interval<E>> {

    public E l, r;

    public Interval() {
    }

    public Interval(E l, E r) {
        this.l = l;
        this.r = r;
    }

    public Interval(Interval<E> interval) {
        this(interval.l, interval.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(l, r);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Interval)) {
            return false;
        }

        Interval<?> interval = (Interval<?>) obj;

        return Objects.equals(l, interval.l)
                && Objects.equals(r, interval.r);
    }

    @Override
    public String toString() {
        return "[" + l + ", " + r + "]";
    }

    @Override
    public int compareTo(Interval<E> o) {
        int c = l.compareTo(o.l);

        if (c == 0) {
            c = r.compareTo(o.r);
        }

        return c;
    }

    public boolean contains(E e) {
        return e.compareTo(l) >= 0 && e.compareTo(r) <= 0;
    }

    public boolean overlaps(Interval<E> interval) {
        return interval.l.compareTo(r) <= 0 && interval.r.compareTo(l) >= 0;
    }

    private static class Example {

        public static void main(String[] args) {
            System.out.println("Interval Example:");
            System.out.println("");

            Interval<Integer> interval1 = new Interval<>(10, 20), interval2 = new Interval<>(10, 20);

            System.out.println("[10, 20] == [10, 20]: " + (interval1 == interval2));
            System.out.println("");

            System.out.println("[10, 20].hashCode() == [10, 20].hashCode(): " + (interval1.hashCode() == interval2.hashCode()));
            System.out.println("");

            System.out.println("[10, 20].equals([10, 20]): " + interval1.equals(interval2));
            System.out.println("");

            System.out.println("[10, 20].compareTo([10, 20]): " + interval1.compareTo(interval2));
            System.out.println("");

            System.out.println("[10, 20].contains(15): " + interval1.contains(15));
            System.out.println("");

            System.out.println("[10, 20].overlaps([15, 25]): " + interval1.overlaps(new Interval<>(15, 25)));
        }

    }

}
