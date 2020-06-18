package compiler.xx_interpreter.direct_interpreter;

import java.util.*;

final class DirectInterpreterSymbolTable {
    
    private final Map<String, Integer> table;
    
    public DirectInterpreterSymbolTable() {
        table = new HashMap<>();
    }
    
    public int getValue(String name) {
        return table.get(name);
    }
    
    public void storeValue(String name, int value) {
        table.put(name, value);
    }
}
