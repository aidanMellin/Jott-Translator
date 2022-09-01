# plc-jot

# PLC CSCI-

# Jott Translator Group Project

```
07/28/
```
## 1 Introduction

In this project you will create a translator for the programming language Jott. You must use
Java to code this.

Jott is a very basic programming language. It will not do everything a normal programming
language can do.

## 2 Detail of the Language

Jott is going to be very simple. This section will discuss each of the items you must implement.
In a later section the grammar of the language will be laid out.

Each phase of the project will be laid out later in this document.

2.1 Mathematics

Jott will do basic integer/double mathematics; addition (+), subtraction (-) division (/),
multiplication (*), and power (∧).

Integer mathematical operations will only work with two integers as operands and return an
integer answer. Integer mathematics with truncate and not round.

Examples:

- 3 + 2becomes 5
- 3 - 2becomes 1
- 3 *-2becomes -
- 5/2becomes 2

Note: the spacing between operands and the operator is not required.

Double mathematical operations will only work with two doubles as operands and return a
double answer.

Examples:

- 3.0 + 2.0becomes 5.
- 3.1 - 2.0becomes 1.
- 3.1 *-2.2becomes -6.
- 5.0/2.0becomes 2.

Note: the spacing between operands and the operator is not required.

Different typed operand expressions, such as3 + 3.0, are not allowed in this language;
integer operation with a double operand. If this occurs it should be reported as an error.

Division by zero is not allowed and should be reported as an error.

See the grammar below for integer/double usage and operations.


2.2 Conditional Operations

Jott will do basic True/False conditional operations; greater than (>), greater than or equal
(>=), less than (<), less than or equal (<=), equal to (==), and not equals (! =).

They follow the standard rules of other programming languages. The return type in Jott is
Boolean.

Examples:

```
Boolean b = 5 > 2;
print[b];
Boolean b2 = 5<= 2;
print[b2];
```
Running of this code will output:

```
True
False
```
Note: the spacing between operands and the operator is not required.

2.3 Strings

Strings must be contained in double quotes and cannot wrap lines. They must also contain
only upper/lower case alphabetical characters or numbers. No punctuation in strings.

Examples:

```
String s = "foo";
String y1 = "bar";
String y2 = "123 rf 17";
```
2.4 Variable Assignment and Usage

Jott allows for variable assignment and usage. Variables in Jott are just names for a provided
value. Variables are types in Jott; Integer, Double, Boolean, or String; they are case-sensitive.

All variables must start with a lowercase letter.

Using a variable in an expression where its type does not match the expression is an error.

Example:

```
Integer x = 5;
print[ x + 3.2 ]
```
This is an error becausexis an integer and3.2is a double.

See the grammar below for the structure of variable assignment and usage.


2.5 While Loops

Jott will have a basic while loop. The format is:

```
while[cond]{
... body ...
}
```
Details:

- cond is a conditional operation.
- the body will be any valid Jott code other than defining another function.
- nested loops are allowed.
- works like while loops in other languages.

Example:

```
Integer x = 5;
while[ x > 0]{
print[x];
x = x - 1;
}
```
Running of this code will output:

```
5
4
3
2
1
```
Note: exact spacing as shown in the format above in not required. The following for example
is allowed:

```
while [ cond]{
... body ...
}
```
```
while[cond ] {
... body ...
}
```
```
while[cond]
{
... body ...}
```
2.6 If Statements

Jott will have a basic if statement. The format is:

```
if[cond]{
... body ...
}
```

```
elseif[cond]{
... body ...
}
else{
... body ...
}
```
Note: The same rules apply here with spacing and newlines as they do in while loops.

Details:

- The if is required.
- There can be zero or more elseif that must come after the if but before the else
- There can me only at most one else; there can be none.
- cond is any statement that evaluates to a True or False result.
- nested ifs are allowed.

Examples:

```
Integer x = 5;
if[x == 5]{
print["Yes"];
}
else{
print["No"];
}
```
```
Integer y = 7;
```
```
if[y < 6]{
print[4];
}
elif[ y >= 7]{
print[3];
}
else{
print[11];
}
```
```
if[x + y > 8]{
print[x + y];
}
```
Running of this code would output:

