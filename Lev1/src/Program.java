import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * @autor Lev Berlinkov, ID 336137351
 * Calculator, Parser and Lexer
 * 
 */

public class Program 
{
	private static ArrayList<Token> tokens = new ArrayList<Token>();
	private static Lexer lexer = new Lexer();
	private static Parser parser = new Parser();
	private static String input = "";
	private static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));     
	
	public static void main(String[] args) throws IOException
	{	
		/*main loop until END command*/
		while(!input.equals("END"))
		{	
			System.out.println("Input expression, or input END for exit:");
						
			input = reader.readLine();
			
			switch(input)
			{				
				case "END":
					break;
				
				default:
					tokens = lexer.tokenize(input);
					
					if(tokens != null)
						 System.out.println(parser.line(tokens));
					break;
			}	
						
		}			
	}

}
