
public class NumberCalcTest implements test.TestCaseIntf {

	public String executeTest(compiler.FileReaderIntf fileReader) throws Exception {
		String output = new String();
		compiler.NumberCalcIntf numberAdder = new compiler.NumberCalc(fileReader);
		int number = numberAdder.getSum();
		output += Integer.toString(number);
		output += '\r';
		output += '\n';
		return output;
	}
}
