package compiler.visitor;

import compiler.statements.*;

public interface StatementVisitorVoid {
    
    void visitAssign(StatementAssign statementAssign);
    
    void visitBlock(StatementBlock statementBlock);
    
    void visitCall(StatementCall statementCall);
    
    void visitDoWhile(StatementDoWhile statementDoWhile);
    
    void visitFor(StatementFor statementFor);
    
    void visitFunctionDefinition(StatementFunctionDefinition statementFunctionDefinition);
    
    void visitIf(StatementIf statementIf);
    
    void visitPrint(StatementPrint statementPrint);
    
    void visitWhile(StatementWhile statementWhile);
}
