import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;




public class QueueMethod extends Planner
{
	

	public QueueMethod(Parser p) throws FileNotFoundException 
	{
		super(p);
		
		this.key=1;
		Actions = new LinkedList <Step>();
		Links = new ArrayList <CausalLink>();	
		openPrecon = new LinkedList <OpenPrecondition>();

		
		ordering = new Order(null,null);
		Step goalState = p.getGoalState();
		goalState.setStepId(0);
		ordering.addStep(goalState);
		order.put(0, ordering);
		
		Actions.add(0, parser.getGoalState());
		//openPrecondition= new OpenPrecondition(0,null);
		this.addGoalOpenPrecondition();

		
	}
	
	

	/**
	 * This function is to add preconditions to the openPreconditions queue
	 * @param step
	 */
	public void addPreconditions(Step step)
	{
		//increment the step Id by one
		//StepId+=1;
		int stepId = step.getStepId();
		
		int preconditionsNum = step.getPreconditionSize();
		for(int i=0; i<preconditionsNum;i++)
		{
			
			//openPrecondition.addStep(StepId);
			
			OpenPrecondition object = new OpenPrecondition(stepId,null);

			object.addOpenPrcondition(step.getPreconditions(i));
			object.addStep(stepId);
			
			openPrecon.addLast(object);
			
			//System.out.print(step.getPreconditions(i).toString() +"\t" + step.getStepId());

		}
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

		this.updateOrdering();
		//this.getOrdering();
		
		for(int i=0;i<order.size();i++)
		{
			System.out.print(order.get(i).getStep().getStepName());
			int size = order.get(i).sizeSteps(); 
			for(int f=0; f<size;f++)
			{
				
				System.out.print("<"+order.get(i).getStepInArray(f).getStepName()+">");

			}
			System.out.print("\n");
		}
		
		
//		//to print out the preconditions in every action
//		for(int i =0; i< Actions.size();i++)
//		{
//			int sizeprecon = Actions.get(i).getPreconditionSize();
//			for(int f=0; f< sizeprecon;f++)
//			{
//				System.out.println(Actions.get(i).getPreconditions(f)+"  ");
//			}
//			System.out.println("\n");
//		}	
		

		
		
		this.printOutSolution();
	}
		
	
	
	
	
	/**
	 * This function prints out the solution set
	 */
	public void printOutSolution()
	{
		LinkedList <Step> orderedSloution = new LinkedList<Step>();

		for(int i = order.size() -1; i> 0 ;i--)
		{

			int size = order.get(i).getArray().size();
			for(int f=0;f<size;f++)
			{
				if(!(orderedSloution.contains(order.get(i).getStepInArray(f))))
				{
					orderedSloution.addLast(order.get(i).getStepInArray(f));
				}
			}
			
			if(!(orderedSloution.contains(order.get(i).getStep())))
			{
				orderedSloution.addLast(order.get(i).getStep());
			}
		}
		
		for(int i =0; i<orderedSloution.size();i++)
		{
			System.out.println(orderedSloution.get(i).getStep().toString());
		}
	}

}
