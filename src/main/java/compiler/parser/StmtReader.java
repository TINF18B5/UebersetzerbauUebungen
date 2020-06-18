package compiler.parser;

import compiler.expressions.*;
import compiler.lexer.*;
import compiler.statements.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StmtReader {
    
    @NotNull
    private final ExpressionReader expressionReader;
    
    @NotNull
    private final Lexer lexer;
    
    public StmtReader(@NotNull Lexer lexer) {
        this.lexer = lexer;
        this.expressionReader = new ExpressionReader(lexer);
    }
    
    @NotNull
    @Contract(mutates = "this", value = "->new")
    public Statement getStatement() throws Exception {
        final Token expect = this.lexer.expect(TokenType.LBRACE, TokenType.FUNCTION, TokenType.PRINT, TokenType.WHILE, TokenType.FOR, TokenType.IF, TokenType.CALL, TokenType.DO, TokenType.IDENT);
        switch(expect.m_type) {
            case IDENT:
                lexer.expect(TokenType.ASSIGN);
                final Expression expression = expressionReader.getExpression();
                lexer.expect(TokenType.SEMICOL);
                return new StatementAssign(expect.getValue(), expression);
            case PRINT:
                return getPrintStatement();
            case LBRACE:
                return getBlockStatement();
            case WHILE:
                return getWhileStatement();
            case FOR:
                return getForStatement();
            case IF:
                return getIfStatement();
            case FUNCTION:
                return getFunctionDefinition();
            case CALL:
                return getCallStatement();
            case DO:
                return getDoStatement();
        }
        throw new IllegalStateException("Should already be caught by Expect!");
    }
    
    @Contract("->new")
    private Statement getPrintStatement() throws Exception {
        final Expression expression = expressionReader.getExpression();
        lexer.expect(TokenType.SEMICOL);
        return new StatementPrint(expression);
    }
    
    @Contract("->new")
    private Statement getDoStatement() throws Exception {
        final Statement body = getStatement();
        lexer.expect(TokenType.WHILE);
        final Expression condition = expressionReader.getExpression();
        lexer.expect(TokenType.SEMICOL);
        return new StatementDoWhile(condition, body);
    }
    
    @Contract("->new")
    private Statement getCallStatement() throws Exception {
        final Token identifier = lexer.expect(TokenType.IDENT, "function name (identifier) expected");
        final String name = identifier.getValue();
        lexer.expect(TokenType.LPAREN);
        if(lexer.optional(TokenType.RPAREN) != null) {
            lexer.expect(TokenType.SEMICOL);
            return new StatementCall(Expression.NONE, name);
        }
        final ArrayList<Expression> arguments = new ArrayList<>();
        do {
            arguments.add(expressionReader.getExpression());
        } while(lexer.optional(TokenType.COMMA) != null);
        
        lexer.expect(TokenType.RPAREN);
        lexer.expect(TokenType.SEMICOL);
        
        return new StatementCall(arguments.toArray(Expression.NONE), name);
    }
    
    @Contract("->new")
    private Statement getFunctionDefinition() throws Exception {
        final Token identifier = lexer.expect(TokenType.IDENT, "function name (identifier) expected");
        final String name = identifier.getValue();
        
        lexer.expect(TokenType.LPAREN);
        final String[] parameterNames;
        if(lexer.optional(TokenType.RPAREN) != null) {
            parameterNames = new String[0];
        } else {
            final ArrayList<String> names = new ArrayList<>();
            do {
                final Token parameterName = lexer.expect(TokenType.IDENT, "Parameter name expected");
                names.add(parameterName.getValue());
            } while(lexer.optional(TokenType.COMMA) != null);
            parameterNames = names.toArray(new String[0]);
            lexer.expect(TokenType.RPAREN);
        }
        final Statement body = getStatement();
        return new StatementFunctionDefinition(name, parameterNames, body);
    }
    
    @Contract("->new")
    private Statement getIfStatement() throws Exception {
        final Expression condition = expressionReader.getExpression();
        final Statement onIf = getStatement();
        final Statement onElse;
        if(lexer.optional(TokenType.ELSE) != null) {
            onElse = getStatement();
        } else {
            onElse = null;
        }
        return new StatementIf(condition, onIf, onElse);
    }
    
    @Contract(value = "->new")
    private Statement getBlockStatement() throws Exception {
        final List<Statement> statementList = new ArrayList<>();
        while(lexer.optional(TokenType.RBRACE) == null) {
            statementList.add(getStatement());
        }
        return new StatementBlock(statementList.toArray(Statement.NONE));
    }
    
    @Contract("->new")
    private Statement getForStatement() throws Exception {
        final Statement initializer = getStatement();
        final Expression condition = expressionReader.getExpression();
        lexer.expect(TokenType.SEMICOL);
        final Statement increment = getStatement();
        final Statement body = getStatement();
        return new StatementFor(initializer, condition, increment, body);
    }
    
    @Contract("->new")
    private Statement getWhileStatement() throws Exception {
        final Expression condition = expressionReader.getExpression();
        final Statement body = getStatement();
        return new StatementWhile(condition, body);
    }
    
    
}
