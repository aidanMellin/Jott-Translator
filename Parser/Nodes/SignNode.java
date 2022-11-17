package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

public class SignNode implements JottTree {

    private final String EMPTY_STRING = "";
    private Token token;
    private int tabCount;

    public SignNode(Token token, int tc) {
        try {
            this.token = token;
            if (this.token == null) return;
            else if (this.token.getTokenType() != TokenType.MATH_OP)
                CreateSyntaxError("Unexpected Token - Expected Math_Op", this.token);
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
        if (token == null) return EMPTY_STRING;
        else return token.getToken();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (token == null) return EMPTY_STRING;
        else return token.getToken();
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        if(token == null) return EMPTY_STRING;
        else return token.getToken();

    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        if (token == null) return EMPTY_STRING;
        else return token.getToken();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return token == null || convertToJott().matches("[-+]?");
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
