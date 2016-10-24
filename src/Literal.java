import java.util.ArrayList;

public class Literal
{


	private String name;
	private ArrayList <String> parameters = new ArrayList <String>();

	private Literal literal;

	private boolean isNegative;
	private boolean executed;


	public Literal(String name, ArrayList <String> parameters)
	{
		this.name= name;
		this.parameters = new ArrayList <String>();
		this.isNegative = false;
		this.executed = false;

	}
	



	public Literal parseStringToLiteral(String string)
	{

		literal = new Literal(null, null);
		int counter =0;
		String[] delim;
		delim = string.split("\\s+");

		//for 'not' literals
		if(delim[0].equals("not"))
		{
			for(int i=1; i<delim.length;i++)
			{
				counter+=1;
			}

			if(counter == 2)
			{	
				literal.addLiteralName(delim[1]);
				literal.addLiteralParameters(delim[2]);
				literal.hasNegativeSign(true);
				return literal;
			}

			if (counter == 3)
			{
				delim = string.split("\\s+");
				literal.addLiteralName(delim[1]);
				literal.addLiteralParameters(delim[2]);
				literal.addLiteralParameters(delim[3]);
//				System.out.print(literal.getLiteralName());
//				System.out.print(literal.getLiteralParameters(0));
//				System.out.println(literal.getLiteralParameters(1));



				literal.hasNegativeSign(true);

				return literal;
			}
		}

		else
		{
			//for every other literals
			for(int i=1; i<delim.length;i++)
			{
				counter+=1;
			}



			if(counter == 1)
			{	
				literal.addLiteralName(delim[0]);
				literal.addLiteralParameters(delim[1]);


				return literal;
			}

			if (counter == 2)
			{
				delim = string.split("\\s+");
				literal.addLiteralName(delim[0]);
				literal.addLiteralParameters(delim[1]);
				literal.addLiteralParameters(delim[2]);

				return literal;
			}
		}
		//System.out.println(literal.getLiteralName());
		return null;


	}




	//Literal Parameters
	public void addLiteralParameters( String name)
	{
		this.parameters.add(name);
	}

	public String getLiteralParameters(int index)
	{
		return this.parameters.get(index);
	}

	public void setLiteralParameters(int index, String name)
	{
		this.parameters.set(index, name);
	}

	public int sizeLiteralParameters()
	{
		return this.parameters.size();
	}



	//Literal Name
	public void addLiteralName(String name)
	{
		this.name = name;
	}

	public String getLiteralName()
	{
		return this.name;
	}

	public void setLiteralName(String name)
	{
		this.name = name;
	}


	public void hasNegativeSign(boolean isNegative)
	{
		 this.isNegative = isNegative;
	}
	

	public boolean isNegative()
	{
			if(this.isNegative == true)
			{
				return true;
			}
			else
			{
				return false;

			}

	}
	
	 /**
	  * This function is to set the literal to executed when it gets connected by Causal Link
	  * @param excuted
	  */
	public void setExcuted(boolean executed)
	{
		this.executed = executed;
	}
	
	
	public boolean isExcuted()
	{
		if(this.executed == true)
			return true;
		else
			return false;
	}
	
	 public String toString() 
	 {
		 String lit = "";
		 int i = this.sizeLiteralParameters();
		 for(int x=0;x<i;x++)
		 {
			 lit = lit + " "+ this.getLiteralParameters(x);
		 }
	        return  this.getLiteralName() + lit;
	  }


}
