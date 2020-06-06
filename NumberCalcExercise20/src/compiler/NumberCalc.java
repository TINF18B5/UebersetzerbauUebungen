package compiler;

public class NumberCalc implements NumberCalcIntf {
    
    private final FileReaderIntf fileReader;
    private final NumberReader numberReader;
    
    public NumberCalc(FileReaderIntf fileReader) {
        this.fileReader = fileReader;
        this.numberReader = new NumberReader(fileReader);
    }
    
    @Override
    public int getSum() throws Exception {
        int sum = getProduct();
        while(fileReader.lookAheadChar() == '+' || fileReader.lookAheadChar() == '-') {
            final char lookAheadChar = fileReader.lookAheadChar();
            fileReader.advance();
            if(lookAheadChar == '+') {
                sum += getProduct();
            } else {
                sum -= getProduct();
            }
        }
        return sum;
    }
    
    @Override
    public int getProduct() throws Exception {
        int product = getFactor();
        while(fileReader.lookAheadChar() == '*' || fileReader.lookAheadChar() == '/') {
            final char lookAheadChar = fileReader.lookAheadChar();
            fileReader.advance();
            if(lookAheadChar == '*') {
                product *= getFactor();
            } else {
                product /= getFactor();
            }
        }
        return product;
    }
    
    @Override
    public int getFactor() throws Exception {
        if(fileReader.lookAheadChar() == '-') {
            fileReader.advance();
            return -getFactor();
        }
        return getAtomic();
    }
    
    public int getAtomic() throws Exception {
        if(fileReader.lookAheadChar() == '(') {
            fileReader.advance();
            final int atomic = getSum();
            fileReader.expect(')');
            return atomic;
        }
        
        return numberReader.getNumber();
    }
}
