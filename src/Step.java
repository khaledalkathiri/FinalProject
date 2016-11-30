import java.util.ArrayList;
import java.util.Collection;

public class Step
{
	
	//private  int index;
	//private  boolean executed;
	private String name;
	private int id;
	private Literal literal;
	
	ArrayList <Literal>  preconditionArray = new ArrayList <Literal>();
	ArrayList <Literal>  effectArray = new ArrayList <Literal>();
	ArrayList <String> parameters = new ArrayList <String>();
	
	
	
	public Step(String name,ArrayList <String> parameters, ArrayList <Literal> preconditionArray, ArrayList <Literal>  effectArray, int id)
	{
		this.preconditionArray = new ArrayList <Literal>();
		this.effectArray = new ArrayList <Literal>();
		this.parameters = new ArrayList <String>();
		this.id = id;
		this.name = name;		
	}
	
	//don't forget to copy the parameter in the copy consturct
	
	public Step(Step aStep)
	{
		this.name = aStep.getStepName();
		this.id = aStep.getStepId();
		this.effectArray = aStep.getEffects();
		this.preconditionArray= aStep.getPreconditions();
		this.parameters =aStep.getParameter();
		
	}
	
	
	


	/**
	 * This used to get a copy of the effect array in the constructor
	 * @return effectArray
	 */
	private ArrayList<Literal> getEffects() 
	{
		ArrayList <Literal> effects = new ArrayList<Literal>();
		int sizeEffects = this.getEffectsSize();
		for(int i=0; i<sizeEffects;i++)
		{
			Literal newLit = new Literal(null,null);
			newLit = effectArray.get(i);
			Literal literal = new Literal(newLit) ;
			effects.add(literal);
		}
		return effects;
	}

	/**
	 * This used to get a copy of the precondition array in the constructor
	 * @return precondition Array
	 */
	private ArrayList<Literal> getPreconditions() 
	{
		ArrayList <Literal> preconditions = new ArrayList<Literal>();
		int sizePrecon = this.getPreconditionSize();
		for(int i=0; i<sizePrecon;i++)
		{
			Literal newLit = new Literal(null,null);
			newLit = preconditionArray.get(i);
			Literal literal = new Literal(newLit) ;
			preconditions.add(literal);
		}
		return preconditions;
	}

	/**
	 * This used to get a copy of the parameters array in the constructor
	 * @return precondition Array
	 */
	private ArrayList<String> getParameter() 
	{
		ArrayList <String> para = new ArrayList<String>();
		int sizePara = this.getParameterSize();
		
		for(int i=0; i<sizePara;i++)
		{
			String s = parameters.get(i);
			para.add(s);
		}
		return para;
	}
	
	
	//Not tested
	public String getStepName()
	{
		return this.name;
	}
	
	public Step getStep()
	{
		return this;
	}
	
	public void addStepName(String name)
	{
		this.name = name;
	}
	
	
	public Literal getEffects(int index)
	{
		return effectArray.get(index);
	}
	
	
	public void addEffects(Literal effect)
	{
		 this.effectArray.add(effect);
	}
	
	public Literal getPreconditions(int index)
	{
		return preconditionArray.get(index);
	}
	
	
	public void addPreconditions(Literal precondition)
	{
		 this.preconditionArray.add(precondition);
	}
	
	
	public int getPreconditionSize()
	{
		return preconditionArray.size();
	}
	
	public int getEffectsSize()
	{
		return effectArray.size();
	}
	
	
	//parameters
	public void addParameter(ArrayList<String> arrayList)
	{
		this.parameters.addAll(arrayList);
	}
	
	public String getParameter(int index)
	{
		return parameters.get(index);
	}
	
	
	public void setParameter(int index ,String para)
	{
		this.parameters.set(index, para);
	}
	
	public int getParameterSize()
	{
		return this.parameters.size();
	}

	//step id
	public int getStepId()
	{
		return this.id;
	}
	
	public void setStepId(int id)
	{
		this.id = id;
	}

	/**
	 * This method is to print out the final result
	 */
	public String toString()
	{
		
		return this.getStepName() + this.getParameter().toString();
	}



}
