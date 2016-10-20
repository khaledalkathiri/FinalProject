
import static org.junit.Assert.*;


import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;


public class TestParser 
{

	@Test
	public void testParsingActionDomain() throws FileNotFoundException
	{
    	String domainName = "Domain.txt";

		Parser p = new Parser();
		p.parseDomain(domainName);
		
		
		//First Action
		assertEquals(" LOAD",p.ActionsDomain.get(0).getStepName());
		//Preconditions
		assertEquals("CargoAt ?c ?a", p.ActionsDomain.get(0).getPreconditions(0).toString());
		assertEquals("PlaneAt ?p ?a", p.ActionsDomain.get(0).getPreconditions(1).toString());
		assertEquals("Cargo ?c", p.ActionsDomain.get(0).getPreconditions(2).toString());
		assertEquals("Plane ?p", p.ActionsDomain.get(0).getPreconditions(3).toString());
		assertEquals("Airport ?a", p.ActionsDomain.get(0).getPreconditions(4).toString());
		//Effects
		assertEquals("In ?c ?p", p.ActionsDomain.get(0).getEffects(0).toString());
		assertEquals("CargoAt ?c ?a", p.ActionsDomain.get(0).getEffects(1).toString());
		assertFalse(p.ActionsDomain.get(0).getEffects(0).isNegative());					//is the effect negative
		assertTrue(p.ActionsDomain.get(0).getEffects(1).isNegative());					//is the effect negative


		//Second Action
		assertEquals(" UNLOAD",p.ActionsDomain.get(1).getStepName());
		//Preconditions
		assertEquals("In ?c ?p", p.ActionsDomain.get(1).getPreconditions(0).toString());
		assertEquals("PlaneAt ?p ?a", p.ActionsDomain.get(1).getPreconditions(1).toString());
		assertEquals("Cargo ?c", p.ActionsDomain.get(1).getPreconditions(2).toString());
		assertEquals("Plane ?p", p.ActionsDomain.get(1).getPreconditions(3).toString());
		assertEquals("Airport ?a", p.ActionsDomain.get(1).getPreconditions(4).toString());
		//Effects
		assertEquals("CargoAt ?c ?a", p.ActionsDomain.get(1).getEffects(0).toString());
		assertEquals("In ?c ?p", p.ActionsDomain.get(1).getEffects(1).toString());
		assertFalse(p.ActionsDomain.get(1).getEffects(0).isNegative());					//is the effect negative
		assertTrue(p.ActionsDomain.get(1).getEffects(1).isNegative());					//is the effect negative

		
		//Third Action
		assertEquals(" FLY",p.ActionsDomain.get(2).getStepName());
		//Preconditions
		assertEquals("PlaneAt ?p ?from", p.ActionsDomain.get(2).getPreconditions(0).toString());
		assertEquals("Plane ?p", p.ActionsDomain.get(2).getPreconditions(1).toString());
		assertEquals("Airport ?from", p.ActionsDomain.get(2).getPreconditions(2).toString());
		assertEquals("Airport ?to", p.ActionsDomain.get(2).getPreconditions(3).toString());
		//Effects
		assertEquals("PlaneAt ?p ?to", p.ActionsDomain.get(2).getEffects(0).toString());
		assertEquals("PlaneAt ?p ?from", p.ActionsDomain.get(2).getEffects(1).toString());
		assertFalse(p.ActionsDomain.get(2).getEffects(0).isNegative());					//is the effect negative
		assertTrue(p.ActionsDomain.get(2).getEffects(1).isNegative());					//is the effect negative

		


		
	}
	
