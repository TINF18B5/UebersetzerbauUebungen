package test.TestCase;

import compiler.*;
import test.*;

public class TestCase implements TestCaseIntf {
    
    @Override
    public String executeTest(FileReaderIntf fileReader) throws Exception {
        final StringBuilder stringBuilder = new StringBuilder();
        char c = fileReader.lookAheadChar();
        while(c != 0) {
            stringBuilder.append(c);
            fileReader.advance();
            c = fileReader.lookAheadChar();
        }
    
        return stringBuilder.toString();
    }
}
