import java.util.ArrayList;

public class Effects 
{
	//private String effect;
	
	ArrayList <String>  effectArray = new ArrayList <String>();
	
//	
//	public Effects()
//	{
//		this.effect = effect;
//	}
	
	
	public void addEffect(String effect)
	{
		effectArray.add(effect);
	}
	
	
	public String getEffect(int index)
	{
		return effectArray.get(index);
		
	}

}
