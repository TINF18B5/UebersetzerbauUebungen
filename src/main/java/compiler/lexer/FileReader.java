package compiler.lexer;

import java.io.*;

public class FileReader {
    
    private final Reader m_inputStreamReader;
    private String m_currentLine;
    private int m_currentLineNextPos;
    private boolean m_eofAfterCurrentLine;
    private char m_nextChar;
    
    public FileReader(InputStream inputStream) throws Exception {
        m_inputStreamReader = new InputStreamReader(inputStream);
        m_currentLine = "";
        m_currentLineNextPos = 0;
        m_eofAfterCurrentLine = false;
        advance();
    }
    
    public static FileReader fromFileName(String fileName) throws Exception {
        FileInputStream inputStream = new FileInputStream(fileName);
        return new FileReader(inputStream);
    }
    
    public char lookAheadChar() {
        return m_nextChar;
    }
    
    public void advance() throws Exception {
        while(m_currentLineNextPos == m_currentLine.length()) {
            if(m_eofAfterCurrentLine) {
                m_nextChar = 0;
                return;
            } else {
                readNextLine();
                m_currentLineNextPos = 0;
            }
        }
        m_nextChar = m_currentLine.charAt(m_currentLineNextPos);
        m_currentLineNextPos++;
    }
    
    public String getCurrentLocationMsg() {
        StringBuilder location = new StringBuilder(m_currentLine);
        for(int i = 0; i < m_currentLineNextPos; ++i) {
            location.append(' ');
        }
        location.append("^\n");
        return location.toString();
    }
    
    private void readNextLine() throws Exception {
        final StringBuilder lineBuilder = new StringBuilder();
        m_currentLine = "";
        while(true) {
            int nextChar = m_inputStreamReader.read();
            if(nextChar == -1) {
                m_eofAfterCurrentLine = true;
                break;
            }
            if(nextChar != '\r') {
                if(nextChar == '\n') {
                    lineBuilder.append('\n');
                    break;
                } else {
                    lineBuilder.append((char) nextChar);
                }
            }
        }
        m_currentLine = lineBuilder.toString();
    }
}
