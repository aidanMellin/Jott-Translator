import java.util.ArrayList;

public class IfStatementNode implements JottTree{

    private final String JOTT_IF = "if";
    private final String LBRACKET_CHAR = "[";
    private final String RBRACKET_CHAR = "]";
    private final String LBRACE_CHAR = "{";
    private final String RBRACE_CHAR = "}";
    private ArrayList<JottTree> subnodes;

    public IfStatementNode() {

    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        return("");
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