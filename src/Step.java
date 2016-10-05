import java.util.ArrayList;

public class Step 
{
	
	//private  int index;
	//private  boolean executed;
	private String name;
	
	private Literal literal;
	
	ArrayList <Literal>  preconditionArray = new ArrayList <Literal>();
	ArrayList <Literal>  effectArray = new ArrayList <Literal>();

	
	
	
	public Step(String name, ArrayList <Literal> preconditionArray, ArrayList <Literal>  effectArray)
	{
		 this.preconditionArray = new ArrayList <Literal>();
		 this.effectArray = new ArrayList <Literal>();
		
		this.name = name;		
	}
	
	
	
	//Not tested
	public String getStepName()
	{
		return name;
	}
	
	public void addStepName(String name)
	{
		this.name = name;
	}
	
	
	public Literal getEffects(int index)
	{
		return effectArray.get(index);
	}
	
	
	public void addEffects(Literal effect)
	{
		 this.effectArray.add(effect);
	}
	
	public Literal getPreconditions(int index)
	{
		return preconditionArray.get(index);
	}
	
	
	public void addPreconditions(Literal precondition)
	{
		 this.preconditionArray.add(precondition);
	}
	
	
	public int getPreconditionSize()
	{
		return preconditionArray.size();
	}
	
	public int getEffectsSize()
	{
		return effectArray.size();
	}
	

	
	
	

}
