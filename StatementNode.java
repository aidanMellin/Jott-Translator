import java.util.ArrayList;

public class StatementNode implements JottTree{

    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private ArrayList<Token> tokens;

    public StatementNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        assert this.tokens != null;
        if (this.tokens.size() == 3) subnodes.add(new VariableDeclarationNode(this.tokens));
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        StringBuilder jott_stmt = new StringBuilder();
        for (JottTree node : subnodes) jott_stmt.append(node.convertToJott());
        return jott_stmt.toString();
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
