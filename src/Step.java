import java.util.ArrayList;

public class Step 
{
	
	//private  int index;
	//private  boolean executed;
	private String name;
	
	
	//private Preconditions precondition;
	//private Effects effect;
	//private Actions action;
	
	ArrayList <String>  preconditionArray = new ArrayList <String>();
	ArrayList <String>  effectArray = new ArrayList <String>();

	
//	
//	public Step(String name, Preconditions precondition, Effects effect)//boolean executed)
//	{
//		//this.index = index;
//		this.name = name;
//		this.effect = effect;
//		this.precondition = precondition;
//		
//		//this.executed = executed;
//		
//	}
	
	
	public Step(String name, ArrayList <String> preconditionArray, ArrayList <String>  effectArray)
	{
		 this.preconditionArray = new ArrayList <String>();
		 this.effectArray = new ArrayList <String>();
		
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
	
	
	public String getEffects(int index)
	{
		return effectArray.get(index);
	}
	
	
	public void addEffects(String effect)
	{
		 this.effectArray.add(effect);
	}
	
	public String getPreconditions(int index)
	{
		return preconditionArray.get(index);
	}
	
	
	public void addPreconditions(String precondition)
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
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///OLD methods before I change the consturctor
//	public String addStepName(String name)
//	{
//		return this.name = name;
//	}
//	
//	
//	public String getEffects(int index)
//	{
//		return effect.getEffect(index);
//	}
//	
//	public void addEffects(String effect)
//	{
//		 this.effect.addEffect(effect);
//	}
//	
//	public String getPreconditions(int index)
//	{
//		return precondition.getPrecondition(index);
//	}
//	
//	
//	/**
//	 * adding preconditions
//	 * @param precondition
//	 */
//	public void addPreconditions(String precondition)
//	{
//		 this.precondition.addPrecondition(precondition);
//	}
	
	

}
