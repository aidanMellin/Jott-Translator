package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class FunctionDefinitionNode implements JottTree {

    private final String RBRACKET_CHAR = "]";
    private final String LBRACKET_CHAR = "[";
    private final String RBRACE_CHAR = "}";
    private final String LBRACE_CHAR = "{";
    private final String RPARAN_CHAR = ")";
    private final String LPARAN_CHAR = "(";
    private final String JAVA_METHOD_STR = "public static ";
    private final String COLON_CHAR = ":";
    private final String INT_STR = "int";
    private final String VOID_STR = "void";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private final ArrayList<Token> tokens;
    private int tabCount;
    private boolean funcExists = false;
    private Token firstToken;
    Hashtable<String, SymbolData> symbolTable;

    public FunctionDefinitionNode(ArrayList<Token> tokens, int tc, Hashtable<String, SymbolData> symbolTable) {
        try {
            tabCount = tc;
            this.tokens = tokens;
            firstToken = this.tokens.get(0);
            if (this.tokens.get(0).getTokenType() != TokenType.ID_KEYWORD)
                CreateSyntaxError("Unexpected Token - Expected ID", this.tokens.get(0));
            subnodes.add(new IdNode(this.tokens.remove(0), tabCount, symbolTable));
            if (symbolTable.containsKey(subnodes.get(0).convertToJott()) && symbolTable.get(subnodes.get(0).convertToJott()).IsFunction) funcExists = true;
            else symbolTable.put(subnodes.get(0).convertToJott(), new SymbolData(
                    subnodes.get(0).convertToJott(),
                    null,
                    (subnodes.get(0).convertToJott().equals("main")),
                    true,
                    true,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    1
            ));
            this.symbolTable = new Hashtable<>(symbolTable);
            String[] keys = this.symbolTable.keySet().toArray(new String[0]);
            for (String key : keys) {
                if (!this.symbolTable.get(key).IsFunction) {
                    this.symbolTable.remove(key);
                }
            }
            if (this.tokens.get(0).getTokenType() != TokenType.L_BRACKET)
                CreateSyntaxError("Unexpected Token - Expected '['", this.tokens.get(0));
            this.tokens.remove(0);
            ArrayList<Token> func_def_params = new ArrayList<>();
            while (this.tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
                func_def_params.add(this.tokens.remove(0));
                assert this.tokens.size() != 0;
            }

            subnodes.add(new FunctionDefinitionParametersNode(func_def_params, tabCount, subnodes.get(0).convertToJott(), this.symbolTable));
            if (this.tokens.get(0).getTokenType() != TokenType.R_BRACKET)
                CreateSyntaxError("Unexpected Token - Expected ']'", this.tokens.get(0));
            this.tokens.remove(0);
            if (this.tokens.get(0).getTokenType() != TokenType.COLON)
                CreateSyntaxError("Unexpected Token - Expected ':'", this.tokens.get(0));
            this.tokens.remove(0);
            if (this.tokens.get(0).getTokenType() != TokenType.ID_KEYWORD)
                CreateSyntaxError("Unexpected Token - Expected ID", this.tokens.get(0));
            subnodes.add(new FunctionReturnNode(this.tokens.remove(0), tabCount, this.symbolTable));

            this.symbolTable.get(subnodes.get(0).convertToJott()).ReturnType = subnodes.get(2).convertToJott();

            if (this.tokens.get(0).getTokenType() != TokenType.L_BRACE)
                CreateSyntaxError("Unexpected Token - Expected '{'", this.tokens.get(0));
            this.tokens.remove(0);
            ArrayList<Token> body = new ArrayList<>();
            int b_count = 1;
            while (b_count != 0) {
                if (this.tokens.get(0).getTokenType() == TokenType.L_BRACE) b_count++;
                body.add(this.tokens.remove(0));
                if (this.tokens.size() == 0) CreateSyntaxError("Error: empty token array", body.get(body.size() - 1));
                if (this.tokens.get(0).getTokenType() == TokenType.R_BRACE) b_count--;
            }
            subnodes.add(new BodyNode(body, tabCount + 1, this.symbolTable));
            if (this.tokens.get(0).getTokenType() != TokenType.R_BRACE)
                CreateSyntaxError("Unexpected Token - Expected '}'", this.tokens.get(0));
            this.tokens.remove(0);
            if (this.tokens.size() != 0) CreateSyntaxError("Unexpected Tokens", this.tokens.get(0));

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
        //x[]:y{ stuff }
        return  "\t".repeat(tabCount) + subnodes.get(0).convertToJott() + LBRACKET_CHAR + subnodes.get(1).convertToJott() + RBRACKET_CHAR + COLON_CHAR +
                subnodes.get(2).convertToJott() + LBRACE_CHAR + "\n" +
                "\t".repeat(tabCount) + subnodes.get(3).convertToJott() +
                "\t".repeat(tabCount) + RBRACE_CHAR +
                "\n" + "\n";
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return "\t".repeat(tabCount) + JAVA_METHOD_STR + subnodes.get(2).convertToJava() + " " +
                subnodes.get(0).convertToJava() +
                LPARAN_CHAR + subnodes.get(1).convertToJava() + RPARAN_CHAR + LBRACE_CHAR + "\n" +
                subnodes.get(3).convertToJava()  +
                "\t".repeat(tabCount)+ RBRACE_CHAR +
                "\n" + "\n";
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        if(subnodes.get(0).convertToC().equals("main")){
            return  INT_STR+ " " + subnodes.get(0).convertToC() +
                    LPARAN_CHAR + VOID_STR + RPARAN_CHAR + LBRACE_CHAR + "\n" +
                    subnodes.get(3).convertToC()  +
                    "\t".repeat(tabCount)+ RBRACE_CHAR +
                    "\n" + "\n";
        }
        return  subnodes.get(2).convertToC() + " " + subnodes.get(0).convertToC() +
                LPARAN_CHAR + subnodes.get(1).convertToC() + RPARAN_CHAR + LBRACE_CHAR + "\n" +
                subnodes.get(3).convertToC()  +
                "\t".repeat(tabCount)+ RBRACE_CHAR +
                "\n" + "\n";
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return "\t".repeat(tabCount) + "def " + subnodes.get(0).convertToPython() + "(" + subnodes.get(1).convertToPython() + "):\n" +
                subnodes.get(3).convertToPython() + "\n";
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        try {
            if (keywords.contains(subnodes.get(0).convertToJott()))
                CreateSemanticError("Cannot use a keyword as a function", firstToken);
            if (subnodes.get(0).convertToJott().equals("main")) {
                if (!symbolTable.get("main").ReturnType.equals("Void"))
                    CreateSemanticError("Main function is not type Void", firstToken);
                else if (symbolTable.get("main").Params.size() != 0)
                    CreateSemanticError("Main Function does not accept any parameters", firstToken);
                else if (funcExists)
                    CreateSemanticError("Function already exists", firstToken);
            } else {
                if (funcExists)
                    CreateSemanticError("Function already exists", firstToken);
            }
            return subnodes.get(0).validateTree() &&
                    subnodes.get(1).validateTree() &&
                    subnodes.get(2).validateTree() &&
                    subnodes.get(3).validateTree();
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
