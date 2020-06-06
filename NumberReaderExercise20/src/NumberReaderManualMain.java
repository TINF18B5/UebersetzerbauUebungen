import java.io.FileInputStream;

public class NumberReaderManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		String fileName = args[0];
		FileInputStream inputStream = new FileInputStream(fileName);
		compiler.FileReaderIntf fileReader = new compiler.FileReader(inputStream);
		compiler.NumberReaderIntf numberReader = new compiler.NumberReader(fileReader);
		int number = numberReader.getNumber();
		System.out.print("Number is: ");
		System.out.println(number);
		System.out.println("END");
	}

}

