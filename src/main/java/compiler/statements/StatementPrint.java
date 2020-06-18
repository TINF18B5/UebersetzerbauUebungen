package compiler.statements;

import compiler.expressions.*;
import compiler.visitor.*;

public class StatementPrint extends Statement {
    
    public final Expression expression;
    
    public StatementPrint(Expression expression) {
        this.expression = expression;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitPrint(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitPrint(this);
    }
}
