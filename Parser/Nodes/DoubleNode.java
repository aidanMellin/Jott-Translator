package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class DoubleNode implements JottTree {

    private final String PERIOD_CHAR = ".";
    private int PERIOD_PLACE;
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;

    public DoubleNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            assert this.tokens != null;
            if (this.tokens.size() == 1) {
                subnodes.add(new SignNode(null, tabCount));
                if (!tokens.get(0).getToken().matches("[0-9]*[.][0-9]+"))
                    CreateSyntaxError("Not a Valid Double Number", this.tokens.get(0));
                for (int i = 0; i < tokens.get(0).getToken().length(); i++)
                    if (tokens.get(0).getToken().charAt(i) == '.') PERIOD_PLACE = i;
                    else subnodes.add(new CharNode(tokens.get(0).getToken().charAt(i), tabCount));
            } else if (this.tokens.size() == 2) {
                if (!tokens.get(0).getToken().matches("[-+]?"))
                    CreateSyntaxError("Unexpected Token - Expected '+' or '-'", this.tokens.get(0));
                if (!tokens.get(1).getToken().matches("[0-9]*[.][0-9]+"))
                    CreateSyntaxError("Not a Valid Double Number", this.tokens.get(1));
                subnodes.add(new SignNode(tokens.get(0), tabCount));
                for (int i = 1; i < tokens.get(1).getToken().length(); i++)
                    if (tokens.get(1).getToken().charAt(i) == '.') PERIOD_PLACE = i;
                    else subnodes.add(new CharNode(tokens.get(1).getToken().charAt(i), tabCount));
            } else CreateSyntaxError("Unexpected Token", this.tokens.get(3));
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
        StringBuilder jott_integer = new StringBuilder();
        for(int i=0; i<subnodes.size(); i++) {
            if (i-1 == PERIOD_PLACE) jott_integer.append(PERIOD_CHAR);
            jott_integer.append(subnodes.get(i).convertToJott());
        }
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

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}