public class NumberCalcTest implements test.TestCaseIntf {
    
    public String executeTest(compiler.FileReaderIntf fileReader) throws Exception {
        compiler.NumberCalcIntf numberAdder = new compiler.NumberCalc(fileReader);
        int number = numberAdder.getSum();
        return Integer.toString(number) + '\n';
    }
}