```
Yes
3
12
```

2.7 Functions

Jott will allow for function definitions and calls.

Format of function definitions in Jott:

```
name[ varName:varType, ... ]:returnType{
... body ...
return ...;
}
```
Details:

- name: will stand for the name of the function. It will follow the same rules as variable
    name.
- parameters are enclosed in square brackets, [ and ]. They will be a comma separated
    list.
- parameters will be defined in the formname:type. Name will follow the same rules as
    a variable. Type will be any type that a variable can be.
- the body will be any valid Jott code other than defining another function.
- return will return a value from the function. The type of the return must match the
    return type of the function.
- function with no return will have a return type ofVoid.Voidis not a valid variable
    type.
- similar to ifs and whiles, the exact spacing above is not require. For example the
    following is valid:
       name[varName:varType, ... ] :returnType {
       line1;line2;return ...;}

Functions must be defined before they can be used in the code. For example if functionfoo
calls functionbarthenbarmust be defined beforefoo.

Format of a function call:

```
name[param1, param2, ...]
```
Details:

- name: is the name of the function being called.
- param1, param2, ... are the arguments to the function. The number and type of the
    arguments must match the function definition.

Examples of using functions:

```
foo[ x:Integer,y:Double]:String
{
if[x>5]{
return "foo";
}
elif[y<3.2]{
return "bar";
}
```

```
return "foobar";
}
```
```
bar[s:String]:Void{ print[s];}
```
```
print[foo[5]];
print[foo[3.2]];
String x = foo[11];
print[x];
bar[foo[5]];
```
2.8 Builtin Functions

Jott will have a few builtin functions. These functions will not be defined in the source file.
They are built into the language; an example of this would beprintin Python.

No other function can be named the same as a builtin function; this will be an error.

2.8.1 printStatement

Jott will have the ability to print data to the screen and go to the next line. It will return
nothing. The parameter to ”print” can be any data type in Jott.It will:

- take in an expression that will be evaluated and printed to the screen.
- it adds a newline after it prints.
- it will return nothing

Examples:

```
print[3];
print[3.2];
print["foobar"];
print[ 3 + 4 ];
```
Running of this code will output:

```
3
3.
foobar
7
```
2.8.2 inputStatement

Jott will have the ability to get input from the user. This will work like theinputin Python.
It will:

- take in a string that will be a prompt to the user.


- take in a integer that is the number of characters to read from the user.
- it will return a String.

Examples:

```
String s = input["Enter a number: ", 256];
print[s];
String s2 = "Enter a letter";
Integer i = 50;
String s3 = input[s,i];
print[s3];
```
Running of this code will output:

```
Enter a number: 50
50
Enter a letter: A
A
```
Note: the 50 and A after the prompt is the user input.

2.8.3 concatStatement

Jott will have the ability to concatenate two string together; only strings. It will:

- take in two Strings
- will return a new String that is the second param concatenated to the end of the first
    param.

Examples:

```
String s = concat["foo", "bar"];
print[s];
String s1 = "foo";
String s2 = concat["bar", s1];
print[s2];
print[concat["foo", concat["bar", "baz"]];
```
Running of this code will output:

```
foobar
barfoo
foobarbaz
```
NOTE: notice the functions can be used as a parameter of another function. This is allowed
as long as the return type of the function matches the type of the function parameter.

2.8.4 lengthStatement

Jott will have the ability to determine the length of a string. It will:

- take in a String.
- return the length of the string; as an Integer.

Examples:


```
Integer i = length["foo"];
print[i];
```
Running of this code will output:

```
3
```
2.9 Comments

Jott will allow for line comments. Comments will start with#. Anything after the#and
until the newline is to be considered part of the comment.

Examples:

```
#this is a comment
print[ "hello" ]; #after this is a comment
# no matter what is here this is a comment !123.;;;
```
2.10 mainFunction

All Jott programs will have a main function. This function is responsible for running the
program; think on themainfunction in C.

It will take in no command line arguments.

Format of themainfunction:

```
main[]:Void {
... body ...
}
```
Details:

- will take in no params.
- will return nothing.
- no other function can be named main.
- just like other functions exact spacing as shown above is not required.

Example:

