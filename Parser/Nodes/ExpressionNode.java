package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExpressionNode implements JottTree{

    private JottTree subnode;
    private ArrayList<Token> tokens;

    public ExpressionNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.subnode = null;

        // <b_expr>
        boolean bExprBool = false;
        for (int i = 0; i < this.tokens.size(); i++) {
            if (this.tokens.get(i).getTokenType().equals(TokenType.REL_OP)) {
                bExprBool = true;
                subnode = new BoolExprNode(this.tokens);
                break;
            }
        }

        // <s_expr>
        boolean sExprBool = false;
        if(!bExprBool){

            if(this.tokens.get(0).getTokenType().equals(TokenType.STRING)) {
                sExprBool = true;
                subnode = new StrExprNode(this.tokens);
            }
        }

        // <d_expr>
        // <i_expr>
        boolean dExprBool = false;
        boolean iExprBool = false;
        if(!sExprBool && !bExprBool) {
            for(int i = 0; i < this.tokens.size(); i++) {
                if(this.tokens.get(i).getTokenType().equals(TokenType.NUMBER)){
                    if( this.tokens.get(i).getToken().contains(".")){
                        dExprBool = true;
                        subnode = new DoubleExprNode(this.tokens);
                    }
                    else {
                        iExprBool = true;
                        subnode = new IntExprNode(this.tokens);
                    }
                    break;
                }
            }
        }

        // All other <id> and <func_call> = <id> = [params]
        // s_expr, b_expr, d_expr, and i_expr all have <id> and <func_call>
        // , for this phase <id> doesn't have any type. So I believe it's safe to simply throw them
        // in s_expr for now, but in phase 3 this will have to change.
        if(!bExprBool && !sExprBool && !iExprBool && !dExprBool) {
            if(this.tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)){
                if(this.tokens.size() == 1){
                    subnode = new IdNode(this.tokens.get(0));
                }
                else {
                    subnode = new FunctionCallNode(this.tokens);
                }
            }
            else {
                CreateSyntaxError("Unexpected Token - Expected <Expression Type First>", this.tokens.get(0));
            }
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
