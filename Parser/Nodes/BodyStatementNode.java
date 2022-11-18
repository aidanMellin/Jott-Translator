package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class BodyStatementNode implements JottTree {

    private ArrayList<JottTree> subnodes = new ArrayList<>();;
    private ArrayList<Token> tokens;

    private final String IF_STR = "if";
    private final String WHILE_STR = "while";
    private int tabCount;
    Hashtable<String, SymbolData> symbolTable;
    private String function;
    private IfStatementNode ifStatementNode;
    public boolean containsReturn;

    public BodyStatementNode(ArrayList<Token> tokens, int tc, Hashtable<String, SymbolData> symbolTable, String func) {
        try {
            function = func;
            tabCount = tc;
            this.tokens = tokens;
            assert this.tokens != null;
            if (this.tokens.get(0).getToken().equals(IF_STR)) {
                ifStatementNode = new IfStatementNode(this.tokens, tabCount, symbolTable, function);
                subnodes.add(ifStatementNode);
                containsReturn = ifStatementNode.containsReturn;
            } else if (this.tokens.get(0).getToken().equals(WHILE_STR)) {
                subnodes.add(new WhileLoopNode(this.tokens, tabCount, symbolTable, function));
            } else if (this.tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
                subnodes.add(new StatementNode(this.tokens, tabCount, symbolTable));
            } else {
                CreateSyntaxError("Unexpected Token - Expected 'Body Statement'", this.tokens.get(0));
            }
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
        return subnodes.get(0).convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return subnodes.get(0).convertToJava();
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        return subnodes.get(0).convertToC();
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return subnodes.get(0).convertToPython();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return subnodes.get(0).validateTree();
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
