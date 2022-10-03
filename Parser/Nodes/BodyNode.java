package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class BodyNode implements JottTree {

    private ArrayList<JottTree> subnodes;
    private ArrayList<Token> tokens;

    private final String EMPTY_STR = "";
    private  final String RETURN_STR = "return";
    private final String IF_STR = "if";
    private final String WHILE_STR = "while";

    public BodyNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        if(this.tokens.get(0).getTokenType() == TokenType.ID_KEYWORD && this.tokens.get(0).getToken() == RETURN_STR) {
            subnodes.add(new ReturnNode(this.tokens));
        }
        else if(this.tokens.get(0).getToken() == EMPTY_STR){

        }
        else {
            ArrayList<Token> bodyStmtTokens = null;
            if((this.tokens.get(0).getToken() == IF_STR) || (this.tokens.get(0).getToken() == WHILE_STR)){
                int leftBraceCount = 0;
                while((this.tokens.get(0).getTokenType() != TokenType.R_BRACE) && (leftBraceCount != 0)) {
                    if(this.tokens.get(0).getTokenType() == TokenType.L_BRACE) {
                        leftBraceCount++;
                    }
                    if(this.tokens.get(0).getTokenType() == TokenType.R_BRACE) {
                        leftBraceCount--;
                    }
                    bodyStmtTokens.add(this.tokens.get(0));
                    this.tokens.remove(0);
                }
            }
            else {
                while(this.tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
                    bodyStmtTokens.add(this.tokens.get(0));
                    this.tokens.remove(0);
                }
            }
            subnodes.add(new BodyStatementNode(bodyStmtTokens));
            subnodes.add(new BodyStatementNode(this.tokens));
        }

    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        if(subnodes.size() == 1){
            return(subnodes.get(0).convertToJott());
        }
        else if(subnodes.size() == 2){
            return(subnodes.get(0).convertToJott() + subnodes.get(1).convertToJott());
        }
        else {
            return("");
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
}