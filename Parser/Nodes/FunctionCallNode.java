package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class FunctionCallNode implements JottTree{

    private final String RBRACKET_STRING = "]";
    private final String LBRACKET_STRING = "[";
    private final ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;

    public FunctionCallNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            assert this.tokens != null;
            if (this.tokens.size() < 3) CreateSyntaxError("Invalid Function Call", this.tokens.get(0));
            if (!this.tokens.get(0).getToken().matches("[a-z][a-zA-z0-9]*"))
                CreateSyntaxError("Invalid Function Name", this.tokens.get(0));
            subnodes.add(new IdNode(this.tokens.get(0), tabCount));
            ArrayList<Token> paramsTokens = new ArrayList<>();
            if (this.tokens.get(1).getTokenType() != TokenType.L_BRACKET)
                CreateSyntaxError("Unexpected Token - Expected '['", this.tokens.get(1));
            if (this.tokens.get(this.tokens.size() - 1).getTokenType() != TokenType.R_BRACKET)
                CreateSyntaxError("Unexpected Token - Expected ']'", this.tokens.get(1));
            for (int i = 2; i < this.tokens.size() - 1; i++) paramsTokens.add(this.tokens.get(i));
            subnodes.add(new ParametersNode(paramsTokens, tabCount));
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
        return subnodes.get(0).convertToJott() +
                LBRACKET_STRING +
                subnodes.get(1).convertToJott() +
                RBRACKET_STRING;
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
        return subnodes.get(0).convertToPython() + "(" + subnodes.get(1).convertToPython() + ")";
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