	@Test
	public void testParsingProblemDomain() throws FileNotFoundException
	{
    	String ProblemName = "Problem.txt";

    	

		Parser p = new Parser();
		p.parseProblem(ProblemName);
		
		//intial State
		assertEquals("init",p.ProblemDomain.get(0).getStepName());
		assertEquals("Cargo C1",p.ProblemDomain.get(0).getEffects(0).toString());
		assertEquals("Cargo C2",p.ProblemDomain.get(0).getEffects(1).toString());
		assertEquals("Plane P1",p.ProblemDomain.get(0).getEffects(2).toString());
		assertEquals("Plane P2",p.ProblemDomain.get(0).getEffects(3).toString());
		assertEquals("Airport SFO",p.ProblemDomain.get(0).getEffects(4).toString());
		assertEquals("Airport JFK",p.ProblemDomain.get(0).getEffects(5).toString());
		assertEquals("CargoAt C1 SFO",p.ProblemDomain.get(0).getEffects(6).toString());
		assertEquals("CargoAt C2 JFK",p.ProblemDomain.get(0).getEffects(7).toString());
		assertEquals("PlaneAt P1 SFO",p.ProblemDomain.get(0).getEffects(8).toString());
		assertEquals("PlaneAt P2 JFK",p.ProblemDomain.get(0).getEffects(9).toString());

		//Goal State
		assertEquals("goal",p.ProblemDomain.get(1).getStepName());
		assertEquals("CargoAt C1 JFK",p.ProblemDomain.get(1).getPreconditions(0).toString());
		assertEquals("CargoAt C2 SFO",p.ProblemDomain.get(1).getPreconditions(1).toString());


	}
	
	
	@Test 
	public void testParsingPredicates() throws FileNotFoundException
	{
		String domainName = "Domain.txt";

		Parser p = new Parser();
		p.parseDomain(domainName);
		
		assertEquals("In ?c ?p", p.PredicatesArray.get(0).toString());
		assertEquals("CargoAt ?c ?a", p.PredicatesArray.get(1).toString());
		
		assertEquals("In ?c ?p", p.ActionsDomain.get(1).getPreconditions(0).toString());
		assertEquals("CargoAt ?c ?a", p.ActionsDomain.get(0).getPreconditions(0).toString());

		
		Literal literal = p.ActionsDomain.get(0).getPreconditions(0);
		assertEquals("CargoAt ?c ?a", literal.toString());
		assertEquals(true,p.predicateArrayHas(literal));


		literal.setLiteralParameters(0, "C1");
		assertEquals("CargoAt C1 ?a",literal.toString());
		assertEquals(2,p.countParaInPredicate(literal));

	}
	

	@Test
	public void testSettingLiteralPara() throws FileNotFoundException
	{
    	String domainName = "Domain.txt";

		Parser p = new Parser();
		p.parseDomain(domainName);
		
		
		//Test setting the parameters
		assertEquals("CargoAt ?c ?a",p.ActionsDomain.get(0).getPreconditions(0).toString());
		p.ActionsDomain.get(0).getPreconditions(0).setLiteralParameters(0, "C1");
		assertEquals("CargoAt C1 ?a",p.ActionsDomain.get(0).getPreconditions(0).toString());
		p.ActionsDomain.get(0).getPreconditions(0).setLiteralParameters(1, "JFK");
		assertEquals("CargoAt C1 JFK",p.ActionsDomain.get(0).getPreconditions(0).toString());


	}
	
	
	@Test
	public void testAddingPreconditions() throws FileNotFoundException
	{
    	String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		
		
		
		//Planner planner = new Planner(domainName,problemName);
		Parser p = new Parser();
		p.setDomainName(domainName);
		p.setProblemName(problemName);
		
		p.parseDomain(domainName);
		p.parseProblem(problemName);
		
		Planner planner = new Planner(p);


		planner.search();
		//assertEquals("At C1 JFK",planner.removeOpenPrecondition());
		//assertEquals("At C2 SFO",planner.removeOpenPrecondition());

		//assertEquals(planner.id.get(0).)

	

	}
	
