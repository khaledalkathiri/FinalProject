import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//when we connect two steps
public class Ordering
{



	//LinkedList <String> openPreconditions = new LinkedList <String>();
	private Map <Step, List<Step>> neighbors = new HashMap<Step,List<Step>>();



	public void add (Step vertex) 
	{
		if (neighbors.containsKey(vertex)) 
			return;
		neighbors.put(vertex, new ArrayList<Step>());
	}

	public boolean contains (Step vertex) 
	{
		return neighbors.containsKey(vertex);
	}


	public void add (Step from, Step to)
	{
		this.add(from);
		this.add(to);
		neighbors.get(from).add(to);
	}

}
