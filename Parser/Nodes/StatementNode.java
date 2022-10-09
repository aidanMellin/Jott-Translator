package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class StatementNode implements JottTree{

    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private ArrayList<Token> tokens;

    public StatementNode(ArrayList<Token> tokens) {
        try {
            this.tokens = tokens;
            assert this.tokens != null;
            if (this.tokens.get(1).getTokenType() == TokenType.L_BRACKET) {
                Token end_stmt = this.tokens.remove(this.tokens.size() - 1);
                subnodes.add(new FunctionCallNode(this.tokens));
                subnodes.add(new EndStatementNode(end_stmt));
            } else if (this.tokens.size() == 3) {
                subnodes.add(new VariableDeclarationNode(this.tokens));
            } else {
                subnodes.add(new AssignmentNode(this.tokens));
            }
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
        StringBuilder jott_stmt = new StringBuilder();
        jott_stmt.append("\t");
        for (JottTree node : subnodes) jott_stmt.append(node.convertToJott());
        if(subnodes.size() == 2){
            jott_stmt.append("\n");
        }
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

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}
