package compiler.xx_interpreter.stack_based_interpreter;

import compiler.statements.*;

final class StackBasedInterpreterFunctionInfo {
    
    public final String[] parameterNames;
    public final Statement body;
    
    public StackBasedInterpreterFunctionInfo(String[] parameterNames, Statement body) {
        this.parameterNames = parameterNames;
        this.body = body;
    }
}
