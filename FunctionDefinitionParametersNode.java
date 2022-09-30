import java.util.ArrayList;

public class FunctionDefinitionParametersNode implements JottTree {

    private final String COLON_CHAR = ":";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final String EMPTY_STRING = "";
    private final ArrayList<Token> tokens;

    public FunctionDefinitionParametersNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        if (this.tokens.size() == 0) subnodes = null;
        else {
            assert this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD;
            subnodes.add(new IdNode(this.tokens.remove(0)));
            assert this.tokens.get(0).getTokenType() == TokenType.COLON;
            this.tokens.remove(0);
            assert this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD;
            subnodes.add(new TypeNode(this.tokens.remove(0)));
            subnodes.add(new FunctionDefinitionParametersTNode(this.tokens));
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        if (subnodes == null) return EMPTY_STRING;
        return subnodes.get(0).convertToJott() +
                COLON_CHAR +
                subnodes.get(1).convertToJott() +
                subnodes.get(2).convertToJott();
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
