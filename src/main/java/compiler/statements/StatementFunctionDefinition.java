package compiler.statements;

import compiler.visitor.*;

public class StatementFunctionDefinition extends Statement {
    
    public final String name;
    public final String[] parameterNames;
    public final Statement body;
    
    public StatementFunctionDefinition(String name, String[] parameterNames, Statement body) {
        this.name = name;
        this.parameterNames = parameterNames;
        this.body = body;
    }
    
    @Override
    public <T> T accept(StatementVisitor<T> visitor) {
        return visitor.visitFunctionDefinition(this);
    }
    
    @Override
    public void accept(StatementVisitorVoid visitor) {
        visitor.visitFunctionDefinition(this);
    }
}
