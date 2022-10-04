package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class FunctionListNode implements JottTree { //TODO

    private ArrayList<Token> tokens;
    private JottTree function_def;
    private JottTree function_list;

    public FunctionListNode(ArrayList<Token> tokens){
        this.tokens = tokens;
        if(!(this.tokens.size() == 0)){
            // functionDefNode
            ArrayList<Token> fDefTokens = new ArrayList<>();
            while(!this.tokens.get(0).getTokenType().equals(TokenType.R_BRACE)) {
                fDefTokens.add(this.tokens.get(0));
                this.tokens.remove(0);
                if(this.tokens.size() == 1 && !this.tokens.get(0).getTokenType().equals(TokenType.R_BRACE)) {
                    CreateSyntaxError("Unexpected Token - Expected '}'", this.tokens.get(0));
                } else if (this.tokens.size() == 1 && this.tokens.get(0).getTokenType() == TokenType.R_BRACE) {
                    fDefTokens.add(this.tokens.remove(0));
                    break;
                }
            }
            function_def = new FunctionDefinitionNode(fDefTokens);

            // functionList
            assert this.tokens != null;
            function_list = new FunctionListNode(this.tokens);
        }
    }
     /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        if(function_def == null) {
            return("");
        }
        else {
            return(function_def.convertToJott() + function_list.convertToJott());
        }
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