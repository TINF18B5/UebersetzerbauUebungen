package compiler.visitor;

import compiler.expressions.*;

public interface ExpressionVisitor<T> {
    
    T visitGetVariable(ExpressionGetVariable expressionGetVariable);
    
    T visitBinary(ExpressionBinary expressionBinary);
    
    T visitUnary(ExpressionUnary expressionUnary);
    
    T visitInt(ExpressionInt expressionInt);
}
