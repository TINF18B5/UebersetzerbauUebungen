package compiler;

import java.io.*;

public class UnaryMinusInstruction implements InstrIntf {
    
    @Override
    public void execute(ExecutionEnvIntf env) {
        final int i = env.popNumber();
        env.pushNumber(-i);
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
