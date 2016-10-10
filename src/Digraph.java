import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * An example class for directed graphs.  The vertex type can be specified.
*/
public class Digraph<V> 
{
    
    /**
     * The implementation here is basically an adjacency list, but instead
     * of an array of lists, a Map is used to map each vertex to its list of 
     * adjacent vertices.
     */ 
	
    private Map <Step, List<Step>> neighbors = new HashMap<Step,List<Step>>();
    
    
    /**
     * String representation of graph.
     */
    public String toString () 
    {
        StringBuffer s = new StringBuffer();
        for (Step v: neighbors.keySet())
        	s.append("\n    " + v.getStepName() + " -> " + neighbors.get(v.getStepName()));
        return s.toString();                
    }
    
    /**
     * Add a vertex to the graph.  Nothing happens if vertex is already in graph.
     */
    public void add (Step vertex) 
    {
        if (neighbors.containsKey(vertex)) 
        	return;
        neighbors.put(vertex, new ArrayList<Step>());
    }
    
    /**
     * True iff graph contains vertex.
     */
    public boolean contains (Step vertex) 
    {
        return neighbors.containsKey(vertex);
    }
    
    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public void add (Step from, Step to)
    {
        this.add(from);
        this.add(to);
        neighbors.get(from).add(to);
    }
    
    /**
     * Remove an edge from the graph.  Nothing happens if no such edge.
     * @throws IllegalArgumentException if either vertex doesn't exist.
     */
    public void remove (Step from, Step to)
    {
        if (!(this.contains(from) && this.contains(to)))
            throw new IllegalArgumentException("Nonexistent vertex");
        neighbors.get(from).remove(to);
    }
    
    /**
     * Report (as a Map) the out-degree of each vertex.
     */
    public Map<String,Integer> outDegree () 
    {
        Map<String,Integer> result = new HashMap<String,Integer>();
        for (Step v: neighbors.keySet())
        	result.put(v.getStepName(), neighbors.get(v).size());
        return result;
    }
    
    /**
     * Report (as a Map) the in-degree of each vertex.
     */
    public Map<String,Integer> inDegree () 
    {
        Map<String,Integer> result = new HashMap<String,Integer>();
        for (Step v: neighbors.keySet()) 
        	result.put(v.getStepName(), 0);      			 // All in-degrees are 0
        for (Step from: neighbors.keySet())
        {
            for (Step to: neighbors.get(from)) 
            {
                result.put(to.getStepName(), result.get(to.getStepName()) + 1);           // Increment in-degree
            }
        }
        return result;
    }
    


    
    /**
     * Main program (for testing).
     * @throws FileNotFoundException 
     */
    public static void main (String[] args) throws FileNotFoundException {
        // Create a Graph with Integer nodes
        Digraph<Step> graph = new Digraph<Step>();
        Step step = new Step(null,null,null);
        Parser p = new Parser();
        Planner planner = new Planner(p);
        p.parseDomain("Domain.txt");
        
        graph.add(p.getAction(0), p.getAction(1));
        graph.add(p.getAction(0), p.getAction(2));
        graph.add(p.getAction(2), p.getAction(1));
        
        // Tetrahedron with tail
        System.out.println("The current graph: " + graph);
        System.out.println("In-degrees: " + graph.inDegree());
        System.out.println("Out-degrees: " + graph.outDegree());
       
    }
}