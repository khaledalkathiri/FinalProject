import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser 
{

	// instance variables for parsing the domain
	private String action = "action";
	private String parameters = "parameters";
	private String precondition = "precondition";
	private String effect = "effect";
	private String predicates = "predicates";
	private String delims = "and";
	private String[] ActionName = null;
	private String[] ParameterStyle = null;
	private String[] ActionPrecondition = null;
	private String[] ActionEffect = null;
	private String[] DomainPredicates = null;

	
	//instance variables for parsing the problem
	private String initialState = "init";
	private String goalState = "goal";
	private String[] initialStateLitrals = null;
	private String[] goalStateLitrals = null;

	
	public ArrayList <Step> ActionsDomain = new ArrayList <Step>();
	public ArrayList <Step> ProblemDomain = new ArrayList <Step>(); 
	private Step step;
	
	
	public ArrayList <Literal> PredicatesArray = new ArrayList <Literal>();	
	private String name;

	private Literal literal;

	
	public Parser()//String domainName, String problemName)
	{
		this.ActionsDomain = new ArrayList <Step>();
		this.ProblemDomain = new ArrayList <Step>();
		this.PredicatesArray = new ArrayList <Literal>();
		//this.problemName = problemName;
		//this.domainName = domainName;
	}

	
	/**
	 * This function is to parse the domain file
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void parseDomain(String fileName) throws FileNotFoundException
	{
		
		File text = new File(fileName);
		Scanner scan = new Scanner(text);
		
		while(scan.hasNextLine())
		{
			step = new Step(null, null, null);
			
			String line = scan.nextLine();
			
			if(line.contains(predicates))
			{
				DomainPredicates = line.split(predicates);
				
				Matcher predicate = Pattern.compile("\\(([^)]+)\\)").matcher(DomainPredicates[1]);
				while(predicate.find())
				{
					//adding the preconditions to the step
					literal = new Literal(null,null);
					Literal s = literal.parseStringToLiteral(predicate.group(1)); 

					PredicatesArray.add(s);
						//System.out.println(s.getLiteralParameters(0));  
						//System.out.print("  \t");

				}
				
			}
			//System.out.print("  \n");

			
			if(line.contains(action))
			{
				ActionName = line.split(action);
					//System.out.print(ActionName[1] +"  ");
				name = ActionName[1];
				
				//adding the action name to the step
				step.addStepName(name);
				
				
				//Action parameters
				String ActionLine = scan.nextLine();
				if(ActionLine.contains(parameters))
				{
					ParameterStyle = ActionLine.split(parameters);
						//System.out.println(ParameterStyle[1]);
					ActionLine = scan.nextLine();
				}
				
				//Action preconditions
				if(ActionLine.contains(precondition))
				{
					ActionPrecondition= ActionLine.split(delims);
						//System.out.println("Preconditions:  ");

					Matcher pre = Pattern.compile("\\(([^)]+)\\)").matcher(ActionPrecondition[1]);
					while(pre.find())
					{
						//adding the preconditions to the step
						//System.out.print(pre.group(1));  
						literal = new Literal(null,null);

						Literal s = literal.parseStringToLiteral(pre.group(1)); 
							//System.out.println(pre.group(1));
						step.addPreconditions(s);
							//System.out.print(s.getLiteralName()); 
							//System.out.println(s.getLiteralParameters(0)); 

							//System.out.print("  \t	");

					}
					ActionLine = scan.nextLine();
				}
				//System.out.println("");

				
				
				//Action Effects
				if(ActionLine.contains(effect))
				{
					ActionEffect = ActionLine.split(delims);
						//System.out.println("Effects:  ");


					Matcher ef = Pattern.compile("\\(([^)]+)\\)").matcher(ActionEffect[1]);
					while(ef.find())
					{    
						//adding the effects to the step
						Literal s = literal.parseStringToLiteral(ef.group(1));
						step.addEffects(s);
							//System.out.print(s.getLiteralName());
							//System.out.print("  \t");
					}

				}
				
				//adding the step to the arrayList of steps
				ActionsDomain.add(step);
					//System.out.println("\n");
			}
		}
	}
	

	
	/**
	 * This function is to parse the problem file
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void parseProblem(String fileName) throws FileNotFoundException
	{
		//fileName = problemName;

		File text = new File(fileName);
		Scanner scan = new Scanner(text);

		while(scan.hasNextLine())
		{
			String line = scan.nextLine();

			
			if(line.contains(initialState))
			{
				step = new Step(null, null, null);
				literal = new Literal(null,null);


					//System.out.println("Initial State: ");

				initialStateLitrals = line.split(initialState);
				
				step.addStepName(initialState);
				Matcher litrals = Pattern.compile("\\(([^)]+)\\)").matcher(initialStateLitrals[1]);
				while(litrals.find())
				{      
					//step.addEffects(litrals.group(1));
						//int numOfPara =literal.searchInPredicateArray(this,litrals.group(1));
						Literal s = literal.parseStringToLiteral(litrals.group(1));	
						//System.out.println(s.getLiteralParameters(0));
						step.addEffects(s);

				}
				line = scan.nextLine();
				ProblemDomain.add(0, step);
			}
			
			if(line.contains(goalState))
			{
				step = new Step(null, null, null);

				goalStateLitrals = line.split(delims);
					//System.out.println("\nGoal: ");
				step.addStepName(goalState);
				Matcher litrals = Pattern.compile("\\(([^)]+)\\)").matcher(goalStateLitrals[1]);
				while(litrals.find())
				{     
					Literal s = literal.parseStringToLiteral(litrals.group(1));	
					step.addPreconditions(s);
					//step.addPreconditions(litrals.group(1));
						//System.out.println(litrals.group(1));
				}
				ProblemDomain.add(1, step);

			}

		}

	}
	
	
	
	
	
	//Domain File
	/**
	 * This function is to get the goal preconditions in the ProblemDomain
	 * @param index
	 * @return
	 */
	public Literal getGoalPreconditions(int index)
	{
		return ProblemDomain.get(1).getPreconditions(index);
	}
	
	
	
	/**
	 * This function will return the size of the precondition(Goal state) in the problem domain
	 * @param index
	 * @return
	 */
	public int getProblemDomainPreconditionSize(int index)
	{
		return ProblemDomain.get(index).getPreconditionSize();
	}

	
	/**
	 * This function will return the size of the effects(initial state) in the problem domain
	 * @param index
	 * @return
	 */
	public int getProblemDomainEffectsSize(int index)
	{
		return ProblemDomain.get(index).getEffectsSize();
	}
	
	
	public Literal getIntialStateEffects(int index)
	{
		return ProblemDomain.get(0).getEffects(index);
	}
	
	
	//Preconditions
	/**
	 * This function will return the size of the preconditions in the ActionDomain
	 * @param index
	 * @return
	 */
	public int getActionsDomainPreconditionSize(int index)
	{
		return ActionsDomain.get(index).getPreconditionSize();
	}
	
	
	/**
	 * This function is to get the preconditions in the ActionDomain
	 * @param step
	 * @param index
	 * @return
	 */
	public Literal getActionsPreconditions(int step, int index)
	{
		return ActionsDomain.get(step).getPreconditions(index);
	}
	
	
	//Effects
	/**
	 * This function will return the size of the effects in the ActionDomain
	 * @param index
	 * @return
	 */
	public int getActionsDomainEffectSize(int index)
	{
		return ActionsDomain.get(index).getEffectsSize();
	}

	/**
	 * This function is to get the effects in the ActionDomain
	 * @param step
	 * @param index
	 * @return
	 */
	public Literal getActionsEffects(int step, int index)
	{
		return ActionsDomain.get(step).getEffects(index);
	}
	
	
	//Actions
	/**
	 * This function is to get the size of the ActionDomain
	 * @return
	 */
	public int getActionDomainSize()
	{
		return ActionsDomain.size();
	}
	
	
	public Step getAction(int index)
	{
		return ActionsDomain.get(index);
	}
	
	
	//Predicates
	public Literal getPredicates(int index)
	{
		return PredicatesArray.get(index);
	}
	
	public int SizePredicatesArray()
	{
		return PredicatesArray.size();
	}


////////////////////////////////////////////NEXT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


	
	
	
}

