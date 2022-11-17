package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class IdNode implements JottTree {

    private String JOTT_PRINT = "print";
    private String JAVA_PRINT = "System.out.println";
    private String C_PRINT = "printf";

    private Token idToken;
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private String idStored;
    private int tabCount;

    public IdNode(Token token, int tc){
        try {
            tabCount = tc;
            this.idToken = token;
            assert idToken != null;
            this.idStored = idToken.getToken();
            if (!idStored.matches("[a-z][a-zA-z0-9]*")) CreateSyntaxError("Unexpected Character", idToken);
            for (int i = 0; i < idStored.length(); i++) subnodes.add(new CharNode(idStored.charAt(i), tabCount));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
     /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        StringBuilder jott_id = new StringBuilder();
        for (JottTree node : subnodes) jott_id.append(node.convertToJott());
        return jott_id.toString();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        StringBuilder java_id = new StringBuilder();
        for (JottTree node : subnodes) java_id.append(node.convertToJava());
        if(java_id.toString().equals(JOTT_PRINT)){
            return JAVA_PRINT;
        }
        return java_id.toString();
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        StringBuilder c_id = new StringBuilder();
        for (JottTree node : subnodes) c_id.append(node.convertToC());
        if(c_id.toString().equals(JOTT_PRINT)){
            return C_PRINT;
        }
        return c_id.toString();
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        StringBuilder jott_id = new StringBuilder();
        for (JottTree node : subnodes) jott_id.append(node.convertToPython());
        return jott_id.toString();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
	 * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return idStored.matches("[a-z][a-zA-z0-9]*");
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