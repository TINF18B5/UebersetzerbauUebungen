package compiler;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class FunctionCallInstruction implements InstrIntf {
    
    private final FunctionSymbol functionSymbol;
    
    public FunctionCallInstruction(FunctionSymbol functionSymbol) {
        this.functionSymbol = functionSymbol;
    }
    
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        final List<String> headerVariables = new ArrayList<>(functionSymbol.header);
        Collections.reverse(headerVariables);
        
        final Map<String, Integer> retainedVariables = new HashMap<>();
        for(String s : headerVariables) {
            final Symbol symbol = env.getSymbol(s);
            retainedVariables.put(s, symbol.m_number);
    
            //Load function arguments
            symbol.m_number = env.popNumber();
        }
        
        env.switchToCall(functionSymbol.body.getIterator());
        retainedVariables.forEach((argumentName, valueBeforeCall) -> env.getSymbol(argumentName).m_number = valueBeforeCall);
    }
    
    @Override
    public void trace(OutputStreamWriter os) throws Exception {
    
    }
}
