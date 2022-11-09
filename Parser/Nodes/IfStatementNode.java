package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Objects;

public class IfStatementNode implements JottTree{

    private final String JOTT_IF = "if";
    private final String LBRACKET_CHAR = "[";
    private final String RBRACKET_CHAR = "]";
    private final String LBRACE_CHAR = "{";
    private final String RBRACE_CHAR = "}";
    private final String ELSE_STRING = "else";
    private final String ELSEIF_STRING = "elseif";

    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private ArrayList<Token> tokens;
    private int tabCount;

    public IfStatementNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            if (this.tokens.size() == 0) subnodes = null;
            else {
                // if
                assert this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD;
                assert this.tokens.get(0).getToken() == JOTT_IF;
                this.tokens.remove(0);
                // [
                assert this.tokens.get(0).getTokenType() == TokenType.L_BRACKET;
                this.tokens.remove(0);
                // b_expr
                ArrayList<Token> b_exprTokens = new ArrayList<>();
                while (this.tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
                    b_exprTokens.add(this.tokens.get(0));
                    this.tokens.remove(0);
                }
                // ]
                subnodes.add(new BoolExprNode(b_exprTokens, 0));
                assert this.tokens.get(0).getTokenType() == TokenType.R_BRACKET;
                this.tokens.remove(0);
                // {
                assert this.tokens.get(0).getTokenType() == TokenType.L_BRACE;
                this.tokens.remove(0);
                // body
                ArrayList<Token> bodyTokens = new ArrayList<>();
                while (this.tokens.get(0).getTokenType() != TokenType.R_BRACE) {
                    bodyTokens.add(this.tokens.get(0));
                    this.tokens.remove(0);
                }
                subnodes.add(new BodyNode(bodyTokens, tabCount));
                // }
                assert this.tokens.get(0).getTokenType() == TokenType.R_BRACE;
                this.tokens.remove(0);

                ArrayList<Token> elseif = new ArrayList<>();
                int b_count = 0;
                while (this.tokens.size() != 0 && (!Objects.equals(this.tokens.get(0).getToken(), ELSE_STRING) || b_count != 0)) {
                    if (this.tokens.get(0).getTokenType() == TokenType.L_BRACE) b_count++;
                    else if (this.tokens.get(0).getTokenType() == TokenType.R_BRACE) b_count--;
                    elseif.add(this.tokens.remove(0));
                }
                subnodes.add(new ElseIfListNode(elseif, tabCount));
                subnodes.add(new ElseNode(this.tokens, tabCount));
            }
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
        return "\t".repeat(tabCount) + JOTT_IF + LBRACKET_CHAR + subnodes.get(0).convertToJott() + RBRACKET_CHAR + LBRACE_CHAR + "\n" +
                subnodes.get(1).convertToJott() + "\n" +
                "\t".repeat(tabCount) + RBRACE_CHAR + subnodes.get(2).convertToJott() + subnodes.get(3).convertToJott();
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

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}
