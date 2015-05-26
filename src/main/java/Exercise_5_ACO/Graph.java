package Exercise_5_ACO;

import java.util.ArrayList;

/**
 * Created by Max Schrimpf
 */
public class Graph {
   private final ArrayList<ArrayList<Float>> graph;
    private final ArrayList<Vertex> vertexes;

    public Graph(ArrayList<ArrayList<Float>> graph) {
        this.graph = graph;
        vertexes = new ArrayList<Vertex>();
        for(int i = 0 ; i < graph.size(); i++) {
           vertexes.add(new Vertex(i));
        }
    }

    public void addPheromonAt(int a, int b, float amt) {
        graph.get(a).set(b, graph.get(a).get(b) + amt) ;
    }

    public float getPheromonAt(int a, int b) {
       return graph.get(a).get(b);
    }

    public Vertex getVertexAt(int a) {
       return vertexes.get(a);
    }

    public void nextIntervall() {
        // über edges iterieren und updaten
    }
}
