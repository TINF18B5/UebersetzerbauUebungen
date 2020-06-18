package compiler.expressions;

import compiler.operators.*;
import compiler.visitor.*;

public class ExpressionUnary extends Expression {
    
    public final UnaryOperator operator;
    public final Expression expression;
    
    public ExpressionUnary(UnaryOperator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }
    
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitUnary(this);
    }
    
    @Override
    public void accept(ExpressionVisitorVoid visitor) {
        visitor.visitUnary(this);
    }
}
