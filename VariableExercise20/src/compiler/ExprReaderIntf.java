package compiler;

public abstract class ExprReaderIntf {
	SymbolTable m_symbolTable;
    LexerIntf m_lexer;

	public ExprReaderIntf(SymbolTable symbolTable, LexerIntf lexer) throws Exception {
		m_symbolTable = symbolTable;
		m_lexer = lexer;
	}
	
	// read atomic expression
	abstract public int getAtomicExpr() throws Exception;

	// read unary expression
	abstract public int getUnaryExpr() throws Exception;
	
	// read product
	abstract public int getProduct() throws Exception;

	// read expression
	abstract public int getExpr() throws Exception;
}
