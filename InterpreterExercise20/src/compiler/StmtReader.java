package compiler;

import java.util.*;

public class StmtReader implements StmtReaderIntf {
    
    private final SymbolTable m_symbolTable;
    private final LexerIntf m_lexer;
    private final ExprReader m_exprReader;
    private final CompileEnvIntf m_compileEnv;
    
    public StmtReader(LexerIntf lexer, CompileEnv compileEnv) throws Exception {
        m_lexer = lexer;
        m_exprReader = new ExprReader(compileEnv.getSymbolTable(), m_lexer, compileEnv);
        m_compileEnv = compileEnv;
        m_symbolTable = compileEnv.getSymbolTable();
    }
    
    @Override
    public void getStmtList() throws Exception {
        while(m_lexer.lookAheadToken().m_type != Token.Type.EOF && m_lexer.lookAheadToken().m_type != Token.Type.RBRACE) {
            getStmt();
        }
    }
    
    @Override
    public void getStmt() throws Exception {
        Token token = m_lexer.lookAheadToken();
        if(token.m_type == Token.Type.IDENT) {
            getAssign();
        } else if(token.m_type == Token.Type.PRINT) {
            getPrint();
        } else if(token.m_type == Token.Type.LBRACE) {
            getBlockStmt();
        } else if(token.m_type == Token.Type.WHILE) {
            getWhileStmt();
        } else if(token.m_type == Token.Type.FOR) {
            getForStmt();
        } else if(token.m_type == Token.Type.IF) {
            getIfStmt();
        } else if(token.m_type == Token.Type.FUNCTION) {
            getFunctionDefinition();
        } else if(token.m_type == Token.Type.CALL) {
            getCallStmt();
        } else if(token.m_type == Token.Type.DO) {
            getDoWhileStmt();
        } else {
            throw new ParserException("Unexpected Token: ", token.toString(), m_lexer.getCurrentLocationMsg(), "begin of statement");
        }
    }
    
    @Override
    public void getCallStmt() throws Exception {
        m_lexer.advance();
        final String functionName = m_lexer.lookAheadToken().m_stringValue;
        m_lexer.expect(Token.Type.IDENT);
        
        final FunctionSymbol functionSymbol = m_compileEnv.getSymbolTable()
                .getFunctionSymbol(functionName);
        if(functionSymbol == null) {
            throw new Exception("Unknown function name " + functionName);
        }
        
        m_lexer.expect(Token.Type.LPAREN);
        int argumentCount = 0;
        while(m_lexer.lookAheadToken().m_type != Token.Type.RPAREN) {
            argumentCount++;
            m_exprReader.getExpr();
            if(m_lexer.lookAheadToken().m_type == Token.Type.RPAREN) {
                break;
            }
            m_lexer.expect(Token.Type.COMMA);
        }
        m_lexer.advance();
        if(argumentCount != functionSymbol.header.size()) {
            throw new Exception("Wrong number of arguments, expected " + functionSymbol.header.size() + " but got " + argumentCount);
        }
        m_lexer.expect(Token.Type.SEMICOL);
        m_compileEnv.addInstr(new FunctionCallInstruction(functionSymbol));
    }
    
    @Override
    public void getFunctionDefinition() throws Exception {
        m_lexer.advance();
        final String functionName = m_lexer.lookAheadToken().m_stringValue;
        m_lexer.expect(Token.Type.IDENT);
        m_lexer.expect(Token.Type.LPAREN);
        final List<String> functionHeader = getFunctionHeader();
        final FunctionSymbol functionSymbol = m_compileEnv.getSymbolTable()
                .getOrCreateFunction(functionName);
        functionSymbol.header = functionHeader;
        
        final InstrBlock currentBlock = m_compileEnv.getCurrentBlock();
        final InstrBlock functionBody = m_compileEnv.createBlock();
        functionSymbol.body = functionBody;
        m_compileEnv.setCurrentBlock(functionBody);
        getStmt();
        
        m_compileEnv.setCurrentBlock(currentBlock);
    }
    
    private List<String> getFunctionHeader() throws Exception {
        final List<String> names = new ArrayList<>();
        
        //Yes, this allows (a,b,) as header, but why not?
        while(m_lexer.lookAheadToken().m_type != Token.Type.RPAREN) {
            final String name = m_lexer.lookAheadToken().m_stringValue;
            m_lexer.expect(Token.Type.IDENT);
            if(names.contains(name)) {
                throw new Exception("Duplicate Function Parameter: " + name);
            }
            names.add(name);
            m_compileEnv.getSymbolTable().getOrCreateSymbol(name);
            if(m_lexer.lookAheadToken().m_type == Token.Type.RPAREN) {
                break;
            }
            m_lexer.expect(Token.Type.COMMA);
        }
        m_lexer.expect(Token.Type.RPAREN);
        return names;
    }
    
