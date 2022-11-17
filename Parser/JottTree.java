/**
 * Interface for all Jott parse tree nodes
 *
 * @author Scott C Johnson
 */

package Parser;

import Tokenizer.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

public interface JottTree {

    ArrayList<String> keywords = new ArrayList<>(Arrays.asList("return", "while", "if", "elseif", "else", "Double", "String", "Boolean", "Void", "Integer"));

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    String convertToJott();

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    String convertToJava();

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    String convertToC();

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    String convertToPython();

    /**
     * This will validate that the tree follows the semantic rules of Jott
	 * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    boolean validateTree();

    void CreateSyntaxError(String msg, Token token) throws Exception;

    void CreateSemanticError(String msg, Token token) throws Exception;
}
