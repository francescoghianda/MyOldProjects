package SASS;

import java.util.ArrayList;

/**
 * Questa calsse serve per cryptare e decriptare delle stringhe.
 * @author SkyArts
 * @version 1.0, 12/23/13
 *
 */


public class SASS // SkyArts Sistem Security
{
	
	private String Decrypted;
	private String Crypted;
	private int key;
	private char[] scomposta;
	private ArrayList<CHAR> caratteri;

	/**
	 * Inizializza le variabili.
	 */
	public SASS()
	{
		caratteri = new ArrayList<CHAR>();
		
		caratteri.add(new CHAR('a' , 1));
		caratteri.add(new CHAR('b' , 2));
		caratteri.add(new CHAR('c' , 3));
		caratteri.add(new CHAR('d' , 4));
		caratteri.add(new CHAR('e' , 5));
		caratteri.add(new CHAR('f' , 6));
		caratteri.add(new CHAR('g' , 7));
		caratteri.add(new CHAR('h' , 8));
		caratteri.add(new CHAR('i' , 9));
		caratteri.add(new CHAR('j' , 10));
		caratteri.add(new CHAR('k' , 11));
		caratteri.add(new CHAR('l' , 12));
		caratteri.add(new CHAR('m' , 13));
		caratteri.add(new CHAR('n' , 14));
		caratteri.add(new CHAR('o' , 15));
		caratteri.add(new CHAR('p' , 16));
		caratteri.add(new CHAR('q' , 17));
		caratteri.add(new CHAR('r' , 18));
		caratteri.add(new CHAR('s' , 19));
		caratteri.add(new CHAR('t' , 20));
		caratteri.add(new CHAR('u' , 21));
		caratteri.add(new CHAR('v' , 22));
		caratteri.add(new CHAR('w' , 23));
		caratteri.add(new CHAR('x' , 24));
		caratteri.add(new CHAR('y' , 25));
		caratteri.add(new CHAR('z' , 26));
		
		caratteri.add(new CHAR('A' , 27));
		caratteri.add(new CHAR('B' , 28));
		caratteri.add(new CHAR('C' , 29));
		caratteri.add(new CHAR('D' , 30));
		caratteri.add(new CHAR('E' , 31));
		caratteri.add(new CHAR('F' , 32));
		caratteri.add(new CHAR('G' , 33));
		caratteri.add(new CHAR('H' , 34));
		caratteri.add(new CHAR('I' , 35));
		caratteri.add(new CHAR('J' , 36));
		caratteri.add(new CHAR('K' , 37));
		caratteri.add(new CHAR('L' , 38));
		caratteri.add(new CHAR('M' , 39));
		caratteri.add(new CHAR('N' , 40));
		caratteri.add(new CHAR('O' , 41));
		caratteri.add(new CHAR('P' , 42));
		caratteri.add(new CHAR('Q' , 43));
		caratteri.add(new CHAR('R' , 44));
		caratteri.add(new CHAR('S' , 45));
		caratteri.add(new CHAR('T' , 46));
		caratteri.add(new CHAR('U' , 47));
		caratteri.add(new CHAR('V' , 48));
		caratteri.add(new CHAR('W' , 49));
		caratteri.add(new CHAR('X' , 50));
		caratteri.add(new CHAR('Y' , 51));
		caratteri.add(new CHAR('Z' , 52));
		
		caratteri.add(new CHAR('0' , 53));
		caratteri.add(new CHAR('1' , 54));
		caratteri.add(new CHAR('2' , 55));
		caratteri.add(new CHAR('3' , 56));
		caratteri.add(new CHAR('4' , 57));
		caratteri.add(new CHAR('5' , 58));
		caratteri.add(new CHAR('6' , 59));
		caratteri.add(new CHAR('7' , 60));
		caratteri.add(new CHAR('8' , 61));
		caratteri.add(new CHAR('9' , 62));
		
		caratteri.add(new CHAR('=' , 63));
		caratteri.add(new CHAR('#' , 64));
		caratteri.add(new CHAR('%' , 65));
		caratteri.add(new CHAR(',' , 66));
		caratteri.add(new CHAR(':' , 67));
		
	}
	
