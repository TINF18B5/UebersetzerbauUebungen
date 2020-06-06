package compiler;

public interface StmtReaderIntf {

	// constructs a statement reader from a given lexer
	// public StmtIntf(LexerIntf lexer, CompileEnvIntf compileEnv) throws Exception;
	
    // get statement list
	public void getStmtList() throws Exception;

	// get next statement
	public void getStmt() throws Exception;
    
    void getCallStmt() throws Exception;
    
    void getFunctionDefinition() throws Exception;
    
    void getIfStmt() throws Exception;
    
    void getForStmt() throws Exception;
    
    void getWhileStmt() throws Exception;
    
    void getDoWhileStmt() throws Exception;
    
    // get assign statement
	public void getAssign() throws Exception;

	// get print statement
	public void getPrint() throws Exception;

	// get block statement
	public void getBlockStmt() throws Exception;
}
