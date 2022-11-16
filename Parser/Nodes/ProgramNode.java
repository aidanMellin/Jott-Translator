package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class ProgramNode implements JottTree { //TODO

    private final String C_CLASSES = "";
    private final String JAVA_CLASS = "public class ";
    private final String JAVA_FILE = "(HAVE TO GET FILE NAME)";
    private final String LBRACE_CHAR = "{";
    private final String RBRACE_CHAR = "}";
    private final JottTree function_list;
    private int tabCount;
    private Token firstToken;

    public ProgramNode(ArrayList<Token> tokens, int tc){
        try {
            tabCount = tc;
            firstToken = tokens.get(0);
            function_list = new FunctionListNode(tokens, tabCount + 1);
            // also add EOF symbol??
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
     /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        return function_list.convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return JAVA_CLASS + JAVA_FILE + LBRACE_CHAR + "\n" + function_list.convertToJava() + RBRACE_CHAR;
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        return("");
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return function_list.convertToPython();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
	 * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        try {
            if (!symbolTable.containsKey("main") && !symbolTable.get("main").IsMain)
                CreateSemanticError("Missing main function from program", firstToken);
            return function_list.validateTree();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }

    public void CreateSemanticError(String msg, Token token) throws Exception {
        System.err.println("Semantic Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}