	/**
	 * Restituisce una stringa cryptata generando automaticamente una chiave di cryptatura.
	 * @param Decrypted una String che rappresenta la stringa da cryptare.
	 * @return Crypted una String che rappresenta la stringa cryptata.
	 */
	public String Crypt(String Decrypted)
	{
		this.Decrypted = Decrypted;
		this.scomposta = this.Decrypted.toCharArray();
		this.Crypted = "";
		
		key = this.Decrypted.length();
		
		for(int i = 0; i < scomposta.length; i++)
		{
			if(getValue(scomposta[i]) != 0)
			{
				this.Crypted += jmpUP(scomposta[i], key);
			}
			else
			{
				this.Crypted += scomposta[i];
			}
			
		}
		
		return this.Crypted;
	}
	
	/**
	 * Restituisce una stringa decryptata generando automaticamente una chiave di decryptatura.
	 * @param Crypted una String che rappresenta la stringa da decryptare.
	 * @return Decrypted una String che rappresenta la stringa decryptata.
	 */
	public String Decrypt(String Crypted)
	{
		this.Crypted = Crypted;
		this.scomposta = this.Crypted.toCharArray();
		this.Decrypted = "";
		
		key = this.Crypted.length();
		
		for(int i = 0; i < scomposta.length; i++)
		{
			if(getValue(scomposta[i]) != 0)
			{
				this.Decrypted += jmpDOWN(scomposta[i], key);
			}
			else
			{
				this.Decrypted += scomposta[i];
			}
		}
		
		return this.Decrypted;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Restituisce una stringa cryptata.</br>
	 * La chiave di cryptatura viene generata partendo dalla stinga worm la quale dovrà essere reinserita per decryptare la stringa
	 * @param Decrypted una String che rappresenta la stringa da cryptare.
	 * @param worm con questa stringa viene generata la chiave di cryptatura.
	 * @return Crypted una String che rappresenta la stringa cryptata.
	 */
	public String CryptWorm(String Decrypted, String worm)
	{
		this.Decrypted = Decrypted;
		this.scomposta = this.Decrypted.toCharArray();
		this.Crypted = "";
		
		key = this.Decrypted.length() / worm.length();
		
		for(int i = 0; i < scomposta.length; i++)
		{
			if(getValue(scomposta[i]) != 0)
			{
				this.Crypted += jmpUP(scomposta[i], key);
			}
			else
			{
				this.Crypted += scomposta[i];
			}
			
		}
		
		return this.Crypted;
	}
	
	/**
	 * Restituisce una stringa decryptata.</br>
	 * La chiave di decryptatura viene generata partendo dalla stinga worm, la stessa usata per la cryptatura.
	 * @param Crypted una String che rappresenta la stringa da decryptare.
	 * @param worm con questa stringa viene generata la chiave di decryptatura.
	 * @return Decrypted una String che rappresenta la stringa decryptata.
	 */
	public String DecryptWorm(String Crypted, String worm)
	{
		this.Crypted = Crypted;
		this.scomposta = this.Crypted.toCharArray();
		this.Decrypted = "";
		
		key = this.Crypted.length() / worm.length();
		
		for(int i = 0; i < scomposta.length; i++)
		{
			if(getValue(scomposta[i]) != 0)
			{
				this.Decrypted += jmpDOWN(scomposta[i], key);
			}
			else
			{
				this.Decrypted += scomposta[i];
			}
		}
		
		return this.Decrypted;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Restituisce una stringa cryptata.</br>
	 * Il metodo è simile al {@link #CryptWorm(String, String)} ma genera una cryptatura piu sicura.</br>
	 * La chiave di cryptatura viene generata partendo dalle stinge worm e worm2, le quali dovranno essere usate per la decryptatura
	 * @param Decrypted una String che rappresenta la stringa da cryptare.
	 * @param worm con questa stringa viene generata la chiave di cryptatura.
	 * @param worm2 con questa stringa viene generata la seconda chiave di cryptatura.
	 * @return Crypted una String che rappresenta la stringa cryptata.
	 */
	public String CryptWorm(String Decrypted, String worm, String worm2)
	{
		this.Decrypted = Decrypted;
		this.scomposta = this.Decrypted.toCharArray();
		this.Crypted = "";
		//ArrayList<Character> intermedia = new ArrayList<Character>();
		char[] intermedia = new char[scomposta.length];
		char[] verme = worm2.toCharArray();
		
		key = this.Decrypted.length() / worm.length();
		
		for(int i = 0; i < scomposta.length; i++)
		{
			if(getValue(scomposta[i]) != 0)
			{
				intermedia[i] += jmpUP(scomposta[i], key);
			}
			else
			{
				intermedia[i] += scomposta[i];
			}
			
		}
		
		key = 0;
		for(int i = 0; i < worm2.length(); i++)
		{
			key += getValue(verme[i]);
		}
		
		for(int i = 0; i < intermedia.length; i++)
		{
			if(getValue(intermedia[i]) != 0)
			{
				Crypted += jmpUP(intermedia[i], key);
			}
			else
			{
				Crypted += intermedia[i];
			}
			
		}
		
		return this.Crypted;
	}
	
	/**
	 * Restituisce una stringa decryptata.</br>
	 * Il metodo è simile al {@link #DecryptWorm(String, String)} ed è l'unico in grado di decryptare le stringhe cryptate con {@link #CryptWorm(String, String, String)}.</br>
	 * La chiave di decryptatura viene generata partendo dalle stinge worm e worm2, le stesse usate per la cryptatura.
	 * @param Crypted una String che rappresenta la stringa da decryptare.
	 * @param worm con questa stringa viene generata la chiave di decryptatura.
	 * @param worm2 con questa stringa viene generata la seconda chiave di decryptatura.
	 * @return Decrypted una String che rappresenta la stringa decryptata.
	 */
	public String DecryptWorm(String Crypted, String worm, String worm2)
	{
		this.Crypted = Crypted;
		this.scomposta = this.Crypted.toCharArray();
		this.Decrypted = "";
		char[] intermedia = new char[scomposta.length];
		char[] verme = worm2.toCharArray();
		
		key = this.Crypted.length() / worm.length();
		
		for(int i = 0; i < scomposta.length; i++)
		{
			if(getValue(scomposta[i]) != 0)
			{
				intermedia[i] += jmpDOWN(scomposta[i], key);
			}
			else
			{
				intermedia[i] += scomposta[i];
			}
		}
		
		key = 0;
		for(int i = 0; i < worm2.length(); i++)
		{
			key += getValue(verme[i]);
		}
		
		for(int i = 0; i < intermedia.length; i++)
		{
			if(getValue(intermedia[i]) != 0)
			{
				Decrypted += jmpDOWN(intermedia[i], key);
			}
			else
			{
				Decrypted += intermedia[i];
			}
		}
		
		return this.Decrypted;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private char jmpUP(char a, int key)
	{
		char b = 0;
		int valoreIniziale = getValue(a);
		int valoreIntermedio = valoreIniziale + key;
		int valoreFinale = 0;
		for(int i = 0; i < valoreIntermedio; i++)
		{
			valoreFinale++;
			if(valoreFinale > caratteri.size())
			{
				valoreFinale = 1;
			}

		}
		b = getChar(valoreFinale);
		return b;
	}
	
	
	private char jmpDOWN(char a, int key)
	{
		char b = 0;
		int valoreIniziale = getValue(a);
		int valoreIntermedio = valoreIniziale - key;
		int valoreFinale = valoreIniziale;
		for(int i = valoreIniziale; i > valoreIntermedio; i--)
		{
			valoreFinale--;
			if(valoreFinale < 1)
			{
				valoreFinale = caratteri.size();
			}
			
		}
		b = getChar(valoreFinale);
		return b;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private int getValue(char l)
	{
		int value = 0;
		
		for(int x = 0; x < caratteri.size(); x++)
		{
			CHAR temp = caratteri.get(x);
			
			if(temp.getChar() == l)
			{
				value = temp.getValue();
				break;
			}
			
		}
		
		return value;
	}
	
	private char getChar(int value)
	{
		char l = 0;
		
		for(CHAR temp : caratteri)
		{
			
			if(temp.getValue() == value)
			{
				l = temp.getChar();
				break;
			}
			
		}
		
		return l;
	}
}
