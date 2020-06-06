package compiler;

import java.util.HashMap;

public class SymbolTable implements SymbolTableIntf {

	private HashMap<String, Symbol> m_symbolMap;
	private HashMap<String, FunctionSymbol> m_function_symbolMap;
	
	public SymbolTable() {
		m_symbolMap = new HashMap<>();
		m_function_symbolMap = new HashMap<>();
	}

	public Symbol getOrCreateSymbol(String symbolName) {
		Symbol symbol = m_symbolMap.get(symbolName);
		if (symbol != null) {
			return symbol;
		} else {
			symbol = new Symbol(symbolName, 0);
			m_symbolMap.put(symbolName, symbol);
			return symbol;
		}
	}

	public Symbol getSymbol(String symbolName) {
        return m_symbolMap.get(symbolName);
	}
	
	public FunctionSymbol getOrCreateFunction(String name) {
	    return m_function_symbolMap.computeIfAbsent(name, FunctionSymbol::new);
    }
    
    public FunctionSymbol getFunctionSymbol(String name) {
	    return m_function_symbolMap.get(name);
    }
}
