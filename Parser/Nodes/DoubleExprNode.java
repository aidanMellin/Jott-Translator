package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class DoubleExprNode implements JottTree {

    private ArrayList<JottTree> subnodes;
    private ArrayList<Token> Tokens;
    private int tabCount;

    public DoubleExprNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            Tokens = tokens;
            subnodes = new ArrayList<>();

            ArrayList<JottTree> temp_subnodes = new ArrayList<>();
            ArrayList<Token> tokens_used = new ArrayList<>();
            int op_count = 0;

            for (int i = 0; i < Tokens.size(); i++) {
                boolean add_token = true;
                Token temp_token = Tokens.get(i);
                if (temp_token.getTokenType() == TokenType.ID_KEYWORD) {
                    if (i < tokens.size() - 1 && Tokens.get(i + 1).getTokenType() == TokenType.L_BRACKET) {
                        int count = 1;
                        ArrayList<Token> tokens_to_send = new ArrayList<>();
                        Token current_token = temp_token;
                        tokens_to_send.add(current_token);
                        while (count < Tokens.size() && current_token.getTokenType() != TokenType.R_BRACKET) {
                            current_token = Tokens.get(count);
                            tokens_to_send.add(current_token);
                            count++;
                        }
                        temp_subnodes.add(new FunctionCallNode(tokens_to_send, 0));
                        i += count;
                    } else {
                        temp_subnodes.add(new IdNode(temp_token, 0));
                    }
                } else if (temp_token.getTokenType() == TokenType.NUMBER) {
                    ArrayList<Token> temp_token_list = new ArrayList<>();
                    temp_token_list.add(temp_token);
                    temp_subnodes.add(new DoubleNode(temp_token_list, 0));

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
                                temp_subnodes.add(new DoubleNode(temp_token_list, 0));
                                i++;
                            }
                        } else {
                            temp_subnodes.add(new OpNode(temp_token, 0));
                            op_count++;
                        }
                    } else {
                        temp_subnodes.add(new OpNode(temp_token, 0));
                        op_count++;
                    }
                    if (op_count > 1) {   //once a second math op has happened, the program takes the tokens used so far and makes them into a seperate node
                        DoubleExprNode temp_condense = new DoubleExprNode(tokens_used, 0);
                        subnodes.add(temp_condense);
                        subnodes.add(temp_subnodes.get(temp_subnodes.size() - 1));
                        temp_subnodes = new ArrayList<>();
                        op_count = 0;
                        add_token = false;
                        tokens_used = new ArrayList<>();
                    }
                }

                if (add_token) {
                    tokens_used.add(temp_token);
                }
            }
            subnodes.addAll(temp_subnodes);
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
        StringBuilder str = new StringBuilder();
        for (JottTree node : subnodes) {
            str.append(node.convertToJott());
        }
        return str.toString();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        StringBuilder str = new StringBuilder();
        for (JottTree node : subnodes) {
            str.append(node.convertToJava());
        }
        return str.toString();
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
        StringBuilder str = new StringBuilder();
        for (JottTree node : subnodes) {
            str.append(node.convertToPython());
        }
        return str.toString();
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