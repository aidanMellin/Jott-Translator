import java.util.Objects;

public class FunctionReturnNode implements JottTree{

    private final JottTree subnode;
    private final String JOTT_VOID = "Void";
    private final Token token;

    public FunctionReturnNode(Token token) {
        this.token = token;
        assert this.token != null;
        if (Objects.equals(this.token.getToken(), JOTT_VOID)) subnode = null;
        else subnode = new TypeNode(this.token);
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        if (subnode == null) return JOTT_VOID;
        else return subnode.convertToJott();
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
