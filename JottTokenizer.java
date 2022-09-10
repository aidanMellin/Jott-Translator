/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

// import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
          int lineNumber = 1;
          while (scanner.hasNextLine()) {
              String line = scanner.nextLine();
              // System.out.println(line);
              for (int i=0; i<line.length(); i++) {
                
                switch (line.charAt(i)) {
                    case '#' -> {continue;}
                    case ',' -> tokens.add(makeNewToken(filename, lineNumber, ",", TokenType.COMMA));
                    case ']' -> tokens.add(makeNewToken(filename, lineNumber, "[", TokenType.R_BRACKET));
                    case '[' -> tokens.add(makeNewToken(filename, lineNumber, "[", TokenType.L_BRACKET));
                    case '}' -> tokens.add(makeNewToken(filename, lineNumber, "}", TokenType.R_BRACE));
                    case '{' -> tokens.add(makeNewToken(filename, lineNumber, "{", TokenType.L_BRACE));
                    case '=' -> {
                      //Conditional: if no other = -> ASSIGN, if == -> relOp. Else -> Error
                      char returned = checkEquals(line.charAt(i+1));
                      if (returned == 'a'){
                        tokens.add(makeNewToken(filename, lineNumber, "=", TokenType.ASSIGN));
                      } else if (returned == 'r'){
                        tokens.add(makeNewToken(filename, lineNumber, "==", TokenType.REL_OP));
                        i+=1;
                      } else{
                        tokens.add(makeError(filename, lineNumber, line, i));
                      }
                    } 
                    case '<' -> tokens.add(makeNewToken(filename, lineNumber, "<", TokenType.REL_OP));
                    case '>' -> tokens.add(makeNewToken(filename, lineNumber, ">", TokenType.REL_OP));
                    case '+','-','*','/' -> tokens.add(makeNewToken(filename, lineNumber, String.valueOf(line.charAt(i)), TokenType.MATH_OP));
                    case ';' -> tokens.add(makeNewToken(filename, lineNumber, ";", TokenType.SEMICOLON));
                    case '.' -> {
                      List<Object> cycled = cycleDigit(line, i); //Get all following digits. 
                      String returned = (String) cycled.get(0);
                      i = (int) cycled.get(1); // Update the index value (should be first non-digit value)
                      Token toAdd = (returned == "**ERROR**") ? makeError(filename, lineNumber, line, i) : makeNewToken(filename, lineNumber, returned, TokenType.NUMBER); //Ternary operator to check if returned string is an Error
                      tokens.add(toAdd);
                    }
                    case '1','2','3','4','5','6','7','8','9','0' -> {
                      List<Object> cycled = cycleDigit(line, i);
                      String returned = (String) cycled.get(0);
                      i = (int) cycled.get(1);

                      if (returned == "**ERROR**"){ // If its an error, just move on THIS MIGHT NEED TO BE CHANGED
                        tokens.add(makeError(filename, lineNumber, line, i));
                        continue;
                      }

                      if (line.charAt(i) == '.'){ // If its a number with decimal values
                        List<Object> decimal = cycleDigit(line, i);
                        i = (int) decimal.get(1);
                        returned += (String) decimal.get(0);
                      }

                      if (!returned.contains("**ERROR**")){
                        tokens.add(makeNewToken(filename, lineNumber, returned, TokenType.NUMBER));
                      }else{
                        tokens.add(makeError(filename, lineNumber, line, i));
                      }

                    } // keep cycling for digit. if next token a ., cycle for more digits, then store as number. Else, store as number
                    case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' -> {} //Keep searching for letter or digit. Store as id/keyword
                    case ':' -> tokens.add(makeNewToken(filename, lineNumber, ":", TokenType.COLON));
                    case '!' -> {} //If next token =, TokenType.NOT_EQUALS. Else error
                    case '\"' -> {} //Cycle until next " and assign string. if no closing before new line, error
                    default -> {tokens.add(makeError(filename, lineNumber, line, i));}
                }
              }
              lineNumber++;
          }
          System.out.println(tokens);
          scanner.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      /**
       * Will need line number from here
       */
		return null;
	}

  private static Token makeError(String filename, int lineNumber, String line, Integer i){
    return makeNewToken(filename, lineNumber, String.valueOf(line.charAt(i)), TokenType.ERROR);
  }

  /**
   * 
   * @param c The char following an = in the tokenizer
   * @return char indicating if next token means that it will be accept, relOp, or an Error
   */
  private static char checkEquals(char c){
    if (c != '=')
      return 'a';
    else if (c == '=')
      return 'r';
    else
      return 'e';
  }

  private static boolean isDigit(char c){
    return c <= 57 && c >= 48;
  }

  private static List<Object> cycleDigit(String line, int i){ //This is probably broken. Check spaces maybe?
    String returnedString;
    if (isDigit(line.charAt(i+1))){
      int count = 1;
      String digitString = "";
      while (isDigit(line.charAt(i+count))){
        digitString += line.charAt(i+count);
        count += 1;
      }
      i+=count;
      returnedString = digitString;
    }else{
      returnedString = "**ERROR**";
    }
    return Arrays.asList(returnedString, i);
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