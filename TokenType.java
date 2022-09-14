/**
 * This represents the types of tokens that can be in Jott.
 */
public enum TokenType {
    COMMA,
    R_BRACKET,
    L_BRACKET,
    R_BRACE,
    L_BRACE,
    ASSIGN,
    REL_OP,
    MATH_OP,
    SEMICOLON,
    NUMBER,
    ID_KEYWORD,
    COLON,
    STRING,
    UNEXPECTED_PERIOD_ERROR,
    UNEXPECTED_CHARACTER_ERROR,
    PREMATURE_END_OF_LINE_ERROR,
    UNCLOSED_STRING_ERROR;
}
