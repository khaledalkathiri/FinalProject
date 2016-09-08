
import java.io.*;


public class MainClass 
{
	
	    public static void main(String [] args) {

	        // The name of the file to open.
	        String fileName = "Domain.txt";
	        String action = "action";
	        String predicates = "predicates";
	        String delim = "[(]";		//for predicates
	        String delims = "[:]";		//for actions

	        // This will reference one line at a time
	        String line = null;

	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) 
	            {
	            	
	            	//This function will printout the predicates
	                String[] token = null;
	                String[] token2 = null;
					//System.out.println(line);
	                if (line.contains(predicates))
	                {
	                	token = line.split(delim);
		                for (int i = 2; i < token.length; i++)
		                    System.out.println(token[i]);
	                }
	                

	                if (line.contains(action))
	                {
						System.out.println(line);

	                	//token2 = line.split(delims);
		               // for (int i = 0; i < token2.length; i++)
		               //     System.out.println(token2[i]);

	                }
	
	             }   

	            //close files.
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println("Unable to open file '" + fileName + "'");                
	        }
	        
	        catch(IOException ex) {
	        	System.out.println("Error reading file '" + fileName + "'");                  
	            	        }
	    }
	}

