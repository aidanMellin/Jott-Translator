package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class IntNode implements JottTree {

    private final ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;

    public IntNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            if (this.tokens.size() == 1) {
                subnodes.add(new SignNode(null, tabCount));
                if (!tokens.get(0).getToken().matches("[0-9]+"))
                    CreateSyntaxError("Unexpected Character", this.tokens.get(0));
                assert tokens.get(0).getTokenType() == TokenType.NUMBER;
                for (int i = 0; i < tokens.get(0).getToken().length(); i++)
                    subnodes.add(new CharNode(tokens.get(0).getToken().charAt(i), tabCount));
            } else if (this.tokens.size() == 2) {
                if (!tokens.get(0).getToken().matches("[-+]?"))
                    CreateSyntaxError("Unexpected Character - Expected '-' or '+'", this.tokens.get(0));
                ;
                if (tokens.get(0).getTokenType() != TokenType.MATH_OP)
                    CreateSyntaxError("Unexpected Token - Expected MathOp", this.tokens.get(0));
                if (!tokens.get(1).getToken().matches("[0-9]+"))
                    CreateSyntaxError("Unexpected Character", this.tokens.get(1));
                ;
                if (tokens.get(1).getTokenType() != TokenType.NUMBER)
                    CreateSyntaxError("Unexpected Token - Expected Number", this.tokens.get(0));
                subnodes.add(new SignNode(tokens.get(0), tabCount));
                for (int i = 1; i < tokens.get(1).getToken().length(); i++)
                    subnodes.add(new CharNode(tokens.get(1).getToken().charAt(i), tabCount));
            }
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
        StringBuilder jott_integer = new StringBuilder();
        for(JottTree node : subnodes) jott_integer.append(node.convertToJott());
        return jott_integer.toString();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        StringBuilder jott_integer = new StringBuilder();
        for(JottTree node : subnodes) jott_integer.append(node.convertToJava());
        return jott_integer.toString();
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        String C_Code = "";

        for(JottTree node : subnodes){
            C_Code += node.convertToC();
        }

        return C_Code;
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        StringBuilder jott_integer = new StringBuilder();
        for(JottTree node : subnodes) jott_integer.append(node.convertToPython());
        return jott_integer.toString();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return convertToJott().matches("-?[0-9]+");
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}