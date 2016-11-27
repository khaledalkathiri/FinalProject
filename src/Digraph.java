import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * An example class for directed graphs.  The vertex type can be specified.
 * There are no edge costs/weights.
 * 
 * Written for CS211, Nov 2006.
 * 
 * @author Paul Chew
 */
public class Digraph<T> {
    
    /**
     * The implementation here is basically an adjacency list, but instead
     * of an array of lists, a Map is used to map each vertex to its list of 
     * adjacent vertices.
     */   
    private Map<T,List<T>> neighbors = new HashMap<T,List<T>>();
    
    /**
     * String representation of graph.
     */
    public String toString () {
        StringBuffer s = new StringBuffer();
        for (T v: neighbors.keySet()) s.append("\n    " + v + " -> " + neighbors.get(v));
        return s.toString();                
    }
    
    /**
     * Add a vertex to the graph.  Nothing happens if vertex is already in graph.
     */
    public void add (T vertex) {
        if (neighbors.containsKey(vertex)) return;
        neighbors.put(vertex, new ArrayList<T>());
    }
    
    /**
     * True iff graph contains vertex.
     */
    public boolean contains (T vertex) {
        return neighbors.containsKey(vertex);
    }
    
    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public void add (T from, T to) {
        this.add(from); this.add(to);
        neighbors.get(from).add(to);
    }
    
    /**
     * Remove an edge from the graph.  Nothing happens if no such edge.
     * @throws IllegalArgumentException if either vertex doesn't exist.
     */
    public void remove (T from, T to) {
        if (!(this.contains(from) && this.contains(to)))
            throw new IllegalArgumentException("Nonexistent vertex");
        neighbors.get(from).remove(to);
    }
    
    /**
     * Report (as a Map) the out-degree of each vertex.
     */
    public Map<T,Integer> outDegree () {
        Map<T,Integer> result = new HashMap<T,Integer>();
        for (T v: neighbors.keySet()) result.put(v, neighbors.get(v).size());
        return result;
    }
    
    /**
     * Report (as a Map) the in-degree of each vertex.
     */
    public Map<T,Integer> inDegree () {
        Map<T,Integer> result = new HashMap<T,Integer>();
        for (T v: neighbors.keySet()) result.put(v, 0);       // All in-degrees are 0
        for (T from: neighbors.keySet()) {
            for (T to: neighbors.get(from)) {
                result.put(to, result.get(to) + 1);           // Increment in-degree
            }
        }
        return result;
    }
    
    /**
     * Report (as a List) the topological sort of the vertices; null for no such sort.
     */
    public List<T> topSort () {
        Map<T, Integer> degree = inDegree();
        // Determine all vertices with zero in-degree
        Stack<T> zeroVerts = new Stack<T>();        // Stack as good as any here
        for (T v: degree.keySet()) {
            if (degree.get(v) == 0) zeroVerts.push(v);
        }
        
        // Determine the topological order
        List<T> result = new ArrayList<T>();
        while (!zeroVerts.isEmpty()) 
        {
            T v = zeroVerts.pop();                  // Choose a vertex with zero in-degree
            result.add(v);                          // Vertex v is next in topol order
            // "Remove" vertex v by updating its neighbors
            for (T neighbor: neighbors.get(v)) 
            {
                degree.put(neighbor, degree.get(neighbor) - 1);
                // Remember any vertices that now have zero in-degree
                if (degree.get(neighbor) == 0) zeroVerts.push(neighbor);
            }
        }
        // Check that we have used the entire graph (if not, there was a cycle)
        if (result.size() != neighbors.size()) 
        	return null;
        
        return result;
    }
    
    /**
     * True iff graph is a dag (directed acyclic graph).
     */
    public boolean isDag () {
        return topSort() != null;
    }
    

    
    /**
     * Main program (for testing).
     * @throws FileNotFoundException 
     */
    public static void main (String[] args) throws FileNotFoundException {
        // Create a Graph with Integer nodes
        Digraph<Step> graph = new Digraph<Step>();
        
		Parser parser = new Parser();
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		Planner planner = new BagMethod(parser);
		Binding binding = new Binding(parser);
        
        Step unload = parser.getAction(1);
        Step load = parser.getAction(0);
        Step fly = parser.getAction(2);
        Step init = parser.getInitialState();
        Step goal = parser.getGoalState();
        
        graph.add(goal, unload);
        graph.add(unload, fly);
        graph.add(unload, load);
        graph.add(fly, init);
        graph.add(load, init);
        graph.add(goal, init);
        graph.add(fly, load);
        

        System.out.println("The current graph: " + graph);
        System.out.println("In-degrees: " + graph.inDegree());
        System.out.println("Out-degrees: " + graph.outDegree());
        System.out.println("A topological sort of the vertices: " + graph.topSort());
        System.out.println("The graph " + (graph.isDag()?"is":"is not") + " a dag");

//        graph.add(4, 1);                                     // Create a cycle
//        System.out.println("Cycle created");
//        System.out.println("The current graph: " + graph);
//        System.out.println("A topological sort of the vertices: " + graph.topSort());
//        System.out.println("The graph " + (graph.isDag()?"is":"is not") + " a dag");
    }
}
