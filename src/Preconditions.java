import java.util.ArrayList;

public class Preconditions
{
	private String precondition;
	ArrayList <String>  preconditionArray = new ArrayList <String>();
//	
//	
//	public Preconditions()
//	{
//		//this.obj = new ArrayList <String>();
//		
//		this.precondition = precondition;
//	}
//	
	/**
	 * Adding preconditions to the arraylist of preconditions
	 * @param precondition
	 */
	public void addPrecondition(String precondition)
	{
		preconditionArray.add(precondition);
	}
	
	
	public String getPrecondition(int index)
	{
		return preconditionArray.get(index);
	}
	

}
