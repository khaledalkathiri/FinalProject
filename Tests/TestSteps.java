import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TestSteps 
{
	@Test
	public void testInitlization()
	{
		Preconditions preconditions = new Preconditions();
		Effects effects = new Effects();
		
		String effect1 = "Have Drill";
		String precondition1 = "At Hardware";
		String precondition2 = "Sells Drill";
		
		
		//adding the effects and preconditions
		effects.effectArray.add(effect1);
		preconditions.preconditionArray.add(precondition1);
		preconditions.preconditionArray.add(precondition2);
		
	
		Steps s1 = new Steps(effects,preconditions);
		s1.steps.add(s1);
		
		assertEquals("Have Drill", s1.getEffects(0, 0));
		assertEquals("At Hardware", s1.getPreconditions(0,0));
		assertEquals("Sells Drill", s1.getPreconditions(0,1));

	}
	
	@Test
	public void testaddingMoreSteps()
	{
		Preconditions preconditions = new Preconditions();
		Effects effects = new Effects();
		
		String effect1 = "Have Drill";
		String precondition1 = "At Hardware";
		String precondition2 = "Sells Drill";
		
		
		//adding the effects and preconditions
		effects.effectArray.add(effect1);
		preconditions.preconditionArray.add(precondition1);
		preconditions.preconditionArray.add(precondition2);
		
		Steps s1 = new Steps(effects,preconditions);
		s1.steps.add(s1);
		
		
		
		//Testing the first step
		assertEquals("Have Drill", s1.getEffects(0,0));
		assertEquals("At Hardware", s1.getPreconditions(0,0));
		assertEquals("Sells Drill", s1.getPreconditions(0,1));
		
		
		//The second step
		String effect2 = "Have Milk";
		String precondition3 = "At supermarket";
		String precondition4 = "Sells Milk";
		
		effects.effectArray.add(effect2);
		preconditions.preconditionArray.add(precondition3);
		preconditions.preconditionArray.add(precondition4);
		effects.addEffect(effect2);
		
		Steps s2 = new Steps(effects,preconditions);
		s2.steps.add(s2);
		s2.addEffects(effect2);

		


		//Testing the second step
		assertEquals("Have Milk", s2.getEffects(0,0));
		assertEquals("At supermarket", s2.getPreconditions(1,0));
		assertEquals("Sells Milk", s2.getPreconditions(1,1));

	}
	
//	@Test
//	public void testMEEEEE()
//	{
//		
//		//Preconditions preconditions = new Preconditions();
//		//Effects effects = new Effects();
//		
//		String effect1 = "Have Drill";
//		String precondition1 = "At Hardware";
//		String precondition2 = "Sells Drill";
//		
//		Steps s1 = new Steps(effects,preconditions);
//		assertTrue(s1.steps.add(s1));
//		
//		s1.addEffects(effect1);
//		s1.addPreconditions(precondition1);
//		s1.addPreconditions(precondition2);
//		
//		assertEquals("Have Drill", s1.getEffects(0, 0));
//		assertEquals("At Hardware", s1.getPreconditions(0,0));
//		assertEquals("Sells Drill", s1.getPreconditions(0,1));
//		
//		
//
//		//The second step
//		String effect2 = "Have Milk";
//		String precondition3 = "At supermarket";
//		String precondition4 = "Sells Milk";
//		
//		Steps s2 = new Steps(effects,preconditions);
//
//		s2.addEffects(effect2);
//		s2.addPreconditions(precondition3);
//		s2.addPreconditions(precondition4);
//		
//		assertTrue(s2.steps.add(s2));
//		System.out.println(s2.getEffects(0, 1));
//
//		
//		//Testing the second step
//		assertEquals("Have Milk", s2.getEffects(0,1));
//		assertEquals("At supermarket", s2.getPreconditions(0,0));
//		assertEquals("Sells Milk", s2.getPreconditions(1,1));
//
//		
//	}
	
	
	@Test
	public void testInitilaizingSteps()
	{
		
		//Preconditions preconditions = new Preconditions();
		//Effects effects = new Effects();
		
		String effect1 = "Have Drill";
		String precondition1 = "At Hardware";
		String precondition2 = "Sells Drill";
		
		Steps s1 = new Steps();
		assertTrue(s1.steps.add(s1));
		
		s1.addEffects(effect1);
		s1.addPreconditions(precondition1);
		s1.addPreconditions(precondition2);
		
		assertEquals("Have Drill", s1.getEffects(0, 0));
		assertEquals("At Hardware", s1.getPreconditions(0,0));
		assertEquals("Sells Drill", s1.getPreconditions(0,1));
		
		

		//The second step
		String effect2 = "Have Milk";
		String precondition3 = "At supermarket";
		String precondition4 = "Sells Milk";
		
		Steps s2 = new Steps();

		s2.addEffects(effect2);
		s2.addPreconditions(precondition3);
		s2.addPreconditions(precondition4);
		
		assertTrue(s2.steps.add(s2));
		System.out.println(s2.getEffects(0, 0));
		System.out.println(s1.getEffects(0, 0));

		
		//Testing the second step
		assertEquals("Have Milk", s2.getEffects(0,0));
		assertEquals("At supermarket", s2.getPreconditions(0,0));
		assertEquals("Sells Milk", s2.getPreconditions(0,1));

		
	}

	
	@Test
	public void testNEWstep()
	{
		Planner s = new Planner();
		Effects initialEffects = new Effects();
		String initialEffects1 = "At Home";
		String initialEffects2 = "Has Money";

		Step intialState = new Step(1,"initialState",null,initialEffects);

		initialEffects.addEffect(initialEffects1);
		initialEffects.addEffect(initialEffects2);

		
		s.addIntialState(intialState);
		assertEquals("At Home", s.getIntialState().getEffects(0));
		assertEquals("Has Money", s.getIntialState().getEffects(1));
		//assertNull(s.getIntialState().getPreconditions(0));

		System.out.println(s.getIntialState().getEffects(1));
		
		Preconditions preconditions = new Preconditions();
		Effects effects = new Effects();
		
		String effect1 = "Have Drill";
		String precondition1 = "At Hardware";
		String precondition2 = "Sells Drill";
		
		Step step1 = new Step(0,"Buy Drill", preconditions,effects);

		preconditions.addPrecondition(precondition1);
		preconditions.addPrecondition(precondition2);
		effects.addEffect(effect1);
		
		s.addStep(step1);
		
		assertEquals("Have Drill",step1.getEffects(0));
		assertEquals("At Hardware",step1.getPreconditions(0));
		assertEquals("Sells Drill",step1.getPreconditions(1));
		

		
		//The second step
		Preconditions preconditions1 = new Preconditions();
		Effects effects1 = new Effects();
		
		
		String effect2 = "Have Milk";
		String precondition3 = "At supermarket";
		String precondition4 = "Sells Milk";
		
		preconditions1.addPrecondition(precondition3);
		preconditions1.addPrecondition(precondition4);
		effects1.addEffect(effect2);
		
		Step step2 = new Step(1,"Buy Milk", preconditions1,effects1);
		
		assertEquals("Have Milk",step2.getEffects(0));
		assertEquals("At supermarket",step2.getPreconditions(0));
		assertEquals("Sells Milk",step2.getPreconditions(1));
		
		s.addStep(step2);
		


	}

}
