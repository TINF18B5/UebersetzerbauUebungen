import java.io.OutputStreamWriter;

import compiler.FileReaderIntf;

public class FilePrinter implements FilePrinterIntf {

	@Override
	public void print(FileReaderIntf fileReader, OutputStreamWriter outStream) throws Exception {
		while (true) {
			char nextChar = fileReader.lookAheadChar();
			if (nextChar == 0) {
				break;
			}
			int charNumeric = (int)nextChar; 
			outStream.append(Integer.toString(charNumeric));
			outStream.append(" ");
			outStream.append(nextChar);
			outStream.append('\n');
			fileReader.advance();
		}
	}

}
