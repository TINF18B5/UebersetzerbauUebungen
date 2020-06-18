package compiler.xx_interpreter.stack_based_interpreter;

import java.util.*;

final class StackBasedInterpreterFunctionTable {
    
    private final Map<String, StackBasedInterpreterFunctionInfo> table;
    
    public StackBasedInterpreterFunctionTable() {
        table = new HashMap<>();
    }
    
    public StackBasedInterpreterFunctionInfo getFunction(String name) {
        if(table.containsKey(name)) {
            return table.get(name);
        }
        throw new IllegalArgumentException("Unknown function: " + name);
    }
    
    public void storeFunctionInfo(String name, StackBasedInterpreterFunctionInfo info) {
        if(table.containsKey(name)) {
            throw new IllegalArgumentException("Already contains function: " + name);
        }
        table.put(name, info);
    }
    
    public void putAll(StackBasedInterpreterFunctionTable into) {
        into.table.putAll(this.table);
    }
}
