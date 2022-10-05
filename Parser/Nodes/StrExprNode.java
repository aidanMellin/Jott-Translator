package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class StrExprNode implements JottTree {

    private JottTree subnode;
    private Token token;
    private ArrayList<Token> tokens;

    public StrExprNode(Token token) {
        try {
            this.token = token;
            assert this.token != null;
            if (this.token.getTokenType() == TokenType.STRING) subnode = new StrLiteralNode(this.token);
            else if (this.token.getTokenType() == TokenType.ID_KEYWORD) subnode = new IdNode(this.token);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public StrExprNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        assert this.tokens != null;
        subnode = new FunctionCallNode(this.tokens);
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
        return("");
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