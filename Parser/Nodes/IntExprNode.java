package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class IntExprNode implements JottTree {
    
    private ArrayList<JottTree> subnodes;
    private ArrayList<Token> Tokens;
    //Currently missing information needed for handling another IntExprNode & a func_call
    public IntExprNode(ArrayList<Token> tokens) {
        Tokens = tokens;
        for (int i = 0; i < Tokens.size(); i++) {
            Token temp_token = Tokens.get(i);
            if (temp_token.getTokenType() == TokenType.ID_KEYWORD) {
                subnodes.add(new IdNode(temp_token));
            } else if (temp_token.getTokenType() == TokenType.NUMBER) {
                ArrayList<Token> temp_token_list = new ArrayList<>();
                temp_token_list.add(temp_token);
                subnodes.add(new IntNode(temp_token_list));

            } else if (temp_token.getTokenType() == TokenType.MATH_OP) {
                if (temp_token.getToken().equals("-")) {
                    if (i == 0 || Tokens.get(i - 1).getTokenType() != TokenType.NUMBER) {
                        ArrayList<Token> temp_token_list = new ArrayList<>();
                        temp_token_list.add(temp_token);
                        if (Tokens.get(i + 1).getTokenType() != TokenType.NUMBER) {
                            System.out.println("ERROR: Invalid expression, negative sign was not followed by a number.");
                            return;
                        } else {
                            temp_token_list.add(Tokens.get(i + 1));
                            subnodes.add(new IntNode(temp_token_list));
                            i++;
                        }
                    } else{
                        subnodes.add(new OpNode(temp_token));
                    }
                }
            }
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        String str = "";
        for(int i = 0; i < subnodes.size(); i++){
            str += subnodes.get(i).convertToJott();
        }
        return str;
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