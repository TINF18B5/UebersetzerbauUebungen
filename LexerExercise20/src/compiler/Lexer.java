package compiler;

public class Lexer implements LexerIntf {
    
    private final FileReaderIntf fileReader;
    Token lookaheadToken;
    
    public Lexer(FileReaderIntf fileReader) throws Exception{
        this.fileReader = fileReader;
        advance();
    }
    
    @Override
    public Token lookAheadToken() {
        return lookaheadToken;
    }
    
    @Override
    public void advance() throws Exception {
        while(isWhiteSpace(fileReader.lookAheadChar())) {
            fileReader.advance();
        }
        Token.Type type = getTokenType(fileReader.lookAheadChar());
        if(type == Token.Type.EOF) {
            lookaheadToken = new Token(Token.Type.EOF);
            return;
        }
        
        final StringBuilder contentBuilder = new StringBuilder();
        do {
            contentBuilder.append(fileReader.lookAheadChar());
            fileReader.advance();
        } while(type.isHasValue() && !isWhiteSpace(fileReader.lookAheadChar()) && getTokenType( fileReader.lookAheadChar()) == type);
        
        if(!type.isHasValue()) {
            this.lookaheadToken = new Token(type);
        } else if(type.isNumeric()) {
            this.lookaheadToken = new Token(type, Integer.parseInt(contentBuilder.toString()));
        } else {
            this.lookaheadToken = new Token(type, contentBuilder.toString());
        }
    }
    
    @Override
    public void expect(Token.Type tokenType) throws Exception {
        if(tokenType == lookaheadToken.m_type) {
            advance();
        }
        
        throw new Exception(String.format("Expected token of type %s but found %s", tokenType, lookaheadToken));
    }
    
    @Override
    public Token.Type getTokenType(char firstChar) throws Exception {
        if(firstChar == 0) {
            return Token.Type.EOF;
        }
        
        if(isIdentifierPart(firstChar)) {
            return Token.Type.IDENT;
        }
        if(isDigit(firstChar)) {
            return Token.Type.INTEGER;
        }
    
        switch(firstChar) {
            case '*':
                return Token.Type.MUL;
            case '+':
                return Token.Type.PLUS;
            case '(':
                return Token.Type.LPAREN;
            case ')':
                return Token.Type.RPAREN;
            case '=':
                return Token.Type.ASSIGN;
        }
        throw new Exception("Unexpected Character " + firstChar);
    }
    
    @Override
    public boolean isIdentifierPart(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
    @Override
    public boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    
    @Override
    public boolean isWhiteSpace(char c) {
        switch(c) {
            case ' ':
            case '\n':
            case '\t':
                return true;
            default:
                return false;
        }
    }
}
