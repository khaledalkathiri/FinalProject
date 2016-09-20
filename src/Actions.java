import java.util.ArrayList;
import java.util.Hashtable;

public class Actions 
{
	private String action;
	private String para1;
	private String para2;
	private String para3;
	
	

	//NOT used properly yet
	ArrayList<Actions>  obj = new ArrayList<Actions>();
	
	
	
	/*
	 * this constructor is when we have just one parameter
	 */
	public Actions(String action, String para1)
	{
		this.setAction(action);
		this.setPara1(para1);
	}
	
	/*
	 * This constructor is when we have two parameters
	 */
	public Actions(String action, String para1, String para2)
	{
		this.setAction(action);
		this.setPara1(para1);
		this.setPara2(para2);
	}

	/*
	 * This function is to add actions to the ArrayList
	 */
	public void addActions(Actions action)
	{
		obj.add(action);
	}
	
	
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPara1() {
		return para1;
	}

	public void setPara1(String para1) {
		this.para1 = para1;
	}

	public String getPara2() {
		return para2;
	}

	public void setPara2(String para2) {
		this.para2 = para2;
	}

	public String getPara3() {
		return para3;
	}

	public void setPara3(String para3) {
		this.para3 = para3;
	}

}
