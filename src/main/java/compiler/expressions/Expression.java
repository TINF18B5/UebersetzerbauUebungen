package compiler.expressions;

import compiler.visitor.*;

public abstract class Expression {
    
    public static final Expression[] NONE = new Expression[0];
    
    public abstract <T> T accept(ExpressionVisitor<T> visitor);
    
    public abstract void accept(ExpressionVisitorVoid visitor);
    
}