```
foo[x:Integer]{
print[x];
}
```
```
main[]:Void{
foo[5];
foo[11];
}
```
Running of this code will output:

```
5
11
```
and will exit the program.


## 3 The Grammar of Jott

This section will outline the grammar of the Jott programming language. Anything that
violates this grammar should be reported as an error.

```
<program > -> <function_list > $$
<function_list > -> <function_def > <function_list >
<function_list > -> 
```
```
<function_def > -> <id >[ func_def_params ]:<function_return >{<body >}
<func_def_params > -> <id >:<type ><function_def_params_t > | 
<func_def_params_t > -> ,<id >:<type ><func_def_params_t > | 
```
```
<body_stmt > -> <if_stmt > | <while_loop > | <stmt >
<return_stmt > -> return <expr ><end_stmt >
<body > -> <body_stmt ><body > | <return_stmt > | 
<end_stmt > -> ;
```
```
<if_stmt > -> if[b_expr ]{ <body > }<elseif_lst ><else >
<else > -> else{<body} | 
<elseif_lst > -> elseif[b_expr ]{ body }<elseif_lst > | 
```
```
<while_loop > -> while[b_expr]{<body >}
```
```
<char > -> <l_char > | <u_char > | <digit >
<l_char > -> a | b | c | d | e | f | g | h | i | j | k | l | m |
n | o | p | q | r | s | t | u | v | w | x | y | z
```
```
<u_char > -> A | B | C | D | E | F | G | H | I | J | K | L | M |
N | O | P | Q | R | S | T | U | V | W | X | Y | Z
<digit > -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
<sign > -> - | + | 
<id> -> <l_char ><char >?
```
```
<stmt > -> <asmt > | <var_dec > | <func_call ><end_stmt >
```
```
<func_call > -> <id >[ params]
```
```
<params > -> <expr ><params_t > | 
<params_t > -> ,<expr ><params_t > | 
```
```
<expr > -> <i_expr > | <d_expr > | <s_expr > | <b_expr > |
<id> | <func_call >
```
```
<type > -> Double | Integer | String | Boolean
<function_return > -> <type > | Void
<var_dec > -> <type > <id ><end_statement >
<asmt > -> Double <id > = <d_expr ><end_statement > |
```

```
Integer <id > = <i_expr ><end_statement > |
String <id > = <s_expr ><end_statement > |
Boolean <id > = <b_expr ><end_statement > |
<id> = <d_expr ><end_statement > |
<id> = <i_expr ><end_statement > |
<id> = <s_expr ><end_statement > |
<id> = <b_expr ><end_statement >
```
```
<op> -> + | * | / | + | -
<rel_op > -> > | >= | < | <= | == | !=
```
```
<dbl > -> <sign ><digit >?.<digit ><digit >>?
<d_expr > -> <id> | <dbl > | <dbl > <op > <dbl > | <dbl > <op > <d_expr > |
<d_expr > <op > <dbl > | <d_expr > <op > <d_expr > |
<func_call >
```
```
<bool > True | False
<b_expr > -> <id> | <bool > | <i_expr > <rel_op > <i_expr > |
<d_expr > <rel_op > <d_expr > |
<s_expr > <rel_op > <s_expr > | <b_expr > <rel_op > <b_expr > |
<func_call >
```
```
<int > -> <sign ><digit ><digit >>?
<i_expr > -> <id> | <int > | <int > <op > <int > | <int > <op > <i_expr > |
<i_expr > <op > <int > | <i_expr > <op > <i_expr > |
<func_call >
```
```
<str_literal > -> "<str >"
<str > -> <char ><str > | <space ><str > | 
<s_expr > -> <str_literal > | <id > | <func_call >
```
Please note the difference between?(Kleene star) and*(multiplication).

Any line starting with#is a comment line and should be ignored by the interpreter; but still
factors into the line numbers.

Like other programming languages that use block notation (Java and C) spacing and new
lines are not important. The following are equivalent:

```
print[x];
print[ x ];
print[x ] ;
print[ x];
```
Multiple statements, separated by a semicolon and any number of spaces, are allowed:

```
String hello = "Hello";String world = " World";
String hello = "Hello"; String world = " World";
```

Multi-line statements are also allowed in Jott:

