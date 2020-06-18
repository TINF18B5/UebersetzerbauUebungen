package compiler.xx_interpreter.stack_based_interpreter;

import java.util.*;

final class StackBasedInterpreterSymbolTable {
    
    private final Map<String, Integer> table;
    
    public StackBasedInterpreterSymbolTable() {
        table = new HashMap<>();
    }
    
    public int getValue(String name) {
        return table.get(name);
    }
    
    public void storeValue(String name, int value) {
        table.put(name, value);
    }
}
