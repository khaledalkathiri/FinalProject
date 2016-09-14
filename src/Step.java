import java.util.ArrayList;

public class Step 
{
	
	private  int index;
	private  boolean executed;
	private String name;
	private Step step;
	
	
	private Preconditions precondition;
	private Effects effect;
	
	
	public Step(int index, String name, Preconditions precondition, Effects effect)//boolean executed)
	{
		this.index = index;
		this.name = name;
		this.effect = effect;
		this.precondition = precondition;
		
		//this.executed = executed;
		
	}
	
	
	public String getEffects(int index)
	{
		return effect.getEffect(index);
	}
	
	public void addEffects(String effect)
	{
		 this.effect.addEffect(effect);
	}
	
	public String getPreconditions(int index)
	{
		return precondition.getPrecondition(index);
	}
	
	public void addPreconditions(String precondition)
	{
		 this.precondition.addPrecondition(precondition);
	}

}