```
String foo =
concat[ "bar", "baz" ];
```

## 4 DFA for Scanning Jott

Here is the DFA to help you tokenize Jott for Phase 1; a PDF will be provided.


## 5 Error Handling

Your translator must handle errors in the syntax and semantics. If a syntax error is found
the Jott translator will not translate to the new language.

When handling an error (violations of the grammar or semantic errors), you will report a
the proper type of error (Syntax/Semanitc), the reason for the error, and the line of code
that caused the error; including the line number of the error (blank lines count as a line).

Example runs with errors, and their messages, have been included with this write-up.

## 6 Running the Program

Executing the program will be done one of three ways depending on the language you choose:

- Jott:java Jott input.jott output.jott Jott
    - giving the same filename for input and output will result in the original being
       overwritten.
- Python 3:java Jott input.jott output.py Python
- Java:java Jott input.jott Output.java Java
    - this one will be tricky because JAVA requires the file name to be the same as the
       class name.
- C:java Jott input.jott output.c C

input.jottis just a generic name for any program written in Jott; Jott program files
typically end in.jott.

The output will be the Jott file converted to the requested language. This will written to
theoutput.c/jott/java/pyfile.

No matter the programming language the user chooses output must reasonably similar to
the provided output in the examples.

The output file must compile and run in the new source language.

There must be four distinct phases in the program; scanning (tokenizing), parsing (build
parse tree), semantic analysis (building an AST), and code generation. You cannot move
onto the next phase without completing the prior phase without error.

NOTE: The parse tree and AST can be the same tree.

This will be placed in a Java class calledJott.

## 7 Example Jott Programs and Outputs

Example Jott programs and their C/Java/Python counterparts will be provided.


## 8 Other Constraints

You are not allowed to use any third part packages not built into Java on the CS machines.
The code must compile on the CS machines. Failure to compile on the CS machine will result
in a zero for that phase.

Mathematical order of operations will not be followed. Left to right parsing of expressions;
3 + 2 * 6will become 30 not 15.

You must handle all errors and/or exceptions. Any errors and/or exceptions thrown and not
handled will result in heavy penalties.

The program crashing at anytime will result in heavy penalties.

Failure of your program to run with the provided samples will result in heavy penalties for
this phase. Test with the samples! But be aware the samples are not all encompassing and
your program will be tested with other tests. So make some of your own.

## 9 Submission

Zip all source files for your program into a zip file calledphaseX.zip(where X is the number
of the phase) and submit to the proper myCourses’ Assignment box. Include a README
explaining how to build your project.

Failing to follow these instructions will result in a zero for that phase.

Emailed Submissions will not be accepted!If it does not make the dropbox it will not
get accepted. Be aware it takes time to upload a large amount of code. Do not wait until the
last second to start the upload. Submission time is when the submission is 100% complete;
not when you start submitting it.

Only one group member needs to submit. Only the last submission is kept and graded.

Questions will not be answered within the late window.

## 10 Grading

This phase will be graded as follows:

- (25%) Phase 1: Tokenizer
- (25%) Phase 2: Parser
- (25%) Phase 3: Semantic Analysis / AST
- (25%) Phase 4: Code Generation


## 11 Phase 1 Details

In phase 1 you will be making a tokenizer for the Jott files. The tokenizer will read a Jott
file and output anArrayList<Token>representing the tokens in the provided file.

The tokenization will be based on the DFA provided in Section 4.

You will create a class callJottTokenizer. This class will have:

- a function defined as:
    public static ArrayList<Token> tokenize(String filename)

```
This function will take in the absolute/relative path of the file to parse. It will return
an ArrayList of tokens. TheTokenclass andTokenTypeenum have been provided.
```
If there is an error you to report it toSystem.errand returnNULL.

Helper functions are allowed and encouraged.

Example Jott source file:

```
#this is an example
main[]:Integer{
print[5];
#this is a comment
print[ "foo bar" ];
}
```
This will result in the following list of tokens:

```
main [ ] : Integer { print [ 5 ] ; print [ "foo bar" ] ; }
```
Note the space are there to separate the tokens and are not actually tokens themselves.

Example of an program with an error reported during this phase:

