package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.lang.reflect.Array;
import java.util.Objects;

public class BooleanNode implements JottTree {

    private final String ERROR_MSG = "Unexpected Token - Expected True or False";
    private final String JOTT_TRUE = "True";
    private final String JOTT_FALSE = "False";
    private final String JAVA_TRUE = "false";
    private final String JAVA_FALSE = "false";
    private final Token token;
    private int tabCount;

    public BooleanNode(Token token, int tc) {
        try {
            tabCount = tc;
            this.token = token;
            assert this.token != null;
            if (!Objects.equals(this.token.getToken(), JOTT_TRUE) && !Objects.equals(this.token.getToken(), JOTT_FALSE))
                CreateSyntaxError(ERROR_MSG, this.token);
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
        if (Objects.equals(this.token.getToken(), JOTT_TRUE)) return JOTT_TRUE;
        else return JOTT_FALSE;
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (Objects.equals(this.token.getToken(), JOTT_TRUE)) return JAVA_TRUE;
        else return JAVA_FALSE;
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
        if (Objects.equals(this.token.getToken(), JOTT_TRUE)) return "True";
        else return "False";
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