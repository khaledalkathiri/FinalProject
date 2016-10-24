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

	//LinkedList <Literal> openPreconditions = new LinkedList <Literal>();
	//LinkedList <Step> openPreconditionSteps = new LinkedList <Step>();		//parallel to the openPrecondition
	LinkedList <Step>  Actions = new LinkedList <Step>();
	ArrayList <CausalLink>  Links = new ArrayList <CausalLink>();

	
	LinkedList <OpenPrecondition> openPrecon = new LinkedList <OpenPrecondition>();


	private Parser parser;
	private Literal literal;
	private Binding binding;
	private Step step;
	private CausalLink causalLink;
	private Ordering ordering;
	private int key;
	private OpenPrecondition openPrecondition;


	public Planner(Parser p) throws FileNotFoundException
	{
		id.put(0, p.getInitialState());
		id.put(1, p.getGoalState());
		this.key=2;
		this.parser = p;
		Actions = new LinkedList <Step>();
		Links = new ArrayList <CausalLink>();

		
		openPrecon = new LinkedList <OpenPrecondition>();

		binding = new Binding(p);
		ordering = new Ordering();
		ordering.add(p.getInitialState(), p.getGoalState());
		openPrecondition= new OpenPrecondition(null,null);

	}




	public void search() throws FileNotFoundException
	{
		//using the bag method to shuffle all the actions in the Domain
		//parser.randomizeActions();
		
		
		OpenPrecondition precondition;

		//adding the goal preconditions to the queue
		step = parser.getGoalState();
		this.addGoalOpenPrecondition();


		//while(!(openPreconditions.isEmpty()))
		while(!(openPrecon.isEmpty()))
		{
			//get the first open precondition in the queue
			precondition = this.getOpenPrecondition();
			System.out.println("The openPrecondition:	"+ precondition.getOpenPrecondtion());


			//search for an effect in the initial state to satisfy it (if there is)
			if(binding.isBounded(precondition.getOpenPrecondtion()))
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
				boolean isFoundSimilarInInitialStat = this.searchSimilarInInitialState(precondition);
				System.out.println("Simailr In Initial State?	"+isFoundSimilarInInitialStat);
				if((isFoundSimilarInInitialStat))
				{
					System.out.println("Found simialr");
					System.out.println(precondition.getOpenPrecondtion());

					this.removeOpenPrecondition();

				}
				
				else
				{
					//search for an action in the action domain to satisfy the open precondition
					//add the action to the plan
					this.searchEffectsInActionDomain(precondition);

				}

			}

			//			for(Literal pre :openPreconditions)
			//			{
			//				System.out.println(pre.toString());
			//			}
			//
			System.out.println("\n\n");

		}
		
		
		for(CausalLink pre :Links)
		{
			System.out.println(pre.getPrecondition().isExcuted()+ "--->"+pre.toString());
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
			//openPreconditions.addLast(parser.getGoalPreconditions(i));
			//openPreconditionSteps.addLast(parser.getGoalState());
			
			OpenPrecondition object = new OpenPrecondition(null,null);

			object.addOpenPrcondition(parser.getGoalPreconditions(i));
			object.addStep(parser.getGoalState());
			openPrecon.add(object);
			
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
	public boolean searchEffectInInitialState(OpenPrecondition openPrecondition)
	{
		causalLink= new CausalLink();

		Literal precondition= openPrecondition.getOpenPrecondtion();
		int effSize = parser.getProblemDomainEffectsSize();
		for(int i=0; i< effSize;i++)
		{
			String temp = precondition.toString();
			if(temp.equals(parser.getIntialStateEffects(i).toString()))
			{
				System.out.println("YESSSSSSSSSS");
				System.out.print(precondition.toString());
				
				causalLink.addLink(parser.getInitialState(), precondition);
				Links.add(causalLink);

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
	public boolean searchEffectsInActionDomain(OpenPrecondition openPrecondition)
	{
		Literal precondition= openPrecondition.getOpenPrecondtion();
		causalLink= new CausalLink();

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





						//add ordering
						//ordering.add(step, openPreconditionSteps.getFirst());


						//remove the precondition from the queue
						if(binding.isBounded(precondition))
						{
							System.out.println("dequuuuued --->"+precondition.toString());
							addPreconditions(step);
							
							//Links.add(causalLink.addLink(openPrecondition.getStep(), precondition));

							//adding the new step to the array of actions
							Actions.addLast(step);
							id.put(key, step);
							key++;
							
							//add causal Link
							causalLink.addLink(step, precondition);
							Links.add(causalLink);
							
							this.removeOpenPrecondition();

						}
						else
						{
							//bind the next precondition to get the current precondition dequeued
							Literal pref = binding.bindNextLiterals(step, precondition);
							
							//to bind the whole step with the new bounded precondition In C1 P1
							binding.bindStepByPreocndition(openPrecondition.getStep(),precondition);
							System.out.println("Not bound"+ pref.toString());
							addPreconditions(step);

							//Links.add(causalLink.addLink(openPrecondition.getStep(), precondition));

							//adding the new step to the array of actions
							Actions.addLast(step);
							id.put(key, step);
							key++;
							
							//add causal Link
							causalLink.addLink(step, precondition);
							Links.add(causalLink);
							
							this.removeOpenPrecondition();
						}
						return true;

					}
				}
			}
		}
		return false;
	}


	/**
	 * This methods searches for a literal in the intial state that is similar to it but not fully bounded.
	 * @param precondition
	 * @return
	 */
	public boolean searchSimilarInInitialState(OpenPrecondition openPrecondition)
	{
		Literal precondition= openPrecondition.getOpenPrecondtion();
		causalLink= new CausalLink();

		//Literal newPrecondition = precondition;

		
		ArrayList <Literal> arry = new ArrayList <Literal>();
		int sizeEffectInitailState = parser.getProblemDomainEffectsSize();	

		arry.clear();

		int index=0;
		for(int y =0; y< sizeEffectInitailState;y++)		//The size of effect in initial state
		{

			//CargoAt == CargoAt
			if(precondition.getLiteralName().equals(parser.getIntialStateEffects(y).getLiteralName()))		
			{
				arry.add(index,parser.getIntialStateEffects(y));
				index++;

			}
		}


		//loop through the array to check if there is a match in the initial State
		for(int f=0; f< arry.size();f++)
		{
			int paraBounded = binding.checkParaBounded(precondition);

			Literal temp = arry.get(f);	//CargoAt C1 SFO & CargoAt C2 JFK
			if(precondition.getLiteralParameters(paraBounded).equals(temp.getLiteralParameters(paraBounded)))
			{

				int paraNotBounded = binding.checkParaNotBounded(precondition);
				String groundLetter = precondition.getLiteralParameters(paraNotBounded);
				String newVariable = temp.getLiteralParameters(paraNotBounded);

				binding.bindPrecondtion(precondition, groundLetter, newVariable);
				binding.bindStep(openPrecondition.getStep(), groundLetter, newVariable);
				
				causalLink.addLink(parser.getInitialState(), precondition);
				Links.add(causalLink);
				return true;
			}
		}
		return false;
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
			//openPreconditions.addLast(step.getPreconditions(i));
			//openPreconditionSteps.addLast(step);
			
			openPrecondition.addStep(step);
			
			OpenPrecondition object = new OpenPrecondition(null,null);

			object.addOpenPrcondition(step.getPreconditions(i));
			object.addStep(step);
			
			openPrecon.addLast(object);
			
			//System.out.print(step.getPreconditions(i).getLiteralName());
			//System.out.println(step.getPreconditions(i).getLiteralParameters(0));
			//System.out.println(openPreconditionSteps.size() + "		"+openPreconditions.size());

		}
	}





	public OpenPrecondition getOpenPrecondition()
	{
		return openPrecon.getFirst();
	}

	public OpenPrecondition removeOpenPrecondition()
	{
		
		return openPrecon.removeFirst();
	}






}
