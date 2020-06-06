package compiler;

import java.util.*;

public class FunctionSymbol {
    public final String name;
    public List<String> header;
    public InstrBlock body;
    
    public FunctionSymbol(String name) {
        this.name = name;
    }
}
