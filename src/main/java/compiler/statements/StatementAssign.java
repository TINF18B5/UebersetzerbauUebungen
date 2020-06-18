package compiler.statements;

import compiler.expressions.*;
import compiler.visitor.*;

public class StatementAssign extends Statement {
    
    public final String name;
    public final Expression value;
    
    public StatementAssign(String name, Expression value) {
        this.name = name;
        this.value = value;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitAssign(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitAssign(this);
    }
}
