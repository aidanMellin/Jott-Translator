package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class FunctionDefinitionParametersTNode implements JottTree {

    private final String COMMA_CHAR = ",";
    private final String COLON_CHAR = ":";
    private final String EMPTY_STRING = "";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;

    public FunctionDefinitionParametersTNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            if (this.tokens.size() == 0) subnodes = null;
            else {
                if (this.tokens.get(0).getTokenType() != TokenType.COMMA)
                    CreateSyntaxError("Unexpected Token - Expected ','", this.tokens.get(0));
                this.tokens.remove(0);
                if (this.tokens.get(0).getTokenType() != TokenType.ID_KEYWORD)
                    CreateSyntaxError("Unexpected Token - Expected ID", this.tokens.get(0));
                subnodes.add(new IdNode(this.tokens.remove(0), tabCount));
                if (this.tokens.get(0).getTokenType() != TokenType.COLON)
                    CreateSyntaxError("Unexpected Token - Expected ':'", this.tokens.get(0));
                this.tokens.remove(0);
                if (this.tokens.get(0).getTokenType() != TokenType.ID_KEYWORD)
                    CreateSyntaxError("Unexpected Token - Expected ID", this.tokens.get(0));
                subnodes.add(new TypeNode(this.tokens.remove(0), tabCount));
                subnodes.add(new FunctionDefinitionParametersTNode(this.tokens, tabCount));
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
        if (subnodes == null) return EMPTY_STRING;
        return COMMA_CHAR +
                subnodes.get(0).convertToJott() +
                COLON_CHAR +
                subnodes.get(1).convertToJott() +
                subnodes.get(2).convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        if (subnodes == null) return EMPTY_STRING;
        return ", " + subnodes.get(0).convertToJava() + subnodes.get(2).convertToJava();
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
        if (subnodes == null) return EMPTY_STRING;
        return ", " + subnodes.get(0).convertToPython() + subnodes.get(2).convertToPython();
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
