package compiler;

import java.io.*;

public class StmtReader implements StmtReaderIntf{
    
    private final LexerIntf lexer;
    private final SymbolTable symbolTable;
    private final PrintWriter printWriter;
    private final ExprReader exprReader;
    
    
    public StmtReader(LexerIntf lexer, ByteArrayOutputStream outStream) throws Exception {
        this.symbolTable = new SymbolTable();
        this.lexer = lexer;
        this.printWriter = new PrintWriter(outStream);
        exprReader = new ExprReader(symbolTable, lexer);
    }
    
    @Override
    public void getStmtList() throws Exception {
        while(lexer.lookAheadToken().m_type != TokenIntf.Type.EOF) {
            getStmt();
        }
        printWriter.flush();
    }
    
    @Override
    public void getStmt() throws Exception {
        //Assign oder Print
        if(lexer.lookAheadToken().m_type == TokenIntf.Type.IDENT) {
            getAssign();
        } else if(lexer.lookAheadToken().m_type == TokenIntf.Type.PRINT) {
            getPrint();
        } else {
            throw new Exception("Identifier or Print expected, got" + lexer.lookAheadToken());
        }
    }
    
    //Identifier ASSIGN expr;
    @Override
    public void getAssign() throws Exception {
        final Token token = lexer.lookAheadToken();
        lexer.advance();
        lexer.expect(TokenIntf.Type.ASSIGN);
        final int expr = exprReader.getExpr();
        final Symbol symbol = symbolTable.createSymbol(token.m_stringValue);
        symbol.m_number = expr;
        lexer.expect(TokenIntf.Type.SEMICOL);
    }
    
    @Override
    public void getPrint() throws Exception {
        lexer.advance();
        final int expr = exprReader.getExpr();
        printWriter.println(expr);
        lexer.expect(TokenIntf.Type.SEMICOL);
    }
}
