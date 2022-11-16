package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;

public class VariableDeclarationNode implements JottTree{

    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;
    private Token firstToken;
    
    public VariableDeclarationNode(ArrayList<Token> tokens, int tc) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            assert this.tokens != null;
            firstToken = this.tokens.get(0);
            if (this.tokens.size() != 3) CreateSyntaxError("Invalid Variable Declaration", this.tokens.get(0));
            subnodes.add(new TypeNode(this.tokens.get(0), tabCount));
            subnodes.add(new IdNode(this.tokens.get(1), tabCount));
            subnodes.add(new EndStatementNode(this.tokens.get(2), tabCount));
            symbolTable.put(subnodes.get(1).convertToJott(), new SymbolData(
                    subnodes.get(1).convertToJott(),
                    subnodes.get(0).convertToJott(),
                    false,
                    false,
                    false,
                    null,
                    null,
                    1)
            );
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
        StringBuilder jott_var_dec = new StringBuilder();
        for (JottTree node : subnodes) jott_var_dec.append(node.convertToJott());
        return "\t".repeat(tabCount) + jott_var_dec;
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return subnodes.get(1).convertToJava();
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
        return subnodes.get(1).convertToPython();
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        try {
            if (symbolTable.containsKey(subnodes.get(1).convertToJott()) && !symbolTable.get(subnodes.get(1).convertToJott()).IsFunction)
                CreateSemanticError("Variable has already been declared", firstToken);
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
