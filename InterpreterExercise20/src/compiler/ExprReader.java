package compiler;

public class ExprReader extends ExprReaderIntf {
    
    public ExprReader(SymbolTable symbolTable, LexerIntf lexer, CompileEnvIntf compileEnv) throws Exception {
        super(symbolTable, lexer, compileEnv);
    }
    
    public void getAtomicExpr() throws Exception {
        Token token = m_lexer.lookAheadToken();
        if(token.m_type == Token.Type.INTEGER) {
            m_lexer.advance();
            m_compileEnv.addInstr(new PushNumberInstruction(token.m_intValue));
        } else if(token.m_type == Token.Type.LPAREN) {
            m_lexer.advance();
            getExpr();
            m_lexer.expect(Token.Type.RPAREN);
        } else if(token.m_type == Token.Type.IDENT) {
            m_lexer.advance();
            m_compileEnv.addInstr(new ReadVariableInstruction(token.m_stringValue));
        } else {
            throw new ParserException("Unexpected Token: ", token.toString(), m_lexer.getCurrentLocationMsg(), "numerical expression");
        }
    }
    
    public void getUnaryExpr() throws Exception {
        Token token = m_lexer.lookAheadToken();
        boolean positive = true;
        while(token.m_type == Token.Type.MINUS) {
            positive = !positive;
            m_lexer.advance();
            token = m_lexer.lookAheadToken();
        }
        getAtomicExpr();
        if(!positive) {
            m_compileEnv.addInstr(new UnaryMinusInstruction());
        }
    }
    
    public void getProduct() throws Exception {
        getUnaryExpr();
        Token token = m_lexer.lookAheadToken();
        while(token.m_type == Token.Type.MUL || token.m_type == Token.Type.DIV) {
            m_lexer.advance();
            if(token.m_type == Token.Type.MUL) {
                getUnaryExpr();
                m_compileEnv.addInstr(new ProductInstruction());
            } else {
                getUnaryExpr();
                m_compileEnv.addInstr(new DivInstruction());
            }
            token = m_lexer.lookAheadToken();
        }
    }
    
    public void getExpr() throws Exception {
        getOrExpr();
    }
    
    public void getAddExpression() throws Exception {
        getProduct();
        Token token = m_lexer.lookAheadToken();
        while(token.m_type == Token.Type.PLUS || token.m_type == Token.Type.MINUS) {
            m_lexer.advance();
            if(token.m_type == Token.Type.PLUS) {
                getProduct();
                m_compileEnv.addInstr(new PlusInstruction());
            } else {
                getProduct();
                m_compileEnv.addInstr(new BinaryMinusInstruction());
            }
            token = m_lexer.lookAheadToken();
        }
    }
    
    public void getCmpExpr() throws Exception {
        getAddExpression();
        Token token = m_lexer.lookAheadToken();
        while(token.m_type == Token.Type.LESS || token.m_type == Token.Type.EQUAL || token.m_type == Token.Type.GREATER) {
            m_lexer.advance();
            getAddExpression();
            if(token.m_type == Token.Type.LESS) {
                m_compileEnv.addInstr(new CompareLessInstruction());
            } else if(token.m_type == Token.Type.EQUAL) {
                m_compileEnv.addInstr(new CompareEqualInstruction());
            } else if(token.m_type == Token.Type.GREATER) {
                m_compileEnv.addInstr(new CompareGreaterInstruction());
            }
            token = m_lexer.lookAheadToken();
        }
    }
    
    
    public void getBitwiseOrExpr() throws Exception {
        getBitwiseAndExpr();
        Token token = m_lexer.lookAheadToken();
        while(token.m_type == Token.Type.BITWISEOR) {
            m_lexer.advance();
            getBitwiseAndExpr();
            m_compileEnv.addInstr(new BitwiseOrExpression());
            token = m_lexer.lookAheadToken();
        }
    }
    
    public void getBitwiseAndExpr() throws Exception {
        getCmpExpr();
        Token token = m_lexer.lookAheadToken();
        while(token.m_type == Token.Type.BITWISEAND) {
            m_lexer.advance();
            getCmpExpr();
            m_compileEnv.addInstr(new BitwiseAndExpression());
            token = m_lexer.lookAheadToken();
        }
    }
    
    public void getOrExpr() throws Exception {
        getAndExpr();
        Token token = m_lexer.lookAheadToken();
        while(token.m_type == Token.Type.OR) {
            m_lexer.advance();
            getAndExpr();
            m_compileEnv.addInstr(new OrExpression());
            token = m_lexer.lookAheadToken();
        }
    }
    
    public void getAndExpr() throws Exception {
        getBitwiseOrExpr();
        Token token = m_lexer.lookAheadToken();
        while(token.m_type == Token.Type.AND) {
            m_lexer.advance();
            getBitwiseOrExpr();
            m_compileEnv.addInstr(new AndExpression());
            token = m_lexer.lookAheadToken();
        }
    }
}
