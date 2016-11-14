
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainClass 
{



	public static void main(String [] args) throws FileNotFoundException 
	{
		Parser parser = new Parser();

		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);

		Binding binding = new Binding(parser);
		
		Planner bagMethod = new BagMethod(parser);
		bagMethod.search();
		
//		Planner priorityQueueMethod = new PriorityQueue(parser);
//		priorityQueueMethod.search();

	}

}         
