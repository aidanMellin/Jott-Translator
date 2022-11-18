/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

package Parser;
import Tokenizer.*;
import Parser.Nodes.*;

import java.util.ArrayList;
import java.util.Hashtable;

public class JottParser {

    /**
     * Parses an ArrayList of Jott tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens, String fileName){
        try {
            Hashtable<String, SymbolData> symbolTable = new Hashtable<>();
            return new ProgramNode(tokens, 0, fileName, symbolTable);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validate(JottTree jottTree) {
        try {
            return jottTree.validateTree();
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String fileName;
        String textFileName = "largerValid.jott";
        String[] fileNameTokens = textFileName.split("\\.");
        fileName = fileNameTokens[0];
        ArrayList<Token> tokens = JottTokenizer.tokenize("Parser/phase3TestCases/largerValid.jott");
        // ArrayList<Token> tokens = JottTokenizer.tokenize("Parser/phase2Tester/parserTestCases/validLoop.jott");

        JottTree jottTree = parse(tokens, fileName);
        String output = (jottTree != null && validate(jottTree)) ? jottTree.convertToPython() : "error resulting in null";
        System.out.println(output);
    }
}
