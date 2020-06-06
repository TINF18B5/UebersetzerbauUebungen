package compiler;

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
	
	public char lookAheadChar() {
		return m_nextChar;
	}
	
	public void advance() throws Exception {
		while (m_currentLineNextPos == m_currentLine.length()) {
			if (m_eofAfterCurrentLine) {
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
	
	public void expect(char c) throws Exception {
		if (m_nextChar != c) {
			String msg = "unexpected character: ";
			msg += m_nextChar;
			msg += "\nExpected: ";
			msg += c;
			throw new Exception(msg);
		}
		advance();
	}

	public void expect(String s) throws Exception {
		for (int i=0; i<s.length(); ++i) {
			if (m_nextChar != s.charAt(i)) {
				String msg = "unexpected character: ";
				msg += m_nextChar;
				msg += "\nExpected: ";
				msg += s.charAt(i);
				throw new Exception(msg);
			}
			advance();
		}
	}

	public String getCurrentLocationMsg() {
		StringBuilder location = new StringBuilder(m_currentLine);
		for (int i=0; i<m_currentLineNextPos; ++i) {
			location.append(' ');
		}
		location.append("^\n");
		return location.toString();
	}

	private void readNextLine() throws Exception {
		m_currentLine = "";
		while (true) {
			int nextChar = m_inputStreamReader.read();
			if (nextChar == -1) {
				m_eofAfterCurrentLine = true;
				return;
			} else if (nextChar == '\r') {
			    continue;
            } else if (nextChar == '\n') {
            	m_currentLine += '\n';
            	return;
            } else {
            	m_currentLine += (char)nextChar;
            }
		}
	}
	
	public static FileReader fromFileName(String fileName) throws Exception {
		FileInputStream inputStream = new FileInputStream(fileName);
        return new FileReader(inputStream);
	}
}
