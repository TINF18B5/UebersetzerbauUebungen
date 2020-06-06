package compiler;

import java.io.*;

public class HelloWorld implements HelloWorldIntf {
    
    private final InputStreamReader inputStreamReader;
    
    public HelloWorld(FileInputStream inputStream) throws Exception {
        this.inputStreamReader = new InputStreamReader(inputStream);
    }
    
    @Override
    public String getName() {
        try {
            return new BufferedReader(inputStreamReader).readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
}
