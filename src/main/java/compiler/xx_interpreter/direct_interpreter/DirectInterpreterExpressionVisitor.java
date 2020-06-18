package compiler.xx_interpreter.direct_interpreter;

import compiler.expressions.*;
import compiler.operators.*;
import compiler.visitor.*;

final class DirectInterpreterExpressionVisitor implements ExpressionVisitor<Integer> {
    
    private final DirectInterpreterSymbolTable symbolTable;
    
    public DirectInterpreterExpressionVisitor(DirectInterpreterSymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
    
    @Override
    public Integer visitGetVariable(ExpressionGetVariable expressionGetVariable) {
        return symbolTable.getValue(expressionGetVariable.name);
    }
    
    @Override
    public Integer visitBinary(ExpressionBinary expressionBinary) {
        final int left = expressionBinary.left.accept(this);
        switch(expressionBinary.operator) {
            case AND:
                if(left == 0) {
                    return 0;
                } else {
                    return expressionBinary.right.accept(this) != 0 ? 1 : 0;
                }
            case OR:
                if(left != 0) {
                    return 1;
                } else {
                    return expressionBinary.right.accept(this) != 0 ? 1 : 0;
                }
        }
        final int right = expressionBinary.right.accept(this);
        switch(expressionBinary.operator) {
            case PLUS:
                return left + right;
            case MINUS:
                return left - right;
            case MUL:
                return left * right;
            case DIV:
                return left / right;
            case BITWISE_AND:
                return left & right;
            case BITWISE_OR:
                return left | right;
            case LESS:
                return left < right ? 1 : 0;
            case EQUAL:
                return left == right ? 1 : 0;
            case GREATER:
                return left > right ? 1 : 0;
        }
        
        throw new IllegalStateException("Should never happen!");
    }
    
    @Override
    public Integer visitUnary(ExpressionUnary expressionUnary) {
        if(expressionUnary.operator == UnaryOperator.MINUS) {
            return -expressionUnary.expression.accept(this);
        } else {
            throw new IllegalStateException("Should never happen!");
        }
    }
    
    @Override
    public Integer visitInt(ExpressionInt expressionInt) {
        return expressionInt.value;
    }
}
