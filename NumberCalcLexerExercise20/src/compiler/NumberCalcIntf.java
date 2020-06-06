package compiler;

public interface NumberCalcIntf {
	
    // creates a NumberCalc for a given FileReader
	// NumberCalcIntf(FileReaderIntf reader)
	
	// read sum
	int getSum() throws Exception;

	// read product
	int getProduct() throws Exception;
	
	// read factor
	int getFactor() throws Exception;

}
