package compiler;

import java.io.*;

public class JumpInstruction implements InstrIntf {
    
    private final InstrBlock block;
    
    public JumpInstruction(InstrBlock block) {
        this.block = block;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.setInstrIter(block.getIterator());
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
