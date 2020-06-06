package compiler;

import java.util.ArrayList;
import java.util.Iterator;

public class InstrBlock {
	private ArrayList<InstrIntf> m_instrList;

	public InstrBlock() {
		m_instrList = new ArrayList<InstrIntf>();
	}
	
	public void addInstr(InstrIntf instr) {
		m_instrList.add(instr);
	}
	
	public Iterator<InstrIntf> getIterator() {
		return m_instrList.listIterator();
	}
	
}
