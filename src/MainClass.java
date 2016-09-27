
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainClass 
{
	
	   	//private static final String String = null;
		//private static final Preconditions Preconditions = null;
		//private static final Effects Effects = null;

		public static void main(String [] args) throws FileNotFoundException 
	    {
	    	String domainName = "Domain.txt";
	    	String ProblemName = "Problem.txt";

	    	
	    	
	    	//Step s = new Step(String , Preconditions , Effects );
	    	Parser p = new Parser();
	    	p.parseDomain(domainName);
	    	//p.printout(1);
	    	//p.parseProblem(ProblemName);
	    	
//	        // The name of the file to open.
//	        String fileName = "Domain.txt";
//	        String action = "action";
//	        String parameters = "parameters";
//	        String precondition = "precondition";
//	        String effect = "effect";
//
//	        String predicates = "predicates";
//	        
//	        String delims = "and";
//	        String token = "(";
//	        
//	        
//	        
//	        String[] ActionName = null;
//	        String[] ParameterStyle = null;
//	        String[] ActionPrecondition = null;
//	        String[] ActionEffect = null;
//
//	        
//	        
//	        File text = new File(fileName);
//	        
//	        Scanner scan = new Scanner(text);
//	        while(scan.hasNextLine())
//	        {
//                String line = scan.nextLine();
//                if(line.contains(action))
//                {
//                	ActionName = line.split(action);
//    	        	System.out.println(ActionName[1]);
//    	        	
//    	        	//Action parameters
//    	        	String ActionLine = scan.nextLine();
//    	        	if(ActionLine.contains(parameters))
//    	        	{
//    	        		ParameterStyle = ActionLine.split(parameters);
//	    	        	System.out.println(ParameterStyle[1]);
//	    	        	ActionLine = scan.nextLine();
//    	        	}
//    	        	if(ActionLine.contains(precondition))
//    	        	{
//    	        		ActionPrecondition= ActionLine.split(delims);
//	    	        	//System.out.println(ActionPrecondition[1]);
//	    	        	System.out.println("Preconditions:  ");
//	    	        	
//	    	            Matcher pre = Pattern.compile("\\(([^)]+)\\)").matcher(ActionPrecondition[1]);
//	    	            while(pre.find())
//	    	            {
//	    	                System.out.print(pre.group(1));  
//		    	        	System.out.print(" === ");
//
//	    	            }
//	    	        	ActionLine = scan.nextLine();
//    	        	}
//    	        	System.out.println("");
//
//    	        	if(ActionLine.contains(effect))
//    	        	{
//    	        		ActionEffect = ActionLine.split(delims);
//	    	        	//System.out.println(ActionEffect[1]);
//	    	        	System.out.println("Effects:  ");
//
//    	        		
//    	        		
//	    	        	Matcher effe = Pattern.compile("\\(([^)]+)\\)").matcher(ActionEffect[1]);
//	    	            while(effe.find())
//	    	            {
//	    	            	
//	    	                System.out.print(effe.group(1));
//		    	        	System.out.print("  ===  ");
//
//	    	            }
//
//    	        	}
//    	        	System.out.println("");
//    	        	System.out.println("");
//
//
//
//                }
//
//	        }
	    }
	        
}         
