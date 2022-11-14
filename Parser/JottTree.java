/**
 * Interface for all Jott parse tree nodes
 *
 * @author Scott C Johnson
 */

package Parser;

import java.util.ArrayList;

import Tokenizer.Token;

public interface JottTree {

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public static String convertToJott(ArrayList<Token> jottTokens){
        String convertedText = "";
        return convertedText;
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public static String convertToJava(ArrayList<Token> jottTokens){
        String convertedText = "";
        return convertedText;
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public static String convertToC(ArrayList<Token> jottTokens){
        String convertedText = "";
        return convertedText;
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public static String convertToPython(ArrayList<Token> jottTokens){
        String convertedText = "";
        return convertedText;
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
	 * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public static boolean validateTree(ArrayList<Token> jottTokens){
        return false;
    }

    public void CreateSyntaxError(String msg, Token token) throws Exception;
}
