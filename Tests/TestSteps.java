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
		effects.obj.add(effect1);
		preconditions.obj.add(precondition1);
		preconditions.obj.add(precondition2);
		
	
		Steps s1 = new Steps(effects,preconditions);
		s1.obj.add(s1);
		
		assertEquals("Have Drill", s1.getEffects(0));
		assertEquals("At Hardware", s1.getPreconditions(0));
		assertEquals("Sells Drill", s1.getPreconditions(1));

	}
	
	@Test
	public void testaddingMoreSteps()
	{
		
		Preconditions preconditions = new Preconditions();
		Effects effects = new Effects();
		Steps s1 = new Steps(effects,preconditions);
		
		String effect1 = "Have Drill";
		String precondition1 = "At Hardware";
		String precondition2 = "Sells Drill";
		
		effects.addEffect(effect1);
		//effects.obj.add(effect1);
		preconditions.addPreconditions(precondition1);
		preconditions.addPreconditions(precondition2);

		//preconditions.obj.add(precondition1);
		//preconditions.obj.add(precondition2);
		
		//s1.obj.add(s1);
		
//		//The second step
//		String effect2 = "Have Milk";
//		String precondition3 = "At supermarket";
//		String precondition4 = "Sells Milk";
//		
//		//effects.obj.add(effect2);
//		preconditions.obj.add(precondition3);
//		preconditions.obj.add(precondition4);
//		effects.addEffect(effect2);
//		
//		Steps s2 = new Steps(effects,preconditions);
//		s2.obj.add(s2);


		
		//Testing the first step
		assertEquals("Have Drill", s1.getEffects(0));
		assertEquals("At Hardware", s1.getPreconditions(0));
		assertEquals("Sells Drill", s1.getPreconditions(1));

//		//Testing the second step
//		assertEquals("Have Milk", s1.getEffects(0));
//		assertEquals("At supermarket", s1.getPreconditions(0));
//		assertEquals("Sells Milk", s1.getPreconditions(1));

	}

}
