import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TestSteps 
{

	
	@Test
	public void testInitlization()
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
	
	@Test
	public void testSearching()
	{
		
	}

}
