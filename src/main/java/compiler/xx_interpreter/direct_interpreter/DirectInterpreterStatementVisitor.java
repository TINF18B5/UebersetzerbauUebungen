package compiler.xx_interpreter.direct_interpreter;

import compiler.statements.*;
import compiler.visitor.*;

import java.io.*;

public final class DirectInterpreterStatementVisitor implements StatementVisitorVoid {
    
    private final PrintWriter printWriter;
    private final DirectInterpreterSymbolTable symbolTable;
    private final DirectInterpreterFunctionTable functionTable;
    private final DirectInterpreterExpressionVisitor expressionVisitor;
    
    public DirectInterpreterStatementVisitor(OutputStream stream) {
        this(new PrintWriter(stream), new DirectInterpreterSymbolTable(), new DirectInterpreterFunctionTable());
    }
    
    private DirectInterpreterStatementVisitor(PrintWriter writer, DirectInterpreterSymbolTable symbolTable, DirectInterpreterFunctionTable functionTable) {
        this.symbolTable = symbolTable;
        this.printWriter = writer;
        this.functionTable = functionTable;
        this.expressionVisitor = new DirectInterpreterExpressionVisitor(symbolTable);
    }
    
    @Override
    public void visitAssign(StatementAssign statementAssign) {
        symbolTable.storeValue(statementAssign.name, statementAssign.value.accept(expressionVisitor));
    }
    
    @Override
    public void visitBlock(StatementBlock statementBlock) {
        for(Statement statement : statementBlock.statements) {
            statement.accept(this);
        }
    }
    
    @Override
    public void visitCall(StatementCall statementCall) {
        final DirectInterpreterFunctionInfo function = functionTable.getFunction(statementCall.name);
        if(statementCall.arguments.length != function.parameterNames.length) {
            throw new IllegalArgumentException("Expected " + function.parameterNames.length + " arguments but got " + statementCall.arguments.length);
        }
        final DirectInterpreterSymbolTable symbolTableForCall = new DirectInterpreterSymbolTable();
        for(int i = 0; i < function.parameterNames.length; i++) {
            symbolTableForCall.storeValue(function.parameterNames[i], statementCall.arguments[i].accept(expressionVisitor));
        }
        
        function.body.accept(new DirectInterpreterStatementVisitor(printWriter, symbolTableForCall, functionTable));
    }
    
    @Override
    public void visitDoWhile(StatementDoWhile statementDoWhile) {
        do {
            statementDoWhile.body.accept(this);
        } while(statementDoWhile.condition.accept(expressionVisitor) != 0);
    }
    
    @Override
    public void visitFor(StatementFor statementFor) {
        statementFor.initializer.accept(this);
        while(statementFor.condition.accept(expressionVisitor) != 0) {
            statementFor.body.accept(this);
            statementFor.increment.accept(this);
        }
    }
    
    @Override
    public void visitFunctionDefinition(StatementFunctionDefinition statementFunctionDefinition) {
        final String[] parameterNames = statementFunctionDefinition.parameterNames;
        final Statement body = statementFunctionDefinition.body;
        final DirectInterpreterFunctionInfo info = new DirectInterpreterFunctionInfo(parameterNames, body);
        functionTable.storeFunctionInfo(statementFunctionDefinition.name, info);
    }
    
    @Override
    public void visitIf(StatementIf statementIf) {
        if(statementIf.condition.accept(expressionVisitor) != 0) {
            statementIf.onIf.accept(this);
        } else if(statementIf.onElse != null) {
            statementIf.onElse.accept(this);
        }
    }
    
    @Override
    public void visitPrint(StatementPrint statementPrint) {
        printWriter.println(statementPrint.expression.accept(expressionVisitor));
        printWriter.flush();
    }
    
    @Override
    public void visitWhile(StatementWhile statementWhile) {
        while(statementWhile.condition.accept(expressionVisitor) != 0) {
            statementWhile.body.accept(this);
        }
    }
}
