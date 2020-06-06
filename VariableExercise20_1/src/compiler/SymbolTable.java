package compiler;

import java.util.*;

public class SymbolTable implements SymbolTableIntf {
    
    private final Map<String, Symbol> table = new HashMap<>();
    
    @Override
    public Symbol createSymbol(String symbolName) {
        return table.computeIfAbsent(symbolName, name -> new Symbol(name, 0));
    }
    
    @Override
    public Symbol getSymbol(String symbolName) {
        return table.get(symbolName);
    }
}
