
import java.io.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainClass 
{



	public static void main(String [] args) throws FileNotFoundException 
	{
		
		final long startTime = System.currentTimeMillis();

		Parser parser = new Parser();

		String domainName = "CargoDomain.txt";
		String problemName = "CargoProblem2.txt";
		

		
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);

		Binding binding = new Binding(parser);
		
		Planner bagMethod = new BagMethod(parser);
		bagMethod.search();
		
//		Planner priorityQueueMethod = new PriorityQueue(parser);
//		priorityQueueMethod.search();
		
//		Planner QueueMethod = new QueueMethod(parser);
//		QueueMethod.search();

		

		final long duration = System.currentTimeMillis() - startTime;
		System.out.println(duration);
	}

}         
