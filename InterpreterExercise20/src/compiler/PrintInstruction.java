package compiler;

import java.io.*;

public class PrintInstruction implements InstrIntf {
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception{
        final int i = env.popNumber();
        final OutputStreamWriter outputStream = env.getOutputStream();
        outputStream.write(i + "\n");
        outputStream.flush();
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
