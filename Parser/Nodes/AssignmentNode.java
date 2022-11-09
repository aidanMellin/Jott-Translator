package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Objects;

public class AssignmentNode implements JottTree { //TODO

    private final String JOTT_DOUBLE = "Double";
    private final String JOTT_INTEGER = "Integer";
    private final String JOTT_STRING = "String";
    private final String JOTT_BOOLEAN = "Boolean";
    private final String EQ_CHAR = "=";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private ArrayList<Token> tokens;
    private int tabCount;

    public AssignmentNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            assert this.tokens != null;
            Token last = this.tokens.get(this.tokens.size()-1);

            // Double <id> = <d_expr><end_statement>
            if (Objects.equals(this.tokens.get(0).getToken(), JOTT_DOUBLE)) {
                if (this.tokens.get(1).getTokenType().equals(TokenType.ID_KEYWORD)) {
                    subnodes.add(new IdNode(this.tokens.get(1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected <id>", this.tokens.get(1));
                }

                ArrayList<Token> d_expr = new ArrayList<>(this.tokens);
                System.err.println(d_expr);
                d_expr.remove(0); // Double
                d_expr.remove(0); // <id>
                if (d_expr.get(0).getToken().equals(EQ_CHAR)) {
                    d_expr.remove(0); // =
                } else {
                    CreateSyntaxError("Unexpected Token - Expected =", this.tokens.get(0));
                }

                d_expr.remove(d_expr.size() - 1); // <d_expr>
                if (d_expr.size() == 0) CreateSyntaxError("Expected <exp> got <end_stmt>", this.tokens.get(this.tokens.size()-1));

                subnodes.add(new DoubleExprNode(d_expr, 0));
                if (this.tokens.get(this.tokens.size() - 1).getTokenType().equals(TokenType.SEMICOLON)) {
                    subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size() - 1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected ;", this.tokens.get(this.tokens.size() - 1));
                }

            } // Integer <id> = <i_expr><end_statement>
            else if (Objects.equals(this.tokens.get(0).getToken(), JOTT_INTEGER)) {
                if (this.tokens.get(1).getTokenType().equals(TokenType.ID_KEYWORD)) {
                    subnodes.add(new IdNode(this.tokens.get(1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected <id>", this.tokens.get(1));
                }
                ArrayList<Token> i_expr = new ArrayList<>(this.tokens);
                i_expr.remove(0); // Integer
                i_expr.remove(0); // <id>
                if (i_expr.get(0).getToken().equals(EQ_CHAR)) {
                    i_expr.remove(0); // =
                } else {
                    CreateSyntaxError("Unexpected Token - Expected =", this.tokens.get(0));
                }
                i_expr.remove(i_expr.size() - 1);
                if (i_expr.size() == 0) CreateSyntaxError("Expected <exp> got <end_stmt>", this.tokens.get(this.tokens.size()-1));

                subnodes.add(new IntExprNode(i_expr, 0));
                if (this.tokens.get(this.tokens.size() - 1).getTokenType().equals(TokenType.SEMICOLON)) {
                    subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size() - 1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected ;", this.tokens.get(this.tokens.size() - 1));
                }
            } // Boolean <id> = <b_expr><end_statement>
            else if (Objects.equals(this.tokens.get(0).getToken(), JOTT_BOOLEAN)) {
                if (this.tokens.get(1).getTokenType().equals(TokenType.ID_KEYWORD)) {
                    subnodes.add(new IdNode(this.tokens.get(1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected <id>", this.tokens.get(1));
                }
                ArrayList<Token> b_expr = new ArrayList<>(this.tokens);
                b_expr.remove(0);
                b_expr.remove(0);
                if (b_expr.get(0).getToken().equals(EQ_CHAR)) {
                    b_expr.remove(0); // =
                } else {
                    CreateSyntaxError("Unexpected Token - Expected =", this.tokens.get(0));
                }
                b_expr.remove(b_expr.size() - 1);
                if (b_expr.size() == 0) CreateSyntaxError("Expected <exp> got <end_stmt>", this.tokens.get(this.tokens.size()-1));
                subnodes.add(new BoolExprNode(b_expr, tabCount));
                if (this.tokens.get(this.tokens.size() - 1).getTokenType().equals(TokenType.SEMICOLON)) {
                    subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size() - 1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected ;", this.tokens.get(this.tokens.size() - 1));
                }
            } // string <id> = <s_expr><end_statement>
            else if (Objects.equals(this.tokens.get(0).getToken(), JOTT_STRING)) {
                if (this.tokens.get(1).getTokenType().equals(TokenType.ID_KEYWORD)) {
                    subnodes.add(new IdNode(this.tokens.get(1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected <id>", this.tokens.get(1));
                }
                ArrayList<Token> s_expr = new ArrayList<>(this.tokens);
                s_expr.remove(0);
                s_expr.remove(0);
                if (s_expr.get(0).getToken().equals(EQ_CHAR)) {
                    s_expr.remove(0); // =
                } else {
                    CreateSyntaxError("Unexpected Token - Expected =", this.tokens.get(0));
                }
                s_expr.remove(s_expr.size() - 1);
                if (s_expr.size() == 0) CreateSyntaxError("Expected <exp> got <end_stmt>", this.tokens.get(this.tokens.size()-1));

                subnodes.add(new StrExprNode(s_expr, 0));
                if (this.tokens.get(this.tokens.size() - 1).getTokenType().equals(TokenType.SEMICOLON)) {
                    subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size() - 1), 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected ;", this.tokens.get(this.tokens.size() - 1));
                }
            } else if (Objects.equals(this.tokens.get(0).getTokenType(), TokenType.ID_KEYWORD)) {
                subnodes.add(new IdNode(this.tokens.get(0), 0)); // <id>
                this.tokens.remove(0);
                if (this.tokens.get(0).getToken().equals(EQ_CHAR)) {
                    this.tokens.remove(0); // =
                } else {
                    CreateSyntaxError("Unexpected Token - Expected =", this.tokens.get(0));
                }


                // <b_expr>
                boolean bExprBool = false;
                for (int i = 0; i < this.tokens.size(); i++) {
                    if (this.tokens.get(i).getTokenType().equals(TokenType.REL_OP)) {
                        bExprBool = true;
                        ArrayList<Token> b_expr = this.tokens;
                        b_expr.remove(b_expr.size() - 1);
                        subnodes.add(new BoolExprNode(b_expr, 0));
                        break;
                    }
                }

                // <s_expr>
                boolean sExprBool = false;
                if (!bExprBool) {

                    if (this.tokens.get(0).getTokenType().equals(TokenType.STRING)) {
                        sExprBool = true;
                        ArrayList<Token> s_expr = this.tokens;
                        s_expr.remove(s_expr.size() - 1);
                        subnodes.add(new StrExprNode(s_expr, 0));
                    }
                }

                boolean dExprBool = false;
                boolean iExprBool = false;
                if (!sExprBool && !bExprBool) {
                    for (int i = 0; i < this.tokens.size(); i++) {
                        if (this.tokens.get(i).getTokenType().equals(TokenType.NUMBER)) {
                            if (this.tokens.get(i).getToken().contains(".")) {
                                dExprBool = true;
                                ArrayList<Token> d_expr = this.tokens;
                                d_expr.remove(d_expr.size() - 1);
                                subnodes.add(new DoubleExprNode(d_expr, 0));
                            } else {
                                iExprBool = true;
                                ArrayList<Token> i_expr = this.tokens;
                                i_expr.remove(i_expr.size() - 1);
                                subnodes.add(new IntExprNode(i_expr, 0));
                            }
                            break;
                        }
                    }
                }

                // All other <id> and <func_call> = <id> = [params]
                // s_expr, b_expr, d_expr, and i_expr all have <id> and <func_call>
                // , for this phase <id> doesn't have any type. So I believe it's safe to simply throw them
                // in s_expr for now, but in phase 3 this will have to change.
                if (!bExprBool && !sExprBool && !iExprBool && !dExprBool) {
                    if (this.tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
                        ArrayList<Token> s_expr = this.tokens;
                        s_expr.remove(s_expr.size() - 1);
                        subnodes.add(new BoolExprNode(s_expr, 0));
                    } else {
                        CreateSyntaxError("Unexpected Token - Expected <Expression Type First>", this.tokens.get(0));
                    }
                }

                if (last.getTokenType().equals(TokenType.SEMICOLON)) {
                    subnodes.add(new EndStatementNode(last, 0));
                } else {
                    CreateSyntaxError("Unexpected Token - Expected ;", this.tokens.get(this.tokens.size() - 1));
                }
            } else {
                CreateSyntaxError("Unexpected Token - Expected <assignment>", this.tokens.get(0));
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
        StringBuilder jott_asmt = new StringBuilder();
        switch (tokens.get(0).getToken()) {
            case JOTT_DOUBLE -> jott_asmt.append(JOTT_DOUBLE + " ");
            case JOTT_BOOLEAN -> jott_asmt.append(JOTT_BOOLEAN + " ");
            case JOTT_INTEGER -> jott_asmt.append(JOTT_INTEGER + " ");
            case JOTT_STRING -> jott_asmt.append(JOTT_STRING + " ");
        }
        jott_asmt.append(subnodes.get(0).convertToJott()).append(" ");
        jott_asmt.append(EQ_CHAR + " ");
        jott_asmt.append(subnodes.get(1).convertToJott());
        jott_asmt.append(subnodes.get(2).convertToJott());
        return jott_asmt.toString();
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

    public void CreateSyntaxError(String msg, Token token) throws Exception {
        System.err.println("Syntax Error:\n" + msg + "\n" + token.getFilename() + ":" + token.getLineNum());
        throw new Exception();
    }
}