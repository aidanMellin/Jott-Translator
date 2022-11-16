package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class ParametersNode implements JottTree{

    private final String EMPTY_STRING = "";
    private ArrayList<JottTree> subnodes = new ArrayList<>();

    private ExpressionNode expressionNode;
    private ParametersTNode parametersTNode;
    private final ArrayList<Token> tokens;
    private int tabCount;
    private int cnt = 0;
    private String function;

    public ParametersNode(ArrayList<Token> tokens, int tc, String func) {
        try {
            function = func;
            this.tokens = tokens;
            if (this.tokens.size() == 0) {
                expressionNode = null;
                parametersTNode = null;
            }
            else {
                ArrayList<Token> expr = new ArrayList<>();
                int b_count = 0;
                while ((b_count != 0 || this.tokens.get(0).getTokenType() != TokenType.COMMA) && this.tokens.size() != 0) {
                    expr.add(this.tokens.remove(0));
                    if (this.tokens.size() == 0) break;
                    if (this.tokens.get(0).getTokenType() == TokenType.L_BRACKET) b_count++;
                    else if (this.tokens.get(0).getTokenType() == TokenType.R_BRACKET) b_count--;
                }
                expressionNode = new ExpressionNode(expr, tabCount);
                parametersTNode = new ParametersTNode(this.tokens, tabCount, cnt+1, func);
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
        if (expressionNode == null) return EMPTY_STRING;
        return expressionNode.convertToJott() + parametersTNode.convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (expressionNode == null) return EMPTY_STRING;
        return expressionNode.convertToJott() + parametersTNode.convertToJott();
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
        if (expressionNode == null) return EMPTY_STRING;
        return expressionNode.convertToJott() + parametersTNode.convertToJott();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        if (subnodes == null) return symbolTable.get(function).Params.size() == cnt;
        else return (symbolTable.get(function).ParamsTypes.get(cnt).equals(expressionNode.expr_type)) &&
                expressionNode.validateTree() &&
                parametersTNode.validateTree();
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}
