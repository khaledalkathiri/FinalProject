//import java.util.ArrayList;
//
//public  class Steps
//{
//	//private Effects effects;
//	//private Preconditions preconditions;
//	
//	
//	//private String effects;
//	//private String preconditions;
//	
//	ArrayList <Steps>  steps = new ArrayList <Steps>();
//
//	ArrayList <String>  effectArray = new ArrayList <String>();
//	ArrayList <String>  preconditionArray = new ArrayList <String>();
//	
//	
//	public Steps()//Effects effects, Preconditions preconditions)
//	{
//		//this.effectArray = new ArrayList <String>();
//		//this.preconditionArray = new ArrayList <String>();
//
//		//this.effects = effects;
//		//this.preconditions = preconditions;
//		//this.steps = new ArrayList <Steps>();
//
//	}
//	
////	public Steps(Effects effects, Preconditions preconditions)
////	{
////		this.effects = effects;
////		this.preconditions = preconditions;
////		//this.steps = new ArrayList <Steps>();
////
////	}
//	
//
//	public boolean addStep(Steps step)
//	{
//		//step = new Steps();
//		if (steps.add(step))
//		return true;
//		
//		else
//			return false;
//	}
//	
//
//	public Steps getStep(int index)
//	{
//		return steps.get(index);
//	}
//	
//	
//	
//	
//	
//	public String getEffects(int index, int index2)
//	{
//		return steps.get(index).effectArray.get(index2);
//	}
//	
//	public String getPreconditions(int index, int index2)
//	{
//		return steps.get(index).preconditionArray.get(index2);
//	}
//	
//	
//	/**
//	 * This function is to add effects to the step
//	 * @param effect
//	 */
//	public void addEffects(String effect)
//	{
//		effectArray.add(effect);
//	}
//	
//
//	public void addPreconditions(String precondition)
//	{
//		preconditionArray.add(precondition);
//	
//	}
//}