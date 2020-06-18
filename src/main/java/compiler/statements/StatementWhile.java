package compiler.statements;

import compiler.expressions.*;
import compiler.visitor.*;

public class StatementWhile extends Statement {
    
    public final Expression condition;
    public final Statement body;
    
    public StatementWhile(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }
    
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitWhile(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitWhile(this);
    }
}
