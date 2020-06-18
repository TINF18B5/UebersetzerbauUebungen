package compiler.lexer;

public enum TokenType {
    EOF,
    IDENT,
    INTEGER(true),
    PLUS,
    MINUS,
    MUL,
    DIV,
    BITWISE_AND,
    BITWISE_OR,
    AND,
    OR,
    LPAREN,
    RPAREN,
    ASSIGN,
    COMMA,
    SEMICOL,
    PRINT,
    IF,
    ELSE,
    WHILE,
    DO,
    FOR,
    CALL,
    LBRACE,
    RBRACE,
    LESS,
    EQUAL,
    GREATER,
    FUNCTION;
    public final boolean isNumeric;
    
    TokenType() {
        isNumeric = false;
    }
    
    TokenType(boolean isNumeric) {
        this.isNumeric = isNumeric;
    }
}
