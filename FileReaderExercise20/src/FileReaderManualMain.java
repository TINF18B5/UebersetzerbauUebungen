import java.io.FileInputStream;
import java.io.OutputStreamWriter;

public class FileReaderManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");
		String fileName = args[0];
		FileInputStream inputStream = new FileInputStream(fileName);
		compiler.FileReaderIntf fileReader = new compiler.FileReader(inputStream);
		FilePrinterIntf filePrinter = new FilePrinter();
		OutputStreamWriter outStream = new OutputStreamWriter(System.out);
		filePrinter.print(fileReader, outStream);
		outStream.flush();
		System.out.println("END");
	}

}
