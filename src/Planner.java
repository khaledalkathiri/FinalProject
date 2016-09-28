import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Planner 
{

	
	private Step intialState;
	private Step goalState;
	

	
	HashMap <Step,Integer> id = new HashMap<Step, Integer>();
	
	ArrayList <Step>  Actions = new ArrayList <Step>();
	LinkedList <String> openPreconditions = new LinkedList <String>();
	
	private Parser parser;
	
	private String problemName;
	private String domainName;

	
	public Planner(String problemName, String domainName) throws FileNotFoundException
	{
		id.put(this.intialState, 0);
		id.put(this.goalState, 1);
		parser = new Parser();
		
		Actions = new ArrayList <Step>();
		openPreconditions = new LinkedList <String>();
		
		this.problemName = problemName;
		this.domainName = domainName;
			
		
	}
	
	public void search() throws FileNotFoundException
	{
		String precondition;
		parser.parseProblem(problemName);
		parser.parseDomain(domainName);

		this.addGoalOpenPrecondition();
		
		precondition = this.getOpenPrecondition();
		this.searchEffectsInActionDomain(precondition);
		
		
		
	}
	
	
	
	/**
	 * This function is to add the goal preconditions to the openPrecondition array
	 * @throws FileNotFoundException
	 */
	public void addGoalOpenPrecondition() throws FileNotFoundException
	{

		int i;
		for(i = 0; i < parser.getProblemDomainPreconditionSize(1); i++)
		{
			openPreconditions.addLast(parser.getGoalPreconditions(i));

		}
	}
	
	
	/**
	 * This function searches for an effect to solve an open precondition
	 * @param precondition
	 */
	public void searchEffectsInActionDomain(String precondition)
	{
		int stepsNum = parser.getActionDomainSize();
		int i;
		for(i=0; i< stepsNum;i++)
		{
			int effectNum = parser.getActionsDomainEffectSize(i);
			int f;
			for(f=0; f< effectNum; f++)
			{
				if(precondition.equals(parser.getActionsEffects(i, f)))
				{
					//adding the new action that satisfies the precondition to ations array
					Actions.add(parser.getAction(i));
					
					//remove the preconditon from the queue
					this.removeOpenPrecondition();
					
					//adding the new preconditions to the array of openPreconditons 
					addPreconditions(i);
					
					//System.out.println(parser.getActionsEffects(i, f));
				}
			}
		}
	}
	
	/**
	 * This function is to add preconditions to the openPreconditions queue
	 * @param step
	 */
	public void addPreconditions(int step)
	{
		int preconditionsNum = parser.getActionsDomainPreconditionSize(step);
		int i;
		for(i=0; i<preconditionsNum;i++)
		{
			openPreconditions.addLast(parser.getActionsPreconditions(step, i));
			System.out.println(parser.getActionsPreconditions(step, i));
		}
	}
	
	
	
	
	
	public String getOpenPrecondition()
	{
		return openPreconditions.getFirst();
	}
	
	public String removeOpenPrecondition()
	{
		return openPreconditions.removeFirst();
	}
	


	

	public String getDomainName() 
	{
		return domainName;
	}
	
	public String getProblemName()
	{
		return problemName;
	}
	
	public void setDomainName(String domainName)
	{
		this.domainName = domainName;
	}
	
	public void setProblemName(String problemName)
	{
		this.problemName =problemName;
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
//
//	public void addIntialState(Step s)
//	{
//		steps.add(0, intialState);
//	}
//	
//	public Step getIntialState()
//	{
//		return steps.get(0);
//	}
//	
//	public void addGoalState(Step s)
//	{
//		steps.add(1,goalState);
//	}
//	
//	public Step getGoalState()
//	{
//		return steps.get(1);
//	}
//	
//	public void addStep(Step s)
//	{
//		steps.add(s);
//	}
	
	
}
