
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;


public class MainClass 
{
	
	    public static void main(String [] args) throws FileNotFoundException 
	    {

	        // The name of the file to open.
	        String fileName = "Domain.txt";
	        String action = "action";
	        String parameters = "parameters";
	        String precondition = "precondition";
	        String effect = "effect";

	        String predicates = "predicates";
	        
	        String delims = "and";
	        String token = "(";
	        
	        
	        
	        String[] ActionName = null;
	        String[] ParameterStyle = null;
	        String[] ActionPrecondition = null;
	        String[] ActionEffect = null;

	        
	        
	        File text = new File(fileName);
	        
	        Scanner scan = new Scanner(text);
	        while(scan.hasNextLine())
	        {
                String line = scan.nextLine();
                if(line.contains(action))
                {
                	ActionName = line.split(action);
    	        	System.out.println(ActionName[1]);
    	        	
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
    	        		int i = ActionLine.length();
    	        		//ActionPrecondition= ActionLine.split("",6);
    	        		ActionPrecondition= ActionLine.split(delims);
	    	        	System.out.println(ActionPrecondition[1]);
	    	        	String[] preconditons = ActionPrecondition[1].split(Pattern.quote("(+"));
	    	        	System.out.println(preconditons[0]);
	    	        	//if(ActionPrecondition[1].contains(token))
	    	        	{
		    	        	//System.out.println(ActionPrecondition[1]);

	    	        	}
	    	        	

    	        		//ActionPrecondition = ActionLine.

    	        	}

                }

                
                //int l = line.indexOf(action);
               // String cmd = line.substring(0, l);
                //String end = line.substring(l);

                //String[] s = line.split(delim);
	        }
	    }
	        
} 
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
//	        
//	        // This will reference one line at a time
//	        String line = null;
//
//	        try {
//	            // FileReader reads text files in the default encoding.
//	            FileReader fileReader = new FileReader(fileName);
//
//	            // Always wrap FileReader in BufferedReader.
//	            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//	            while((line = bufferedReader.readLine()) != null) 
//	            {
//	            	
//	            	//This function will printout the predicates
//	                String[] token = null;
//	                String[] token2 = null;
//					//System.out.println(line);
//	                if (line.contains(predicates))
//	                {
//	                	
//	                	token = line.split(delim);
//		                for (int i = 2; i < token.length; i++)
//		                    System.out.println(token[i]);
//	                }
//	                
//
//	                if (line.contains(action))
//	                {
//						System.out.println(line);
//
//	                	//token2 = line.split(delims);
//		               // for (int i = 0; i < token2.length; i++)
//		               //     System.out.println(token2[i]);
//
//	                }
//	
//	             }   
//
//	            //close files.
//	            bufferedReader.close();         
//	        }
//	        catch(FileNotFoundException ex) {
//	            System.out.println("Unable to open file '" + fileName + "'");                
//	        }
//	        
//	        catch(IOException ex) {
//	        	System.out.println("Error reading file '" + fileName + "'");                  
//	            	        }
//	    }
//	}

