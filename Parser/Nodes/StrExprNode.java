package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class StrExprNode implements JottTree {

    private JottTree subnode;
    private Token token;
    private ArrayList<Token> tokens;
    private int tabCount;

    public StrExprNode(Token token, int tc) {
        try {
            tabCount = tc;
            this.token = token;
            assert this.token != null;
            if (this.token.getTokenType() == TokenType.STRING) subnode = new StrLiteralNode(this.token, tabCount);
            else if (this.token.getTokenType() == TokenType.ID_KEYWORD) subnode = new IdNode(this.token, tabCount);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public StrExprNode(ArrayList<Token> tokens, int tc) {
        tabCount = tc;
        this.tokens = tokens;
        assert this.tokens != null;
        if (this.tokens.size() == 1) {
            if (this.tokens.get(0).getTokenType() == TokenType.STRING) subnode = new StrLiteralNode(this.tokens.get(0), tabCount);
            else if (this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) subnode = new IdNode(this.tokens.get(0), tabCount);
        } else {
            subnode = new FunctionCallNode(this.tokens, tabCount);
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        return subnode.convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return subnode.convertToJava();
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        return subnode.convertToC();
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return subnode.convertToPython();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return(true);
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