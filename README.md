# ASSEMBLER DOCUMENTATION


## Code Structure: 
- The sample input given to us is stored in the file name “sample.txt”. We have used FileReader and BufferedReader classes to obtain it. 
ArrayList of String arrays called “arr” stores the file line by line in order to access it easily later.
- Symbol class is a class each object of which has two String attributes and two integer attributes - for the name, the type(Variable or Label), the value and the address respectively.
- Literal class is a class each object of which has a String attribute and an integer attribute - for the value(in string format) and the address respectively.
- Opcode class is a class each object of which has two String attributes - name of the opcode(as in Assembly language) and it’s corresponding binary opcode(as in Machine language).
- ArrayList of symbol class objects is used to represent symbol table.
- ArrayList of literal class objects is used to represent literal table. 
- ArrayList of opcode class objects is used to represent opcode table.
- We are scanning the input file from bottom to top, so as to deal with the declared variables first.
- When a variable is declared, it is added to the symbol table under “Variable” with the given value.
- If a label is found, it is added to the symbol table under “Label” with “none” value.
- When a symbol or literal class object is initialised, it is given a binary address at that time itself.
- “findsym” method takes a symbol as a parameter and returns its address if symbol is found. If not, it throws SymbolNotFoundException.
- “findlit” method takes a literal as a parameter and returns its address if literal is found. If not,  it throws LiteralNotFoundException.
- “op()” method takes an opcode(string input) as a parameter and assigns to it the appropriate binary opcode. If an opcode is not recognised by the system, an OpcodeNotFoundException is thrown.
- “check1()” method takes a string as an input and checks the arraylist, which, it also takes as an input, for it. It is specific to an arraylist of type symbol.
- “check2()” method takes a string as an input and checks the arraylist, which, it also takes as an input, for it. It is specific to an arraylist of type opcode.
- “bitter()” is a method which returns an 8 bit address given a Binary string input.
- The final output, i.e. the symbol table, the literal table, the opcode table and the translated machine code are saved in a file called “output.txt”.
- [“()” at the end of a method name does not imply that that method takes no parameter. It is only used to indicate a method name.]