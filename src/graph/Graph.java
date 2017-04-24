package graph;

import java.util.HashMap;

/**
 * @author Manoj Khanna
 */

@SuppressWarnings("unused")
public class Graph<E, W> {

    public HashMap<Integer, Vertex<E, W>> vertexMap;

    public Graph() {
        vertexMap = new HashMap<>();
    }

    public Graph(int c) {
        vertexMap = new HashMap<>(c);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (Vertex<E, W> vertex : vertexMap.values()) {
            if (string.length() > 0) {
                string.append("\n");
            }

            string.append(vertex);
        }

        return string.toString();
    }

    public void addVertex(int u, E e) {
        if (!vertexMap.containsKey(u)) {
            vertexMap.put(u, new Vertex<>(u, e));
        }
    }

    public void removeVertex(int u) {
        if (vertexMap.containsKey(u)) {
            for (Integer v : vertexMap.remove(u).edgeMap.keySet()) {
                removeEdge(v, u);
            }
        }
    }

    public void addEdge(int u, int v, W w) {
        if (vertexMap.containsKey(u)) {
            vertexMap.get(u).edgeMap.put(v, new Edge<>(v, w));
        }
    }

    public void removeEdge(int u, int v) {
        if (vertexMap.containsKey(u)) {
            vertexMap.get(u).edgeMap.remove(v);
        }
    }

    public void clear() {
        vertexMap.clear();
    }

    public boolean contains(int u) {
        return vertexMap.containsKey(u);
    }

    public int size() {
        return vertexMap.size();
    }

    public boolean isEmpty() {
        return vertexMap.isEmpty();
    }

    public E get(int u) {
        if (vertexMap.containsKey(u)) {
            return vertexMap.get(u).e;
        }

        return null;
    }

    public void set(int u, E e) {
        if (vertexMap.containsKey(u)) {
            vertexMap.get(u).e = e;
        }
    }

    public W getWeight(int u, int v) {
        if (vertexMap.containsKey(u)) {
            HashMap<Integer, Edge<W>> edgeMap = vertexMap.get(u).edgeMap;

            if (edgeMap.containsKey(v)) {
                return edgeMap.get(v).w;
            }
        }

        return null;
    }

    public void setWeight(int u, int v, W w) {
        if (vertexMap.containsKey(u)) {
            HashMap<Integer, Edge<W>> edgeMap = vertexMap.get(u).edgeMap;

            if (edgeMap.containsKey(v)) {
                edgeMap.get(v).w = w;
            }
        }
    }

    public static class Vertex<E, W> {

        public int u;
        public E e;
        public HashMap<Integer, Edge<W>> edgeMap;

        public Vertex(int u, E e) {
            this.u = u;
            this.e = e;

            edgeMap = new HashMap<>();
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();

            for (Edge<W> edge : edgeMap.values()) {
                if (string.length() > 0) {
                    string.append(", ");
                }

                string.append(edge);
            }

            return u + "(" + e + ") -> " + string.toString();
        }

    }

    public static class Edge<W> {

        public int v;
        public W w;

        public Edge(int v, W w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public String toString() {
            return v + (w != null ? "(" + w + ")" : "");
        }

    }

    private static class Example {

        public static void main(String[] args) {
            System.out.println("Graph Example:");
            System.out.println("");

            Graph<Character, Integer> graph = new Graph<>();

            for (int i = 0; i < 5; i++) {
                graph.addVertex(i, (char) ('A' + i));
            }

            System.out.println("addVertex(...):\n" + graph);
            System.out.println("");

            graph.addEdge(0, 1, 10);
            graph.addEdge(0, 3, 40);
            graph.addEdge(1, 2, 20);
            graph.addEdge(2, 2, 30);
            graph.addEdge(3, 0, null);

            System.out.println("addEdge(...):\n" + graph);
            System.out.println("");

            graph.removeVertex(1);

            System.out.println("removeVertex(1):\n" + graph);
            System.out.println("");

            graph.removeEdge(2, 2);

            System.out.println("removeEdge(2, 2):\n" + graph);
            System.out.println("");

            System.out.println("contains(1): " + graph.contains(1));
            System.out.println("size(): " + graph.size());
            System.out.println("isEmpty(): " + graph.isEmpty());
            System.out.println("get(3): " + graph.get(3));

            graph.set(3, 'M');

            System.out.println("set(3, M): " + graph.get(3));

            System.out.println("getWeight(0, 3): " + graph.getWeight(0, 3));

            graph.setWeight(0, 3, 80);

            System.out.println("setWeight(0, 3, 80): " + graph.getWeight(0, 3));
        }

    }

}
