import java.util.ArrayList;

public class Literal
{

	
	private String name;
	private ArrayList <String> parameters = new ArrayList <String>();
	
	private Literal literal;
	

	
	
	public Literal(String name, ArrayList <String> parameters)
	{
		this.name= name;
		this.parameters = new ArrayList <String>();

	}
	
	
	
	public Literal parseStringToLiteral(String string)
	{

		literal = new Literal(null, null);

		int counter =0;
		String[] delim;
		delim = string.split("\\s+");

		
		//counting the number of parameters
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
		this.parameters.add(index, name);
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
	

	
	
	
	public boolean isExcuted(Literal literal )
	{
		return false;
	}


}
