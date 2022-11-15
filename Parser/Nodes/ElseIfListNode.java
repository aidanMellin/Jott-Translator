package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Objects;

public class ElseIfListNode implements JottTree{

    private final String JOTT_ELSEIF = "elseif";
    private final String LBRACKET_CHAR = "[";
    private final String RBRACKET_CHAR = "]";
    private final String LBRACE_CHAR = "{";
    private final String RBRACE_CHAR = "}";
    private final String EMPTY_STRING = "";

    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private ArrayList<Token> tokens;
    private int tabCount;

    public ElseIfListNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            if (this.tokens.size() == 0) subnodes = null;
            else {
                if (!Objects.equals(this.tokens.get(0).getToken(), JOTT_ELSEIF))
                    CreateSyntaxError("Unexpected Token - Expected 'elseif'", this.tokens.get(0));
                this.tokens.remove(0);
                if (this.tokens.get(0).getTokenType() != TokenType.L_BRACKET)
                    CreateSyntaxError("Unexpected Token - Expected '['", this.tokens.get(0));
                this.tokens.remove(0);
                ArrayList<Token> b_expr = new ArrayList<>();
                int b_count = 1;
                while (b_count != 0) {
                    if (this.tokens.get(0).getTokenType() == TokenType.L_BRACKET) b_count++;
                    b_expr.add(this.tokens.remove(0));
                    if (this.tokens.size() == 0)
                        CreateSyntaxError("Error: empty token array", b_expr.get(b_expr.size() - 1));
                    if (this.tokens.get(0).getTokenType() == TokenType.R_BRACKET) b_count--;
                }
                subnodes.add(new BoolExprNode(b_expr, 0));
                if (this.tokens.get(0).getTokenType() != TokenType.R_BRACKET)
                    CreateSyntaxError("Unexpected Token - Expected ']'", this.tokens.get(0));
                this.tokens.remove(0);
                if (this.tokens.get(0).getTokenType() != TokenType.L_BRACE)
                    CreateSyntaxError("Unexpected Token - Expected '{'", this.tokens.get(0));
                this.tokens.remove(0);
                ArrayList<Token> body = new ArrayList<>();
                b_count = 1;
                while (b_count != 0) {
                    if (this.tokens.get(0).getTokenType() == TokenType.L_BRACE) b_count++;
                    body.add(this.tokens.remove(0));
                    if (this.tokens.size() == 0)
                        CreateSyntaxError("Error: empty token array", body.get(body.size() - 1));
                    if (this.tokens.get(0).getTokenType() == TokenType.R_BRACE) b_count--;
                }
                subnodes.add(new BodyNode(body, tabCount + 1));
                if (this.tokens.get(0).getTokenType() != TokenType.R_BRACE)
                    CreateSyntaxError("Unexpected Token - Expected '}'", this.tokens.get(0));
                this.tokens.remove(0);
                subnodes.add(new ElseIfListNode(this.tokens, tabCount));
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
        if (subnodes == null) return EMPTY_STRING;
        else return JOTT_ELSEIF + LBRACKET_CHAR + subnodes.get(0).convertToJott() + RBRACKET_CHAR + LBRACE_CHAR + "\n" +
                subnodes.get(1).convertToJott() +
                "\t".repeat(tabCount) + RBRACE_CHAR + subnodes.get(2).convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (subnodes == null) return EMPTY_STRING;
        else return "}else if(" + subnodes.get(0).convertToPython() + "){\n" +
                subnodes.get(1).convertToPython() +
                ";\n\t".repeat(tabCount) + subnodes.get(2).convertToPython()+ "\n"+"\t".repeat(tabCount)+"}";
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
        if (subnodes == null) return EMPTY_STRING;
        else return "elif " + subnodes.get(0).convertToPython() + ":\n" +
                subnodes.get(1).convertToPython() +
                "\n\t".repeat(tabCount) + subnodes.get(2).convertToPython();
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
