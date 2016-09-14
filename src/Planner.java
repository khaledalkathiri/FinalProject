import java.util.ArrayList;
import java.util.HashMap;

public class Planner 
{
	private Effects effects;
	private Preconditions precondition;
	
	private Step intialState;
	private Step goalState;
	
	//HashMap <Step,Integer> id = new HashMap();
	
	ArrayList <Step>  steps = new ArrayList <Step>();

	
	public Planner()
	{
		this.intialState = new Step(1,"Intial State", null ,effects);
		this.goalState = new Step (0, "Goal State", precondition, null);
				
	}
	
	public void addIntialState(Step s)
	{
		steps.add(0, s);
	}
	
	public Step getIntialState()
	{
		return steps.get(0);
	}
	
	public void addGoalState(Step s)
	{
		steps.add(goalState);
	}
	
	public Step getGoalState()
	{
		return goalState;
	}
	
	public void addStep(Step s)
	{
		steps.add(s);
	}
	
}
