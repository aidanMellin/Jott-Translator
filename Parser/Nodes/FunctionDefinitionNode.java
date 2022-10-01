package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class FunctionDefinitionNode implements JottTree {

    private final String RBRACKET_CHAR = "]";
    private final String LBRACKET_CHAR = "[";
    private final String RBRACE_CHAR = "}";
    private final String LBRACE_CHAR = "{";
    private final String COLON_CHAR = ":";
    private final ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;

    public FunctionDefinitionNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        assert this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD;
        subnodes.add(new IdNode(this.tokens.remove(0)));
        assert this.tokens.get(0).getTokenType() == TokenType.L_BRACKET;
        this.tokens.remove(0);
        ArrayList<Token> func_def_params = new ArrayList<>();
        while (this.tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            func_def_params.add(this.tokens.remove(0));
            assert this.tokens.size() != 0;
        }
        subnodes.add(new FunctionDefinitionParametersNode(func_def_params));
        assert this.tokens.get(0).getTokenType() == TokenType.R_BRACKET;
        this.tokens.remove(0);
        assert this.tokens.get(0).getTokenType() == TokenType.COLON;
        this.tokens.remove(0);
        assert this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD;
        subnodes.add(new FunctionReturnNode(this.tokens.remove(0)));
        assert this.tokens.get(0).getTokenType() == TokenType.L_BRACE;
        ArrayList<Token> body = new ArrayList<>();
        int b_count = 1;
        while (b_count != 0) {
            if (this.tokens.get(0).getTokenType() == TokenType.L_BRACE) b_count++;
            body.add(this.tokens.remove(0));
            assert this.tokens.size() != 0;
            if (this.tokens.get(0).getTokenType() == TokenType.R_BRACE) b_count--;
        }
        subnodes.add(new BodyNode(body));
        assert this.tokens.get(0).getTokenType() == TokenType.R_BRACE;
        this.tokens.remove(0);
        assert this.tokens.size() == 0;
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        return subnodes.get(0).convertToJott() +
                LBRACKET_CHAR +
                subnodes.get(1).convertToJott() +
                RBRACKET_CHAR +
                COLON_CHAR +
                subnodes.get(2).convertToJott() +
                LBRACE_CHAR +
                subnodes.get(3).convertToJott() +
                RBRACE_CHAR;
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
