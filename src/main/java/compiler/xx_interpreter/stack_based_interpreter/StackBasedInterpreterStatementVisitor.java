package compiler.xx_interpreter.stack_based_interpreter;

import compiler.expressions.*;
import compiler.statements.*;
import compiler.visitor.*;

import java.io.*;

public final class StackBasedInterpreterStatementVisitor implements StatementVisitorVoid {
    
    private final StackBasedExecutionEnvironment environment;
    private final StackBasedInterpreterExpressionVisitor expressionVisitor;
    
    
    private StackBasedInterpreterStatementVisitor(StackBasedExecutionEnvironment environment) {
        this.environment = environment;
        this.expressionVisitor = new StackBasedInterpreterExpressionVisitor(environment);
    }
    
    public StackBasedInterpreterStatementVisitor(PrintStream out) {
        this(new StackBasedExecutionEnvironment(out));
    }
    
    @Override
    public void visitAssign(StatementAssign statementAssign) {
        statementAssign.value.accept(expressionVisitor);
        environment.getSymbolTable().storeValue(statementAssign.name, environment.pop());
    }
    
    @Override
    public void visitBlock(StatementBlock statementBlock) {
        for(Statement statement : statementBlock.statements) {
            statement.accept(this);
        }
    }
    
    @Override
    public void visitCall(StatementCall statementCall) {
        final StackBasedInterpreterFunctionInfo function = environment.getFunctionTable()
                .getFunction(statementCall.name);
        if(statementCall.arguments.length != function.parameterNames.length) {
            throw new IllegalArgumentException("Expected " + function.parameterNames.length + " arguments but got " + statementCall.arguments.length);
        }
        for(Expression argument : statementCall.arguments) {
            argument.accept(expressionVisitor);
        }
        
        StackBasedExecutionEnvironment environmentForCall = environment.createInnerEnvironment();
        for(int i = function.parameterNames.length - 1; i >= 0; i--) {
            environmentForCall.getSymbolTable()
                    .storeValue(function.parameterNames[i], environment.pop());
        }
        
        function.body.accept(new StackBasedInterpreterStatementVisitor(environmentForCall));
    }
    
    @Override
    public void visitDoWhile(StatementDoWhile statementDoWhile) {
        do {
            statementDoWhile.body.accept(this);
            statementDoWhile.condition.accept(expressionVisitor);
        } while(environment.popAsBool());
        
    }
    
    @Override
    public void visitFor(StatementFor statementFor) {
        statementFor.initializer.accept(this);
        statementFor.condition.accept(expressionVisitor);
        while(environment.popAsBool()) {
            statementFor.body.accept(this);
            statementFor.increment.accept(this);
            statementFor.condition.accept(expressionVisitor);
        }
    }
    
    @Override
    public void visitFunctionDefinition(StatementFunctionDefinition statementFunctionDefinition) {
        final String[] parameterNames = statementFunctionDefinition.parameterNames;
        final Statement body = statementFunctionDefinition.body;
        final StackBasedInterpreterFunctionInfo info = new StackBasedInterpreterFunctionInfo(parameterNames, body);
        environment.getFunctionTable().storeFunctionInfo(statementFunctionDefinition.name, info);
    }
    
    @Override
    public void visitIf(StatementIf statementIf) {
        statementIf.condition.accept(expressionVisitor);
        if(environment.popAsBool()) {
            statementIf.onIf.accept(this);
        } else if(statementIf.onElse != null) {
            statementIf.onElse.accept(this);
        }
    }
    
    @Override
    public void visitPrint(StatementPrint statementPrint) {
        statementPrint.expression.accept(expressionVisitor);
        environment.writeToOutput(environment.pop());
    }
    
    @Override
    public void visitWhile(StatementWhile statementWhile) {
        statementWhile.condition.accept(expressionVisitor);
        while(environment.popAsBool()) {
            statementWhile.body.accept(this);
            statementWhile.condition.accept(expressionVisitor);
        }
    }
}
