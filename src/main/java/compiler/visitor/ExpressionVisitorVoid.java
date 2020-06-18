package compiler.visitor;

import compiler.expressions.*;

public interface ExpressionVisitorVoid {
    
    void visitGetVariable(ExpressionGetVariable expressionGetVariable);
    
    void visitBinary(ExpressionBinary expressionBinary);
    
    void visitUnary(ExpressionUnary expressionUnary);
    
    void visitInt(ExpressionInt expressionInt);
}
