package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.lang.reflect.Array;
import java.util.Objects;

public class BooleanNode implements JottTree {

    private final String ERROR_MSG = "Unexpected Token - Expected True or False";
    private final String JOTT_TRUE = "True";
    private final String JOTT_FALSE = "False";
    private final Token token;

    public BooleanNode(Token token) {
        this.token = token;
        assert this.token != null;
        if (!Objects.equals(this.token.getToken(), JOTT_TRUE) && !Objects.equals(this.token.getToken(), JOTT_FALSE)) CreateSyntaxError(ERROR_MSG, this.token);
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

    public void CreateSyntaxError(String msg, Token token) {
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        System.exit(0);
    }
}