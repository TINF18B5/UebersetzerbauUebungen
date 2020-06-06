package compiler;

public class NumberAdder implements NumberAdderIntf {
    
    private final FileReaderIntf fileReader;
    private final NumberReader numberReader;
    
    
    public NumberAdder(FileReaderIntf fileReader) {
        this.fileReader = fileReader;
        numberReader = new NumberReader(fileReader);
    }
    
    @Override
    public int getSum() throws Exception {
        int sum = numberReader.getNumber();
        while(fileReader.lookAheadChar() == '+') {
            fileReader.advance();
            sum += numberReader.getNumber();
        }
        return sum;
    }
}
