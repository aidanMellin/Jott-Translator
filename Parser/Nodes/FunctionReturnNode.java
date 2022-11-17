package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.Hashtable;
import java.util.Objects;

public class FunctionReturnNode implements JottTree{

    private final JottTree subnode;
    private final String JOTT_VOID = "Void";
    private final String JAVA_VOID = "void";
    private final Token token;

    private int tabCount;
    Hashtable<String, SymbolData> symbolTable;
    public FunctionReturnNode(Token token, int tc, Hashtable<String, SymbolData> symbolTable) {
        try {
            this.symbolTable = symbolTable;
            tabCount = tc;
            this.token = token;
            assert this.token != null;
            if (Objects.equals(this.token.getToken(), JOTT_VOID)) subnode = null;
            else subnode = new TypeNode(this.token, tabCount, this.symbolTable);
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
        if (subnode == null) return JOTT_VOID;
        else return subnode.convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (subnode == null) return JAVA_VOID;
        else return subnode.convertToJava();
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
        if (subnode == null) return true;
        else return subnode.validateTree();
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
