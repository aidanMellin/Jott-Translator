package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.Objects;

public class TypeNode implements JottTree{

    private final Token token;
    private final String JOTT_INTEGER = "Integer";
    private final String JOTT_DOUBLE = "Double";
    private final String JOTT_STRING = "String";
    private final String JOTT_BOOLEAN = "Boolean";
    private int tabCount;
    public TypeNode(Token token, int tc) {
        try {
            tabCount = tc;
            this.token = token;
            assert this.token != null;
            if (this.token.getTokenType() != TokenType.ID_KEYWORD)
                CreateSyntaxError("Unexpected Token - Expected ID", this.token);
            if (!Objects.equals(this.token.getToken(), JOTT_DOUBLE)
                    && !Objects.equals(this.token.getToken(), JOTT_BOOLEAN)
                    && !Objects.equals(this.token.getToken(), JOTT_INTEGER)
                    && !Objects.equals(this.token.getToken(), JOTT_STRING))
                CreateSyntaxError("Unexpected Token - Expected Double, Integer, String, or Boolean", this.token);
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
        if (Objects.equals(token.getToken(), JOTT_BOOLEAN)) return JOTT_BOOLEAN;
        else if (Objects.equals(token.getToken(), JOTT_DOUBLE)) return JOTT_DOUBLE;
        else if (Objects.equals(token.getToken(), JOTT_INTEGER)) return JOTT_INTEGER;
        else return JOTT_STRING;
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (Objects.equals(token.getToken(), JOTT_BOOLEAN)) return "BOOLEAN";
        else if (Objects.equals(token.getToken(), JOTT_DOUBLE)) return "DOUBLE";
        else if (Objects.equals(token.getToken(), JOTT_INTEGER)) return "INTEGER";
        else return "String";
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
        if (Objects.equals(token.getToken(), JOTT_BOOLEAN)) return "BOOLEAN";
        else if (Objects.equals(token.getToken(), JOTT_DOUBLE)) return "DOUBLE";
        else if (Objects.equals(token.getToken(), JOTT_INTEGER)) return "INTEGER";
        else return "String";
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return token.getToken().equals(JOTT_BOOLEAN) ||
                token.getToken().equals(JOTT_DOUBLE) ||
                token.getToken().equals(JOTT_INTEGER) ||
                token.getToken().equals(JOTT_STRING);
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}
