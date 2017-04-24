package tree;

/**
 * @author Manoj Khanna
 */

public abstract class SegmentTree<E, U> {   //TODO: Redo later

    private final Node<E, U>[] nodes;

    @SuppressWarnings("unchecked")
    public SegmentTree(E[] e) {
        this.nodes = new Node[2 * (int) Math.pow(2, Math.ceil(Math.log(e.length) / Math.log(2))) - 1];

        create(e, 0, e.length - 1, 0);
    }

    protected abstract E get(E e, E le, E re, int l, int r);

    protected abstract E set(E e, U u, int l, int r);

    protected U lazy(U ou, U nu, int l, int r) {
        return null;
    }

    @Override
    public String toString() {
        return treeString();
    }

    private void create(E[] e, int l, int r, int i) {
        Node<E, U> node = nodes[i] = new Node<>(l, r);

        if (l == r) {
            node.e = e[l];
            return;
        }

        int m = (l + r) / 2;

        create(e, l, m, 2 * i + 1);
        create(e, m + 1, r, 2 * i + 2);

        node.e = get(null, nodes[2 * i + 1].e, nodes[2 * i + 2].e, l, r);
    }

    private E get(int l, int r, int i) {
        Node<E, U> node = nodes[i];

        if (node.l > r || node.r < l) {
            return null;
        }

        if (node.l >= l && node.r <= r) {
            return node.e;
        }

        E le = get(l, r, 2 * i + 1),
                re = get(l, r, 2 * i + 2);

        if (le != null && re != null) {
            return get(null, le, re, node.l, node.r);
        } else if (le != null) {
            return le;
        } else {
            return re;
        }
    }

    private void set(int l, int r, U u, int i) {
        Node<E, U> node = nodes[i];

        if (node.l > r || node.r < l) {
            return;
        }

        if (node.l == node.r) {
            node.e = set(node.e, u, node.l, node.r);
            return;
        }

        set(l, r, u, 2 * i + 1);
        set(l, r, u, 2 * i + 2);

        node.e = get(node.e, nodes[2 * i + 1].e, nodes[2 * i + 2].e, node.l, node.r);
    }

    private E lazyGet(int l, int r, int i) {
        return null;
    }

    private void lazySet(int l, int r, U u, int i) {
        Node<E, U> node = nodes[i];

        if (node.l > r || node.r < l) {
            return;
        }

        if (node.l >= l && node.r <= r) {
            node.u = lazy(node.u, u, node.l, node.r);
            node.e = set(node.e, node.u, node.l, node.r);

            if (node.l < node.r) {
                Node<E, U> lNode = nodes[2 * i + 1],
                        rNode = nodes[2 * i + 2];

                lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);

                rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);
            }

