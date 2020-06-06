package compiler;

import java.io.*;

public class AssignInstruction implements InstrIntf {
    
    private final Symbol variable;
    
    public AssignInstruction(Symbol variable) {
        this.variable = variable;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) {
        variable.m_number = env.popNumber();
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
