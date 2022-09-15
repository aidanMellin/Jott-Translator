/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/

// import javax.sound.midi.SysexMessage;
import javax.management.relation.RelationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JottTokenizer {

    private static boolean noError = true;

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
        noError = true;
        ArrayList<Token> tokens = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            int lineNumber = 1;
            while (scanner.hasNextLine() && noError) {
                String line = scanner.nextLine();
                // System.out.println(line);
                for (int i=0; i<line.length(); i++) {
                    if (!noError) break;
                    switch (line.charAt(i)) {
                        case '#' -> i += (line.length() - i); // ask if there are exceptions to this rule
                        case ',' -> tokens.add(makeNewToken(filename, lineNumber, ",", TokenType.COMMA));
                        case '[' -> tokens.add(makeNewToken(filename, lineNumber, "[", TokenType.L_BRACKET));
                        case ']' -> tokens.add(makeNewToken(filename, lineNumber, "]", TokenType.R_BRACKET));
                        case '}' -> tokens.add(makeNewToken(filename, lineNumber, "}", TokenType.R_BRACE));
                        case '{' -> tokens.add(makeNewToken(filename, lineNumber, "{", TokenType.L_BRACE));
                        case '=' -> {
                            //Conditional: if no other = -> ASSIGN, if == -> relOp. Else -> Error
                            if(i + 1 < line.length()){
                                char returned = checkEquals(line.charAt(i+1));
                                if (returned == 'a'){
                                    tokens.add(makeNewToken(filename, lineNumber, "=", TokenType.ASSIGN));
                                } else {
                                    tokens.add(makeNewToken(filename, lineNumber, "==", TokenType.REL_OP));
                                    i+=1;
                                }
                            }else{
                                tokens.add(makeNewToken(filename, lineNumber, "=", TokenType.ASSIGN));
                            }
                        }
                        case '<' -> {
                            if (i+1 < line.length()) {
                                if (checkEquals(line.charAt(i + 1)) == 'r') {
                                    tokens.add(makeNewToken(filename, lineNumber, "<=", TokenType.REL_OP));
                                    i++;
                                } else {
                                    tokens.add(makeNewToken(filename, lineNumber, "<", TokenType.REL_OP));
                                }
                            } else {
                                tokens.add(makeNewToken(filename, lineNumber, "<", TokenType.REL_OP));
                            }
                        }
                        case '>' -> {
                            if (i+1 < line.length()) {
                                if (checkEquals(line.charAt(i + 1)) == 'r') {
                                    tokens.add(makeNewToken(filename, lineNumber, ">=", TokenType.REL_OP));
                                    i++;
                                } else {
                                    tokens.add(makeNewToken(filename, lineNumber, ">", TokenType.REL_OP));
                                }
                            } else {
                                tokens.add(makeNewToken(filename, lineNumber, ">", TokenType.REL_OP));
                            }
                        }
                        case '+','-','*','/' -> tokens.add(makeNewToken(filename, lineNumber, String.valueOf(line.charAt(i)), TokenType.MATH_OP));
                        case ';' -> tokens.add(makeNewToken(filename, lineNumber, ";", TokenType.SEMICOLON));
                        case '.', '1','2','3','4','5','6','7','8','9','0' -> {
                            List<Object> cycled = cycleNumber(line, i);
                            String returned = (String) cycled.get(0);
                            i = (int) cycled.get(1)-1;
                            if(returned.equals("."))
                                throw new Exception("Syntax Error:\n"+"Invalid use of symbol: '.'\n"+filename+":"+lineNumber);
                            else
                                tokens.add(makeNewToken(filename, lineNumber, returned, TokenType.NUMBER));
                        } // keep cycling for digit. if next token a ., cycle for more digits, then store as number. Else, store as number
                        case 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' -> {
                            List<Object> cycled = cycleIDKeyword(line, i);
                            String returned = (String) cycled.get(0);
                            i = (int) cycled.get(1)-1;
                            tokens.add(makeNewToken(filename, lineNumber, returned, TokenType.ID_KEYWORD));
                        } //Keep searching for letter or digit. Store as id/keyword
                        case ':' -> tokens.add(makeNewToken(filename, lineNumber, ":", TokenType.COLON));
                        case '!' -> {
                            if (i+1 < line.length()) {
                                if (checkEquals(line.charAt(i + 1)) == 'r') {
                                    tokens.add(makeNewToken(filename, lineNumber, "!=", TokenType.REL_OP));
                                    i++;
                                } else {
                                    throw new Exception("Syntax Error:\n"+"Invalid use of symbol: '!'\n"+filename+":"+lineNumber);
                                }
                            } else {
                                throw new Exception("Syntax Error:\n"+"Invalid end of line: '!'\n"+filename+":"+lineNumber);
                            }
                        }
                        case '\"' -> {
                            List<Object> cycled = cycleString(line, i);
                            String returned = (String) cycled.get(0);
                            i = (int) cycled.get(1)-1;
                            if(returned.equals("*CHAR_ERROR*"))
                                throw new Exception("Syntax Error:\n"+"Unknown character in String: '"+line.charAt(i)+"'\n"+filename+":"+lineNumber);
                            else if (returned.equals("*QUOTE_ERROR*"))
                                throw new Exception("Syntax Error:\n"+"Unclosed String literal\n"+filename+":"+lineNumber);
                            else
                                tokens.add(makeNewToken(filename, lineNumber, "\""+returned+"\"", TokenType.STRING));
                        } //Cycle until next " and assign string. if no closing before new line, error
                        default -> {
                            if (line.charAt(i) != ' ')
                                throw new Exception("Syntax Error:\n"+"Unknown character: '"+line.charAt(i)+"'\n"+filename+":"+lineNumber);

                        }
                    }
                }
                lineNumber++;
            }
            //for(Token tok : tokens)
              //  System.out.println("Token - " + tok.toString());
            scanner.close();
        } catch (Exception e) {
          System.err.println(e.getMessage());
          return null;
        }
        return tokens;
    }

    private static List<Object> cycleString(String line, int i) {
        String returnedString = "";
        int count = 1;

        while (count + 1 <= line.length() - i){
          if(line.charAt(i+count) == '\"'){
            count += 1;
            return Arrays.asList(returnedString, i+count);
          }
          if (String.valueOf(line.charAt(i+count)).matches("^[a-zA-Z\\d\\s]*")) {
              returnedString += line.charAt(i + count);
              count += 1;
          } else {
              return Arrays.asList("*CHAR_ERROR*", i+count+1);
          }
        }
        return Arrays.asList("*QUOTE_ERROR*", i+count);
    }

    private static Token makeError(String filename, int lineNumber, String line, Integer i, TokenType tokenType){
      noError = false;
      return makeNewToken(filename, lineNumber, String.valueOf(line.charAt(i)), tokenType);
    }

    /**
    *
    * @param c The char following an = in the tokenizer
    * @return char indicating if next token means that it will be accepting, relOp, or an Error
    */
    private static char checkEquals(char c){
    if (c != '=')
      return 'a';
    else
      return 'r';
    }

    private static boolean isAlpha(char c){
    int checkASCII = (int) c;
    boolean isUpper = checkASCII <= 90 && checkASCII >= 65;
    boolean isLower = checkASCII <= 122 && checkASCII >= 97;
    return isUpper || isLower;
    }

    private static List<Object> cycleIDKeyword(String line, int i) {
    String returnedString = "";
    int count = 0;
    while (true){
      if(count >= line.length()-i)
        break;
      if (isAlpha(line.charAt(i+count)) || isDigit(line.charAt(i+count))){
        returnedString += line.charAt(i+count);
        count += 1;
      } else {
        break;
      }
    }
    return Arrays.asList(returnedString, i+count);
    }

    private static boolean isDigit(char c){
    int checkASCII = (int) c;
    boolean withinBounds = checkASCII <= 57 && checkASCII >= 48;
    return withinBounds;
    }

    /**
    * Cycle through line to determine the value of a number
    * @param line
    * @param i
    * @return body of token to be added, updated i value (in list form)
    */

    private static List<Object> cycleNumber(String line, int i){
    String returnedString = "";
    // returnedString += line.charAt(i);
    int count = 0;

    while (true){
      if (line.charAt(i+count) == '.'){
        returnedString += ".";
        count+=1;
      }
      if(count >= line.length()-i)
        break;
      if(isDigit(line.charAt(i+count))){
        while (isDigit(line.charAt(i+count))){
          returnedString += line.charAt(i+count);
          count += 1;
        }
      }else{
        break;
      }
    }
    return Arrays.asList(returnedString, i+count);
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
    tokenize("phase1_tester/tokenizerTestCases/strings.jott");
    }
}