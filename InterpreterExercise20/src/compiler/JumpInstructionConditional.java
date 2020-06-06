package compiler;

import java.io.*;

public class JumpInstructionConditional implements InstrIntf {
    
    private final InstrBlock onIf;
    private final InstrBlock onElse;
    
    public JumpInstructionConditional(InstrBlock onIf, InstrBlock onElse) {
        this.onIf = onIf;
        this.onElse = onElse;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        final int i = env.popNumber();
        if(i != 0) {
            env.setInstrIter(onIf.getIterator());
        } else {
            env.setInstrIter(onElse.getIterator());
        }
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
