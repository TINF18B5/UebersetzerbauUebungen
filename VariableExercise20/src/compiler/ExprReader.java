package compiler;

public class ExprReader extends ExprReaderIntf {

	public ExprReader(SymbolTable symbolTable, LexerIntf lexer) throws Exception {
		super(symbolTable, lexer);
	}
	
	public int getAtomicExpr() throws Exception {
		int number = 0;
		Token token = m_lexer.lookAheadToken(); 
		if (token.m_type == Token.Type.INTEGER) {
			m_lexer.advance();
			number = token.m_intValue;
		} else if (token.m_type == Token.Type.LPAREN) {
			m_lexer.advance();
			number = getExpr();
			m_lexer.expect(Token.Type.RPAREN);
		} else if (token.m_type == Token.Type.IDENT) {
			m_lexer.advance();
			Symbol var = m_symbolTable.getSymbol(token.m_stringValue);
			number = var.m_number;
		} else {
			throw new ParserException("Unexpected Token: ", token.toString(), m_lexer.getCurrentLocationMsg(), "numerical expression");
		}
		return number;
	}
	
	public int getUnaryExpr() throws Exception {
		int sign = 1;
		Token token = m_lexer.lookAheadToken(); 
		while (token.m_type == Token.Type.MINUS) {
			m_lexer.advance();
			sign = -sign;
			token = m_lexer.lookAheadToken();
		}
		int number = getAtomicExpr();
		return sign * number;
	}

	public int getProduct() throws Exception {
		int number = getUnaryExpr();
		Token token = m_lexer.lookAheadToken(); 
		while (token.m_type == Token.Type.MUL || token.m_type == Token.Type.DIV) {
			m_lexer.advance();
			if (token.m_type == Token.Type.MUL) {
				number *= getUnaryExpr();
			} else {
				number /= getUnaryExpr();
			}
			token = m_lexer.lookAheadToken(); 
		}
		return number;
	}

	public int getExpr() throws Exception {
		int number = getProduct();
		Token token = m_lexer.lookAheadToken(); 
		while (token.m_type == Token.Type.PLUS || token.m_type == Token.Type.MINUS) {
			m_lexer.advance();
			if (token.m_type == Token.Type.PLUS) {
				number += getProduct();
			} else {
				number -= getProduct();
			}
			token = m_lexer.lookAheadToken(); 
		}
		return number;
	}

}
