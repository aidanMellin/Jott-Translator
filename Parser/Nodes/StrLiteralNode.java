package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class StrLiteralNode implements JottTree {

    private JottTree subnode;
    private final String QUOTE_CHAR = "\"";
    private Token token;
    private int tabCount;

    public StrLiteralNode(Token token, int tc) {
        try {
            tabCount = tc;
            this.token = token;
            assert this.token != null;
            if (token.getTokenType() != TokenType.STRING)
                CreateSyntaxError("Unexpected Token - Expected String", this.token);
            if (!token.getToken().matches("\"[a-zA-Z0-9\s]*\"")) CreateSyntaxError("Unrecognized Character", this.token);
            subnode = new StrNode(token.getToken().substring(1, token.getToken().length()-1), tabCount);
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
        return(QUOTE_CHAR + subnode.convertToJott() + QUOTE_CHAR);
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
