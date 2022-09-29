import java.util.ArrayList;
import java.util.Objects;

public class AssignmentNode implements JottTree {

    private final String JOTT_DOUBLE = "Double";
    private final String JOTT_INTEGER = "Integer";
    private final String JOTT_STRING = "String";
    private final String JOTT_BOOLEAN = "Boolean";
    private final String EQ_CHAR = "=";
    private ArrayList<JottTree> subnodes = new ArrayList<>();
    private ArrayList<Token> tokens;

    public AssignmentNode(ArrayList<Token> tokens) {
        this.tokens = tokens;
        assert this.tokens != null;
        if (Objects.equals(this.tokens.get(0).getToken(), JOTT_DOUBLE)) {
            subnodes.add(new IdNode(this.tokens.get(1)));
            ArrayList<Token> d_expr = this.tokens;
            d_expr.remove(0);
            d_expr.remove(0);
            d_expr.remove(0);
            d_expr.remove(d_expr.size()-1);
            subnodes.add(new DoubleExprNode(d_expr));
            subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size()-1)));
        } else if (Objects.equals(this.tokens.get(0).getToken(), JOTT_INTEGER)) {
            subnodes.add(new IdNode(this.tokens.get(1)));
            ArrayList<Token> i_expr = this.tokens;
            i_expr.remove(0);
            i_expr.remove(0);
            i_expr.remove(0);
            i_expr.remove(i_expr.size()-1);
            subnodes.add(new IntExprNode(i_expr));
            subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size()-1)));
        } else if (Objects.equals(this.tokens.get(0).getToken(), JOTT_BOOLEAN)) {
            subnodes.add(new IdNode(this.tokens.get(1)));
            ArrayList<Token> b_expr = this.tokens;
            b_expr.remove(0);
            b_expr.remove(0);
            b_expr.remove(0);
            b_expr.remove(b_expr.size()-1);
            subnodes.add(new BoolExprNode(b_expr));
            subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size()-1)));
        } else if (Objects.equals(this.tokens.get(0).getToken(), JOTT_STRING)) {
            subnodes.add(new IdNode(this.tokens.get(1)));
            ArrayList<Token> s_expr = this.tokens;
            s_expr.remove(0);
            s_expr.remove(0);
            s_expr.remove(0);
            s_expr.remove(s_expr.size()-1);
            subnodes.add(new StrExprNode(s_expr));
            subnodes.add(new EndStatementNode(this.tokens.get(this.tokens.size()-1)));
        }
    }

    /**
     * Will output a string of this tree in Jott
     * @return a string representing the Jott code of this tree
     */
    public String convertToJott()
    {
        StringBuilder jott_asmt = new StringBuilder();
        if (tokens.get(0).getToken() == JOTT_DOUBLE) jott_asmt.append(JOTT_DOUBLE + " ");
        else if (tokens.get(0).getToken() == JOTT_BOOLEAN) jott_asmt.append(JOTT_BOOLEAN + " ");
        else if (tokens.get(0).getToken() == JOTT_INTEGER) jott_asmt.append(JOTT_INTEGER + " ");
        else if (tokens.get(0).getToken() == JOTT_STRING) jott_asmt.append(JOTT_STRING + " ");
        jott_asmt.append(subnodes.get(0).convertToJott() + " ");
        jott_asmt.append(EQ_CHAR + " ");
        jott_asmt.append(subnodes.get(1).convertToJott());
        jott_asmt.append(subnodes.get(2).convertToJott());
        return jott_asmt.toString();
    }

    /**
     * Will output a string of this tree in Java
     * @return a string representing the Java code of this tree
     */
    public String convertToJava()
    {
        return("");
    }

    /**
     * Will output a string of this tree in C
     * @return a string representing the C code of this tree
     */
    public String convertToC()
    {
        return("");
    }

    /**
     * Will output a string of this tree in Python
     * @return a string representing the Python code of this tree
     */
    public String convertToPython()
    {
        return("");
    }

    /**
     * This will validate that the tree follows the semantic rules of Jott
     * Errors validating will be reported to System.err
     * @return true if valid Jott code; false otherwise
     */
    public boolean validateTree()
    {
        return(false);
    }
}