import java.util.ArrayList;

public class Order 
{
	

	private Step step;
	private ArrayList<Step> ordering;
	
	public Order(Step step, ArrayList<Step> ordering)
	{
		this.step = step;
		this.ordering= new ArrayList<Step>();
	}
	
	public void addStep(Step step)
	{
		this.step=step;
	}
	
	
	public void addOrder(Step step)
	{
		this.ordering.add(step);
	}
	
	
	/**
	 * This function returns the step 
	 * @return
	 */
	public Step getStep()
	{
		return step;
	}
	
	
	/**
	 * This function returns a step in the array of ordering steps
	 * @return
	 */
	public Step getStepInArray(int index)
	{
		return ordering.get(index);
	}
	
	public ArrayList<Step> getArray()
	{
		return this.ordering;
	}
	
	public int sizeSteps()
	{
		return ordering.size();
	}
	
	
	
	public String toString()
	{
		String order ="";
		int size = this.sizeSteps();
		
		for(int i =0;i<size;i++)
		{
			order = order + this.getStepInArray(i).getStepName() + "  ";
		}
		
		return  this.getStep().getStepName() +"-->"+ order;
	}
	
}
