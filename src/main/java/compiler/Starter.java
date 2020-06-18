package compiler;

import compiler.lexer.*;
import compiler.parser.*;
import compiler.statements.*;
import compiler.xx_interpreter.direct_interpreter.*;
import compiler.xx_interpreter.stack_based_interpreter.*;

public class Starter {
    
    public static void main(String[] args) throws Exception {
        final FileReader fileReader = FileReader.fromFileName(args[0]);
        final Lexer lexer = new Lexer(fileReader);
        final StmtReader stmtReader = new StmtReader(lexer);
        final Statement statement = stmtReader.getStatement();
        
        System.out.println(statement);
        final DirectInterpreterStatementVisitor visitorDirect = new DirectInterpreterStatementVisitor(System.out);
        statement.accept(visitorDirect);
        
        final StackBasedInterpreterStatementVisitor visitorStackBased = new StackBasedInterpreterStatementVisitor(System.out);
        statement.accept(visitorStackBased);
    }
}
