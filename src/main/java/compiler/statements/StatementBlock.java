package compiler.statements;

import compiler.visitor.*;

public class StatementBlock extends Statement {
    
    public final Statement[] statements;
    
    public StatementBlock(Statement[] statements) {
        this.statements = statements;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitBlock(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitBlock(this);
    }
}