```
#this is an example
main[]:Integer{
print[5;
if[ x! 5 ]{
print[ 10 ];
}
}
```
This would report the error:

```
Syntax Error
Invalid token "!"
filename.jott:
```
The character after the!is a space. There is no valid transition from!using a space and it
is not in an accepting state.

Notice it did not pick up on the missing]in this phase. It is only tokenizing and not checking
structure.

Error messages in this phase should report the error type and where the error occurred in
the file. Sample format:


Syntax Error:
<Message>
<filename>:<line_number>


## 12 Phase 2 Details

In phase 2 you will make a parser based on the grammar listed in Section 3. The goal is to
build a parse tree.

You will create a class calledJottParser. This class will have:

- a function defined as:
    public static JottTree parse(ArrayList<Token> tokens)

```
This function will take in an ArrayList of tokens created by a JottTokenizer and return
the root of the tree represented by those tokens. If there is an error creating the tree
this function will report the error toSystem.errand returnnull.
JottTreeis a provided Interface described below.
```
JottTree Interface (more details in the provided file):

```
public Interface JottTree{
public String convertToJott();
public String convertToJava(String className);
public String convertToC();
public String convertToPython();
public boolean validateTree();
}
```
In this Phase you will only be implementing theconvertToJott(). This function will output
the tree as Jott code.

This interface can be used to subclass all of your nodes in the parse tree.

Note that in this phase it may produce semantically invalid Jott code; this will be validated
in Phase 3;

As spacing is not required in Jott it may be hard to read the file after it is created. You are
welcome to add spacing and newline for readability.

Let’s look at the example from Phase 1.

Example Jott source file:

```
#this is an example
main[]:Integer{
print[5];
#this is a comment
print[ "foo bar" ];
}
```
This will result in the following list of tokens:

```
main [ ] : Integer { print [ 5 ] ; print [ "foo bar" ] ; }
```
Notice the return is missing. That is not validated in this phase.

A tree should be made that looks like:


HINT: Make tree nodes for most of the left-hand items in the grammar; digits, chars, etc do
not really need to be their own nodes. Items such as braces, semicolons, etc also do not have
to be in the final tree. They just really need to be verified for existence.

WhenconvertToJottis called the output should be:


main[]:Integer{print[5];print["foo bar"];}

Notice the comments and spacing is gone, but it is basically the same code. It is fine if the
spacing is added back, but not required.

Example invalid Jott source file:

```
#this is an example
main[]:Integer{
print[5];
#this is a comment
print[ "foo bar" ]
}
```
Notice the semicolon is missing.

Example invalid Jott source file:

```
#this is an example
123foo[]:Integer{
print[5];
#this is a comment
print[ "foo bar" ]
}
```
Notice the function name is invalid. There is another error; missing semicolon after the
second print. Your program will report the first error it sees and stop.

Example invalid Jott source file:

```
#this is an example
foo[]:Integer{
Integer x =;
print[5];
#this is a comment
print[ "foo bar" ]
}
```
Notice the value after the equals is missing. Removing the = or adding a value after the
equals would make this valid.

This would report:

```
Syntax Error
Assignment missing right operand
filename.jott:
```
Notice all of these are issues with typing the code and not the meaning.

Example valid Jott source file for parsing but not phase 3:

```
#this is an example
foo[]:Integer{
Integer x = 3;
Double y = 3.2 + x;
```

```
print[5];
#this is a comment
print[ "foo bar" ]
}
```
Valid for paring becauseDouble <id> = <double> + <id>;is valid syntax. Phase 3: se-
mantic analysis will report this error.

Error messages in this phase should report the error type and where the error occurred in
the file. Sample format:

```
Syntax Error:
<Message>
<filename>:<line_number>
```

## 13 Phase 3 Details

In this phase you will implement thevalidateTree function; this is basically an action
routine. This function will determine if the parse tree follows all the required semantic rules.

For example:

```
Integer i = 5; //valid
Integer i = 5.5; //invalid
Integer y = i; //valid
foo[ y ]; // invalid if foo is expecting a non-integer
foo[]; // invalid if foo expects params
```
```
foo[]:Integer{ //invalid missing return
print[5];
}
```
Other examples (not all inclusive) of semantic errors:

