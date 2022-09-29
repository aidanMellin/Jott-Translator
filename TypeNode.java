import java.util.Objects;

public class TypeNode implements JottTree{

    private final Token token;
    private final String JOTT_INTEGER = "Integer";
    private final String JOTT_DOUBLE = "Double";
    private final String JOTT_STRING = "String";
    private final String JOTT_BOOLEAN = "Boolean";
    public TypeNode(Token token) {
        this.token = token;
        assert this.token != null;
        assert this.token.getTokenType() == TokenType.ID_KEYWORD;
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        if (Objects.equals(token.getToken(), JOTT_BOOLEAN)) return JOTT_BOOLEAN;
        else if (Objects.equals(token.getToken(), JOTT_DOUBLE)) return JOTT_DOUBLE;
        else if (Objects.equals(token.getToken(), JOTT_INTEGER)) return JOTT_INTEGER;
        else if (Objects.equals(token.getToken(), JOTT_STRING)) return JOTT_STRING;
        else return null;
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
