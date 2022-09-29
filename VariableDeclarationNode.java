import java.util.ArrayList;

public class VariableDeclarationNode implements JottTree{

    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    
    public VariableDeclarationNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        assert this.tokens.size() == 3;
        subnodes.add(new TypeNode(this.tokens.get(0)));
        subnodes.add(new IdNode(this.tokens.get(1)));
        subnodes.add(new EndStatementNode(this.tokens.get(2)));
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        StringBuilder jott_var_dec = new StringBuilder();
        for (JottTree node : subnodes) jott_var_dec.append(node.convertToJott());
        return jott_var_dec.toString();
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
