package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class FunctionCallNode implements JottTree{

    private final String RBRACKET_STRING = "]";
    private final String LBRACKET_STRING = "[";
    private final String RPARAN_STRING = ")";
    private final String LPARAN_STRING = "(";
    private final ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;
    private Token firstToken;
    Hashtable<String, SymbolData> symbolTable;

    public FunctionCallNode(ArrayList<Token> tokens, int tc, Hashtable<String, SymbolData> symbolTable) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            firstToken = this.tokens.get(0);
            assert this.tokens != null;
            if (this.tokens.size() < 3) CreateSyntaxError("Invalid Function Call", this.tokens.get(0));
            if (!this.tokens.get(0).getToken().matches("[a-z][a-zA-z0-9]*"))
                CreateSyntaxError("Invalid Function Name", this.tokens.get(0));
            subnodes.add(new IdNode(this.tokens.get(0), tabCount, symbolTable));
            ArrayList<Token> paramsTokens = new ArrayList<>();
            if (this.tokens.get(1).getTokenType() != TokenType.L_BRACKET)
                CreateSyntaxError("Unexpected Token - Expected '['", this.tokens.get(1));
            if (this.tokens.get(this.tokens.size() - 1).getTokenType() != TokenType.R_BRACKET)
                CreateSyntaxError("Unexpected Token - Expected ']'", this.tokens.get(1));
            for (int i = 2; i < this.tokens.size() - 1; i++) paramsTokens.add(this.tokens.get(i));
            subnodes.add(new ParametersNode(paramsTokens, tabCount, subnodes.get(0).convertToJott(), symbolTable, firstToken));
            this.symbolTable = symbolTable;
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
        return subnodes.get(0).convertToJott() +
                LBRACKET_STRING +
                subnodes.get(1).convertToJott() +
                RBRACKET_STRING;
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return subnodes.get(0).convertToJava() +
                LPARAN_STRING +
                subnodes.get(1).convertToJava() +
                RPARAN_STRING;
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        if(subnodes.get(0).convertToC().equals("printf")) {
            String formatType = "";
            if(symbolTable.containsKey(subnodes.get(1).convertToJott())){
                switch (symbolTable.get(subnodes.get(1).convertToJott()).ReturnType) {
                    case "Integer" -> formatType = "%d";
                    case "Double" -> formatType = "%f";
                    case "Boolean" -> formatType = "%d";
                    case "String" -> formatType = "%s";
                }
            }
            else if(subnodes.get(1).convertToJott().contains("\"")) {
                formatType = "%s";
            }

            return subnodes.get(0).convertToC() +
                    LPARAN_STRING +
                    "\""+ formatType +"\\n\", " +
                    subnodes.get(1).convertToC() +
                    RPARAN_STRING;
        }

        return subnodes.get(0).convertToC() +
                LPARAN_STRING +
                subnodes.get(1).convertToC() +
                RPARAN_STRING;
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        if (subnodes.get(0).convertToJott().equals("concat")) {
            return subnodes.get(1).convertToPython();
        }
        return subnodes.get(0).convertToPython() +
                LPARAN_STRING +
                subnodes.get(1).convertToPython() +
                RPARAN_STRING;
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        try {
            if (!symbolTable.containsKey(subnodes.get(0).convertToJott()))
                CreateSemanticError("Unrecognized function call", firstToken);
            else if (!symbolTable.get(subnodes.get(0).convertToJott()).IsFunction)
                CreateSemanticError("Cannot call a function that is a variable", firstToken);
            return subnodes.get(0).validateTree() &&
                    subnodes.get(1).validateTree();
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
