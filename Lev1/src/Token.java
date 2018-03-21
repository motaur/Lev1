
public class Token 
{
	public enum Type {num, var, end, add, sub, mul, dev, equ, rp, lp};
	private String value;
	private Type type;
		
	/*Constructor*/
	public Token(String value)
	{
		this.value = value;
	}
	
	/*getters and setters*/
	public String getValue() 
	{
		return value;
	}

	public Type getType() 
	{
		return type;
	}

	public void setType(Type type) 
	{
		this.type = type;
	}
	
	public void setValue(String value) 
	{
		this.value = value;
	}
	
}
