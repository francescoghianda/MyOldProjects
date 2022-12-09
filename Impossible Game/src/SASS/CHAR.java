package SASS;

public class CHAR 
{
	private int value;
	private char l;
	
	protected CHAR(char l, int value)
	{
		this.value = value;
		this.l = l;
	}
	
	protected char getChar()
	{
		return this.l;
	}
	protected int getValue()
	{
		return this.value;
	}
}
