package compiler.expressions;

import compiler.operators.*;
import compiler.visitor.*;

public class ExpressionBinary extends Expression {
    
    public final BinaryOperator operator;
    public final Expression left;
    public final Expression right;
    
    public ExpressionBinary(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitBinary(this);
    }
    
    @Override
    public void accept(ExpressionVisitorVoid visitor) {
        visitor.visitBinary(this);
    }
}
