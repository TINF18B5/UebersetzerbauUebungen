package compiler;

import java.io.*;

public class ForLoop implements InstrIntf {
    
    private final InstrBlock initializer;
    
    
    public ForLoop(InstrBlock initializer) {
        this.initializer = initializer;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.execute(initializer.getIterator());
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
