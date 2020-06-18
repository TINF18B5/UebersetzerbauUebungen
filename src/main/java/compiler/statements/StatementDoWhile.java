package compiler.statements;

import compiler.expressions.*;
import compiler.visitor.*;

public class StatementDoWhile extends Statement {
    
    public final Expression condition;
    public final Statement body;
    
    public StatementDoWhile(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitDoWhile(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitDoWhile(this);
    }
}
