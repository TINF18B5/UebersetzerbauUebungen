package compiler.parser;

import compiler.expressions.*;
import compiler.lexer.*;
import compiler.operators.*;

public class ExpressionReader {
    
    private final Lexer lexer;
    
    public ExpressionReader(Lexer lexer) {
        this.lexer = lexer;
    }
    
    public Expression getExpression() throws Exception {
        return getOrExpression();
    }
    
    private Expression getOrExpression() throws Exception {
        Expression a = getAndExpression();
        while(lexer.optional(TokenType.OR) != null) {
            a = new ExpressionBinary(BinaryOperator.OR, a, getAndExpression());
        }
        return a;
    }
    
    private Expression getAndExpression() throws Exception {
        Expression a = getBitwiseOrExpression();
        while(lexer.optional(TokenType.AND) != null) {
            a = new ExpressionBinary(BinaryOperator.AND, a, getBitwiseOrExpression());
        }
        return a;
    }
    
    private Expression getBitwiseOrExpression() throws Exception {
        Expression a = getBitwiseAndExpression();
        while(lexer.optional(TokenType.BITWISE_OR) != null) {
            a = new ExpressionBinary(BinaryOperator.BITWISE_OR, a, getBitwiseAndExpression());
        }
        return a;
    }
    
    private Expression getBitwiseAndExpression() throws Exception {
        Expression a = getCompareExpression();
        while(lexer.optional(TokenType.BITWISE_AND) != null) {
            a = new ExpressionBinary(BinaryOperator.BITWISE_AND, a, getCompareExpression());
        }
        return a;
    }
    
    private Expression getCompareExpression() throws Exception {
        Expression a = getAddExpression();
        Token t = lexer.optional(TokenType.LESS, TokenType.EQUAL, TokenType.GREATER);
        while(t != null) {
            switch(t.m_type) {
                case LESS:
                    a = new ExpressionBinary(BinaryOperator.LESS, a, getAddExpression());
                    break;
                case EQUAL:
                    a = new ExpressionBinary(BinaryOperator.EQUAL, a, getAddExpression());
                    break;
                case GREATER:
                    a = new ExpressionBinary(BinaryOperator.GREATER, a, getAddExpression());
            }
            t = lexer.optional(TokenType.LESS, TokenType.EQUAL, TokenType.GREATER);
        }
        return a;
    }
    
    private Expression getAddExpression() throws Exception {
        Expression a = getProductExpression();
        Token t = lexer.optional(TokenType.PLUS, TokenType.MINUS);
        while(t != null) {
            switch(t.m_type) {
                case PLUS:
                    a = new ExpressionBinary(BinaryOperator.PLUS, a, getProductExpression());
                    break;
                case MINUS:
                    a = new ExpressionBinary(BinaryOperator.MINUS, a, getProductExpression());
            }
            t = lexer.optional(TokenType.PLUS, TokenType.MINUS);
        }
        return a;
    }
    
    private Expression getProductExpression() throws Exception {
        Expression a = getUnaryExpression();
        Token t = lexer.optional(TokenType.MUL, TokenType.DIV);
        while(t != null) {
            switch(t.m_type) {
                case MUL:
                    a = new ExpressionBinary(BinaryOperator.MUL, a, getUnaryExpression());
                    break;
                case DIV:
                    a = new ExpressionBinary(BinaryOperator.DIV, a, getUnaryExpression());
            }
            t = lexer.optional(TokenType.MUL, TokenType.DIV);
        }
        return a;
    }
    
    private Expression getUnaryExpression() throws Exception {
        boolean positive = true;
        while(lexer.optional(TokenType.MINUS) != null) {
            positive = !positive;
        }
        
        Expression a = getAtomicExpression();
        if(positive) {
            return a;
        }
        return new ExpressionUnary(UnaryOperator.MINUS, a);
    }
    
    private Expression getAtomicExpression() throws Exception {
        final Token expect = lexer.expect(TokenType.LPAREN, TokenType.IDENT, TokenType.INTEGER);
        
        switch(expect.m_type) {
            case LPAREN:
                final Expression expression = getExpression();
                lexer.expect(TokenType.RPAREN);
                return expression;
            case IDENT:
                return new ExpressionGetVariable(expect.getValue());
            case INTEGER:
                return new ExpressionInt(expect.getNumericValue());
        }
        throw new IllegalStateException("There should already be an exception thrown from expect!");
    }
}
