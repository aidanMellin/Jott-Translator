package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class BoolExprNode implements JottTree { //TODO

    private ArrayList<JottTree> subnodes;
    private ArrayList<Token> Tokens;

    public BoolExprNode(ArrayList<Token> tokens) {
        try {
            Tokens = tokens;
            subnodes = new ArrayList<>();

            ArrayList<JottTree> temp_subnodes = new ArrayList<>();
            ArrayList<Token> tokens_used = new ArrayList<>();
            int op_count = 0;

            if (this.tokens.size() == 1 && (this.tokens.get(0).getToken() == "True" || this.tokens.get(0).getToken() == "False")){
                subnodes.add(new BooleanNode(this.tokens.get(0)));
                return;
            }
            for (int i = 0; i < Tokens.size(); i++) {
                Token temp_token = Tokens.get(i);

                if (temp_token.getTokenType() == TokenType.NUMBER) {
                    int count = i;
                    boolean is_double = temp_token.getToken().contains(".");
                    ArrayList<Token> num_tokens = new ArrayList<>();
                    while (temp_token.getTokenType() != TokenType.REL_OP) {
                        num_tokens.add(temp_token);
                        count++;
                        temp_token = Tokens.get(count);
                    }
                    if (is_double) {
                        temp_subnodes.add(new DoubleExprNode(num_tokens));
                    } else {
                        temp_subnodes.add(new IntExprNode(num_tokens));
                    }
                    tokens_used.addAll(num_tokens);
                    i = count - 1;
                } else if (temp_token.getTokenType() == TokenType.MATH_OP) {
                    Token prev_token = null;
                    if (i > 0) {
                        prev_token = Tokens.get(Tokens.size() - 1);
                    }
                    boolean is_double = true;
                    boolean use_prev = true;
                    if (prev_token != null) {
                        if (prev_token.getTokenType() == TokenType.NUMBER) {
                            is_double = temp_token.getToken().contains(".");
                        } else if (prev_token.getTokenType() != TokenType.ID_KEYWORD) {
                            use_prev = false;
                            Token next = Tokens.get(i + 1);
                            if (next.getTokenType() == TokenType.NUMBER) {
                                is_double = temp_token.getToken().contains(".");
                            }
                        }
                    } else {
                        use_prev = false;
                        Token next = Tokens.get(i + 1);
                        if (next.getTokenType() == TokenType.NUMBER) {
                            is_double = temp_token.getToken().contains(".");
                        }
                    }
                    int count = i;
                    ArrayList<Token> num_tokens = new ArrayList<>();
                    while (temp_token.getTokenType() != TokenType.REL_OP) {
                        num_tokens.add(temp_token);
                        count++;
                        temp_token = Tokens.get(count);
                    }
                    ArrayList<Token> temp = new ArrayList<>();
                    if (use_prev) {
                        temp.add(prev_token);
                    }
                    temp.addAll(num_tokens);

                    if (is_double) {
                        temp_subnodes.add(new DoubleExprNode(temp));
                    } else {
                        temp_subnodes.add(new IntExprNode(temp));
                    }
                    i = count - 1;
                    tokens_used.addAll(num_tokens);
                } else if (temp_token.getTokenType() == TokenType.REL_OP) {
                    temp_subnodes.add(new RelOpNode(temp_token));
                    op_count++;
                    if (op_count > 1) {   //once a second rel op has happened, the program takes the tokens used so far and makes them into a seperate node
                        BoolExprNode temp_condense = new BoolExprNode(tokens_used);
                        subnodes.add(temp_condense);
                        subnodes.add(temp_subnodes.get(temp_subnodes.size() - 1));
                        temp_subnodes = new ArrayList<>();
                        op_count = 0;
                        tokens_used = new ArrayList<>();
                    }
                    if (op_count == 0) {
                        tokens_used.add(temp_token);
                    }
                } else if (temp_token.getTokenType() == TokenType.STRING) {
                    temp_subnodes.add(new StrExprNode(temp_token));
                    tokens_used.add(temp_token);
                } else if (temp_token.getTokenType() == TokenType.ID_KEYWORD) {
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
                        temp_subnodes.add(new FunctionCallNode(tokens_to_send));
                        i += count;
                        tokens_used.addAll(tokens_to_send);
                    } else {
                        temp_subnodes.add(new IdNode(temp_token));
                        tokens_used.add(temp_token);
                    }
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

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}