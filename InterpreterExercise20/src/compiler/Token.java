package compiler;

public class Token {
	public enum Type {
		EOF,
		IDENT,
		INTEGER,
		PLUS,
		MINUS,
		MUL,
		DIV,
		BITWISEAND,
		BITWISEOR,
		AND,
		OR,
		LPAREN,
		RPAREN,
		ASSIGN,
		COMMA,
		SEMICOL,
		PRINT,
		IF,
		ELSE,
		WHILE,
		DO,
		FOR,
		CALL,
		LBRACE,
		RBRACE,
		LESS,
		EQUAL,
		GREATER,
		FUNCTION
	}

	public Type m_type;
	public int m_intValue; 
	public String m_stringValue;

	public String toString() {
		String s = type2String(m_type);
		if (m_type == Type.IDENT) {
			s += ' ';
			s += m_stringValue;
		} else if (m_type == Type.INTEGER) {
			s += ' ';
			s += Integer.toString(m_intValue);
		}
		return s;
	}
	
	static String type2String(Type type) {
		if (type == Type.EOF) {
			return "EOF";
		} else if (type == Type.IDENT) {
			return "IDENT";
		} else if (type == Type.INTEGER) {
			return "INTEGER";
		} else if (type == Type.PLUS) {
			return "PLUS";
		} else if (type == Type.MINUS) {
			return "MINUS";
		} else if (type == Type.MUL) {
			return "MUL";
		} else if (type == Type.DIV) {
			return "DIV";
		} else if (type == Type.BITWISEAND) {
			return "BITWISEAND";
		} else if (type == Type.BITWISEOR) {
			return "BITWISEOR";
		} else if (type == Type.AND) {
			return "AND";
		} else if (type == Type.OR) {
			return "OR";
		} else if (type == Type.LPAREN) {
			return "LPAREN";
		} else if (type == Type.RPAREN) {
			return "RPAREN";
		} else if (type == Type.ASSIGN) {
			return "ASSIGN";
		} else if (type == Type.COMMA) {
			return "SEMICOL";
		} else if (type == Type.SEMICOL) {
			return "SEMICOL";
		} else if (type == Type.PRINT) {
			return "PRINT";
		} else if (type == Type.IF) {
			return "IF";
		} else if (type == Type.ELSE) {
			return "ELSE";
		} else if (type == Type.WHILE) {
			return "WHILE";
		} else if (type == Type.DO) {
			return "DO";
		} else if (type == Type.FOR) {
			return "FOR";
		} else if (type == Type.CALL) {
			return "CALL";
		} else if (type == Type.LBRACE) {
			return "LBRACE";
		} else if (type == Type.RBRACE) {
			return "RBRACE";
		} else if (type == Type.LESS) {
			return "LESS";
		} else if (type == Type.EQUAL) {
			return "EQUAL";
		} else if (type == Type.GREATER) {
			return "GREATER";
		} else if (type == Type.FUNCTION) {
			return "FUNCTION";
		} else {
			return null;
		}
		
	}
}
