package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class DoubleExprNode implements JottTree {

    private final String JOTT_INTEGER = "Integer";
    private final String JOTT_DOUBLE = "Double";
    private final String JOTT_STRING = "String";
    private final String JOTT_BOOLEAN = "Boolean";

    private ArrayList<JottTree> subnodes;
    private ArrayList<Token> Tokens;
    private int tabCount;
    Hashtable<String, SymbolData> symbolTable;
    private String expr_type;

    public DoubleExprNode(ArrayList<Token> tokens, int tc, Hashtable<String, SymbolData> symbolTable) {
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
                        temp_subnodes.add(new FunctionCallNode(tokens_to_send, 0, symbolTable));
                        i += count;
                        expr_type = "func_call";
                    } else {
                        temp_subnodes.add(new IdNode(temp_token, 0, symbolTable));
                        expr_type = "id";
                    }
                } else if (temp_token.getTokenType() == TokenType.NUMBER) {
                    ArrayList<Token> temp_token_list = new ArrayList<>();
                    temp_token_list.add(temp_token);
                    temp_subnodes.add(new DoubleNode(temp_token_list, 0, symbolTable));
                    expr_type = "dbl";
                } else if (temp_token.getTokenType() == TokenType.MATH_OP) {
                    if (temp_token.getToken().equals("-")) {
                        if (i == 0 || (Tokens.get(i - 1).getTokenType() != TokenType.NUMBER && Tokens.get(i - 1).getTokenType() != TokenType.ID_KEYWORD)) {
                            ArrayList<Token> temp_token_list = new ArrayList<>();
                            temp_token_list.add(temp_token);
                            if (Tokens.get(i + 1).getTokenType() != TokenType.NUMBER) {
                                System.out.println("ERROR: Invalid expression, negative sign was not followed by a number.");
                                return;
                            } else {
                                temp_token_list.add(Tokens.get(i + 1));
                                temp_subnodes.add(new DoubleNode(temp_token_list, 0, symbolTable));
                                expr_type = "dbl";
                                i++;
                            }
                        } else {
                            temp_subnodes.add(new OpNode(temp_token, 0, symbolTable));
                            op_count++;
                        }
                    } else {
                        temp_subnodes.add(new OpNode(temp_token, 0, symbolTable));
                        op_count++;
                    }
                    if (op_count > 1) {   //once a second math op has happened, the program takes the tokens used so far and makes them into a seperate node
                        DoubleExprNode temp_condense = new DoubleExprNode(tokens_used, 0, symbolTable);
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
            this.symbolTable = symbolTable;
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
        String C_Code = "";
        for(JottTree node: subnodes){
            C_Code += node.convertToC();
        }
        return C_Code;
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
        try {
            if (subnodes.size() == 1) {
                switch (expr_type) {
                    case "dbl":
                        break;
                    case "func_call":
                        if (!symbolTable.containsKey(this.Tokens.get(0).getToken()) ||
                                !symbolTable.get(this.Tokens.get(0).getToken()).ReturnType.equals("Double") ||
                                !symbolTable.get(this.Tokens.get(0).getToken()).IsFunction)
                            CreateSemanticError("Mis-match typing in double expression: invalid function use", this.Tokens.get(0));
                        break;
                    case "id":
                        if (!symbolTable.containsKey(this.Tokens.get(0).getToken()) ||
                                !symbolTable.get(this.Tokens.get(0).getToken()).ReturnType.equals("Boolean") ||
                                symbolTable.get(this.Tokens.get(0).getToken()).IsFunction)
                            CreateSemanticError("Mis-match typing in double expression: invalid variable use", this.Tokens.get(0));
                    default:
                        CreateSemanticError("Error in validating double expr", Tokens.get(0));
                        break;
                }
                return subnodes.get(0).validateTree();
            } else {
                /*if (subnodes.get(0).getClass() != subnodes.get(2).getClass() ||
                        subnodes.get(0).getClass() != this.getClass() ||
                        subnodes.get(2).getClass() != this.getClass())
                    CreateSemanticError("Mis-matching types in expression", Tokens.get(0));*/
                boolean param1Symbol = false;
                boolean param2Symbol = false;

                if (symbolTable.containsKey(subnodes.get(0).convertToJott())) {
                    param1Symbol = true;
                }
                if (symbolTable.containsKey(subnodes.get(2).convertToJott())) {
                    param2Symbol = true;
                }
                if(param1Symbol && param2Symbol){
                    if (symbolTable.get(subnodes.get(0).convertToJott()).ReturnType != symbolTable.get(subnodes.get(0).convertToJott()).ReturnType) {
                        CreateSemanticError("Mis-matching types in expression", Tokens.get(0));
                    }
                }
                else if(!param1Symbol && param2Symbol){
                    String param1ReturnType = "";
                    switch (subnodes.get(0).getClass().toString()) {
                        case "class Parser.Nodes.IntNode": param1ReturnType = JOTT_INTEGER;
                            break;
                        case "class Parser.Nodes.DoubleNode": param1ReturnType = JOTT_DOUBLE;
                            break;
                        case "class Parser.Nodes.BoolNode": param1ReturnType = JOTT_BOOLEAN;
                            break;
                        case "class Parser.Nodes.StrNode": param1ReturnType = JOTT_STRING;
                            break;
                    }
                    if (!symbolTable.get(subnodes.get(2).convertToJott()).ReturnType.equals(param1ReturnType) ) {
                        CreateSemanticError("Mis-matching types in expression", Tokens.get(0));
                    }

                }
                else if(param1Symbol && !param2Symbol){
                    String param2ReturnType = "";
                    System.out.println(subnodes.get(2).getClass().toString());
                    switch (subnodes.get(2).getClass().toString()) {
                        case "class Parser.Nodes.IntNode": param2ReturnType = JOTT_INTEGER;
                            break;
                        case "class Parser.Nodes.DoubleNode": param2ReturnType = JOTT_DOUBLE;
                            break;
                        case "class Parser.Nodes.BoolNode": param2ReturnType = JOTT_BOOLEAN;
                            break;
                        case "class Parser.Nodes.StrNode": param2ReturnType = JOTT_STRING;
                            break;
                    }
                    if (!symbolTable.get(subnodes.get(0).convertToJott()).ReturnType.equals(param2ReturnType)) {
                        CreateSemanticError("Mis-matching types in expression", Tokens.get(0));
                    }
                }
                else {
                    if (subnodes.get(0).getClass() != subnodes.get(2).getClass()) {
                        CreateSemanticError("Mis-matching types in expression", Tokens.get(0));
                    }
                }
                return subnodes.get(0).validateTree() &&
                        subnodes.get(1).validateTree() &&
                        subnodes.get(2).validateTree();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception{
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }

    public void CreateSemanticError(String msg, Token token) throws Exception {
        System.err.println("Semantic Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}