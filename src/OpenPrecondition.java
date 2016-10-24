

/**
 * This class is for adding the openPrecondition so I can keep track of their steps
 * @author khaledalkathiri
 *
 */
public class OpenPrecondition 
{
	
	private Step step;
	private Literal openPrecondition;
	
	public OpenPrecondition(Step step, Literal openPrecondition)
	{
		this.step = step;
		this.openPrecondition = openPrecondition;
	}
	
	
	public void addStep(Step step)
	{
		this.step = step;
	}
	
	public void addOpenPrcondition(Literal openPrecondition)
	{
		this.openPrecondition= openPrecondition;
	}
	
	/**
	 * This function returns the openPrecondition
	 * @return
	 */
	public Literal getOpenPrecondtion()
	{
		return openPrecondition;
	}
	
	
	/**
	 * This function returns the step of the openPreoconditon
	 * @return
	 */
	public Step getStep()
	{
		return step;
	}
	
	

}
