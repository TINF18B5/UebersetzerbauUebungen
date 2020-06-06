package compiler;

import java.io.*;
import java.util.*;

public class WhileInstruction implements InstrIntf {
    
    private final InstrBlock headerBlock;
    
    public WhileInstruction(InstrBlock headerBlock) {
        this.headerBlock = headerBlock;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.execute(headerBlock.getIterator());
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
