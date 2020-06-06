package compiler;

public class NumberCalc implements NumberCalcIntf {
    
    private final LexerIntf lexer;
    
    public NumberCalc(FileReaderIntf fileReader) throws Exception {
        this.lexer = new Lexer(fileReader);
    }
    
    @Override
    public int getSum() throws Exception {
        int value = getProduct();
        while(lexer.lookAheadToken().m_type == TokenIntf.Type.PLUS) {
            lexer.advance();
            value += getProduct();
        }
        return value;
    }
    
    @Override
    public int getProduct() throws Exception {
        int value = getFactor();
        while(lexer.lookAheadToken().m_type == TokenIntf.Type.MUL) {
            lexer.advance();
            value *= getFactor();
        }
        return value;
    }
    
    @Override
    public int getFactor() throws Exception {
        int value;
        final Token token = lexer.lookAheadToken();
        if(token.m_type == TokenIntf.Type.LPAREN) {
            lexer.advance();
            value = getSum();
            lexer.expect(TokenIntf.Type.RPAREN);
        } else if(token.m_type == TokenIntf.Type.INTEGER) {
            lexer.expect(TokenIntf.Type.INTEGER);
            value = token.m_intValue;
        } else {
            throw new Exception("Not a number" + token);
        }
        return value;
        
    }
}
