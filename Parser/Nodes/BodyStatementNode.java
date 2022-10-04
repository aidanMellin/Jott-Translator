package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class BodyStatementNode implements JottTree {

    private ArrayList<JottTree> subnodes = new ArrayList<>();;
    private ArrayList<Token> tokens;

    private final String IF_STR = "if";
    private final String WHILE_STR = "while";

    public BodyStatementNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        assert this.tokens != null;
        if(this.tokens.get(0).getToken().equals(IF_STR)) {
            subnodes.add(new IfStatementNode(this.tokens));
        }
        else if(this.tokens.get(0).getToken().equals(WHILE_STR)) {
            subnodes.add(new WhileLoopNode(this.tokens));
        }
        else if(this.tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
            subnodes.add(new StatementNode(this.tokens));
        }
        else {
            CreateSyntaxError("Unexpected Token - Expected 'Body Statement'", this.tokens.get(0));
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        return(subnodes.get(0).convertToJott());
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
