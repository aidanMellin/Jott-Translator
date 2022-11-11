package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class ParametersNode implements JottTree{

    private final String EMPTY_STRING = "";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;

    public ParametersNode(ArrayList<Token> tokens, int tc) {
        try {
            this.tokens = tokens;
            if (this.tokens.size() == 0) subnodes = null;
            else {
                ArrayList<Token> expr = new ArrayList<>();
                int b_count = 0;
                while ((b_count != 0 || this.tokens.get(0).getTokenType() != TokenType.COMMA) && this.tokens.size() != 0) {
                    expr.add(this.tokens.remove(0));
                    if (this.tokens.size() == 0) break;
                    if (this.tokens.get(0).getTokenType() == TokenType.L_BRACKET) b_count++;
                    else if (this.tokens.get(0).getTokenType() == TokenType.R_BRACKET) b_count--;
                }
                subnodes.add(new ExpressionNode(expr, tabCount));
                subnodes.add(new ParametersTNode(this.tokens, tabCount));
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
        if (subnodes == null) return EMPTY_STRING;
        StringBuilder jott_params = new StringBuilder();
        for (JottTree node : subnodes) jott_params.append(node.convertToJott());
        return jott_params.toString();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return("");
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
        if (subnodes == null) return EMPTY_STRING;
        StringBuilder jott_params = new StringBuilder();
        for (JottTree node : subnodes) jott_params.append(node.convertToPython());
        return jott_params.toString();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return(false);
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}
