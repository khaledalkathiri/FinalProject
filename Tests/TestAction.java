import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestAction {

	@Test
	public void testIntializingActionsWithOnePara() 
	{
		Actions action1= new Actions("Cook", "Cake");
		assertEquals("Cook", action1.getAction());
		assertEquals("Cake", action1.getPara1());
	}
	
	@Test
	public void testIntializingActionsWithTwoPara()
	{
		Actions action1 = new Actions("Fly","From", "To");
		assertEquals("Fly", action1.getAction());
		assertEquals("From", action1.getPara1());
		assertEquals("To", action1.getPara2());
	}
	
	
	@Test
	public void testAddingActions()
	{
		Actions action1 = new Actions("Fly","From", "To");
		Actions action2 = new Actions("Cook", "Cake");
		
		ArrayList<Actions>  obj = new ArrayList<Actions>();
		assertTrue(obj.add(action1));
		assertTrue(obj.add(action2));
		assertEquals("Fly", action1.getAction());
		assertEquals("Cook", action2.getAction());



	}

}
