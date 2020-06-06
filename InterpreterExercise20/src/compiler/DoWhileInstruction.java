package compiler;

import java.io.*;

public class DoWhileInstruction implements InstrIntf {
    
    private final InstrBlock body;
    
    public DoWhileInstruction(InstrBlock body) {
        this.body = body;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.execute(body.getIterator());
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
