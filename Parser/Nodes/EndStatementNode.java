package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

public class EndStatementNode implements JottTree {

    private final String JOTT_JAVA_C_END = ";\n";
    private final String PYTHON_END = "\n";

    private Token token;
    private int tabCount;

    public EndStatementNode(Token token, int tc) {
        try {
            tabCount = tc;
            this.token = token;
            if (this.token.getTokenType() != TokenType.SEMICOLON)
                CreateSyntaxError("Unexpected Token - Expected ';'", this.token);
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
        return(JOTT_JAVA_C_END);
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return(JOTT_JAVA_C_END);
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        return(JOTT_JAVA_C_END);
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return (PYTHON_END);
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

    public void CreateSemanticError(String msg, Token token) throws Exception {
        System.err.println("Semantic Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}
