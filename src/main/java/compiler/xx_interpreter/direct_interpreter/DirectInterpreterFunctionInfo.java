package compiler.xx_interpreter.direct_interpreter;

import compiler.statements.*;

final class DirectInterpreterFunctionInfo {
    
    public final String[] parameterNames;
    public final Statement body;
    
    public DirectInterpreterFunctionInfo(String[] parameterNames, Statement body) {
        this.parameterNames = parameterNames;
        this.body = body;
    }
}
