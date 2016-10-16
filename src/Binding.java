
public class Binding
{

	private Step variable;
	private Literal precondition;
	Parser parser;


	public Binding(Parser parser)
	{
		this.parser= parser;
		this.variable = variable;
		this.precondition = precondition;


	}


	/**
	 * This function bound the current precondition with the next step
	 * @param variable
	 * @param effect
	 * @param effectNum
	 */
	public void bindLiterals(Step variable, Literal effect, int effectNum)  
	{
		//size of parameters in the precondition
		int size = effect.sizeLiteralParameters();

		//adding the ground letters to array 
		String arry[] = new String[size];
		for(int i =0; i< size; i++)
		{
			arry[i]= variable.getEffects(effectNum).getLiteralParameters(i);
		}

		for(int i =0; i<size;i++)
		{
			String parameter = effect.getLiteralParameters(i);	//At (C1) JFK

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
	
	
	/**
	 * This method is to bind the current step based on a precondition
	 * @return
	 */
	public Step bindLiterals(Step variable, Literal precondtion)
	{
		//size of parameters in the precondition
		int size = precondtion.sizeLiteralParameters();

		//adding the ground letters to array 
		String arry[] = new String[size];
		
		for(int i =0; i< size; i++)
		{
			arry[i]= variable.getEffects(effectNum).getLiteralParameters(i);
		}

		for(int i =0; i<size;i++)
		{
			String parameter = effect.getLiteralParameters(i);	//At (C1) JFK

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
		
		
		
		
		
		
		
		return variable;
	}


	/**
	 * 
	 * @param variable
	 * @param precondition
	 * @return
	 */
	public Literal bindNextLiterals(Step variable, Literal precondition)		//Variable (LOAD) 	precondition( IN C! p)
	{

		int sizeEffectInitailState = parser.getProblemDomainEffectsSize();	
		int sizeStepPrecondtion = variable.getPreconditionSize();

		for(int x=0;x<sizeStepPrecondtion;x++ )		//The size of preconditions in the action
		{

			Literal literal = variable.getPreconditions(x);
			Literal arry[] = new Literal[sizeEffectInitailState];
			if(!(this.isBounded(literal)))
			{
				//This to add the steps that have the same names to the temp array to check them
				int index=0;
				for(int y =0; y<sizeEffectInitailState;y++)		//The size of effect in initial state
				{
					if(literal.getLiteralName().equals(parser.getIntialStateEffects(y).getLiteralName()))		//CargoAt == CargoAt
					{
						arry[index] = parser.getIntialStateEffects(y);
						index++;
					}
				}

				int paraBounded = this.checkParaBounded(literal);
				for(int f=0; f< arry.length;f++)
				{
					Literal temp = arry[f];
					if(literal.getLiteralParameters(paraBounded).equals(temp.getLiteralParameters(paraBounded)))
					{
						//herer you call the method to bind all variables
						System.out.println(temp.toString());
						bindLiterals(variable,temp);
						return literal;

					}
				}
			}
		}

		return null;
	}




	/**
	 * This function is to check if the literal is fully bounded or not
	 * @param literal
	 * @return true if the literal is fully bounded
	 */
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
	
	
	
	public boolean isParaBounded(String para)
	{
		if(para.contains("?"))
			return false;
		else
		return true;
	}


	/**
	 * This 
	 * @param literal
	 * @return
	 */
	public int checkParaNotBounded(Literal literal)
	{
		int size = literal.sizeLiteralParameters();
		for(int i =0; i< size;i++)
		{
			if(literal.getLiteralParameters(i).contains("?"))
				return i;
		}
		return -1;
	}
	
	
	public int checkParaBounded(Literal literal)
	{
		int size = literal.sizeLiteralParameters();
		for(int i =0;i<size; i++)
		{
			if(!(literal.getLiteralParameters(i).contains("?")))
				return i;
		}
		return -1;
	}




}
