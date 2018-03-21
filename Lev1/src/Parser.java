import java.util.ArrayList;
import java.util.Hashtable;

public class Parser 
{
	private Hashtable <String, Integer> memory = new Hashtable <String, Integer>();	//memory for variables
	private ArrayList<Token> tokens = new ArrayList<Token>();						//tokens list
	private int i = 0;																//iterator for tokens

	/*cases*/	
	private int factor()
	{
		int result = 0;
			
		if(tokens.get(i).getType() == Token.Type.var && tokens.get(i+1).getType() != Token.Type.equ)		//variable
		{
			try
			{
				result = memory.get(tokens.get(i).getValue());
			}
			catch(NullPointerException e)
			{
				System.out.println("variable does not exist.");
				i--;
			}
						
		}
		else if(tokens.get(i).getType() == Token.Type.lp)			//expression in closes
		{
			i++;
			result = expression();
			i++;
		}
		else if(tokens.get(i).getType() == Token.Type.num) 			//number
		{
			result = Integer.parseInt(tokens.get(i).getValue());
		}
		else if(tokens.get(i).getType() == Token.Type.sub)			//unary minus
		{
			i++;
			result = 0 - factor();
		}
			
		return result;
	}
		
	private int term()
	{		
		int result = factor();
		
		while(tokens.get(i+1).getType() == Token.Type.mul || tokens.get(i+1).getType() == Token.Type.dev)
		{
			Token t = tokens.get(++i);
			
			if(t.getType() == Token.Type.mul)
			{
				i++;
				result *= factor();
			}
			else
			{
				i++;

				/*divivding by zero checking*/
				try 
				{
					result /= factor();
				} 
				catch (ArithmeticException e) 
				{
					System.out.println("division by zero exception");
				}
				
			}
				
		}
		
		return result;
	}
	
	private int expression()
	{					
		int result = term();
				
		while(tokens.get(i+1).getType() == Token.Type.add || tokens.get(i+1).getType() == Token.Type.sub)
		{
			Token t = tokens.get(++i);	//next token
			
			if(t.getType() == Token.Type.add)
			{
				i++;
				result += term();
			}
			else
			{
				i++;
				result -= term();
			}
				
		}
					
		return result;
	}
	
	public int line(ArrayList<Token> tokens)
	{		
		this.tokens = tokens;
		int result = 0;
		i = 0;
		
		/*first token is variable and next is 'equals' --> a = "......" */
		if(tokens.get(i).getType() == Token.Type.var && tokens.get(i + 1).getType() == Token.Type.equ )
		{
			String var = tokens.get(i).getValue();		//save value of variable
			i = i + 2;									//go to next token after 'equals' token
			result = expression();						//put expression after 'equals' to result
			memory.put(var, result);  					//put variable and his value to memory	
			System.out.println("[Saved data: " + var + " = " + result + "]");
		}
		/*first token is expression*/
		else
			result = expression();
				
		return result;	
	}
			

}