    @Override
    public void getIfStmt() throws Exception {
        m_lexer.advance();
        m_exprReader.getExpr();
        final InstrBlock currentBlock = m_compileEnv.getCurrentBlock();
        final InstrBlock onIf = m_compileEnv.createBlock();
        final InstrBlock onElse = m_compileEnv.createBlock();
        final InstrBlock onAfter = m_compileEnv.createBlock();
        
        m_compileEnv.setCurrentBlock(onIf);
        getStmt();
        m_compileEnv.addInstr(new JumpInstruction(onAfter));
        
        m_compileEnv.setCurrentBlock(onElse);
        if(m_lexer.lookAheadToken().m_type == Token.Type.ELSE) {
            m_lexer.advance();
            getStmt();
        }
        m_compileEnv.addInstr(new JumpInstruction(onAfter));
        
        m_compileEnv.setCurrentBlock(onAfter);
        currentBlock.addInstr(new JumpInstructionConditional(onIf, onElse));
    }
    
    @Override
    public void getForStmt() throws Exception {
        m_lexer.advance();
        m_lexer.expect(Token.Type.LPAREN);
        final InstrBlock currentBlock = m_compileEnv.getCurrentBlock();
        final InstrBlock initializer = m_compileEnv.createBlock();
        final InstrBlock condition = m_compileEnv.createBlock();
        final InstrBlock increment = m_compileEnv.createBlock();
        final InstrBlock loopBody = m_compileEnv.createBlock();
        final InstrBlock afterLoop = m_compileEnv.createBlock();
        
        
        m_compileEnv.setCurrentBlock(initializer);
        getStmt();
        m_compileEnv.addInstr(new JumpInstruction(condition));
        
        m_compileEnv.setCurrentBlock(condition);
        m_exprReader.getExpr();
        m_lexer.expect(Token.Type.SEMICOL);
        m_compileEnv.addInstr(new JumpInstructionConditional(loopBody, afterLoop));
        
        m_compileEnv.setCurrentBlock(increment);
        getStmt();
        m_lexer.expect(Token.Type.RPAREN);
        m_compileEnv.addInstr(new JumpInstruction(condition));
        
        m_compileEnv.setCurrentBlock(loopBody);
        getStmt();
        m_compileEnv.addInstr(new JumpInstruction(increment));
        
        
        currentBlock.addInstr(new ForLoop(initializer));
        m_compileEnv.setCurrentBlock(afterLoop);
    }
    
    @Override
    public void getWhileStmt() throws Exception {
        m_lexer.advance();
        final InstrBlock currentBlock = m_compileEnv.getCurrentBlock();
        final InstrBlock headerBlock = m_compileEnv.createBlock();
        final InstrBlock bodyBlock = m_compileEnv.createBlock();
        final InstrBlock after = m_compileEnv.createBlock();
        
        m_compileEnv.setCurrentBlock(headerBlock);
        m_exprReader.getExpr();
        m_compileEnv.addInstr(new JumpInstructionConditional(bodyBlock, after));
        
        m_compileEnv.setCurrentBlock(bodyBlock);
        getStmt();
        m_compileEnv.addInstr(new JumpInstruction(headerBlock));
        m_compileEnv.setCurrentBlock(after);
        currentBlock.addInstr(new WhileInstruction(headerBlock));
    }
    
    @Override
    public void getDoWhileStmt() throws Exception {
        m_lexer.advance();
        final InstrBlock currentBlock = m_compileEnv.getCurrentBlock();
        final InstrBlock body = m_compileEnv.createBlock();
        final InstrBlock condition = m_compileEnv.createBlock();
        final InstrBlock after = m_compileEnv.createBlock();
        
        m_compileEnv.setCurrentBlock(body);
        getStmt();
        m_compileEnv.addInstr(new JumpInstruction(condition));
        
        m_lexer.expect(Token.Type.WHILE);
        m_compileEnv.setCurrentBlock(condition);
        m_exprReader.getExpr();
        m_lexer.expect(Token.Type.SEMICOL);
        m_compileEnv.addInstr(new JumpInstructionConditional(body, after));
    
        m_compileEnv.setCurrentBlock(after);
        currentBlock.addInstr(new DoWhileInstruction(body));
    }
    
    @Override
    public void getAssign() throws Exception {
        Token token = m_lexer.lookAheadToken();
        String varName = token.m_stringValue;
        m_lexer.advance();
        m_lexer.expect(Token.Type.ASSIGN);
        m_exprReader.getExpr();
        Symbol var = m_symbolTable.getOrCreateSymbol(varName);
        m_lexer.expect(Token.Type.SEMICOL);
        m_compileEnv.addInstr(new AssignInstruction(var));
    }
    
    @Override
    public void getPrint() throws Exception {
        m_lexer.advance(); // PRINT
        m_exprReader.getExpr();
        m_lexer.expect(Token.Type.SEMICOL);
        m_compileEnv.addInstr(new PrintInstruction());
    }
    
    @Override
    public void getBlockStmt() throws Exception {
        m_lexer.expect(Token.Type.LBRACE);
        getStmtList();
        m_lexer.expect(Token.Type.RBRACE);
    }
    
}
