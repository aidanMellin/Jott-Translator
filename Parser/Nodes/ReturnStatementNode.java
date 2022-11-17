package Parser.Nodes;
import Tokenizer.*;
import Parser.*;
import java.util.ArrayList;
import java.util.Objects;

public class ReturnStatementNode implements JottTree {

    private ArrayList<Token> tokens;
    private ArrayList<JottTree> subnodes = new ArrayList<>();

    private final String JOTT_RETURN = "return";
    private int tabCount;

    public ReturnStatementNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            assert tokens != null;
            if (!Objects.equals(this.tokens.get(0).getToken(), JOTT_RETURN))
                CreateSyntaxError("Unexpected Token - Expected 'return'", this.tokens.get(0));
            if (this.tokens.size() == 1)
                CreateSyntaxError("Invalid Return Statement - No Line Ending", this.tokens.get(0));
            this.tokens.remove(0);
            ArrayList<Token> expr = new ArrayList<>();
            while (this.tokens.get(0).getTokenType() != TokenType.SEMICOLON && this.tokens.size() != 1)
                expr.add(this.tokens.remove(0));
            if (this.tokens.size() != 1 && this.tokens.get(0).getTokenType() != TokenType.SEMICOLON)
                CreateSyntaxError("Unexpected Token - Expected ';'", this.tokens.get(0));
            subnodes.add(new ExpressionNode(expr, tabCount));
            subnodes.add(new EndStatementNode(this.tokens.remove(0), tabCount));
            if (this.tokens.size() != 0) CreateSyntaxError("Expected } got <id>", this.tokens.get(0));
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
        return  "\t".repeat(tabCount) + JOTT_RETURN + " " +
                subnodes.get(0).convertToJott() +
                subnodes.get(1).convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return  "\t".repeat(tabCount) + JOTT_RETURN + " " +
                subnodes.get(0).convertToJott() +
                subnodes.get(1).convertToJott();
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
        return  "\t".repeat(tabCount) + JOTT_RETURN + " " +
                subnodes.get(0).convertToPython() +
                subnodes.get(1).convertToPython();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return(true);
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