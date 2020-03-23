//Nishtha Ahuja 2017354
//Aarushi Agrawal 2017324
import java.io.*;

import java.util.*;
public class assembler 
{
	public static int n2;
	public static void main(String[] args) throws IOException, OpcodeNotFoundException, SymbolNotFoundException
	{
		// TODO Auto-generated method stub
	
		
		
//		File f2 = new File("sampleinput.txt");
//		FileOutputStream fos2 = new FileOutputStream(f2);
//		ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
		
		ArrayList<String[]> arr = new ArrayList<String[]>();
		FileReader f = new FileReader("/Users/aarushiiagrawal/Desktop/IIITD/sample.txt");
		BufferedReader br = new BufferedReader(f);
		 String line = null;
		 while ((line = br.readLine()) != null) {
			 String[] ss = line.split("	");
			 arr.add(ss);
		 }
		 
		 n2=-1;
		 
		  ArrayList<opcode> opCode = new ArrayList<opcode>(); 
		  ArrayList<literal> literals = new ArrayList<literal>(); 
		  ArrayList<symbol> symbols = new ArrayList<symbol>();

		  for(int k=arr.size()-1; k>=0;k--) 
		  {
			  if(arr.get(k)[1].equals("DS")) 
			  {
				  String s1=arr.get(k)[0];
				  String n1 = arr.get(k)[2];
				  symbol e = new symbol(s1,n1,"Variable");
				  symbols.add(e);
			  }
			  else if(!arr.get(k)[0].equals(""))
			  {
				  String s1=arr.get(k)[0];
				  symbol e = new symbol(s1,"None","Label");
				  symbols.add(e);
			  }
			  else if(arr.get(k)[1].equals("START"))
			  {
				  n2 = Integer.parseInt(arr.get(k)[2]);
				  //System.out.println("hello"+n2);
			  }
		  }
		  
		  
		  PrintStream out = new PrintStream("./output.txt");
		  System.setOut(out);
		  
		  
		  System.out.println("Symbol Table");
		  for(int i=symbols.size()-1; i>=0; i--)
		  {
			  System.out.println(symbols.get(i).s  + " " + symbols.get(i).n+ " "+symbols.get(i).add+ " "+ symbols.get(i).type);
		  }
		  
		  System.out.println();
		  System.out.println("Opcode Table");
		  for(int i=0; i<arr.size(); i++)
		  {
			  String s = arr.get(i)[1];
			  if(!arr.get(i)[1].equals("DS") && !check2(s,opCode))
			  {
				  try {
				  opCode.add(new opcode(arr.get(i)[1],op(s)));
				  }
				  catch(OpcodeNotFoundException e)
				  {
					 System.out.println(e.getMessage()); 
				  }
			  }
		  }
		  
		  for(int i=0; i<opCode.size(); i++)
		  {
			  System.out.println(opCode.get(i).name+" "+opCode.get(i).opcode);
		  }
		  
		  System.out.println();
		  System.out.println("Literal Table");
			for(int k=0;k<arr.size();k++) 
			{
				if(arr.get(k).length==3 && arr.get(k)[2].charAt(0)=='\"')
					{
						literal lit = new literal(arr.get(k)[2]);
						literals.add(lit);
					}
			}
			
			for(int k=0; k<literals.size(); k++)
			{
				String st = "";
				for(int i=0; i<literals.get(k).s.length(); i++)
				{
					if(literals.get(k).s.charAt(i)!='\"' && literals.get(k).s.charAt(i)!='=')
						st=st+literals.get(k).s.charAt(i);
				}
				System.out.println(st+" "+ literals.get(k).add);
			}
		  
		  System.out.println();
		  System.out.println("Machine Code");
		  int machineaddress;
		  if(n2>=100)
			  machineaddress=0;
		  else
			  machineaddress=150;
		  
		  for(int k=0; k<arr.size();k++)
		  {
			  if(!arr.get(k)[1].equals("DS") && !arr.get(k)[1].equals("STP") && !arr.get(k)[1].equals("CLA") && !arr.get(k)[1].equals("START"))
			  {
				  String lab = arr.get(k)[0];
				  int flag=0;
				  String labe="";
				  //System.out.println("hi"+lab);
				  if(!lab.equals(""))
				  {
					  //System.out.println("here");
					  labe = findsym(lab,symbols);
					  flag=1;
				  }
				  String name = arr.get(k)[2];
				  String add="";
				  try
				  {
					  add = findsym(name,symbols);
				  }
				  catch(SymbolNotFoundException e)
				  {
					  try
					  {
						  add=findlit(name,literals);
					  }
					  catch(LiteralNotFoundException ex)
					  {
						  System.out.println(e.getMessage());
						  System.out.println(ex.getMessage());
					  }
				  }
				  try {
				  String n = op(arr.get(k)[1]);
				  if(flag==1)
					  System.out.println(labe+" "+n+ " "+add);
				  else
					  System.out.println(bitter(Integer.toBinaryString(machineaddress))+" "+n+ " "+add);
				  }
				  catch(OpcodeNotFoundException e)
				  {
					  System.out.println(e.getMessage());
				  }
				  if(flag==0)
					  machineaddress++;
			  }
			  else if(arr.get(k)[1].equals("STP") || arr.get(k)[1].equals("CLA"))
			  {
				  String n = op(arr.get(k)[1]);
				  System.out.println(Integer.toBinaryString(machineaddress)+" "+n);
				  machineaddress++;
			  }
		  }
		  
	}
	
