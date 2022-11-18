package Parser.Nodes;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class ProgramNode implements JottTree { //TODO

    private final String C_LIBRARIES = "#include <stdio.h>\n#include <stdlib.h>\n#include <string.h>";
    private final String JAVA_CLASS = "public class ";
    private final String LBRACE_CHAR = "{";
    private final String RBRACE_CHAR = "}";
    private String FILE_NAME;
    private final JottTree function_list;
    private int tabCount;
    private Token firstToken;
    Hashtable<String, SymbolData> symbolTable;


    public ProgramNode(ArrayList<Token> tokens, int tc, String fileName, Hashtable<String, SymbolData> symbolTable){
        try {
            this.FILE_NAME = fileName;
            tabCount = tc;
            firstToken = tokens.get(0);
            symbolTable.put("print", new SymbolData(
                    "print",
                    "Void",
                    false,
                    true,
                    true,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    1
            ));
            symbolTable.get("print").Params.add("expr");
            symbolTable.put("input", new SymbolData(
                    "input",
                    "String",
                    false,
                    true,
                    true,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    1
            ));
            symbolTable.get("input").Params.add("msg");
            symbolTable.get("input").Params.add("size");
            symbolTable.get("input").ParamsTypes.add("String");
            symbolTable.get("input").ParamsTypes.add("Integer");
            symbolTable.put("concat", new SymbolData(
                    "concat",
                    "String",
                    false,
                    true,
                    true,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    1
            ));
            symbolTable.get("concat").Params.add("str1");
            symbolTable.get("concat").Params.add("str2");
            symbolTable.get("concat").ParamsTypes.add("String");
            symbolTable.get("concat").ParamsTypes.add("String");
            symbolTable.put("length", new SymbolData(
                    "length",
                    "Integer",
                    false,
                    true,
                    true,
                    new ArrayList<>(),
                    new ArrayList<>(),
                    1
            ));
            symbolTable.get("length").Params.add("str");
            symbolTable.get("length").ParamsTypes.add("String");
            function_list = new FunctionListNode(tokens, tabCount, symbolTable);
            this.symbolTable = symbolTable;

            // also add EOF symbol??
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
        return function_list.convertToJott();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        while(FILE_NAME.contains("/")){
            FILE_NAME = FILE_NAME.substring(FILE_NAME.indexOf("/") + 1);
        }

        return JAVA_CLASS + FILE_NAME + LBRACE_CHAR + "\n" + function_list.convertToJava() + RBRACE_CHAR;
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        return C_LIBRARIES + "\n\n" + function_list.convertToC() + "\n" + 
        "char* concat(char* str1, char* str2){\n\tchar str3[100];\n\tint i = 0, j = 0;\n\twhile (str1[i] != '\0') {\n\t\tstr3[j] = str1[i];\n\t\ti++;\n\t\tj++;\n\t}\n\ti = 0;\n\twhile (str2[i] != '\0') {\n\t\tstr3[j] = str2[i];\n\t\ti++;\n\t\tj++;\n\t}\n\tstr3[j] = '\0';\n\treturn str3;\n}";
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return function_list.convertToPython() + "main()";
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
	 * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        try {
            if (!symbolTable.containsKey("main"))
                CreateSemanticError("Missing main function from program", firstToken);
            if (!symbolTable.get("main").IsMain)
                CreateSemanticError("Missing main function from program", firstToken);
            return function_list.validateTree();
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