package compiler;

public class Token {
    
    public Token.Type m_type;
    public int m_intValue;
    public String m_stringValue;
    
    public Token(Type m_type, String content) {
        this.m_type = m_type;
        this.m_intValue = -1;
        this.m_stringValue = content;
    }
    
    public Token(Type m_type, int content) {
        this.m_type = m_type;
        this.m_intValue = content;
        this.m_stringValue = null;
    }
    
    public Token(Type m_type) {
        this.m_type = m_type;
        this.m_intValue = -1;
        this.m_stringValue = null;
    }
    
    @Override
    public String toString() {
        if(!m_type.hasValue)
            return m_type.toString();
        return m_type + " " + (m_type.numeric ? m_intValue : m_stringValue);
    }
    
    public enum Type {
        EOF, IDENT(true, false), INTEGER(true, true), PLUS(false, false), MUL, LPAREN, RPAREN, ASSIGN;
        
        private final boolean hasValue;
        private final boolean numeric;
        
        Type(boolean hasValue, boolean numeric) {
            this.hasValue = hasValue;
            this.numeric = numeric;
        }
        
        Type() {
            this.hasValue = false;
            this.numeric = false;
        }
        
        public boolean isHasValue() {
            return hasValue;
        }
        
        public boolean isNumeric() {
            return numeric;
        }
    }
}
