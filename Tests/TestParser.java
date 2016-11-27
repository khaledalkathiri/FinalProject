
import static org.junit.Assert.*;



import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

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
		
		//parameters
		assertEquals("(?c", p.ActionsDomain.get(0).getParameter(0));
		assertEquals(" LOAD[?c, ?p, ?a]", p.ActionsDomain.get(0).toString());


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
	public void testGettingOpenPrecondition() throws FileNotFoundException, CloneNotSupportedException
	{
		Parser parser = new Parser();
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		Planner planner = new QueueMethod(parser);
		planner.addGoalOpenPrecondition();
		Literal firstprecon = planner.getOpenPrecondition().getOpenPrecondtion();
		assertEquals("CargoAt C1 JFK",firstprecon.toString());
		OpenPrecondition precon = new OpenPrecondition(0,firstprecon);

		planner.searchEffectsInActionDomain(precon);
		

		Literal secondprecon = planner.getOpenPrecondition().getOpenPrecondtion();
		assertEquals("CargoAt C2 SFO",secondprecon.toString());
		precon = new OpenPrecondition(0,secondprecon);
		planner.searchEffectsInActionDomain(precon);
		
		
		assertEquals("In C1 ?p",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("PlaneAt ?p JFK",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("Cargo C1",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("Plane ?p",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("Airport JFK",planner.getOpenPrecondition().getOpenPrecondtion().toString());

		assertEquals("In ?c ?p", parser.ActionsDomain.get(1).getPreconditions(0).toString());

		assertEquals("In ?c ?p", parser.getAction(1).getPreconditions(0).toString());
		assertEquals("In ?c ?p", parser.getAction(1).getPreconditions(0).toString());

		assertEquals("In C2 ?p",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("PlaneAt ?p SFO",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("Cargo C2",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("Plane ?p",planner.getOpenPrecondition().getOpenPrecondtion().toString());
		assertEquals("Airport SFO",planner.getOpenPrecondition().getOpenPrecondtion().toString());

		
	}
	
	
	@Test
	public void testCausalLink() throws FileNotFoundException
	{
		Parser parser = new Parser();
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		
//		String domainName = "ShoppingDomain.txt";
//		String problemName = "ShoopingProblem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		//Planner planner = new QueueMethod(parser);
		//Planner planner = new PriorityQueue(parser);
		Planner planner = new BagMethod(parser);

		Binding binding = new Binding(parser);
		
		planner.search();
		
//		assertEquals("", planner.Actions.get(2).getEffects(1));

//		
//		Literal precondition = parser.getGoalPreconditions(0);
//		assertEquals("CargoAt C1 JFK", precondition.toString());
//		assertEquals(false,precondition.isExcuted());
//
//				
//		Step unload = parser.ActionsDomain.get(1);
//		assertEquals(" UNLOAD",unload.getStepName());
//		assertEquals("In ?c ?p", unload.getPreconditions(0).toString());
//		assertEquals("PlaneAt ?p ?a",unload.getPreconditions(1).toString());
//		assertEquals("Cargo ?c", unload.getPreconditions(2).toString());
//		assertEquals("Plane ?p", unload.getPreconditions(3).toString());
//		assertEquals("Airport ?a", unload.getPreconditions(4).toString());
//		assertEquals("CargoAt ?c ?a", unload.getEffects(0).toString());
//		assertEquals("In ?c ?p", unload.getEffects(1).toString());
//		
//		OpenPrecondition obj = new OpenPrecondition(0,precondition);
//		
//		CausalLink link = new CausalLink(obj, unload,precondition);
//		obj.getOpenPrecondtion().setExcuted(true);
//		assertEquals(true,precondition.isExcuted());
//		assertEquals("CargoAt C1 JFK --> UNLOAD-->CargoAt C1 JFK", link.toString());
//		assertEquals("CargoAt C1 JFK",link.getPrecondition().getOpenPrecondtion().toString());
//		
//		planner.search();
//		assertEquals("CargoAt C1 JFK",planner.getCausalLink(0).getPrecondition().getOpenPrecondtion().toString());
//		assertEquals("In C1 P1",planner.getCausalLink(1).getPrecondition().getOpenPrecondtion().toString());
//		assertEquals("CargoAt C1 JFK", planner.getCausalLink(0).getEffect().toString());
//		
		//planner.CheckThreats();

	}
	
	@Test
	public void testOrdering() throws FileNotFoundException
	{
		Parser parser = new Parser();
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		Planner planner = new Planner(parser);
		Binding binding = new Binding(parser);
		
		Order ordering = new Order(null, null);
		Step unload = parser.ActionsDomain.get(1);
		Step load = parser.ActionsDomain.get(0);

		Step goal = parser.getGoalState();

		ordering.addStep(goal);
		ordering.addOrder(unload);
		ordering.addOrder(load);
		assertEquals("goal--> UNLOAD   LOAD  ", ordering.toString());
		
		OpenPrecondition temp = new OpenPrecondition(null,null);
		Literal openPrecondition = parser.getGoalPreconditions(0);
		temp.addOpenPrcondition(openPrecondition);
		temp.addStep(goal);
		
		
		planner.searchEffectsInActionDomain(temp);
		assertEquals("CargoAt C1 JFK", temp.getOpenPrecondtion().toString());
		
		assertEquals("goal", planner.order.get(0).getStep().getStepName());
		assertEquals(" UNLOAD", planner.order.get(0).getStepInArray(0).getStepName());
		
		temp= new  OpenPrecondition(null,null);
		Literal nextPrecondition = parser.getAction(1).preconditionArray.get(0);
		temp.addOpenPrcondition(nextPrecondition);
		temp.addStep(unload);
		//to make sure we have the right precondtion
		assertEquals("In C1 ?p", temp.getOpenPrecondtion().toString());

		
		planner.searchEffectsInActionDomain(temp);
		assertEquals(" UNLOAD", planner.order.get(1).getStep().getStepName());
		assertEquals(" LOAD", planner.order.get(1).getStepInArray(0).getStepName());

		
		temp= new  OpenPrecondition(null,null);
		Literal nextPrecondition2 = parser.getAction(1).preconditionArray.get(1);
		temp.addOpenPrcondition(nextPrecondition2);
		temp.addStep(unload);
		assertEquals("PlaneAt P1 JFK", temp.getOpenPrecondtion().toString());

		planner.searchEffectsInActionDomain(temp);
		assertEquals(" FLY", planner.order.get(1).getStepInArray(1).getStepName());

		


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
		
		//Planner planner = new Planner(p);
		//Planner planner = new QueueMethod(p);
		Planner planner = new PriorityQueue(p);


		planner.search();
		assertEquals("goal",planner.Actions.get(0).getStepName());
		assertEquals(" UNLOAD",planner.Actions.get(1).getStepName());
		assertEquals(" FLY",planner.Actions.get(2).getStepName());
		assertEquals(" LOAD",planner.Actions.get(3).getStepName());
//		
//		assertEquals("In C1 P1", planner.Actions.get(0).getPreconditions(0).toString());
//		assertEquals("PlaneAt P1 JFK", planner.Actions.get(0).getPreconditions(1).toString());
//		
//		assertEquals("CargoAt C1 SFO", planner.Actions.get(1).getPreconditions(0).toString());
//		assertEquals("PlaneAt P1 SFO", planner.Actions.get(1).getPreconditions(1).toString());
//
//		assertEquals("PlaneAt P1 SFO", planner.Actions.get(2).getPreconditions(0).toString());
//		assertEquals("Airport SFO", planner.Actions.get(2).getPreconditions(2).toString());

		
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
		OpenPrecondition openprecon = new OpenPrecondition(null, null);
		openprecon.addOpenPrcondition(parser.getGoalPreconditions(0));
		openprecon.addStep(parser.getGoalState());
		
		assertEquals("Cargo C1", parser.getIntialStateEffects(0).toString());
		assertEquals("CargoAt C1 JFK", parser.getGoalPreconditions(0).toString());
		assertFalse(planner.searchEffectInInitialState(openprecon));
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
	public void testBindingSimilar() throws FileNotFoundException
	{
		Parser parser = new Parser();
		
		String domainName = "Domain.txt";
		String problemName = "Problem.txt";
		parser.parseDomain(domainName);
		parser.parseProblem(problemName);
		
		Planner planner = new Planner(parser);
		Binding binding = new Binding(parser);
		OpenPrecondition openprecon = new OpenPrecondition(null, null);
		openprecon.addOpenPrcondition(parser.getGoalPreconditions(0));
		openprecon.addStep(parser.getGoalState());
		
		Step unload = parser.ActionsDomain.get(1);
		assertEquals("CargoAt C1 JFK", openprecon.getOpenPrecondtion().toString());
		openprecon.getOpenPrecondtion().setLiteralParameters(1, "?a");
		assertEquals("CargoAt C1 ?a", openprecon.getOpenPrecondtion().toString());

		//The problem here is that Actions LinkedList is empty
		planner.searchSimilarInInitialState(openprecon);
		assertEquals("CargoAt C1 SFO", openprecon.getOpenPrecondtion().toString());

		//it is really hard to test it because Action LinkedList is empty
//		assertEquals("In C1 ?p", unload.getPreconditions(0).toString());
//		assertEquals("PlaneAt ?p SFO", unload.getPreconditions(1).toString());
//		assertEquals("Cargo C1", unload.getPreconditions(2).toString());
//		assertEquals("Plane ?p", unload.getPreconditions(3).toString());
//		assertEquals("Airport SFO",unload.getPreconditions(4).toString());
//		assertEquals("CargoAt C1 SFO",unload.getEffects(0).toString());
//		assertEquals("In C1 ?p",unload.getEffects(1).toString());
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
		
		literal.setLiteralParameters(0, "?p");
		literal.setLiteralParameters(1, "?a");
		assertEquals("At ?p ?a", literal.toString());
		


	}

}
