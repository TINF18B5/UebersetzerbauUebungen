import java.io.FileInputStream;

public class NumberCalcManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		String fileName = args[0];
		FileInputStream inputStream = new FileInputStream(fileName);
		compiler.FileReaderIntf fileReader = new compiler.FileReader(inputStream);
		compiler.NumberCalcIntf numberAdder = new compiler.NumberCalc(fileReader);
		int sum = numberAdder.getSum();
		System.out.println(sum);
		System.out.println("END");
	}

}
