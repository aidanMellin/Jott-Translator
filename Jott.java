import java.io.File;

public class Jott {
    public static void main(String[] args) {
        String lang, inputFile , outputFile = "";
        try{
            inputFile = args[0];
            outputFile = args[1];
            lang = args[2];
            System.out.println(String.format("Outputting %s, %s, %s",inputFile, outputFile, lang));
        }catch (IndexOutOfBoundsException e){
            System.out.println(String.format("Error Caught: Incomplete arguments to run program.\n%s", e));
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