package test;

import compiler.*;

/**
 * Read the whole file and output it
 */
public class TestCase implements TestCaseIntf {
    
    @Override
    public String executeTest(FileReaderIntf fileReader) throws Exception {
        final StringBuilder stringBuilder = new StringBuilder();
    
        char lookAheadChar = fileReader.lookAheadChar();
        while(lookAheadChar != 0) {
            stringBuilder.append(lookAheadChar);
            fileReader.advance();
            lookAheadChar = fileReader.lookAheadChar();
        }
    
        return stringBuilder.toString();
    }
}
