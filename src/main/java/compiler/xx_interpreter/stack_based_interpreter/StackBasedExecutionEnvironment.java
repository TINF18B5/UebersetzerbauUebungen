package compiler.xx_interpreter.stack_based_interpreter;

import java.io.*;
import java.util.*;

final class StackBasedExecutionEnvironment {
    
    private final Stack<Integer> numberStack;
    private final StackBasedInterpreterSymbolTable symbolTable;
    private final StackBasedInterpreterFunctionTable functionTable;
    private final PrintStream printStream;
    
    public StackBasedExecutionEnvironment(PrintStream printStream) {
        this.printStream = printStream;
        numberStack = new Stack<>();
        symbolTable = new StackBasedInterpreterSymbolTable();
        functionTable = new StackBasedInterpreterFunctionTable();
    }
    
    public int pop() {
        return numberStack.pop();
    }
    
    public boolean popAsBool() {
        return pop() != 0;
    }
    
    public void push(int number) {
        numberStack.push(number);
    }
    
    public StackBasedInterpreterSymbolTable getSymbolTable() {
        return symbolTable;
    }
    
    public StackBasedInterpreterFunctionTable getFunctionTable() {
        return functionTable;
    }
    
    public StackBasedExecutionEnvironment createInnerEnvironment() {
        final StackBasedExecutionEnvironment stackBasedExecutionEnvironment = new StackBasedExecutionEnvironment(printStream);
        this.functionTable.putAll(stackBasedExecutionEnvironment.functionTable);
        return stackBasedExecutionEnvironment;
    }
    
    public void writeToOutput(int value) {
        printStream.println(value);
    }
}
