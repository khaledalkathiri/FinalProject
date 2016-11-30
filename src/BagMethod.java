import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;




public class BagMethod extends Planner
{
	


	public BagMethod(Parser p) throws FileNotFoundException 
	{
		super(p);
		
		this.key=1;
		Actions = new LinkedList <Step>();
		Links = new ArrayList <CausalLink>();	
		openPrecon = new LinkedList <OpenPrecondition>();

		
		Step goalState = p.getGoalState();
		goalState.setStepId(0);
		
		Actions.add(0, parser.getGoalState());
		this.addGoalOpenPrecondition();
		this.shufflePreconditions(); //To shuffle the goal preconditions
		
	}
	
	
	public void shufflePreconditions()
	{
		Collections.shuffle(openPrecon);
	}

	/**
	 * This function is to add preconditions to the openPreconditions queue
	 * @param step
	 */
	public void addPreconditions(Step step)
	{

		int stepId = step.getStepId();
		
		int preconditionsNum = step.getPreconditionSize();
		for(int i=0; i<preconditionsNum;i++)
		{
						
			OpenPrecondition object = new OpenPrecondition(stepId,null);
			object.addOpenPrcondition(step.getPreconditions(i));
			object.addStep(stepId);
			
			openPrecon.addLast(object);
			
			//System.out.print(step.getPreconditions(i).toString() +"\t" + step.getStepId());

		}
		this.shufflePreconditions();
	}


	public void search() throws FileNotFoundException
	{
		while(!(openPrecon.isEmpty()))
		{
			if(!(this.resolveOpenPrecondition()))
			{
				System.out.println("No Plan Found");
				break;
			}

			this.updateCausalLinks();
			System.out.println("\n\n");
			
			if(!(this.CheckThreats()))
			{
				System.out.println("No Plan Found");
				break;
			}

		}


		//to print out the solution if it exists
		if(openPrecon.isEmpty())
		{
			
			//this.tempmethod();

			
			System.out.println("The Following plan has been found:");
			this.printOutSolution();

		}
	}


	public boolean resolveOpenPrecondition() throws FileNotFoundException
	{



		OpenPrecondition precondition;

		//get the first open precondition in the queue
		precondition = this.getOpenPrecondition();
		System.out.println("The openPrecondition:	"+ precondition.getOpenPrecondtion());
		System.out.println("Action is "+ Actions.get(precondition.getStepID()).getStepName()+
				"	ActionID is "+precondition.getStepID());


		//search for an effect in the initial state to satisfy it (if there is)
		if(binding.isBounded(precondition.getOpenPrecondtion()))
		{
			boolean isFoundInIntialState = this.searchEffectInInitialState(precondition);
			System.out.println("In Initial State?	"+isFoundInIntialState);

			if(!(isFoundInIntialState))
			{
				if(!( this.searchInEffects(precondition)))
				{
					boolean isFoundinActionDomain =this.searchEffectsInActionDomain(precondition);
					if(!(isFoundinActionDomain))
					{
						return false;
					}
				}
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
				return true;

			}

			else
			{
				if((this.searchSimilarInEffects(precondition)))
				{
					return true;
				}
				else
				{
					//search for an action in the action domain to satisfy the open precondition
					//add the action to the plan
					boolean isFoundinActionDomain =this.searchEffectsInActionDomain(precondition);
					if(!(isFoundinActionDomain))
					{
						System.out.println("No Plan found");
						return false;
					}
				}
			}
		}

		return true;

	}

	public void tempmethod()
	{
		
		
		for(CausalLink pre :Links)
		{
			System.out.println(pre.getPrecondition().getOpenPrecondtion().isNegative()+ "--->"+pre.toString());
		}
		
		System.out.println(Links.size());

		
		this.printOutSolution();
	}
		
	
	
	
	
	
	
	/**
	 * This function prints out the solution set
	 */
	public void printOutSolution()
	{
		graph.topSort();
		if(!(graph.isDag()))
		{
			System.out.println("No plan");
		}
		else
		{
		int size = graph.topSort().size();
		for(int i= size -1;i>=0;i--)
		{
			System.out.println(graph.topSort().get(i));
		}
		}
	}

}
