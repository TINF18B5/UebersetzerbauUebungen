package compiler.statements;

import compiler.visitor.*;

public abstract class Statement {
    
    public static final Statement[] NONE = new Statement[0];
    
    public abstract <T> T accept(StatementVisitor<T> visitor);
    
    public abstract void accept(StatementVisitorVoid visitor);
}
