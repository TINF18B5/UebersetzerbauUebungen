package compiler.lexer;

public class Token {
    
    public TokenType m_type;
    private String value;
    private int numValue;
    
    public int getNumericValue() {
        if(this.m_type.isNumeric) {
            return this.numValue;
        } else {
            return Integer.parseInt(this.value, 10);
        }
    }
    
    public String getValue() {
        if(this.m_type.isNumeric) {
            return Integer.toString(this.numValue, 10);
        } else {
            return this.value;
        }
    }
    
    public void setValue(String s) {
        if(this.m_type.isNumeric) {
            this.numValue = Integer.parseInt(s, 10);
        } else {
            this.value = s;
        }
    }
    
    public void setValue(int i) {
        if(this.m_type.isNumeric) {
            this.numValue = i;
        } else {
            this.value = String.valueOf(i);
        }
    }
}
