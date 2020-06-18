package compiler.lexer;

import org.jetbrains.annotations.*;

import java.util.*;
import java.util.stream.*;

public class Lexer {
    
    private final FileReader fileReader;
    private Token lookaheadToken;
    
    public Lexer(FileReader fileReader) throws Exception {
        this.fileReader = fileReader;
        advance();
    }
    
    public void advance() throws Exception {
        TokenType tokenType = getTokenType(fileReader.lookAheadChar());
        lookaheadToken = new Token();
        lookaheadToken.m_type = tokenType;
        if(tokenType == TokenType.IDENT) {
            String ident = getIdent();
            lookaheadToken.setValue(ident);
            switch(ident) {
                case "PRINT":
                    lookaheadToken.m_type = TokenType.PRINT;
                    break;
                case "IF":
                    lookaheadToken.m_type = TokenType.IF;
                    break;
                case "ELSE":
                    lookaheadToken.m_type = TokenType.ELSE;
                    break;
                case "WHILE":
                    lookaheadToken.m_type = TokenType.WHILE;
                    break;
                case "DO":
                    lookaheadToken.m_type = TokenType.DO;
                    break;
                case "FOR":
                    lookaheadToken.m_type = TokenType.FOR;
                    break;
                case "CALL":
                    lookaheadToken.m_type = TokenType.CALL;
                    break;
                case "FUNCTION":
                    lookaheadToken.m_type = TokenType.FUNCTION;
                    break;
            }
        } else if(tokenType == TokenType.INTEGER) {
            int number = getNumber();
            lookaheadToken.setValue(number);
        } else if(tokenType == TokenType.ASSIGN) {
            fileReader.advance();
            if(fileReader.lookAheadChar() == '=') {
                lookaheadToken.m_type = TokenType.EQUAL;
                fileReader.advance();
            }
        } else if(tokenType == TokenType.BITWISE_AND) {
            fileReader.advance();
            if(fileReader.lookAheadChar() == '&') {
                lookaheadToken.m_type = TokenType.AND;
                fileReader.advance();
            }
        } else if(tokenType == TokenType.BITWISE_OR) {
            fileReader.advance();
            if(fileReader.lookAheadChar() == '|') {
                lookaheadToken.m_type = TokenType.OR;
                fileReader.advance();
            }
        } else {
            fileReader.advance();
        }
        while(isWhiteSpace(fileReader.lookAheadChar())) {
            fileReader.advance();
        }
    }
    
    @Contract(pure = true)
    public Token lookahead() {
        return lookaheadToken;
    }
    
    @Contract(mutates = "this")
    public Token expect(TokenType... type) throws Exception {
        final Token lookahead = lookahead();
        for(TokenType tokenType : type) {
            if(lookahead.m_type == tokenType) {
                advance();
                return lookahead;
            }
        }
        
        throw new ParserException("FOUND: ", lookahead.m_type.name(), getCurrentLocationMsg(), Arrays
                .stream(type)
                .map(Enum::name)
                .collect(Collectors.joining(", ", "Any of [", "]")));
    }
    
    @Contract(mutates = "this")
    public Token expect(TokenType type, String message) throws Exception {
        final Token lookahead = lookahead();
        if(lookahead.m_type != type) {
            throw new ParserException("", message, getCurrentLocationMsg(), "");
        }
        advance();
        return lookahead;
    }
    
    @Contract(mutates = "this")
    public Token optional(TokenType type) throws Exception {
        final Token lookahead = lookahead();
        if(lookahead.m_type == type) {
            advance();
            return lookahead;
        }
        return null;
    }
    
    @Contract(mutates = "this")
    public Token optional(TokenType... anyOf) throws Exception {
        final Token lookahead = lookahead();
        if(Arrays.asList(anyOf).contains(lookahead.m_type)) {
            advance();
            return lookahead;
        }
        return null;
    }
    
    @Contract(pure = true)
    private TokenType getTokenType(char firstChar) throws Exception {
        if(firstChar == 0) {
            return TokenType.EOF;
        } else if('a' <= firstChar && firstChar <= 'z') {
            return TokenType.IDENT;
        } else if('A' <= firstChar && firstChar <= 'Z') {
            return TokenType.IDENT;
        } else if('0' <= firstChar && firstChar <= '9') {
            return TokenType.INTEGER;
        } else if(firstChar == '+') {
            return TokenType.PLUS;
        } else if(firstChar == '-') {
            return TokenType.MINUS;
        } else if(firstChar == '*') {
            return TokenType.MUL;
        } else if(firstChar == '/') {
            return TokenType.DIV;
        } else if(firstChar == '&') {
            return TokenType.BITWISE_AND;
        } else if(firstChar == '|') {
            return TokenType.BITWISE_OR;
        } else if(firstChar == '(') {
            return TokenType.LPAREN;
        } else if(firstChar == ')') {
            return TokenType.RPAREN;
        } else if(firstChar == '=') {
            return TokenType.ASSIGN;
        } else if(firstChar == ';') {
            return TokenType.SEMICOL;
        } else if(firstChar == ',') {
            return TokenType.COMMA;
        } else if(firstChar == '{') {
            return TokenType.LBRACE;
        } else if(firstChar == '}') {
            return TokenType.RBRACE;
        } else if(firstChar == '<') {
            return TokenType.LESS;
        } else if(firstChar == '>') {
            return TokenType.GREATER;
        } else {
            throw new ParserException("Unexpected character: ", Character.toString(firstChar), fileReader
                    .getCurrentLocationMsg(), "");
        }
    }
    
    private String getIdent() throws Exception {
        StringBuilder ident = new StringBuilder();
        char nextChar = fileReader.lookAheadChar();
        do {
            ident.append(nextChar);
            fileReader.advance();
            nextChar = fileReader.lookAheadChar();
        } while(isIdentifierPart(nextChar));
        return ident.toString();
    }
    
    @Contract(pure = true)
    private boolean isIdentifierPart(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }
    
    private int getNumber() throws Exception {
        int number = 0;
        char nextChar = fileReader.lookAheadChar();
        do {
            number *= 10;
            number += nextChar - '0';
            fileReader.advance();
            nextChar = fileReader.lookAheadChar();
        } while(isDigit(nextChar));
        return number;
    }
    
    @Contract(pure = true)
    public boolean isDigit(char c) {
        return ('0' <= c && c <= '9');
    }
    
    @Contract(pure = true)
    public boolean isWhiteSpace(char c) {
        return (c == ' ' || c == '\n' || c == '\r' || c == '\t');
    }
    
    public String getCurrentLocationMsg() {
        return fileReader.getCurrentLocationMsg();
    }
}
