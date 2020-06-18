package compiler.visitor;

import compiler.statements.*;

public interface StatementVisitor<T> {
    
    T visitAssign(StatementAssign statementAssign);
    
    T visitBlock(StatementBlock statementBlock);
    
    T visitCall(StatementCall statementCall);
    
    T visitDoWhile(StatementDoWhile statementDoWhile);
    
    T visitFor(StatementFor statementFor);
    
    T visitFunctionDefinition(StatementFunctionDefinition statementFunctionDefinition);
    
    T visitIf(StatementIf statementIf);
    
    T visitPrint(StatementPrint statementPrint);
    
    T visitWhile(StatementWhile statementWhile);
}
