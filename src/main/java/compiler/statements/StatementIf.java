package compiler.statements;


import compiler.expressions.*;
import compiler.visitor.*;
import org.jetbrains.annotations.*;

public class StatementIf extends Statement {
    
    public final Expression condition;
    public final Statement onIf;
    @Nullable
    public final Statement onElse;
    
    public StatementIf(Expression condition, Statement onIf, @Nullable Statement onElse) {
        this.condition = condition;
        this.onIf = onIf;
        this.onElse = onElse;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitIf(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitIf(this);
    }
}
