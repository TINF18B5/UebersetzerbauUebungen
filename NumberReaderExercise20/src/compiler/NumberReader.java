package compiler;

public class NumberReader implements NumberReaderIntf {
    
    private final FileReaderIntf fileReader;
    
    public NumberReader(FileReaderIntf fileReader) {
        this.fileReader = fileReader;
    }
    
    @Override
    public int getNumber() throws Exception {
        int number = 0;
        char readChar = fileReader.lookAheadChar();
        if(!isDigit(readChar)){
            throw new IllegalArgumentException("Not a digit! " + readChar);
        }
        
        while(isDigit(readChar)) {
            number = number * 10 + readChar - '0';
            
            fileReader.advance();
            readChar = fileReader.lookAheadChar();
        }
        
        return number;
    }
    
    @Override
    public boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
}
