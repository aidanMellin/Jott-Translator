package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class FunctionDefinitionParametersTNode implements JottTree {

    private final String COMMA_CHAR = ",";
    private final String COLON_CHAR = ":";
    private final String EMPTY_STRING = "";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;
    private Token firstToken;
    Hashtable<String, SymbolData> symbolTable;

    public FunctionDefinitionParametersTNode(ArrayList<Token> tokens, int tc, String func, Hashtable<String, SymbolData> symbolTable) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            if (this.tokens.size() == 0) subnodes = null;
            else {
                firstToken = this.tokens.get(0);
                if (this.tokens.get(0).getTokenType() != TokenType.COMMA)
                    CreateSyntaxError("Unexpected Token - Expected ','", this.tokens.get(0));
                this.tokens.remove(0);
                if (this.tokens.get(0).getTokenType() != TokenType.ID_KEYWORD)
                    CreateSyntaxError("Unexpected Token - Expected ID", this.tokens.get(0));
                subnodes.add(new IdNode(this.tokens.remove(0), tabCount, symbolTable));
                symbolTable.get(func).Params.add(subnodes.get(0).convertToJott());

                if (this.tokens.get(0).getTokenType() != TokenType.COLON)
                    CreateSyntaxError("Unexpected Token - Expected ':'", this.tokens.get(0));
                this.tokens.remove(0);
                if (this.tokens.get(0).getTokenType() != TokenType.ID_KEYWORD)
                    CreateSyntaxError("Unexpected Token - Expected ID", this.tokens.get(0));
                subnodes.add(new TypeNode(this.tokens.remove(0), tabCount, symbolTable));
                symbolTable.get(func).ParamsTypes.add(subnodes.get(0).convertToJott());

                subnodes.add(new FunctionDefinitionParametersTNode(this.tokens, tabCount, func, symbolTable));
                symbolTable.put(subnodes.get(0).convertToJott(), new SymbolData(
                        subnodes.get(0).convertToJott(),
                        subnodes.get(1).convertToJott(),
                        false,
                        true,
                        false,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        1
                ));
            }
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
        if (subnodes == null) return EMPTY_STRING;
        return ", " + subnodes.get(0).convertToC() + subnodes.get(2).convertToC();
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
        try {
            if (subnodes == null) return true;
            else if (symbolTable.containsKey(subnodes.get(0).convertToJott()))
                CreateSemanticError("Variable is already declared in program", firstToken);
            return subnodes.get(0).validateTree() &&
                    subnodes.get(1).validateTree() &&
                    subnodes.get(2).validateTree();
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
