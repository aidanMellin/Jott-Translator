package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import javax.crypto.BadPaddingException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ElseNode implements JottTree{

    private final String JOTT_ELSE = "else";
    private final String LBRACE_CHAR = "{";
    private JottTree body;
    private final String RBRACE_CHAR = "}";
    private final String EMPTY_STRING = "";
    private ArrayList<Token> tokens;


    public ElseNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        if (this.tokens == null) body = null;
        else {
            assert this.tokens.size() >= 3;
            assert this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD;
            assert this.tokens.get(1).getTokenType() == TokenType.L_BRACE;
            assert this.tokens.get(this.tokens.size()-1).getTokenType() == TokenType.L_BRACE;
            ArrayList<Token> bodyTokens = this.tokens;
            bodyTokens.remove(0);
            bodyTokens.remove(0);
            bodyTokens.remove(bodyTokens.size()-1);
            body = new BodyNode(bodyTokens);
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        if (body == null) return EMPTY_STRING;
        else return JOTT_ELSE + LBRACE_CHAR + body.convertToJott() + RBRACE_CHAR;
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
}
