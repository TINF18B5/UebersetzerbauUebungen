import java.io.FileInputStream;
import java.io.OutputStreamWriter;

public class HelloWorldManualMain {

	public static void main(String[] args) throws Exception {
		System.out.println("BEGIN");

		// open input file
		String fileName = args[0];
		FileInputStream inputStream = new FileInputStream(fileName);

		// create input file processor (to be implemented)
		compiler.HelloWorldIntf helloWorld = new compiler.HelloWorld(inputStream);

		// use input file processor to create output
		OutputStreamWriter outStream = new OutputStreamWriter(System.out);
		outStream.append("Hello ");
		outStream.append(helloWorld.getName());
		outStream.append("\n");
		outStream.flush();

		System.out.println("END");
	}

}
