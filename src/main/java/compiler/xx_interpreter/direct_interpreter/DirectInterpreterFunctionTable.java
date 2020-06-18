package compiler.xx_interpreter.direct_interpreter;

import java.util.*;

final class DirectInterpreterFunctionTable {
    
    private final Map<String, DirectInterpreterFunctionInfo> table;
    
    public DirectInterpreterFunctionTable() {
        table = new HashMap<>();
    }
    
    public DirectInterpreterFunctionInfo getFunction(String name) {
        if(table.containsKey(name)) {
            return table.get(name);
        }
        throw new IllegalArgumentException("Unknown function: " + name);
    }
    
    public void storeFunctionInfo(String name, DirectInterpreterFunctionInfo info) {
        if(table.containsKey(name)) {
            throw new IllegalArgumentException("Already contains function: " + name);
        }
        table.put(name, info);
    }
}
