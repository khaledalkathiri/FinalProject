import java.io.File;
import java.io.FileNotFoundException;
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
	//private String predicates = "predicates";

	private String delims = "and";
	private String[] ActionName = null;
	private String[] ParameterStyle = null;
	private String[] ActionPrecondition = null;
	private String[] ActionEffect = null;

	
	
	//instance variables for parsing the problem
	private String initialState = "init";
	private String goalState = "goal";
	private String[] initialStateLitrals = null;
	private String[] goalStateLitrals = null;

	
	public void parseDomain(String fileName) throws FileNotFoundException
	{

		File text = new File(fileName);

		Scanner scan = new Scanner(text);
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			if(line.contains(action))
			{
				ActionName = line.split(action);
				System.out.print(ActionName[1] +"  ");

				//Action parameters
				String ActionLine = scan.nextLine();
				if(ActionLine.contains(parameters))
				{
					ParameterStyle = ActionLine.split(parameters);
					System.out.println(ParameterStyle[1]);
					ActionLine = scan.nextLine();
				}
				if(ActionLine.contains(precondition))
				{
					ActionPrecondition= ActionLine.split(delims);
					//System.out.println(ActionPrecondition[1]);
					System.out.println("Preconditions:  ");

					Matcher pre = Pattern.compile("\\(([^)]+)\\)").matcher(ActionPrecondition[1]);
					while(pre.find())
					{
						System.out.print(pre.group(1));  
						System.out.print("  \t");

					}
					ActionLine = scan.nextLine();
				}
				System.out.println("");

				if(ActionLine.contains(effect))
				{
					ActionEffect = ActionLine.split(delims);
					//System.out.println(ActionEffect[1]);
					System.out.println("Effects:  ");



					Matcher effe = Pattern.compile("\\(([^)]+)\\)").matcher(ActionEffect[1]);
					while(effe.find())
					{            	
						System.out.print(effe.group(1));
						System.out.print("  \t");
					}

				}
				System.out.println("\n");
			}
		}
	}
	
	
	public void parseProblem(String fileName) throws FileNotFoundException
	{
		File text = new File(fileName);
		
		Scanner scan = new Scanner(text);
		
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			//System.out.println(line);

			
			if(line.contains(initialState))
			{
				System.out.println("Initial State: ");

				initialStateLitrals = line.split(initialState);
				//System.out.println(initialStateLitrals[1]);
				
				Matcher litrals = Pattern.compile("\\(([^)]+)\\)").matcher(initialStateLitrals[1]);
				while(litrals.find())
				{            	
					System.out.println(litrals.group(1));
				}
				line = scan.nextLine();

			}
			
			if(line.contains(goalState))
			{
				goalStateLitrals = line.split(delims);
				//System.out.println(goalStateLitrals[1]);
				System.out.println("\nGoal: ");
				
				Matcher litrals = Pattern.compile("\\(([^)]+)\\)").matcher(goalStateLitrals[1]);
				while(litrals.find())
				{            	
					System.out.println(litrals.group(1));
				}

			}

		}

	}
	
	
}