            node.u = null;
            return;
        }


    }

    private void lazyUpdate(int l, int r, U u, int i) {
        Node<E, U> node = nodes[i];

        if (node.l > r || node.r < l) {
            return;
        }

        if (node.l >= l && node.r <= r) {
            node.p = true;
            node.u = lazy(node.u, u, node.l, node.r);
            return;
        }

        Node<E, U> lNode = nodes[2 * i + 1],
                rNode = nodes[2 * i + 2];

        if (node.u != null) {
            lNode.p = true;
            lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);

            rNode.p = true;
            rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);

            node.u = null;
        }

        lazyUpdate(l, r, u, 2 * i + 1);
        lazyUpdate(l, r, u, 2 * i + 2);

        node.p = lNode.p || rNode.p;
    }

    private void lazyPropagate(int l, int r, int i) {
        Node<E, U> node = nodes[i];

        if (node.l > r || node.r < l || !node.p) {
            return;
        }

        if (node.l == node.r) {
            node.e = set(node.e, node.u, node.l, node.r);
            node.p = false;
            node.u = null;
            return;
        }

        Node<E, U> lNode = nodes[2 * i + 1],
                rNode = nodes[2 * i + 2];

        if (node.u != null) {
            lNode.p = true;
            lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);

            rNode.p = true;
            rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);

            node.u = null;
        }

        lazyPropagate(l, r, 2 * i + 1);
        lazyPropagate(l, r, 2 * i + 2);

        node.e = get(node.e, lNode.e, rNode.e, node.l, node.r);
        node.p = lNode.p || rNode.p;
    }

    private String treeString(int i, int d) {
        String s = "";

        if (i > 0) {
            s += "\n";
        }

        for (int j = 0; j < 4 * d; j++) {
            s += " ";
        }

        Node<E, U> node = nodes[i];

        s += node;

        if (node.l < node.r) {
            s += treeString(2 * i + 1, d + 1);
            s += treeString(2 * i + 2, d + 1);
        }

        return s;
    }

    private String arrayString(int i) {
        String s = "";

        if (i == 0) {
            s += "[";
        }

        Node<E, U> node = nodes[i];

        if (node.l == node.r) {
            if (node.l > 0) {
                s += ", ";
            }

            s += node.e;
        } else {
            s += arrayString(2 * i + 1);
            s += arrayString(2 * i + 2);
        }

        if (i == 0) {
            s += "]";
        }

        return s;
    }

    public E get(int l, int r) {
        return get(l, r, 0);
    }

    public void set(int l, int r, U u) {
        set(l, r, u, 0);
    }

    public E lazyGet(int l, int r) {
        return lazyGet(l, r, 0);
    }

    public void lazySet(int l, int r, U u) {
        lazySet(l, r, u, 0);
    }

    public void lazyUpdate(int l, int r, U u) {
        lazyUpdate(l, r, u, 0);
    }

    public void lazyPropagate(int l, int r) {
        lazyPropagate(l, r, 0);
    }

    public String treeString() {
        return treeString(0, 0);
    }

    public String arrayString() {
        return arrayString(0);
    }

    private static class Node<E, U> {

        private final int l, r;

        private E e;
        private U u;
        private boolean p;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }

        @Override
        public String toString() {
            return "[" + l + ", " + r + "] = " + e + (u != null ? " -> " + u : "");
        }

    }

    private static class Example {

        public static void main(String[] args) {
            //            SegmentTree<Integer, Integer> segmentTree = new SegmentTree<Integer, Integer>(new Integer[]{10, 20, 30, 40, 50, 60}) {
//
//                @Override
//                protected Integer get(Integer ce, Integer le, Integer re, int l, int r) {
//                    return le + re;
//                }
//
//                @Override
//                protected Integer set(Integer ce, Integer cu, int l) {
//                    return ce + cu;
//                }
//
//                @Override
//                protected Integer update(Integer cu, Integer nu, int l, int r) {
//                    if (cu == null) {
//                        return nu;
//                    }
//
//                    return cu + nu;
//                }
//
//            };
//
//            System.out.println("create():\n" + segmentTree);
//
//            System.out.println("");
//
//            segmentTree.set(2, 4, 100);
//            System.out.println("set(2, 4, 100):\n" + segmentTree);
//
//            System.out.println("");
//
//            segmentTree.update(0, 5, 100, false);
//            System.out.println("lazyUpdate(0, 5, 100, false):\n" + segmentTree);
//
//            System.out.println("");
//
//            segmentTree.update(2, 4, 100, false);
//            System.out.println("lazyUpdate(2, 4, 100, false):\n" + segmentTree);
//
//            System.out.println("");
//
//            segmentTree.update(3, 3, 100, true);
//            System.out.println("lazyUpdate(3, 3, 100, true):\n" + segmentTree);
//
//            System.out.println("");
//
//            segmentTree.propagate(2, 4);
//            System.out.println("lazyPropagate(2, 4):\n" + segmentTree);
//
//            System.out.println("");
//
//            System.out.println("get(2, 4): " + segmentTree.get(2, 4));
        }

    }

}
