package test;

import compiler.*;

public class TestSuite extends TestSuiteIntf {
    
    public TestSuite(FileReaderIntf fileReaderIntf, TestCase testCase) {
        super(fileReaderIntf, testCase);
    }
    
    @Override
    //testSequence: testCase*
    void readAndExecuteTestSequence() throws Exception {
        while(m_fileReader.lookAheadChar() != 0) {
            readAndExecuteTestCase();
        }
    }
    
    @Override
    //testCase: DOLLAR_IN TestContent DOLLAR_OUT TestContent
    void readAndExecuteTestCase() throws Exception {
        readDollarIn();
        final String in = readTestContent();
        readDollarOut();
        final String out = readTestContent();
        
        this.executeTestCase(in, out);
    }
    
    @Override
    //testContent: ~'$'*
    String readTestContent() throws Exception {
        final StringBuilder stringBuilder = new StringBuilder();
        char c = m_fileReader.lookAheadChar();
        while(c != '$' && c != 0) {
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
    }
    
    @Override
    void readDollarOut() throws Exception {
        m_fileReader.expect('$');
        m_fileReader.expect('O');
        m_fileReader.expect('U');
        m_fileReader.expect('T');
    }
}
