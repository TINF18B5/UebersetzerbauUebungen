package compiler.statements;

import compiler.expressions.*;
import compiler.visitor.*;

public class StatementCall extends Statement {
    
    public final Expression[] arguments;
    public final String name;
    
    public StatementCall(Expression[] arguments, String name) {
        this.arguments = arguments;
        this.name = name;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitCall(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitCall(this);
    }
}
