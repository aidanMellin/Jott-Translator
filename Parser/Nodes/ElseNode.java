package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import javax.crypto.BadPaddingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

public class ElseNode implements JottTree{

    private final String ELSE = "else";
    private final String LBRACE_CHAR = "{";
    private JottTree body;
    private final String RBRACE_CHAR = "}";
    private final String EMPTY_STRING = "";
    private ArrayList<Token> tokens;
    private int tabCount;
    Hashtable<String, SymbolData> symbolTable;
    private String function;


    public ElseNode(ArrayList<Token> tokens, int tc, Hashtable<String, SymbolData> symbolTable, String func) {
        try {
            function = func;
            tabCount = tc;
            this.tokens = tokens;
            if (this.tokens.size() == 0) body = null;
            else {
                if (!(this.tokens.size() >= 3)) CreateSyntaxError("Invalid Token List", this.tokens.get(0));
                if (this.tokens.get(0).getTokenType() != TokenType.ID_KEYWORD)
                    CreateSyntaxError("Unexpected Token - Expected ID", this.tokens.get(0));
                if (this.tokens.get(1).getTokenType() != TokenType.L_BRACE)
                    CreateSyntaxError("Unexpected Token - Expected '{'", this.tokens.get(1));
                if (this.tokens.get(this.tokens.size() - 1).getTokenType() != TokenType.R_BRACE)
                    CreateSyntaxError("Unexpected Token - Expected '}'", this.tokens.get(this.tokens.size() - 1));
                ArrayList<Token> bodyTokens = this.tokens;
                bodyTokens.remove(0);
                bodyTokens.remove(0);
                bodyTokens.remove(bodyTokens.size() - 1);
                body = new BodyNode(bodyTokens, tabCount + 1, symbolTable, function);
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
        if (body == null) return EMPTY_STRING;
        else return ELSE + LBRACE_CHAR + "\n" +
                body.convertToJott() +
                "\t".repeat(tabCount) + RBRACE_CHAR + "\n";
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (body == null) return EMPTY_STRING;
        else return ELSE + LBRACE_CHAR + "\n" +
                body.convertToJava() +
                "\t".repeat(tabCount)+ RBRACE_CHAR + "\n";
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        if(body == null) return EMPTY_STRING;
        else return ELSE + LBRACE_CHAR + "\n" +
                body.convertToC() +
                "\t".repeat(tabCount) + RBRACE_CHAR + "\n";
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        if (body == null) return EMPTY_STRING;
        else return "else" + ":\n" +
                body.convertToPython();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        if (body == null) return true;
        else return body.validateTree();
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
