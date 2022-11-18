package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Objects;

public class BooleanNode implements JottTree {

    private final String ERROR_MSG = "Unexpected Token - Expected True or False";
    private final String JOTT_TRUE = "True";
    private final String JOTT_FALSE = "False";
    private final String JAVA_TRUE = "true";
    private final String JAVA_FALSE = "false";
    private final Token token;
    private int tabCount;
    Hashtable<String, SymbolData> symbolTable;

    public BooleanNode(Token token, int tc, Hashtable<String, SymbolData> symbolTable) {
        try {
            tabCount = tc;
            this.token = token;
            assert this.token != null;
            if (!Objects.equals(this.token.getToken(), JOTT_TRUE) && !Objects.equals(this.token.getToken(), JOTT_FALSE))
                CreateSyntaxError(ERROR_MSG, this.token);
            this.symbolTable = symbolTable;
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
        if (Objects.equals(this.token.getToken(), JOTT_TRUE)) return JAVA_TRUE;
        else return JAVA_FALSE;
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
        return this.token.getToken().equals(JOTT_TRUE) || this.token.getToken().equals(JOTT_FALSE);
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