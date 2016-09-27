import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class TestParser 
{

	@Test
	public void testParsingActionDomain() throws FileNotFoundException
	{
    	String ProblemName = "Problem.txt";
    	String domainName = "Domain.txt";    	

		Parser p = new Parser();
		p.parseDomain(domainName);
		
		//first Action
		assertEquals(" LOAD",p.ActionsDomain.get(0).getStepName());
		//Preconditions
		assertEquals("At ?c ?a", p.ActionsDomain.get(0).getPreconditions(0));
		assertEquals("At ?p ?a", p.ActionsDomain.get(0).getPreconditions(1));
		assertEquals("Cargo ?c", p.ActionsDomain.get(0).getPreconditions(2));
		assertEquals("Plane ?p", p.ActionsDomain.get(0).getPreconditions(3));
		assertEquals("Airport ?a", p.ActionsDomain.get(0).getPreconditions(4));
		assertEquals("Airport ?a", p.ActionsDomain.get(0).getPreconditions(4));
		//Effects
		assertEquals("In ?c ?p", p.ActionsDomain.get(0).getEffects(0));
		assertEquals("not (At ?c ?a", p.ActionsDomain.get(0).getEffects(1));

		
		
		//second Action
		assertEquals(" UNLOAD",p.ActionsDomain.get(1).getStepName());
		//Preconditions
		assertEquals("In ?c ?p",p.ActionsDomain.get(1).getPreconditions(0));
		assertEquals("At ?p ?a",p.ActionsDomain.get(1).getPreconditions(1));
		assertEquals("Cargo ?c",p.ActionsDomain.get(1).getPreconditions(2));
		assertEquals("Plane ?p",p.ActionsDomain.get(1).getPreconditions(3));
		assertEquals("Airport ?a",p.ActionsDomain.get(1).getPreconditions(4));
		//Effects
		assertEquals("At ?c ?a", p.ActionsDomain.get(1).getEffects(0));
		assertEquals("not (In ?c ?p", p.ActionsDomain.get(1).getEffects(1));

		
		//Third Action

	}
	
	@Test
	public void testParsingProblemDomain() throws FileNotFoundException
	{
    	String ProblemName = "Problem.txt";
    	String domainName = "Domain.txt";

    	

		Parser p = new Parser();
		p.parseProblem(ProblemName);
		
		assertEquals("init",p.ProblemDomain.get(0).getStepName());
		assertEquals("Cargo C1",p.ProblemDomain.get(0).getEffects(0));
		assertEquals("Cargo C2",p.ProblemDomain.get(0).getEffects(1));
		assertEquals("Plane P1",p.ProblemDomain.get(0).getEffects(2));
		assertEquals("Plane P2",p.ProblemDomain.get(0).getEffects(3));
		assertEquals("Airport SFO",p.ProblemDomain.get(0).getEffects(4));
		assertEquals("Airport JFK",p.ProblemDomain.get(0).getEffects(5));
		assertEquals("At C1 SFO",p.ProblemDomain.get(0).getEffects(6));
		assertEquals("At C2 JFK",p.ProblemDomain.get(0).getEffects(7));
		assertEquals("At P1 SFO",p.ProblemDomain.get(0).getEffects(8));
		assertEquals("At P2 JFK",p.ProblemDomain.get(0).getEffects(9));
		
		
		assertEquals("goal",p.ProblemDomain.get(1).getStepName());
		assertEquals("At C1 JFK",p.ProblemDomain.get(1).getPreconditions(0));
		assertEquals("At C2 SFO",p.ProblemDomain.get(1).getPreconditions(1));


	}
	
	@Test
	public void testAddingPreconditions() throws FileNotFoundException
	{
    	String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		
		Planner planner = new Planner(domainName,problemName);
		Parser p = new Parser();

		planner.setDomainName(domainName);
		planner.setProblemName(problemName);
		
		
		planner.search();
		assertEquals("At C1 JFK",planner.removeOpenPrecondition());
		assertEquals("At C2 SFO",planner.removeOpenPrecondition());

		
	}

}
