import java.io.FileInputStream;

public class NumberAdderManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		String fileName = args[0];
		FileInputStream inputStream = new FileInputStream(fileName);
		compiler.FileReaderIntf fileReader = new compiler.FileReader(inputStream);
		compiler.NumberAdderIntf numberAdder = new compiler.NumberAdder(fileReader);
		int sum = numberAdder.getSum();
		System.out.println(sum);
		System.out.println("END");
	}

}
