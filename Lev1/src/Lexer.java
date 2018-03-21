import java.util.ArrayList;

public class Lexer 
{	
	private final String language = "0123456789 +-*/=();abcdefghijklmnoprstuqvwxyz";
		
	/*left breaker and right breaker amount*/
	private int lp = 0;
	private int rp = 0;
		
	public ArrayList<Token> tokenize(String line)
	{
		ArrayList<Token> tokens = new ArrayList<Token>();					//list of tokens
		StringBuffer currentToken = new StringBuffer();						//token collect
		
		if(line == "" || line == null || line.length() < 2)					//checking that line is not empty
		{
			System.out.println("Illegal expression");
			return null;
		}
		
		/*tokenize*/								
		for(int i = 0; i < line.length()-1; i++)
		{			
			char ch = line.charAt(i);  										//get char from line
			char next = line.charAt(i+1);									//get next char LOOK AHEAD
						
			if (Character.isDigit(ch) && Character.isDigit(next))			//is digit and next is digit --> is integer --> put in one token
			{
				currentToken.append(ch);
			}
			else if( Character.isDigit(ch) && (language.indexOf(next) > 9) ) // char is digit, but next is not
			{
				currentToken.append(ch);
				Token t = new Token(currentToken.toString());
				tokens.add(t);
				currentToken.setLength(0);
			}
			else if (language.indexOf(ch) > 9) 									//is symbol
			{
				currentToken.append(ch);
				Token t = new Token(currentToken.toString());
				tokens.add(t);
				currentToken.setLength(0);
			}
			else
			{
				System.out.println("Unknown token");
				return null;
			}
				
		}
		
		if(language.indexOf(line.charAt(line.length()-1)) >= 0)					//last symbol
		{
			currentToken.append(line.charAt(line.length()-1));
			Token t = new Token(currentToken.toString());
			tokens.add(t);
		}
						
		/*detecting tokens type*/
		for(int j = 0; j < tokens.size(); j++)
		{
			int i = language.indexOf(tokens.get(j).getValue().charAt(0));				//indicator what type of token index in language
			
			if(i >= 0 && i <= 9)
			{				
				tokens.get(j).setType(Token.Type.num);		//number
			}
			else if(i > 18 )
			{
				tokens.get(j).setType(Token.Type.var);		//variable
			}
			else if(i == 10)
			{				
				tokens.remove(j--);							//ignore and remove spaces
			}
			else if(i == 11)
			{
				tokens.get(j).setType(Token.Type.add);		//symbols
			}
			else if(i == 12)
			{
				tokens.get(j).setType(Token.Type.sub);
			}
			else if(i == 13) 
			{
				tokens.get(j).setType(Token.Type.mul);
			}
			else if(i == 14) 
			{
				tokens.get(j).setType(Token.Type.dev);
			}
			else if(i == 15) 
			{
				tokens.get(j).setType(Token.Type.equ);
			}
			else if(i == 16) 
			{
				tokens.get(j).setType(Token.Type.lp);
			}
			else if(i == 17) 
			{
				tokens.get(j).setType(Token.Type.rp);
			}
			else if(i == 18) 
			{
				tokens.get(j).setType(Token.Type.end);
			}
			else
			{
				System.out.println("wrong symbol");
				return null;
			}
		}
				
		if(check(tokens))
			return tokens;
		else
			return null;
				
	}
	
	/*errors checking*/
	private boolean check(ArrayList<Token> tokens)
	{			
		/* ';' last symbol checking */
		if (tokens.get(tokens.size() - 1 ).getType() != Token.Type.end)
		{
			System.out.println("';' - must be the last character");
			return false;
		}
		
		/*count breakers*/
		for(int i = 0; i < tokens.size()-1; i++)
		{
			if(tokens.get(i).getType() == Token.Type.rp && lp == 0)
			{
				System.out.println("breakets error");
				return false;
			}
				
			if(tokens.get(i).getType() == Token.Type.lp)
				lp++;
			
			if(tokens.get(i).getType() == Token.Type.rp)
				rp++;
			
			if(tokens.get(i).getType() == Token.Type.lp && tokens.get(i + 1).getType() == Token.Type.rp)
			{
				System.out.println("expression in breakets can't be empty");
				return false;
			}
		}
		
		/*breakers amount checking*/
		if(lp != rp)
		{
			System.out.println("breakets error");
			return false;
		}
		
		/*operators errors*/
		for(int i= 0; i < tokens.size() - 1; i++)
		{
			int index = language.indexOf(tokens.get(i).getValue().charAt(0));
			int nextIndex = language.indexOf(tokens.get(i + 1).getValue().charAt(0));
			
			if( (index > 10 && index < 15)/*Operator*/ && (nextIndex > 10 && nextIndex < 15)/*operator*/)
			{
				System.out.println("opertaor can't be after another operator");
				return false;
			}
			 
			if(tokens.get(i).getType() == Token.Type.num && tokens.get(i + 1).getType() == Token.Type.equ)
			{
				System.out.println("Illegal expression");
				return false;
			}
			
		}
		
		if(tokens.get(0).getType() == Token.Type.add || tokens.get(0).getType() == Token.Type.equ || tokens.get(0).getType() == Token.Type.mul || tokens.get(0).getType() == Token.Type.dev)
		{
			System.out.println("incorrect use of binary operator");
			return false;
		}
		
		//checking amount of tokens
		if (tokens.size() < 2) 
		{
			System.out.println("Empty expression");
			return false;
		}
				
		return true;
	}
	
}
