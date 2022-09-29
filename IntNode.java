import java.util.ArrayList;

public class IntNode implements JottTree {

    private final ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;

    public IntNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        if (this.tokens.size() == 1) {
            subnodes.add(new SignNode(null));
            assert tokens.get(0).getToken().matches("[0-9]+");
            for (int i=0; i<tokens.get(0).getToken().length(); i++)
                subnodes.add(new CharNode(tokens.get(0).getToken().charAt(i)));
        } else if (this.tokens.size() == 2) {
            assert tokens.get(0).getToken().matches("[-+]?");
            assert tokens.get(1).getToken().matches("[0-9]+");
            subnodes.add(new SignNode(tokens.get(0)));
            for (int i=1; i<tokens.get(1).getToken().length(); i++)
                subnodes.add(new CharNode(tokens.get(1).getToken().charAt(i)));
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        StringBuilder jott_integer = new StringBuilder();
        for(JottTree node : subnodes) jott_integer.append(node.convertToJott());
        return jott_integer.toString();
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