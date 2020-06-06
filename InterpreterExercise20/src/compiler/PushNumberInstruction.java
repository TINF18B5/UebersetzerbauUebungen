package compiler;

import java.io.*;

public class PushNumberInstruction implements InstrIntf{
    
    private final int num;
    
    public PushNumberInstruction(int num) {
        this.num = num;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) {
        env.pushNumber(num);
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
