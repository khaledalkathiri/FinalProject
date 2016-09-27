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
			
		
//		domainName = parser.getDomainName();
		//parser = new Parser(domainName,problemName);
		//parser.parseDomain(domainName);
		//parser.parseProblem(problemName);
		//parser.parseProblem(parser.getProblemName());
		
	}
	
	public void search() throws FileNotFoundException
	{
		String precondition;
		parser.parseProblem(problemName);
		parser.parseDomain(domainName);

		this.addGoalOpenPrecondition();
		
		precondition = this.getOpenPrecondition();
		this.searchActionDomain();
		
		
		
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
	
	
	
	public void searchActionDomain()
	{
		int stepsNum = parser.getActionDomainSize();
		int i;
		for(i=0; i< stepsNum;i++)
		{
			int effectNum = parser.getActionsDomainEffectSize(i);
			int f;
			for(f=0; f< effectNum; f++)
			{
				System.out.print(parser.getActionsEffects(i, f));
			}
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
	
	
	public void searchSteps(Object step)
	{
		
	}
	
}
