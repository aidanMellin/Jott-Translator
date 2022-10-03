package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class FunctionCallNode implements JottTree{

    private final String RBRACKET_STRING = "]";
    private final String LBRACKET_STRING = "[";
    private final ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;

    public FunctionCallNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        assert this.tokens != null;
        if (!this.tokens.get(0).getToken().matches("[a-z][a-zA-z0-9]*")) CreateSyntaxError("Invalid Function Name", this.tokens.get(0));
        subnodes.add(new IdNode(this.tokens.get(0)));
        ArrayList<Token> paramsTokens = new ArrayList<>();
        paramsTokens.addAll(2, tokens);
        paramsTokens.remove(paramsTokens.size()-1);
        subnodes.add(new ParametersNode(paramsTokens));
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        StringBuilder jott_func_call = new StringBuilder();
        jott_func_call.append(subnodes.get(0).convertToJott());
        jott_func_call.append(LBRACKET_STRING);
        jott_func_call.append(subnodes.get(1).convertToJott());
        jott_func_call.append(RBRACKET_STRING);
        return jott_func_call.toString();
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

    public void CreateSyntaxError(String msg, Token token) {
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        System.exit(0);
    }
}
