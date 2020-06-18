package compiler.expressions;

import compiler.visitor.*;

public class ExpressionGetVariable extends Expression {
    
    
    public final String name;
    
    public ExpressionGetVariable(String name) {
        this.name = name;
    }
    
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitGetVariable(this);
    }
    
    @Override
    public void accept(ExpressionVisitorVoid visitor) {
        visitor.visitGetVariable(this);
    }
}
