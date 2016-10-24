import java.util.HashMap;
import java.util.Map;

public class CausalLink
{
	
	private Step newStep;

	private Literal precondition;
	private boolean excuted;

	
	
	
	public CausalLink()
	{
		this.excuted = false;
	}
	
	
	
	public void addLink(Step newStep, Literal precondition)
	{
		this.newStep = newStep;
		this.precondition = precondition;
		precondition.setExcuted(true);

	}

	public Step getStepName()
	{
		return this.newStep;
	}

	public Literal getPrecondition()
	{
		return this.precondition;
	}

	
	
	public String toString()
	{

		return this.getPrecondition() + " -->" + this.getStepName().getStepName();
	}
	
	


}
