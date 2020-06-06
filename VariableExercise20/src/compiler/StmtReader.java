package compiler;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class StmtReader implements StmtReaderIntf {
	private SymbolTable m_symbolTable;
	private LexerIntf m_lexer;
    private ExprReader m_exprReader;
    private OutputStreamWriter m_outStream;

	public StmtReader(LexerIntf lexer, OutputStream outStream) throws Exception {
		m_symbolTable = new SymbolTable();
		m_lexer = lexer;
		m_exprReader = new ExprReader(m_symbolTable, m_lexer);
		m_outStream = new OutputStreamWriter(outStream, "UTF-8");
	}
	
	public void getStmtList() throws Exception {
		while (m_lexer.lookAheadToken().m_type != Token.Type.EOF) {
			getStmt();
		}
		m_outStream.flush();
	}

	public void getStmt() throws Exception {
		Token token = m_lexer.lookAheadToken();
		if (token.m_type == Token.Type.IDENT) {
			getAssign();
		} else if (token.m_type == Token.Type.PRINT) {
			getPrint();
		}
	}
	
	public void getAssign() throws Exception {
		Token token = m_lexer.lookAheadToken();
		String varName = token.m_stringValue;
		m_lexer.advance();
		m_lexer.expect(Token.Type.ASSIGN);
		int number = m_exprReader.getExpr();
		Symbol var = m_symbolTable.createSymbol(varName);
		var.m_number = number;
		m_lexer.expect(Token.Type.SEMICOL);
	}

	public void getPrint() throws Exception {
		m_lexer.advance(); // PRINT
		int number = m_exprReader.getExpr();
		m_outStream.write(Integer.toString(number));
		m_outStream.write('\n');
		m_lexer.expect(Token.Type.SEMICOL);
	}


}
