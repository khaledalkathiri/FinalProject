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



	private Parser parser;
	private Literal literal;
	private Binding binding;
	private Step step;
	private CausalLink causalLink;
	private Ordering ordering;
	private int key;


	public Planner(Parser p) throws FileNotFoundException
	{
		id.put(0, p.getInitialState());
		id.put(1, p.getGoalState());
		this.key=2;
		this.parser = p;
		Actions = new ArrayList <Step>();
		openPreconditions = new LinkedList <Literal>();
		openPreconditionSteps = new LinkedList <Step>();

		binding = new Binding(p);
		causalLink= new CausalLink(null,null,null);
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


		while(!(openPreconditions.isEmpty()))
		{
		//get the first open precondition in the queue
		precondition = this.getOpenPrecondition();
		System.out.println("The openPrecondition:	"+ precondition);


		//search for an effect in the initial state to satisfy it (if there is)
		if(binding.isBounded(precondition))
		{
			boolean isFoundInIntialState = this.searchEffectInInitialState(precondition);
			System.out.println("In Initial State?	"+isFoundInIntialState);

			if(!(isFoundInIntialState))
			{
				this.searchEffectsInActionDomain(precondition);

			}
		}

		else
		{
			//here I have to add a new call for method to search in initial state to match it
			
			//search for an action in the action domain to satisfy the open precondition
			//add the action to the plan
			this.searchEffectsInActionDomain(precondition);
		}

		//			for(Literal pre :openPreconditions)
		//			{
		//				System.out.println(pre.toString());
		//			}
		//
					System.out.println("\n\n");

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
				this.removeOpenPrecondition();

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
		causalLink= new CausalLink(null,null,null);
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
						//System.out.println(parser.getActionsEffects(i, f).getLiteralParameters(0));

						//To get the step 
						step = parser.getAction(i);

						//bind the variables 
						binding.bindLiterals(step, precondition , f);

						//adding the new step to the array of actions
						Actions.add(step);
						id.put(key, step);
						key++;

						//add causal Link
						//causalLink.addLink(step, precondition, openPreconditionSteps.getFirst());
						//Links.add(causalLink);

						//add ordering
						//ordering.add(step, openPreconditionSteps.getFirst());


						//remove the precondition from the queue
						if(binding.isBounded(precondition))
						{
							System.out.println("dequuuuued --->"+precondition.toString());
							addPreconditions(step);

							this.removeOpenPrecondition();

						}
						else
						{
							//bind the next precondition to get the current precondition dequeued
							Literal pref = binding.bindNextLiterals(step, precondition);

							Step modifiedStep= binding.bindStepByPreocndition(openPreconditionSteps.getFirst(),precondition);
							System.out.println("Not bound"+ pref.toString());
							addPreconditions(step);

							this.removeOpenPrecondition();
							//System.out.println(step.getPreconditions(0).toString());
						}

						//adding the new preconditions to the array of openPreconditons 
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






}
