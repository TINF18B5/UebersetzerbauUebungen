package compiler.xx_interpreter.stack_based_interpreter;

import compiler.expressions.*;
import compiler.visitor.*;

final class StackBasedInterpreterExpressionVisitor implements ExpressionVisitorVoid {
    
    private final StackBasedExecutionEnvironment environment;
    
    public StackBasedInterpreterExpressionVisitor(StackBasedExecutionEnvironment environment) {
        this.environment = environment;
    }
    
    @Override
    public void visitGetVariable(ExpressionGetVariable expressionGetVariable) {
        final int value = environment.getSymbolTable().getValue(expressionGetVariable.name);
        environment.push(value);
    }
    
    @Override
    public void visitBinary(ExpressionBinary expressionBinary) {
        expressionBinary.left.accept(this);
        final int left = environment.pop();
        switch(expressionBinary.operator) {
            case AND:
                if(left == 0) {
                    environment.push(0);
                } else {
                    expressionBinary.right.accept(this);
                    environment.push(environment.pop() != 0 ? 1 : 0);
                }
                return;
            case OR:
                if(left != 0) {
                    environment.push(1);
                } else {
                    expressionBinary.right.accept(this);
                    environment.push(environment.pop() != 0 ? 1 : 0);
                }
                return;
        }
        
        expressionBinary.right.accept(this);
        final int right = environment.pop();
        switch(expressionBinary.operator) {
            case PLUS:
                environment.push(left + right);
                break;
            case MINUS:
                environment.push(left - right);
                break;
            case MUL:
                environment.push(left * right);
                break;
            case DIV:
                environment.push(left / right);
                break;
            case BITWISE_AND:
                environment.push(left & right);
                break;
            case BITWISE_OR:
                environment.push(left | right);
                break;
            case LESS:
                environment.push(left < right ? 1 : 0);
                break;
            case EQUAL:
                environment.push(left == right ? 1 : 0);
                break;
            case GREATER:
                environment.push(left > right ? 1 : 0);
                break;
            default:
                throw new IllegalStateException("Should never happen! Operator: " + expressionBinary.operator);
        }
    }
    
    @Override
    public void visitUnary(ExpressionUnary expressionUnary) {
        expressionUnary.accept(this);
        environment.push(-environment.pop());
    }
    
    @Override
    public void visitInt(ExpressionInt expressionInt) {
        environment.push(expressionInt.value);
    }
}
