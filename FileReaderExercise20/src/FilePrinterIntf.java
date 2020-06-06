import java.io.OutputStreamWriter;

public interface FilePrinterIntf {
	
	// reads file from fileReader and prints content character wise on outStream
	void print(compiler.FileReaderIntf fileReader, OutputStreamWriter outStream) throws Exception;
}
