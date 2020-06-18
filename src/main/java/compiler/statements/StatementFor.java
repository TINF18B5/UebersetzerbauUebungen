package compiler.statements;

import compiler.expressions.*;
import compiler.visitor.*;

public class StatementFor extends Statement {
    
    public final Statement initializer;
    public final Expression condition;
    public final Statement increment;
    public final Statement body;
    
    public StatementFor(Statement initializer, Expression condition, Statement increment, Statement body) {
        this.initializer = initializer;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitFor(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitFor(this);
    }
}
