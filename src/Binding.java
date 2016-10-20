import java.util.ArrayList;

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
	 * @param precondition
	 * @param effectNum
	 */
	public void bindLiterals(Step variable, Literal precondition, int effectNum)  
	{
		
		int x = parser.countParaInPredicate(precondition);
		
		//to hold the letters
		String GroundArry[] = new String[x];
		String  arry[] = new String[x];


		for (int i = 0;i< x; i++)
		{
			Literal predicate = parser.getPredicate(precondition);
			//adding the ground letters to array 
			//GroundArry[i]= predicate.getLiteralParameters(i);
			GroundArry[i]= variable.getEffects(effectNum).getLiteralParameters(i);
			arry[i]= precondition.getLiteralParameters(i);
			
			System.out.println(GroundArry[i]+"   "+ arry[i]);

		}
		
		//bind the step
		for (int f =0; f< arry.length; f++)
		{
			bindStep(variable,GroundArry[f],arry[f]);

		}
	

	}


	/**
	 * This function returns a step with the new binding letters	
	 * @param variable
	 * @param groundLetter
	 * @param newVariable
	 * @return
	 */
	public Step bindStep(Step variable,String groundLetter,String newVariable)
	{

		int preconditionsSize = variable.getPreconditionSize();
		int effectsSize = variable.getEffectsSize();


		//preconditions
		for(int i=0;i<preconditionsSize; i++)
		{
			int sizePara = variable.getPreconditions(i).sizeLiteralParameters();
			for(int f =0; f<sizePara;f++)
			{
				if(variable.getPreconditions(i).getLiteralParameters(f).equals(groundLetter))
					variable.getPreconditions(i).setLiteralParameters(f, newVariable);
			}
		}


		//effects
		for(int y=0;y<effectsSize; y++)
		{
			int sizePara = variable.getEffects(y).sizeLiteralParameters();
			for(int f =0; f<sizePara;f++)
			{
				if(variable.getEffects(y).getLiteralParameters(f).equals(groundLetter))
					variable.getEffects(y).setLiteralParameters(f, newVariable);
			}
		}


		return variable;
	}


	/**
	 * This function is to bind the precondition with the new variables
	 * @param precondition
	 * @param groundLetter
	 * @param newVariable
	 * @return
	 */
	private Literal bindPrecondtion(Literal precondition, String groundLetter, String newVariable) 
	{
		int precondtionParaSize = precondition.sizeLiteralParameters();
		for(int i=0;i<precondtionParaSize;i++)
		{
			if(precondition.getLiteralParameters(i).equals(groundLetter))
				precondition.setLiteralParameters(i, newVariable);

		}
		return precondition;
	}


	/**
	 * This function binds the current step when the precondition is bounded with the next step
	 * @param step
	 * @param precondition
	 * @return
	 */
	public Step bindStepByPreocndition (Step step,Literal precondition)
	{
		int x = parser.countParaInPredicate(precondition);
		
		String GroundArry[] = new String[x];
		String  arry[] = new String[x];

		for (int i = 0;i< x; i++)
		{
			Literal predicate = parser.getPredicate(precondition);
			//adding the ground letters to array 
			GroundArry[i]= predicate.getLiteralParameters(i);
			arry[i]= precondition.getLiteralParameters(i);

		}
		
		//bind the step
		for (int f =0; f< arry.length; f++)
		{
			bindStep(step,GroundArry[f],arry[f]);

		}
		
		return step;
	}
	

	/**
	 * 
	 * @param variable
	 * @param precondition
	 * @return
	 */
	public Literal bindNextLiterals(Step variable, Literal precondition)		//Variable (LOAD) 	precondition( IN C1 p)
	{
		Literal newPrecondition = precondition;
		
		int sizeEffectInitailState = parser.getProblemDomainEffectsSize();	
		int sizeStepPrecondtion = variable.getPreconditionSize();

		for(int x=0;x<sizeStepPrecondtion;x++ )		//The size of preconditions in the action
		{


			Literal literal = variable.getPreconditions(x);
			ArrayList <Literal> arry = new ArrayList <Literal>();

			if(!(this.isBounded(literal)))
			{

				//This to add the steps that have the same names to the temp array to check them
				int index=0;
				for(int y =0; y< sizeEffectInitailState;y++)		//The size of effect in initial state
				{

					//CargoAt == CargoAt
					if(literal.getLiteralName().equals(parser.getIntialStateEffects(y).getLiteralName()))		
					{
						arry.add(index,parser.getIntialStateEffects(y));
						index++;

					}
				}


				//loop through the array to check if there is a match in the initial State
				for(int f=0; f< arry.size();f++)
				{
					int paraBounded = this.checkParaBounded(literal);

					Literal temp = arry.get(f);	//CargoAt C1 SFO & CargoAt C2 JFK
					if(literal.getLiteralParameters(paraBounded).equals(temp.getLiteralParameters(paraBounded)))
					{

						int paraNotBounded = this.checkParaNotBounded(literal);
						String groundLetter = literal.getLiteralParameters(paraNotBounded);
						String newVariable = temp.getLiteralParameters(paraNotBounded);

						Step boundedStep = bindStep(variable,groundLetter,newVariable);
						newPrecondition = bindPrecondtion(precondition, groundLetter, newVariable);
						//variable = boundedStep;

						arry.clear();
					}
				}
			}
		}
		return newPrecondition;
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


	/**
	 * This function is to check whether the  parameter is bounded or not 
	 * @param para
	 * @return false if the parameter empty
	 */
	public boolean isParaBounded(String para)
	{
		if(para.contains("?"))
			return false;
		else
			return true;
	}


	/**
	 * This function is to check which parameter is not bounded
	 * @param literal
	 * @return the index of the parameter
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


	/**
	 * This function is to check which parameter is bounded
	 * @param literal
	 * @return the index of the parameter
	 */
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

//	/**
//	 * This function bound the current precondition with the next step
//	 * @param variable
//	 * @param effect
//	 * @param effectNum
//	 */
//	public void bindLiterals(Step variable, Literal effect, int effectNum)  
//	{
//		//size of parameters in the precondition
//		int size = effect.sizeLiteralParameters();
//
//		//adding the ground letters to array 
//		String arry[] = new String[size];
//		for(int i =0; i< size; i++)
//		{
//			//This need to be change
//			//need to be taken from predicate because it might has parameters
//			arry[i]= variable.getEffects(effectNum).getLiteralParameters(i);
//			System.out.println(arry[i]);
//		}
//
//		for(int i =0; i<size;i++)
//		{
//			String parameter = effect.getLiteralParameters(i);	//At (C1) JFK
//
//			//The preconditions
//			int sizPrecondition = variable.getPreconditionSize();
//			for(int f =0; f < sizPrecondition;f++)
//			{
//				//size literals
//				int sizeLiterals = variable.getPreconditions(f).sizeLiteralParameters();
//				for(int x= 0; x < sizeLiterals;x++)
//				{
//					System.out.println(variable.getPreconditions(f).toString());
//
//					if(variable.getPreconditions(f).getLiteralParameters(x).equals(arry[i]))
//					{
//
//						variable.getPreconditions(f).setLiteralParameters(x, parameter);
//						//						System.out.print(variable.getPreconditions(f).getLiteralParameters(0));
//						//						System.out.println(variable.getPreconditions(f).getLiteralParameters(1));
//
//					}
//				}
//			}
//
//			//The effects
//			int sizeEffects = variable.getEffectsSize();
//			for(int f = 0; f<sizeEffects;f++)
//			{
//				int sizeLiterals = variable.getEffects(f).sizeLiteralParameters();
//				for(int x=0; x<sizeLiterals;x++)
//				{
//					if(variable.getEffects(f).getLiteralParameters(x).equals(arry[i]))
//					{
//						variable.getEffects(f).setLiteralParameters(x, parameter);
//						//						System.out.print(variable.getEffects(f).getLiteralName());
//						//						System.out.print(variable.getEffects(f).getLiteralParameters(0));
//						//						System.out.println(variable.getEffects(f).getLiteralParameters(1));
//
//					}
//
//				}
//			}
//		}
//
//	}


}
