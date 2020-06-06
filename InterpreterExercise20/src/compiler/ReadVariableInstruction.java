package compiler;

import java.io.*;

public class ReadVariableInstruction implements InstrIntf {
    
    private final String name;
    
    public ReadVariableInstruction(String name) {
        this.name = name;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) {
        final Symbol symbol = env.getSymbol(name);
        env.pushNumber(symbol.m_number);
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
