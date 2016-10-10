
public class CausalLink
{
	
	private Step oldStep;
	private Step newStep;
	private Literal precondition;
	
	
	
	public CausalLink(Step oldStep, Literal precondition, Step  newStep)
	{
		this.oldStep = oldStep;
		this.newStep = newStep;
		this.precondition = precondition;
	}

}