	@Test
	public void bindStepByPrecondition()throws FileNotFoundException
	{
		Parser parser = new Parser();
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		Planner planner = new Planner(parser);
		Binding binding = new Binding(parser);
		
		Literal openPreocodition = parser.getGoalPreconditions(0);
		openPreocodition.setLiteralName("In");
		openPreocodition.setLiteralParameters(0, "C1");
		openPreocodition.setLiteralParameters(1,"?p");
		
		assertEquals("In C1 ?p", openPreocodition.toString());

		
		Step load = parser.ActionsDomain.get(0);
		binding.bindLiterals(load, openPreocodition, 0);

		assertEquals(" LOAD",load.getStepName());
		assertEquals("CargoAt C1 ?a", load.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p ?a", load.getPreconditions(1).toString());
		assertEquals("Cargo C1", load.getPreconditions(2).toString());
		assertEquals("Plane ?p", load.getPreconditions(3).toString());
		assertEquals("Airport ?a", load.getPreconditions(4).toString());
		assertEquals("In C1 ?p", load.getEffects(0).toString());
		assertEquals("CargoAt C1 ?a", load.getEffects(1).toString());
		
		openPreocodition.setLiteralParameters(1, "P1");
		assertEquals("In C1 P1", openPreocodition.toString());

		binding.bindStepByPreocndition(load, openPreocodition);
		assertEquals(" LOAD",load.getStepName());
		assertEquals("CargoAt C1 ?a", load.getPreconditions(0).toString());
		assertEquals("PlaneAt P1 ?a", load.getPreconditions(1).toString());
		assertEquals("Cargo C1", load.getPreconditions(2).toString());
		assertEquals("Plane P1", load.getPreconditions(3).toString());
		assertEquals("Airport ?a", load.getPreconditions(4).toString());
		assertEquals("In C1 P1", load.getEffects(0).toString());
		assertEquals("CargoAt C1 ?a", load.getEffects(1).toString());
		

		
	}
	
	@Test
	public void bindNextVariable()throws FileNotFoundException
	{
		Parser parser = new Parser();
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		Planner planner = new Planner(parser);
		Binding binding = new Binding(parser);
		
		assertEquals("Cargo C1", parser.getIntialStateEffects(0).toString());
		assertEquals("CargoAt C1 JFK", parser.getGoalPreconditions(0).toString());
		assertFalse(planner.searchEffectInInitialState(parser.getGoalPreconditions(0)));
		Literal openPrecondition = parser.getGoalPreconditions(0);
				
				
				
		Step unload = parser.ActionsDomain.get(1);
		assertEquals(" UNLOAD",unload.getStepName());
		assertEquals("In ?c ?p", unload.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p ?a",unload.getPreconditions(1).toString());
		assertEquals("Cargo ?c", unload.getPreconditions(2).toString());
		assertEquals("Plane ?p", unload.getPreconditions(3).toString());
		assertEquals("Airport ?a", unload.getPreconditions(4).toString());
		assertEquals("CargoAt ?c ?a", unload.getEffects(0).toString());
		assertEquals("In ?c ?p", unload.getEffects(1).toString());
		
		Step load = parser.ActionsDomain.get(0);
		assertEquals(" LOAD",load.getStepName());
		assertEquals("CargoAt ?c ?a", load.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p ?a", load.getPreconditions(1).toString());
		assertEquals("Cargo ?c", load.getPreconditions(2).toString());
		assertEquals("Plane ?p", load.getPreconditions(3).toString());
		assertEquals("Airport ?a", load.getPreconditions(4).toString());
		assertEquals("In ?c ?p", load.getEffects(0).toString());
		assertEquals("CargoAt ?c ?a", load.getEffects(1).toString());
		
		//Bind the first openPrecondition
		binding.bindLiterals(unload, openPrecondition, 0);
		assertEquals(" UNLOAD",unload.getStepName());
		assertEquals("In C1 ?p", unload.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p JFK",unload.getPreconditions(1).toString());
		assertEquals("Cargo C1", unload.getPreconditions(2).toString());
		assertEquals("Plane ?p", unload.getPreconditions(3).toString());
		assertEquals("Airport JFK", unload.getPreconditions(4).toString());
		assertEquals("CargoAt C1 JFK", unload.getEffects(0).toString());
		assertEquals("In C1 ?p", unload.getEffects(1).toString());
		
		
		//Bind the next openPrecondition
		openPrecondition = unload.getPreconditions(0);
		//openPrecondition.setLiteralParameters(0, "C1");
		assertEquals("In C1 ?p",openPrecondition.toString());
		
		//The variables should be bounded right now but not fully bounded.
		binding.bindLiterals(load, openPrecondition, 0);
		assertEquals(" LOAD",load.getStepName());
		assertEquals("CargoAt C1 ?a", load.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p ?a", load.getPreconditions(1).toString());
		assertEquals("Cargo C1", load.getPreconditions(2).toString());
		assertEquals("Plane ?p", load.getPreconditions(3).toString());
		assertEquals("Airport ?a", load.getPreconditions(4).toString());
		assertEquals("In C1 ?p", load.getEffects(0).toString());
		assertEquals("CargoAt C1 ?a", load.getEffects(1).toString());
		
		assertEquals(false, binding.isBounded(openPrecondition));
		binding.bindNextLiterals(load, openPrecondition);
		
		assertEquals(" LOAD",load.getStepName());
		assertEquals("CargoAt C1 SFO", load.getPreconditions(0).toString());
		assertEquals("PlaneAt P1 SFO", load.getPreconditions(1).toString());
		assertEquals("Cargo C1", load.getPreconditions(2).toString());
		assertEquals("Plane P1", load.getPreconditions(3).toString());
		assertEquals("Airport SFO", load.getPreconditions(4).toString());
		assertEquals("In C1 P1", load.getEffects(0).toString());
		assertEquals("CargoAt C1 SFO", load.getEffects(1).toString());
		
		assertEquals("In C1 P1", openPrecondition.toString());
		assertEquals(true, binding.isBounded(openPrecondition));
		binding.bindLiterals(load, openPrecondition, 0);

	}
	
	
	@Test
	public void testBinding() throws FileNotFoundException
	{
		

		Parser parser = new Parser();
		
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		Planner planner = new Planner(parser);
		Binding binding = new Binding(parser);
		
		Step unload = parser.ActionsDomain.get(1);
		Literal precondition = parser.getGoalPreconditions(0);
		assertEquals("CargoAt C1 JFK", precondition.toString());
		
		assertEquals("In ?c ?p", unload.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p ?a", unload.getPreconditions(1).toString());
		assertEquals("Cargo ?c", unload.getPreconditions(2).toString());
		assertEquals("Plane ?p", unload.getPreconditions(3).toString());
		assertEquals("Airport ?a",unload.getPreconditions(4).toString());
		assertEquals("CargoAt ?c ?a",unload.getEffects(0).toString());
		assertEquals("In ?c ?p",unload.getEffects(1).toString());

		//bind the variables
		binding.bindLiterals(unload, precondition, 0);
		
		assertEquals("In C1 ?p", unload.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p JFK", unload.getPreconditions(1).toString());
		assertEquals("Cargo C1", unload.getPreconditions(2).toString());
		assertEquals("Plane ?p", unload.getPreconditions(3).toString());
		assertEquals("Airport JFK",unload.getPreconditions(4).toString());
		assertEquals("CargoAt C1 JFK",unload.getEffects(0).toString());
		assertEquals("In C1 ?p",unload.getEffects(1).toString());
		
		//To check if it is fully bounded or not
		assertTrue(binding.isBounded(precondition));
		assertFalse(binding.isBounded(unload.getPreconditions(0)));	//In C1 ?p
		assertTrue(binding.isBounded(unload.getPreconditions(2)));	//Cargo C1
		
		//To check which parameter is not bounded
		assertEquals(1,binding.checkParaNotBounded(unload.getPreconditions(0))); //In C1 ?p
		
		
		
		
		
		//This is to test binding literals "Literals that are not fully bounded"
		precondition.setLiteralParameters(0, "?c");
		precondition.setLiteralParameters(1, "SFO");
		assertEquals("CargoAt ?c SFO", precondition.toString());
		assertFalse(binding.isBounded(precondition));

		
		binding.bindLiterals(unload, precondition, 0);

		assertEquals("In ?c ?p", unload.getPreconditions(0).toString());
		assertEquals("PlaneAt ?p SFO", unload.getPreconditions(1).toString());
		assertEquals("Cargo ?c", unload.getPreconditions(2).toString());
		assertEquals("Plane ?p", unload.getPreconditions(3).toString());
		assertEquals("Airport SFO",unload.getPreconditions(4).toString());
		assertEquals("CargoAt ?c SFO",unload.getEffects(0).toString());
		assertEquals("In ?c ?p",unload.getEffects(1).toString());
		

	}
	
	

	@Test
	public void testPlanner() throws FileNotFoundException
	{
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";

		String name = "GoalState";
		ArrayList<Literal> preconditions= null; //= new ArrayList <Literal>();
		ArrayList<Literal> effects = null;
		
		Parser parser = new Parser();
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		Planner planner = new Planner(parser);


		Literal literal = new Literal(null, null);
		literal.addLiteralName("At");
		literal.addLiteralParameters("C1");
		literal.addLiteralParameters("SFO");
		
		planner.searchEffectInInitialState(literal);
		literal.setLiteralParameters(1, "?q");
		planner.searchEffectInInitialState(literal);
		literal.setLiteralParameters(0, "?p");
		planner.searchEffectInInitialState(literal);

		Literal literal1 = new Literal(null, null);
		literal1.addLiteralName("At");
		literal1.addLiteralParameters("C1");
		//planner.searchEffectInInitialState(literal1);



	}

}
