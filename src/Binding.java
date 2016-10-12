
public class Binding
{
	
	private Step variable;
	private Literal precondition;
	
	
	public Binding(Step variable, Literal precondition)
	{
		this.variable = variable;
		this.precondition = precondition;

	}
	
	
	
	public void bindLiterals(Step variable, Literal precondition, int effectNum)  
	{
		//size of parameters in the precondition
		int size = precondition.sizeLiteralParameters();
		
		//adding the ground letters to array 
		String arry[] = new String[size];
		for(int i =0; i< size; i++)
		{
			arry[i]= variable.getEffects(effectNum).getLiteralParameters(i);
		}
		
		for(int i =0; i<size;i++)
		{
			String parameter = precondition.getLiteralParameters(i);	//At (C1) JFK
			
			//The preconditions
			int sizPrecondition = variable.getPreconditionSize();
			for(int f =0; f < sizPrecondition;f++)
			{
				//size literals
				int sizeLiterals = variable.getPreconditions(f).sizeLiteralParameters();
				for(int x= 0; x < sizeLiterals;x++)
				{
					if(variable.getPreconditions(f).getLiteralParameters(x).equals(arry[i]))
					{
						variable.getPreconditions(f).setLiteralParameters(x, parameter);
//						System.out.print(variable.getPreconditions(f).getLiteralName());
//						System.out.print(variable.getPreconditions(f).getLiteralParameters(0));
//						System.out.println(variable.getPreconditions(f).getLiteralParameters(1));

					}
				}
			}
			
			//The effects
			int sizeEffects = variable.getEffectsSize();
			for(int f = 0; f<sizeEffects;f++)
			{
				int sizeLiterals = variable.getEffects(f).sizeLiteralParameters();
				for(int x=0; x<sizeLiterals;x++)
				{
					if(variable.getEffects(f).getLiteralParameters(x).equals(arry[i]))
					{
						variable.getEffects(f).setLiteralParameters(x, parameter);
//						System.out.print(variable.getEffects(f).getLiteralName());
//						System.out.print(variable.getEffects(f).getLiteralParameters(0));
//						System.out.println(variable.getEffects(f).getLiteralParameters(1));

					}

				}
			}
		}

	}
	
	
	public boolean isBounded(Literal literal)
	{
		int size = literal.sizeLiteralParameters();
		for(int i =0; i< size;i++)
		{
			if(literal.getLiteralParameters(i).contains("?"))
				return false;
		}
		return true;
	}
	
	
	public int checkParaNotBounded(Literal literal)
	{
		int size = literal.sizeLiteralParameters();
		for(int i =0; i< size;i++)
		{
			if(literal.getLiteralParameters(i).contains("?"))
				return i;
		}
		return 0;
	}




}
