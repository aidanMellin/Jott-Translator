/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
  public static ArrayList<Token> tokenize(String filename){
      ArrayList<Token> tokens = new ArrayList<>();
      try {
          File file = new File(filename);
          Scanner scanner = new Scanner(file);
          int lineCount = 1;
          while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              System.out.println(line);
              for (int i=0; i<line.length(); i++) {
                  switch (line.charAt(i)) {
                      case '[' -> tokens.add(makeNewToken(filename, lineCount, "[", TokenType.L_BRACKET));
                      case ']' -> tokens.add(makeNewToken(filename, lineCount, "[", TokenType.R_BRACKET));
                      case ',' -> tokens.add(makeNewToken(filename, lineCount, ",", TokenType.COMMA));
                      case '{' -> tokens.add(makeNewToken(filename, lineCount, "{", TokenType.L_BRACE));
                      case '}' -> tokens.add(makeNewToken(filename, lineCount, "}", TokenType.R_BRACE));
                      case '+','-','*','/' -> tokens.add(makeNewToken(filename, lineCount, String.valueOf(line.charAt(i)), TokenType.MATH_OP));
                      case ';' -> tokens.add(makeNewToken(filename, lineCount, ";", TokenType.SEMICOLON));
                      case ':' -> tokens.add(makeNewToken(filename, lineCount, ":", TokenType.COLON));
                      default -> {tokens.add(makeNewToken(filename, lineCount, String.valueOf(line.charAt(i)), TokenType.ERROR));
                      }
                  }
              }
              lineCount++;
          }
          System.out.println(tokens);
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      /**
       * Will need line number from here
       */
		return null;
	}

  /**
   * 
   * @param filename
   * @param lineNumber
   * @param body
   * @return Token
   */
  public static Token makeNewToken(String filename, int lineNumber, String body, TokenType type){
    Token newToken = new Token(body, filename, lineNumber, type);
    return newToken;
  }


  public static void main(String[] args) {
    /**
     * Need to have the filename assigned
     */
    tokenize("phase1_tester/tokenizerTestCases/mathOpsTest.jott");
  }

}