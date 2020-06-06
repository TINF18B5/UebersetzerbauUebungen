package test;

import compiler.*;
import test.TestCase.*;

public class TestSuite extends TestSuiteIntf {
    
    public TestSuite(FileReaderIntf fileReaderIntf, TestCase testCase) {
        super(fileReaderIntf, testCase);
    }
    
    @Override
    void readAndExecuteTestSequence() throws Exception {
        while(m_fileReader.lookAheadChar() != 0) {
            readAndExecuteTestCase();
        }
    }
    
    @Override
    void readAndExecuteTestCase() throws Exception {
        readDollarIn();
        final String input = readTestContent();
        readDollarOut();
        final String expectedOut = readTestContent();
        
        
        executeTestCase(input, expectedOut);
    }
    
    @Override
    String readTestContent() throws Exception {
        final StringBuilder stringBuilder = new StringBuilder();
        char c = m_fileReader.lookAheadChar();
        
        //No EoF and no $
        while(c != 0 && c != '$') {
            stringBuilder.append(c);
            m_fileReader.advance();
            c = m_fileReader.lookAheadChar();
        }
        
        return stringBuilder.toString();
    }
    
    @Override
    void readDollarIn() throws Exception {
        m_fileReader.expect('$');
        m_fileReader.expect('I');
        m_fileReader.expect('N');
        readOptNewLine();
    }
    
    @Override
    void readDollarOut() throws Exception {
        m_fileReader.expect('$');
        m_fileReader.expect('O');
        m_fileReader.expect('U');
        m_fileReader.expect('T');
        readOptNewLine();
    }
    
    void readOptNewLine() throws Exception {
        if(m_fileReader.lookAheadChar() == '\r') {
            m_fileReader.advance();
        }
        if(m_fileReader.lookAheadChar() == '\n') {
            m_fileReader.advance();
        }
    }
}
