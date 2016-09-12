import java.util.ArrayList;

public  class Steps
{
	private Effects effects;
	private Preconditions precondition;
	
	ArrayList <Steps>  obj = new ArrayList <Steps>();

	
	public Steps(Effects effects, Preconditions precondition)
	{
		this.effects = effects;
		this.precondition = precondition;
		//this.obj = new ArrayList <Steps>();

	}
	

	public void addStep(Steps step)
	{
		obj.add(step);
	}
	
	public Steps getStep(int index)
	{
		return obj.get(index);
	}
	
	public String getEffects(int index)
	{
		return effects.obj.get(index);
	}
	
	public String getPreconditions(int index)
	{
		return precondition.obj.get(index);
	}
	
}
