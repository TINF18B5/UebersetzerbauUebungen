package compiler;

public interface SymbolTableIntf {

    // construct an empty symbol table	
	// public SymbolTableIntf();

	// get symbol for given symbolName, creates a no symbol with the given name was found
	public Symbol getOrCreateSymbol(String symbolName);

	// get symbol for given symbolName, returns null if no symbol with the given name was found
	public Symbol getSymbol(String symbolName);
}
