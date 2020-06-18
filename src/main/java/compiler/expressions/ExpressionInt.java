package compiler.expressions;

import compiler.visitor.*;

public class ExpressionInt extends Expression {
    
    public final int value;
    
    public ExpressionInt(int value) {
        this.value = value;
    }
    
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitInt(this);
    }
    
    @Override
    public void accept(ExpressionVisitorVoid visitor) {
        visitor.visitInt(this);
    }
}
