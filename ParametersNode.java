import java.util.ArrayList;

public class ParametersNode implements JottTree{

    private final String EMPTY_STRING = "";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;

    public ParametersNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        if (this.tokens == null) subnodes = null;
        else {
            ArrayList<Token> expr = new ArrayList<>();
            int b_count = 0;
            while ((b_count != 0 || this.tokens.get(0).getTokenType() != TokenType.COMMA) && this.tokens.size() != 0) {
                expr.add(this.tokens.get(0));
                this.tokens.remove(0);
                if (this.tokens.get(0).getTokenType() == TokenType.L_BRACKET) b_count++;
                else if (this.tokens.get(0).getTokenType() == TokenType.R_BRACKET) b_count++;

            }
            subnodes.add(new ExpressionNode(expr));
            subnodes.add(new ParametersTNode(this.tokens));
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        if (subnodes == null) return EMPTY_STRING;
        StringBuilder jott_params = new StringBuilder();
        for (JottTree node : subnodes) jott_params.append(node.convertToJott());
        return jott_params.toString();
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
