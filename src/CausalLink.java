import java.util.HashMap;
import java.util.Map;

public class CausalLink
{
	
	private Step newStep;
	private Step oldStep;

	private Literal precondition;
	private boolean excuted;
	private CausalLink causalLink;

	
	
	
	public CausalLink(Step newStep, Literal precondition, Step oldStep)
	{
		this.newStep = newStep;
		this.oldStep = oldStep;
		this.precondition = precondition;
		this.excuted = false;
	}
	
	
	
	public void addLink(Step newStep, Literal precondition, Step oldStep)
	{
		this.precondition.setExcuted(true);
		//System.out.println("it's excuted");
			
			
	}


	public String toString()
	{
		return oldStep.getStepName()+ " -->" + newStep.getStepName();
	}
	
	


}
