package compiler;

import java.io.*;

public class CompareEqualInstruction implements InstrIntf {
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        final int b = env.popNumber();
        final int a = env.popNumber();
        env.pushNumber(a == b ? 1 : 0);
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
