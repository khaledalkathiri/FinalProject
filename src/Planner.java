import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Planner 
{





	HashMap <Integer, Step> id = new HashMap<Integer, Step>();

	LinkedList <Literal> openPreconditions = new LinkedList <Literal>();
	LinkedList <Step> openPreconditionSteps = new LinkedList <Step>();		//parallel to the openPrecondition
	ArrayList <Step>  Actions = new ArrayList <Step>();
	ArrayList <CausalLink>  Links = new ArrayList <CausalLink>();
	ArrayList <ObjectInfo> temp = new ArrayList <ObjectInfo>();				//When we have more than one option



	private Parser parser;
	private Literal literal;
	private Binding binding;
	private Step step;
	private CausalLink causalLink;
	private Ordering ordering;
	private ObjectInfo object;


	public Planner(Parser p) throws FileNotFoundException
	{
		id.put(0, p.getInitialState());
		id.put(1, p.getGoalState());
		this.parser = p;
		Actions = new ArrayList <Step>();
		openPreconditions = new LinkedList <Literal>();
		openPreconditionSteps = new LinkedList <Step>();

		binding =new Binding(null,null);
		//causalLink= new CausalLink(null,null);
		ordering = new Ordering();
		ordering.add(p.getInitialState(), p.getGoalState());
		
	}




	public void search() throws FileNotFoundException
	{
		//using the bag method to shuffle all the actions in the Domain
		//parser.randomizeActions();
		
		
		Literal precondition;

		//adding the goal preconditions to the queue
		step = parser.getGoalState();
		this.addGoalOpenPrecondition();
		


		//get the first open precondition in the queue
		precondition = this.getOpenPrecondition();
		
		

		//search for an effect in the initial state to satisfy it (if there is)
		if(binding.isBounded(precondition))
		{
			boolean isFoundInIntialState = this.searchEffectInInitialState(precondition);
			System.out.println(precondition.toString() + "  "+isFoundInIntialState);
		}


		
		//search for an action in the action domain to satisfy the open precondition
		//add the action to the plan
		this.searchEffectsInActionDomain(precondition);


		for(Literal pre :openPreconditions)
		{
			System.out.println(pre.toString());
		}



	}


	/**
	 * This function is to add the goal preconditions to the openPrecondition array
	 * @throws FileNotFoundException
	 */
	public void addGoalOpenPrecondition() throws FileNotFoundException
	{

		//to get how many preconditions in the goal state
		for(int i = 0; i < parser.getProblemDomainPreconditionSize(); i++)
		{
			openPreconditions.addLast(parser.getGoalPreconditions(i));
			openPreconditionSteps.addLast(parser.getGoalState());
			//System.out.println(openPreconditionSteps.get(0).getPreconditions(i).toString());

			//System.out.println(openPreconditionSteps.size() + "		"+openPreconditions.size());

		}

	}

	
	/**
	 * This method is to check the initial state if it satisfies the openPrecondition
	 * Works only with the bounded precondition
	 * @param precondition
	 * @return true if the precondition is found
	 * @return false if the precondition is not found
	 */
	public boolean searchEffectInInitialState(Literal precondition)
	{
		int effSize = parser.getProblemDomainEffectsSize();
		for(int i=0; i< effSize;i++)
		{
			String temp = precondition.toString();
			if(temp.equals(parser.getIntialStateEffects(i).toString()))
			{
				System.out.println("YESSSSSSSSSS");
				System.out.print(precondition.toString());
				System.out.println(parser.getIntialStateEffects(i).toString());
				return true;
			}
		}
		return false;
	}

	/**
	 * This function searches for an effect in ActionDomain to solve an open precondition
	 * @param precondition
	 */
	public void searchEffectsInActionDomain(Literal precondition)
	{
		temp = new ArrayList <ObjectInfo>();
		object = new ObjectInfo(0,0);
		int stepsNum = parser.getActionDomainSize();		//how many action in the domain
		for(int i=0; i< stepsNum;i++)
		{
			int effectNum = parser.getActionsDomainEffectSize(i);		//how many effects in every action
			for(int f=0; f< effectNum; f++)
			{
				//System.out.println(parser.getActionsEffects(i, f).getLiteralName());
				if(precondition.getLiteralName().equals(parser.getActionsEffects(i, f).getLiteralName()))
				{
					//The literal is not negative
					if(!(parser.getActionsEffects(i, f).isNegative()))
					{
						object = new ObjectInfo(i,f);
						temp.add(object);
						//temp.add(parser.getActionsEffects(i, f));
						//System.out.print(parser.getActionsEffects(i, f).getLiteralName());
						//System.out.println(parser.getActionsEffects(i, f).getLiteralParameters(0));
						
						//To get the step 
						//step = parser.getAction(i);

						//bind the variables 
						//binding.bindLiterals(step, precondition , f);

						//add ordering



						//remove the precondition from the queue
						//this.removeOpenPrecondition();

						//adding the new preconditions to the array of openPreconditons 
						//addPreconditions(step);
					}
					//System.out.println(parser.getAction(i).getStepName());

				}
			}
		}
	}




	/**
	 * This function is to add preconditions to the openPreconditions queue
	 * @param step
	 */
	public void addPreconditions(Step step)
	{

		int preconditionsNum = step.getPreconditionSize();
		for(int i=0; i<preconditionsNum;i++)
		{
			openPreconditions.addLast(step.getPreconditions(i));
			openPreconditionSteps.addLast(step);
			//System.out.print(step.getPreconditions(i).getLiteralName());
			//System.out.println(step.getPreconditions(i).getLiteralParameters(0));
			//System.out.println(openPreconditionSteps.size() + "		"+openPreconditions.size());

		}
	}





	public Literal getOpenPrecondition()
	{
		return openPreconditions.getFirst();
	}

	public Literal removeOpenPrecondition()
	{
		openPreconditionSteps.removeFirst();
		return openPreconditions.removeFirst();
	}









	/**
	 * This function is to check the initial state when it satisfies an open precondition
	 * @param precondition
	 */
	public void searchEffectInInitialStateOLD(Literal precondition)
	{
		int effSize = parser.getProblemDomainEffectsSize();


		for(int i=0; i< effSize;i++)
		{
			//check the name of the literal in the initial state (At)
			if(precondition.getLiteralName().equals(parser.getIntialStateEffects(i).getLiteralName()))
			{
				//how many parameter in the found effect
				int sizeParameters = parser.getIntialStateEffects(i).sizeLiteralParameters();
				for(int x = 0; x<sizeParameters;x++)
				{
					if(binding.isBounded(precondition))		//to check the precondition if it is bounded 
					{
						int counter=0;		//(At ?? ??) === (At ?? ??)
						if(precondition.getLiteralParameters(counter).equals(parser.getIntialStateEffects(i).getLiteralParameters(counter)))
						{
							counter++;		//(At C1 ??) === (At C1 ??)
							if(precondition.getLiteralParameters(counter).equals(parser.getIntialStateEffects(i).getLiteralParameters(counter)))
							{
								System.out.println("The Variable is bounded");
								//System.out.println("found");
								break;
								//(At C1 JFK) === (At C1 JFK)
								//System.out.print(parser.getIntialStateEffects(i).getLiteralName());
								//System.out.println(parser.getIntialStateEffects(i).getLiteralParameters(x));
							}
						}
					}else						//when the precondition is not bounded
					{
						int paraNum =binding.checkParaNotBounded(precondition);
						int counter = 0;

						if(counter != paraNum)
						{
							if(precondition.getLiteralParameters(counter).equals(parser.getIntialStateEffects(i).getLiteralParameters(counter)))
							{
								System.out.println("First Paramter is good");
								break;

							}
						}
						else if(counter == paraNum)
						{
							counter++;
							if(precondition.getLiteralParameters(counter).equals(parser.getIntialStateEffects(i).getLiteralParameters(counter)))
							{
								System.out.println("The Variable is NOT bounded");

							}
						}



					}
				}
			}
		}

		//System.out.println("Nothing found");
	}




}