	public static String findsym(String s, ArrayList<symbol> symbols) throws SymbolNotFoundException
	{
		for(int i=0; i<symbols.size(); i++)
		{
			if(s.equals(symbols.get(i).s))
				return symbols.get(i).add;	
		}
		throw new SymbolNotFoundException("Symbol not found!");
	}
	
	public static String findlit(String s, ArrayList<literal> literals) throws LiteralNotFoundException
	{
		for(int i=0; i<literals.size(); i++)
		{
			if(s.equals(literals.get(i).s))
				return literals.get(i).add;	
		}
		throw new LiteralNotFoundException("Literal not found!");
	}
	
	public static String bitter(String s)
	{
		String ans=s;
		int t = s.length();
		int d = 8-t;
		for(int i=0; i<d; i++)
		{
			ans="0"+ans;
		}
		return ans;
	}
	
	public static String op(String s) throws OpcodeNotFoundException
	{
		String opcode="-1";
		
		if(s.equals("CLA")) 
		  {
			 opcode="0000" ;
		  }
		  
		else if(s.equals("LAC")) 
		  {
			 opcode="0001" ;
		  }
		  
		else if(s.equals("SAC")) 
		  {
			 opcode="0010" ;
		  }
		  
		else if(s.equals("ADD")) 
		  {
			 opcode="0011" ;
		  }
		  
		else if(s.equals("SUB")) 
		  {
			 opcode="0011" ;
		  }
		  
		else if(s.equals("BRZ")) 
		  {
			 opcode="0101" ;
		  }
		  
		else if(s.equals("BRN")) 
		  {
			 opcode="0110" ;
		  }
		  
		else if(s.equals("BRP")) 
		  {
			 opcode="0111" ;
		  }
		  
		else if(s.equals("INP")) 
		  {
			 opcode="1000" ;
		  }
		  
		else if(s.equals("DSP")) 
		  {
			 opcode="1001" ;
		  }
		  
		else if(s.equals("MUL")) 
		  {
			 opcode="1010" ;
		  }
		
		else if(s.equals("DIV")) 
		  {
			 opcode="1011" ;
		  }
		  
		else if(s.equals("STP")) 
		  {
			 opcode="1100" ;
		  }
		else if(s.equals("START"))
		{
			opcode="1111";
		}
		else
		{
			throw new OpcodeNotFoundException("Opcode Not Found!");
		}
		  return opcode;
	}
	
	public static boolean check1(String l, ArrayList<symbol> array)
	{
		
		for(int i=0; i<array.size(); i++)
		{
			if(array.get(i).s.equals(l))
				return true;
		}
		return false;
	}
	
	public static boolean check2(String s, ArrayList<opcode> array)
	{
		
		for(int i=0; i<array.size(); i++)
		{
			if(array.get(i).name.equals(s))
				return true;
		}
		return false;
	}
	
	
	
	
}

class symbol
{
	String s; String n; String add; String type; public static int counter=assembler.n2+1;
	symbol(String str, String in, String typ)
	{
		this.s=str;
		this.n=in;
		this.add=assembler.bitter(Integer.toBinaryString(counter));
		this.type=typ;
		counter++;
	}
}

class literal
{
	String s; String add;
	literal(String str)
	{
		this.s=str;
		this.add=assembler.bitter(Integer.toBinaryString(symbol.counter));
		symbol.counter++;
	}
}


class opcode
{
	String name; String opcode;
	opcode(String s, String n)
	{
		this.name=s;
		this.opcode=n;
	}		
}


class OpcodeNotFoundException extends Exception
{
	public OpcodeNotFoundException(String s)
	{
		super(s);
	}
}

class SymbolNotFoundException extends Exception
{
	public SymbolNotFoundException(String s)
	{
		super(s);
	}
}

class LiteralNotFoundException extends Exception
{
	public LiteralNotFoundException(String s)
	{
		super(s);
	}
}