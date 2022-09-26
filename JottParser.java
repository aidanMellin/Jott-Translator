/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;

public class JottParser {

    /**
     * Parses an ArrayList of Jott tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     */
    public static JottTree parse(ArrayList<Token> tokens){
        try {
            return new ProgramNode(tokens);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        ArrayList<Token> tokens = JottTokenizer.tokenize("phase1_tester/tokenizerTestCases/strings.jott");
        JottTree jottTree = parse(tokens);
        String output = (jottTree != null) ? jottTree.convertToJott() : "error resulting in null";
        System.out.println(output);
    }
}