- Function call to a yet defined function.
    - An error should be reported similar to:
       Semantic Error
       Call to unknown function <function_name>
       <filename>.jott:<linenumber>
- Missing/incorrectly defined main function.
- Invalid type being assigned into a variable.
- Invalid type being passed into a function param.
- Missing return statement for a non-void function. If statements will make this tricky.
- Use of undefined variable;
- Uninitialized variable being used.

HINT: If you structure the parse tree with various types of nodes in Phase 2, this will
become easy. For instance if you have aDoubleNodethat knows it is a double value, a table
of variables and their type, and implement anAssignmentNode. TheAssignmentNodecan
ask its children, right childDoubleNodeand left childVariableNode, their types and if they
are different report the error.

In this phase the missing return shown in phase 2 should be reported. An error should be
report similar to:

```
Semantic Error:
Missing return for non-Void function main
filename.jott:2
```
Error messages in this phase should report the error type and where the error occurred in
the file. Sample format:

```
Semantic Error:
<Message>
<filename>:<line_number>
```

## 14 Phase 4 Details

In this phase you will implement the convertToC/Java/Python functions; this is the code
generation phase.

Example Jott source file:

```
#this is an example
main[]:Void{
Integer x = 5;
print[x];
#this is a comment
print[ "foo bar" ];
}
```
Based on the command line input this will be converted to one of the 4 source languages;
Java, C, Python, or Jott.

Converted to Jott:

```
main[]:Integer{Integer x=5;print[x];print["foo bar"];}
```
Notice the code is all on one line and all extra whitespace is removed. Jott is not whitespace
dependent. Another acceptable solution is:

```
main[]:Void{
Integer x = 5;
print[5];
print[ "foo bar" ];
}
```
Adding whitespace for readability is fine.

Converted to C:

```
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
```
```
int main(void){printf("%d\n", 5);printf("%s\n","foo bar");return 1;}
```
Notice the return statement. C requires a return. Just return 1;

Another acceptable solution is:

```
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
```
```
int main(void){
int x = 5;
printf("%d\n", x);
printf("%s\n","foo bar");
return 1;
}
```

Note:

- By default include the shown header files. You might need them for string manipula-
    tion, variable declaration, printing, etc. So instead of tracking if you used a print, just
    include thestdio.h.
- printfwill require format strings and types. It also requires the newline character to
    follow the printing rules of Jott.
- voidfor the main function params. This will avoid compiling errors.

Converted to Python

```
def main():
x=5
print(x)
print("foo bar")
```
```
main()
```
Note:

- Python requires that you call the main function.
- Python requires the spacing to break up lines. This will be interesting with nested if
    and while loops.

Converted to Java:

```
public class Name{
public static void main(int x = 5;String args[]){System.out.println(5);
System.out.println("foo bar");}}
```
Note: this show multiple lines only because it would not fit on the page.

Another acceptable solution is:

```
public class Name{
public static void main(String args[]){
int x = 5;
System.out.println(x);
System.out.println("foo bar");
}
}
```
Note:

- Java requires everything to be in a class.
- Java requires the class name and file name to be the same.

## 15 Sample Code and Testers

Samples and testers will be provided for each phase. Your program/code must work with
these samples and testers. If they fail to work with the samples and tester there will be heavy
penalties.


Samples and testers will not be all encompassing. Create and test with your own test cases.
Testers are really just there for you to have the proper structure to ensure testing works
when grading.

## 16 Hello Worldprogram

When learning a new language typically ”Hello World” is one of the first program taught.
In Jott ”Hello World” will look like (contained in a file calledhelloworld.jott):

```
main[]:Void{
print["Hello World"];
}
```
Note the return value is 1 but that is not required, it could have been any integer value.

Converting this program from Jott to the three other languages will result in:

Python, converted by runningjava Jott helloworld.jott helloworld.py Python:

```
def main():
print("Hello World")
```
```
main()
```
C, converted by runningjava Jott helloworld.jott helloworld.c C:

```
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
```
```
int main(void){
printf("Hello World\n");
return 1;
}
```
Java, converted by runningjava Jott helloworld.jott HelloWorld.java Java:

```
public class HelloWorld{
public static void main(String args[]){
System.out.println("Hello World");
}
}
```

