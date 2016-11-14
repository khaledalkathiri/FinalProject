import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Planner 
{

	LinkedList <Step>  Actions = new LinkedList <Step>();
	ArrayList <CausalLink>  Links = new ArrayList <CausalLink>();

	Map <Integer, Order> order = new HashMap <Integer, Order>();

	LinkedList <OpenPrecondition> openPrecon = new LinkedList <OpenPrecondition>();



	protected Parser parser;
	protected Literal literal;
	protected Binding binding;
	protected Step step;
	protected CausalLink causalLink;
	protected Order ordering;
	protected int key;
	//protected OpenPrecondition openPrecondition;
	protected int StepId;




	public Planner(Parser p) throws FileNotFoundException
	{
		this.parser = p;
		binding = new Binding(p);
		this.StepId=0;
		Step goalState = p.getGoalState();
		goalState.setStepId(0);


	}




	public void search() throws FileNotFoundException
	{	

	}


	/**
	 * This function is to add preconditions to the openPreconditions queue
	 * @param step
	 */
	public void addPreconditions(Step step)
	{

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
			OpenPrecondition object = new OpenPrecondition(0,null);
			object.addOpenPrcondition(parser.getGoalPreconditions(i));
			object.addStep(0);
			openPrecon.addLast(object);


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

		Literal precondition= openPrecondition.getOpenPrecondtion();
		Step currentStep = Actions.get(openPrecondition.getStepID());
		int effSize = parser.getProblemDomainEffectsSize();
		for(int i=0; i< effSize;i++)
		{
			String temp = precondition.toString();
			if(temp.equals(parser.getIntialStateEffects(i).toString()))
			{
				if(!(parser.getIntialStateEffects(i).isNegative()))
				{
					System.out.println("Found In initial State");

					//this.applyNegation(currentStep);

					causalLink= new CausalLink(openPrecondition,parser.getInitialState(),parser.getIntialStateEffects(i));
					causalLink.getPrecondition().getOpenPrecondtion().setExcuted(true);
					Links.add(causalLink);

					//this.addOrdering(currentStep, parser.getInitialState());


					return true;
				}	
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
		int id= openPrecondition.getStepID();

		Step currentStep = Actions.get(openPrecondition.getStepID());


		ordering = new Order(null,null);

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
						Step newStep = new Step(step);

						//bind the variables 												
						newStep = binding.bindLiterals(newStep, precondition , f);

						StepId+=1;
						newStep.setStepId(StepId);


						addPreconditions(newStep);	


						Actions.addLast(newStep);
						this.addOrdering(currentStep, newStep);

						//this.applyNegation(boundedStep);


						//add causal Link
						causalLink= new CausalLink(openPrecondition,newStep,newStep.getEffects(f));
						causalLink.getPrecondition().getOpenPrecondtion().setExcuted(true);
						Links.add(causalLink);

						return true;

					}
				}
			}
		}
		return false;
	}

	/**
	 * This method checks if there is any causal links between these two steps 
	 * @param current
	 * @param next
	 * @return true if there is a connection
	 * @return false if there is no connection
	 */
	public boolean stepsAreConnectedWithCausalLink(Step current, Step next)
	{
		int nextStepID = next.getStepId();
		
		//int effectSize = next.getEffectsSize();
		int effectSize = current.getEffectsSize();
		System.out.println(current.getEffects(0).toString());
		
		for(int i=0;i<effectSize;i++)
		{
			for(int f=0;f <Links.size();f++)
			{
				if(Links.get(f).getEffect().toString().equals(current.getEffects(i).toString()))
				{
					if((Links.get(f).getPrecondition().getStepID() == nextStepID)&&(Links.get(f).getStepName() == next))
					{
						System.out.println(Links.get(f).toString());
						System.out.println(next.toString()+"current" + current.toString()+"Steps are connected with CL \n\n\n\n\n\n\n\n\n");
						return true;
					}
				}
			}
			
		}
		
		return false;
	}

	
	/**
	 * This method searches for an effect in the Actions array
	 * @param openPrecondition
	 * @return
	 */
	public boolean searchInEffects(OpenPrecondition openPrecondition)
	{
		Literal precondition= openPrecondition.getOpenPrecondtion();
		Step currentStep = Actions.get(openPrecondition.getStepID());
		
		int sizeActions = Actions.size();
		//bc the first action is goal
		for(int i=1;i<sizeActions;i++)
		{
			if((openPrecondition.getStepID() != i))		//not to search in the same Action's effect
			{
				int sizeEffects = Actions.get(i).getEffectsSize();
				for(int x=0;x<sizeEffects;x++)
				{	
					Literal effect = Actions.get(i).getEffects(x);
					if(	(!(effect.isNegative()))	&&(effect.toString().equals(precondition.toString())))
					{
						if(!(this.stepsAreConnectedWithCausalLink(currentStep, Actions.get(i))))
						{
							System.out.println("Found in Effects");
							this.addOrdering(currentStep, Actions.get(i));

							//add causal Link
							causalLink= new CausalLink(openPrecondition,Actions.get(i),effect);
							causalLink.getPrecondition().getOpenPrecondtion().setExcuted(true);
							Links.add(causalLink);
							
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	
	
	
	/**
	 * This function searches for any literal that can resolve the open Precondition
	 * 
	 */
	public boolean searchAnyInInitailState(OpenPrecondition openPrecondition, ArrayList<Literal> arry)
	{
		Literal precondition= openPrecondition.getOpenPrecondtion();
		Step currentStep = Actions.get(openPrecondition.getStepID());

		Collections.shuffle(arry);
		Literal literal = arry.get(0);
		
		if(!(arry.get(0).isNegative()))
		{
			int paraNotBounded = binding.checkParaNotBounded(precondition);
			String groundLetter = precondition.getLiteralParameters(paraNotBounded);
			String newVariable = arry.get(0).getLiteralParameters(paraNotBounded);
			System.out.println(newVariable);

			//to bind the dequeued precondition with the new parameters
			binding.bindPrecondtion(precondition, groundLetter, newVariable);
			binding.bindStep(currentStep, groundLetter, newVariable);

			causalLink= new CausalLink(openPrecondition,parser.getInitialState(),arry.get(0));

			causalLink.getPrecondition().getOpenPrecondtion().setExcuted(true);
			Links.add(causalLink);
			this.addOrdering(currentStep, parser.getInitialState());

			System.out.println(precondition.toString());

			return true;
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
		Step currentStep = Actions.get(openPrecondition.getStepID());

		
		ArrayList <Literal> arry = new ArrayList <Literal>();
		int sizeEffectInitailState = parser.getProblemDomainEffectsSize();	

		int index=0;
		int y;
		for( y =0; y< sizeEffectInitailState;y++)		//The size of effect in initial state
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
			if(paraBounded == -1)
			{
				if((searchAnyInInitailState(openPrecondition,arry)))
				{
					return true;
				}
				return false;
			}

			Literal temp = arry.get(f);	//CargoAt C1 SFO & CargoAt C2 JFK
			if(	(precondition.getLiteralParameters(paraBounded).equals(temp.getLiteralParameters(paraBounded)))
					&& (!(temp.isNegative())))
			{

				int paraNotBounded = binding.checkParaNotBounded(precondition);
				String groundLetter = precondition.getLiteralParameters(paraNotBounded);
				String newVariable = temp.getLiteralParameters(paraNotBounded);

				//to bind the dequeued precondition with the new parameters
				binding.bindPrecondtion(precondition, groundLetter, newVariable);
				binding.bindStep(currentStep, groundLetter, newVariable);


				causalLink= new CausalLink(openPrecondition,parser.getInitialState(),temp);
				causalLink.getPrecondition().getOpenPrecondtion().setExcuted(true);
				Links.add(causalLink);

				this.addOrdering(currentStep, parser.getInitialState());

				return true;
			}
		}
		return false;
	}

	
	/**
	 * 
	 * @param openPrecondition
	 * @return
	 */
	public boolean searchSimilarInEffects(OpenPrecondition openPrecondition)
	{
		Literal precondition= openPrecondition.getOpenPrecondtion();
		Step currentStep = Actions.get(openPrecondition.getStepID());

		Map <Integer, Literal> arry = new HashMap <Integer, Literal>();

		//ArrayList <Literal> arry = new ArrayList <Literal>();
		int sizeActions = Actions.size();	
		for(int i=1;i<sizeActions;i++)
		{
			int sizeEffects = Actions.get(i).getEffectsSize();
			for(int y =0; y< sizeEffects;y++)		
			{
				//CargoAt == CargoAt
				if((precondition.getLiteralName().equals(Actions.get(i).getEffects(y).getLiteralName()))
						&&(!(Actions.get(i).getEffects(y).isNegative())))
				{

					arry.put(Actions.get(i).getStepId(), Actions.get(i).getEffects(y));
					//arry.add(index,Actions.get(i).getEffects(y));
					//index++;

				}
			}
		}
		
		for(int key: arry.keySet())
		{
			int paraBounded = binding.checkParaBounded(precondition);
			if(paraBounded == -1)
			{
				return false;
			}
			
			Literal temp = arry.get(key);	//CargoAt C1 SFO & CargoAt C2 JFK
			if(	(precondition.getLiteralParameters(paraBounded).equals(temp.getLiteralParameters(paraBounded)))
					&& (!(temp.isNegative())))
			{
				System.out.println(Actions.get(key).toString());
				System.out.println(currentStep.toString());

				if(!(this.stepsAreConnectedWithCausalLink(currentStep, Actions.get(key))))
				{
					int paraNotBounded = binding.checkParaNotBounded(precondition);
					String groundLetter = precondition.getLiteralParameters(paraNotBounded);
					String newVariable = temp.getLiteralParameters(paraNotBounded);

					//to bind the dequeued precondition with the new parameters
					binding.bindPrecondtion(precondition, groundLetter, newVariable);
					binding.bindStep(currentStep, groundLetter, newVariable);


					
					//this.applyNegation(currentStep);
					causalLink= new CausalLink(openPrecondition,Actions.get(key),temp);
					causalLink.getPrecondition().getOpenPrecondtion().setExcuted(true);
					Links.add(causalLink);

					this.addOrdering(currentStep, Actions.get(key));

					return true;
				}
			}
		}

		return false;
	}


	//
	//	/** NOT IMPORTEDNT ANYMORE
	//	 * This function searches for a causal link thats connected to the given precondition
	//	 * @param precon
	//	 * @return step that connected to the precondition
	//	 * This function has to be revised because there could be a case where there is two causalLinks
	//	 * that might be connected to the same precondition
	//	 */
	//	public Step searchCausalLinks(Literal precon)
	//	{
	//		int sizeLinks = Links.size();
	//		Step s;
	//		for(int i=0; i<sizeLinks;i++)
	//		{
	//			if(Links.get(i).getPrecondition().getOpenPrecondtion().toString().equals(precon.toString()))
	//			{
	//
	//				s = Links.get(i).getStepName();
	//				System.out.println("\n\n\n\n\n\n\n"+ s.getStepName());
	//
	//				return s;
	//			}
	//		}
	//		return null;		
	//	}


	/**
	 * This function updates the variables between the causalLinks
	 * @param causalLink
	 */
	public void updateCausalLinks()
	{
		for(int i=0;i<Links.size();i++)
		{
			CausalLink causalLink = Links.get(i);
			//to bind variables between a step and an effect
			int oldStepId = causalLink.getPrecondition().getStepID();
			Step oldStep = Actions.get(oldStepId);
			Literal effect = causalLink.getEffect();
			binding.bindStepByPreocndition(oldStep, effect);

			//to bind variables between a precondition and a step
			Step newStep = causalLink.getStepName();
			Literal precondition = causalLink.getPrecondition().getOpenPrecondtion();
			binding.bindStepByPreocndition(newStep, precondition);

		}

	}




	/**
	 * This method is to add ordering 
	 * if the step exists in the HashMa, it will add its step to the array
	 * @param s1
	 * @param s2
	 */
	public void addOrdering(Step s1, Step s2)
	{
		if(order.containsKey(s1.getStepId()))
		{	
			if(!(order.get(s1.getStepId()).getArray().contains(s2)))
			{
				//prevent adding the initial state
				if(!(s2.equals(parser.getInitialState())))	
				{
					order.get(s1.getStepId()).addOrder(s2);

				}
			}
		}
		else
		{
			ordering = new Order(null,null);
			ordering.addStep(s1);
			//prevent adding the initial state
			if(!(s2.equals(parser.getInitialState())))
			{
				ordering.addOrder(s2);
			}
			order.put(s1.getStepId(), ordering);
		}
	}


	/**
	 * This method is to update the ordering 
	 * It only should be called one time
	 */
	public void updateOrdering()
	{

		int sizeMap = order.size();
		for(int i =0;i<sizeMap;i++)
		{
			//how many step in the array
			int sizeSteps = order.get(i).sizeSteps();

			for(int f=0; f<sizeSteps;f++)
			{
				//get the first step in the array
				Step s = order.get(i).getStepInArray(f);

				//look for similar key in the map
				if(order.containsKey(s.getStepId()))
				{
					//get the size of the new step array to map it to the previous
					int sizeStepsInArray = order.get(s.getStepId()).sizeSteps();
					for(int k=0;k<sizeStepsInArray;k++)
					{
						Step u = order.get(s.getStepId()).getStepInArray(k);
						if(!(order.get(i).getArray().contains(u)))
							order.get(i).addOrder(u);

					}
				}
			}
		}
	}


	public void getOrdering()
	{
		int sizeOrdering = order.size();
		for(int i=0;i< sizeOrdering;i++)
		{
			System.out.println(order.get(i).getStep().getStepName());
		}
	}



	/**
	 * This function resolve the threat by just reordering the steps
	 * @return true if the threat is resolved
	 * @return false if the threat cannot be resolved by reordering
	 */
	public boolean rearrangeOrdering(ArrayList <CausalLink> threats)
	{
		Step s1 = Actions.get(threats.get(0).getPrecondition().getStepID());
		Step s2 = Actions.get(threats.get(1).getPrecondition().getStepID());
		Literal effect = threats.get(0).getEffect();
		if(!(this.isThereOrderingBtw(s1,s2)))
		{
			Step negatedStep = s1;
			Step normalStep = s2;
			if(this.isPreconditionNegate(s1,effect))
			{
				 negatedStep = s1;
				 normalStep = s2;
				Links.remove(threats.get(0));	//remove the link
			}
			
			if(this.isPreconditionNegate(s2,effect))
			{
				 negatedStep = s2;
				 normalStep = s1;
				Links.remove(threats.get(1));	//remove the link

			}
		
			this.addThreatsOrdering(negatedStep,normalStep);
			return true;
		}
		
		return false;
	}

	
	/**
	 * 
	 * @param s1
	 * @param s2
	 */
	public void addThreatsOrdering(Step s1, Step s2)
	{
		if(order.containsKey(s1.getStepId()))
		{
			order.get(s1.getStepId()).getArray().add(s2);
		}
		
	}


	
	/**
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public ArrayList <Step> checkOrdering(Step s1, Step s2)
	{
		ArrayList <Step> ordering = new ArrayList<Step>();
		
		int sizeS1NumOfSteps = order.get(s1.getStepId()).sizeSteps();
		for(int i=0;i<sizeS1NumOfSteps;i++)
		{

			Step f = order.get(s1.getStepId()).getStepInArray(0);
			ordering.add(f);

			//if s1 contains connected steps
			if(order.containsKey(f.getStepId()))
			{
				int sizeFNumOfSteps = order.get(f.getStepId()).sizeSteps();
				for(int k=0; k< sizeFNumOfSteps;k++)
				{
					Step j = order.get(f.getStepId()).getStepInArray(k);
					ordering.add(j);
				}
			}			
		}
		
		int sizeS2NumOfSteps = order.get(s2.getStepId()).sizeSteps();
		for(int x=0;x<sizeS2NumOfSteps;x++)
		{

			Step f = order.get(s2.getStepId()).getStepInArray(0);
			ordering.add(f);

			//if s1 contains connected steps
			if(order.containsKey(f.getStepId()))
			{
				int sizeFNumOfSteps = order.get(f.getStepId()).sizeSteps();
				for(int k=0; k< sizeFNumOfSteps;k++)
				{
					Step j = order.get(f.getStepId()).getStepInArray(k);
					ordering.add(j);
				}
			}			
		}

		return ordering;

		
	}
	
	
/**
 * This method checks if there is ordering between the steps or not 
 * @param s1
 * @param s2
 * @return true if there is no ordering
 * @return false if there no ordering
 */
	public boolean isThereOrderingBtw(Step s1, Step s2)
	{
		

		this.updateOrdering();
		//System.out.println(order.get(s1.getStepId()).getStep().getStepName());
		//System.out.println(order.get(s2.getStepId()).getStep().getStepName());

		ArrayList <Step> tempOrdering = new ArrayList <Step>();
		tempOrdering = this.checkOrdering(s1, s2);
		
		if(	(tempOrdering.contains(s1)) || (tempOrdering.contains(s2))	)
		{
			System.out.println(" FOUND A STEP SHOULD BE ADDED" );
			return true;
		}
		
		else
		{
			System.out.println("NOT FOUND IT SHOULD BE REORDING 'There is no ordering btw them'");
			return false;
		}
//
//		if((!(order.get(s1.getStepId()).getArray().contains(s2)))
//				&&(!(order.get(s2.getStepId()).getArray().contains(s1))))
//		{
//			System.out.println("NOT FOUND IT SHOULD BE REORDING 'There is no ordering btw them'");
//			return false;
//		}
//		else
//		{
//			System.out.println(" FOUND A STEP SHOULD BE ADDED" );
//			return true;
//		}
	}

	public boolean addStep(ArrayList <CausalLink> threats)
	{
		OpenPrecondition TempOpenPrecon = new OpenPrecondition(-1,null);
		TempOpenPrecon.addOpenPrcondition(threats.get(0).getPrecondition().getOpenPrecondtion());
		Step s1 = Actions.get(threats.get(0).getPrecondition().getStepID());
		Step s2 = Actions.get(threats.get(1).getPrecondition().getStepID());
		
		System.out.println(threats.get(0).toString());
		System.out.println(threats.get(1).toString());


		Literal effect = threats.get(0).getEffect();
		effect.hasNegativeSign(true);
		
		if(s1.getStepId() < s2.getStepId()) //This means s2 comes before s1(s2 more closer to init)
		{
			TempOpenPrecon.addStep(s1.getStepId());
			openPrecon.add(TempOpenPrecon);
			Links.remove(threats.get(0));	//remove the link
			
			
//			if(!(this.searchInEffects(TempOpenPrecon)))
//			{
//				System.out.println("Found here");
//				this.searchEffectsInActionDomain(TempOpenPrecon);
//
//				return true;
//			}
			return true;
		}
		else	//This means s1 comes before s2 (s1 more closer to init)
		{
			TempOpenPrecon.addStep(s2.getStepId());
			openPrecon.add(TempOpenPrecon);
			Links.remove(threats.get(1));	//remove the link

//			if(!(this.searchInEffects(TempOpenPrecon)))
//			{
//				System.out.println("Cannot be found in actions");
//
//				this.searchEffectsInActionDomain(TempOpenPrecon);
//
//				return true;
//			}
			return true;
		}

		//return false;
	}
	
	


	
	/**
	 * This function checks if there is a threat between variables
	 */
	public boolean CheckThreats()
	{
		ArrayList <CausalLink> threats = new ArrayList <CausalLink>();

		threats = this.getThreatenCausalLinks();
		
		if(threats.isEmpty())
			return true;
		
		if(this.rearrangeOrdering(threats))
		{
			System.out.println("The threat has been resolved by reording the steps" );
			return true;
			//Links.remove(Links.get(i));	//remove the link
		}
		else
		{
			if(this.addStep(threats))
			{
				System.out.println("The threat has been resolved by adding a new step");
				return true;
				//Links.remove(Links.get(i));	//remove the link
			}	
			else
			{
				System.out.println("The threat can not be resolved");
				return false;
			}

		}
	}

	
	//the new function not tested yet
	public ArrayList<CausalLink> getThreatenCausalLinks()
	{
		ArrayList <CausalLink> threats = new ArrayList <CausalLink>();
		
		int sizeLinks = Links.size();
		for(int i =0;i<sizeLinks;i++)
		{
			Literal thisEffect = Links.get(i).getEffect();
			for(int x=0; x<sizeLinks;x++)
			{
				Literal effect = Links.get(x).getEffect();
				if((thisEffect.toString().equals(effect.toString()) && (Links.get(i) != Links.get(x))))
				{
					if(Links.get(i).getStepName() == Links.get(x).getStepName())
					{
						int stepid = Links.get(i).getPrecondition().getStepID();
						Step s = Actions.get(stepid);
						if((isPreconditionNegate(s,thisEffect)))
						{
							threats.add(Links.get(i));
							threats.add(Links.get(x));
						}
					}

				}
			}

		}
		
		return threats;
	}




	/**
	 * This function checks if the step negates a satisfied precondition
	 * @param the step of the precondition
	 * @param the effect that connects the openprecondition
	 * @return
	 */
	public boolean isPreconditionNegate(Step s, Literal effect)
	{
		int sizeEffect = s.getEffectsSize();
		for(int i=0;i<sizeEffect;i++)
		{
			if(s.getEffects(i).toString().equals(effect.toString()))
			{
				if(s.getEffects(i).isNegative())
				{
					return true;
				}
			}
		}
		return false;
	}




	/**
	 * This function checks how many times the effect is connected with a causal Link
	 * @param link
	 * @return
	 */
	public int hasCausalLink(CausalLink link)
	{
		Literal effect = link.getEffect();
		int sizeCausalLinks = Links.size();
		int counter =0;
		for(int i=0;i<sizeCausalLinks;i++)
		{
			if(Links.get(i).getEffect().equals(effect))
			{
				counter++;
			}
		}
		return counter;
	}


	public CausalLink getCausalLink(int index)
	{
		return Links.get(index);
	}

	//	/**
	//	 * This method is to reset the effects in the initial state if they become negative
	//	 * @param effect
	//	 * @return
	//	 */
	//	public void searchEffectInOrder(Literal effect)
	//	{
	//
	//		int effectSize = parser.getProblemDomainEffectsSize();
	//		for(int i=0; i< effectSize;i++)
	//		{
	//			String temp = effect.toString();
	//			if(temp.equals(parser.getIntialStateEffects(i).toString()))
	//			{
	//				int sizeLinks = Links.size();
	//				for(int f=0;f<sizeLinks;f++)
	//				{
	//					if(Links.get(f).getPrecondition().getOpenPrecondtion().toString().equals(effect.toString()))
	//					{
	//						effect.hasNegativeSign(true);
	//						Links.get(f).getPrecondition().getOpenPrecondtion().hasNegativeSign(true);
	//
	//					}
	//				}
	//			}
	//		}
	//	}
	//
	//	/**
	//	 * This function applies the negation after the step has been chosen
	//	 * @param step
	//	 */
	//	public void applyNegation()
	//	{
	//		int sizeActions = order.size();
	//		for(int i=0;i<sizeActions;i++)
	//		{
	//			int sizeEffects = order.get(i).getStep().getEffectsSize();
	//			for(int f=0;f<sizeEffects;f++)
	//			{
	//				if((order.get(i).getStep().getEffects(f).isNegative()))
	//				{
	//					Literal lit = Actions.get(i).getEffects(f);	
	//					System.out.println("Negative effects: " + lit);
	//					this.searchEffectInOrder(lit);
	//				}
	//			}	
	//		}
	//	}




	public OpenPrecondition getOpenPrecondition()
	{
		OpenPrecondition openprecondition = openPrecon.getFirst();
		this.removeOpenPrecondition();
		return openprecondition;
	}

	public OpenPrecondition removeOpenPrecondition()
	{

		return openPrecon.removeFirst();
	}

	//	public int getStepId()
	//	{
	//		return StepId;
	//	}

	//	public void applyNegation(Step step)
	//	{
	//		int sizeEffects = step.getEffectsSize();
	//		for(int i=0; i<sizeEffects;i++)
	//		{
	//			if(step.getEffects(i).isNegative())
	//			{
	//				if(binding.isBounded(step.getEffects(i)))
	//					this.searchInitialState(step.getEffects(i));
	//
	//			}
	//		}
	//	}
}
