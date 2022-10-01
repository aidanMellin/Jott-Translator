package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Objects;

public class StrNode implements JottTree {

    private final ArrayList<JottTree> subnodes = new ArrayList<>();
    private final String EMPTY_STR = "";
    private final String tokenString;

    public StrNode(String tokenString) {
        this.tokenString = tokenString;
        if (!Objects.equals(tokenString, EMPTY_STR)) {
            subnodes.add(new CharNode(tokenString.charAt(0)));
            if (tokenString.length() > 1) subnodes.add(new StrNode(tokenString.substring(1)));
            else subnodes.add(new StrNode(EMPTY_STR));
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        StringBuilder jott_string = new StringBuilder();
        if (Objects.equals(tokenString, EMPTY_STR)) return jott_string.toString();
        for (JottTree node : subnodes) jott_string.append(node.convertToJott());
        return jott_string.toString();
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
