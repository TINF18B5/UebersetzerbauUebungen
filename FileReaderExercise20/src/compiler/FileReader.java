package compiler;

import java.io.*;

public class FileReader implements FileReaderIntf {
    
    private final InputStreamReader inputStreamReader;
    private char lookAheadChar = 0;
    
    
    public FileReader(FileInputStream inputStream) throws Exception{
        inputStreamReader = new InputStreamReader(inputStream);
        advance();
    }
    
    @Override
    public char lookAheadChar() {
        return lookAheadChar;
    }
    
    @Override
    public void advance() throws Exception {
        if(inputStreamReader.ready()) {
            lookAheadChar = (char) inputStreamReader.read();
        } else {
            lookAheadChar = 0;
        }
    }
}
