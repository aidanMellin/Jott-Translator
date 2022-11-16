package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

public class RelOpNode implements JottTree {

    private final Token token;
    private int tabCount;
    public RelOpNode(Token token, int tc) {
        try {
            tabCount = tc;
            this.token = token;
            assert this.token != null;
            if (this.token.getTokenType() != TokenType.REL_OP)
                CreateSyntaxError("Unexpected Token - Expected Rel_Op", this.token);
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
        return(token.getToken());
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return(token.getToken());
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        return(token.getToken());
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return(token.getToken());
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return token != null;
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}