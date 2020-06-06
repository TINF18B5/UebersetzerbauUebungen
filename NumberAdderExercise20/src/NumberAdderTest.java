
public class NumberAdderTest implements test.TestCaseIntf {

	public String executeTest(compiler.FileReaderIntf fileReader) throws Exception {
		String output = new String();
		compiler.NumberAdderIntf numberAdder = new compiler.NumberAdder(fileReader);
		int number = numberAdder.getSum();
		output += Integer.toString(number);
		output += '\r';
		output += '\n';
		return output;
	}
}
