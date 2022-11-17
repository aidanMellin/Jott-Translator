import Parser.JottParser;
import Parser.JottTree;
import Tokenizer.JottTokenizer;
import Tokenizer.Token;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;

public class Jott {
    public static void main(String[] args) {
        String fileName = "fileName";
        String lang, inputFile, outputFile;
        try{
            inputFile = args[0];
            outputFile = args[1];
            lang = args[2];
            System.out.printf("Outputting %s, %s, %s%n",inputFile, outputFile, lang);
            try {
                ArrayList<Token> jottTokens = JottTokenizer.tokenize(inputFile);
                JottTree jottTree = JottParser.parse(jottTokens, fileName);
                if (jottTree == null) {
                    System.out.println("Error during parsing");
                    System.exit(1);
                }
                String output = "";
                if (Objects.equals(lang, "jott")) output = jottTree.convertToJott();
                else if (Objects.equals(lang, "java")) output = jottTree.convertToJava();
                else if (Objects.equals(lang, "c")) output = jottTree.convertToC();
                else if (Objects.equals(lang, "python")) output = jottTree.convertToPython();
                else {
                    System.out.println("Unknown language");
                    System.exit(1);
                }
                FileWriter writer = new FileWriter(outputFile);
                writer.write(output);
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }catch (IndexOutOfBoundsException e){
            System.out.printf("Error Caught: Incomplete arguments to run program.\n%s%n\nusage: Jott.java inputFile outputFile language", e);
            System.exit(1);
        }
    }

    public static String validateTree(String inputFile, String lang){
        if(true){
            return "";
        }
        return "bad.";
    } 
}