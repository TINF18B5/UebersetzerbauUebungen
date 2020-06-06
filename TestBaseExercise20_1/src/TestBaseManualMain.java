import compiler.FileReader;
import test.*;

public class TestBaseManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		test.TestSuiteIntf test = new test.TestSuite(FileReader.fromFileName(args[0]), new TestCase());
		test.testRun();
		System.out.println("END");
	}

}
