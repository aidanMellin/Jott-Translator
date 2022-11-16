package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExpressionNode implements JottTree{

    private JottTree subnode;
    private ArrayList<Token> tokens;
    private int tabCount;
    private boolean isVar = false;
    public String expr_type;
    private Token firstToken;

    public ExpressionNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            firstToken = this.tokens.get(0);
            this.subnode = null;

            // <b_expr>
            boolean bExprBool = false;
            for (int i = 0; i < this.tokens.size(); i++) {
                if (this.tokens.get(i).getTokenType().equals(TokenType.REL_OP)) {
                    bExprBool = true;
                    subnode = new BoolExprNode(this.tokens, 0);
                    expr_type = "Boolean";
                    break;
                }
            }

            // <s_expr>
            boolean sExprBool = false;
            if (!bExprBool) {

                if (this.tokens.get(0).getTokenType().equals(TokenType.STRING)) {
                    sExprBool = true;
                    if(this.tokens.size() == 1) {
                        Token strToken = this.tokens.get(0);
                        subnode = new StrExprNode(strToken, 0);
                    }
                    else {
                        subnode = new StrExprNode(this.tokens, 0);
                    }
                    expr_type = "String";

                }
            }

            // <d_expr>
            // <i_expr>
            boolean dExprBool = false;
            boolean iExprBool = false;
            if (!sExprBool && !bExprBool) {
                for (int i = 0; i < this.tokens.size(); i++) {
                    if (this.tokens.get(i).getTokenType().equals(TokenType.NUMBER)) {
                        if (this.tokens.get(i).getToken().contains(".")) {
                            dExprBool = true;
                            expr_type = "Double";
                            subnode = new DoubleExprNode(this.tokens, 0);
                        } else {
                            iExprBool = true;
                            expr_type = "Integer";
                            subnode = new IntExprNode(this.tokens, 0);
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
                    isVar = true;
                    if (this.tokens.size() == 1) {
                        subnode = new IdNode(this.tokens.get(0), 0);
                    } else {
                        subnode = new FunctionCallNode(this.tokens, 0);
                    }
                    if (symbolTable.containsKey(subnode.convertToJott())) expr_type = symbolTable.get(subnode.convertToJott()).ReturnType;
                } else {
                    CreateSyntaxError("Unexpected Token - Expected <Expression Type First>", this.tokens.get(0));
                }
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
        return(subnode.convertToJott());
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return subnode.convertToJava();
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
        return subnode.convertToPython();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        try {
            if (isVar)
                if (!symbolTable.containsKey(subnode.convertToJott()))
                    CreateSemanticError("Unrecognized or undeclared variable or function", firstToken);
            return subnode.validateTree();
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
