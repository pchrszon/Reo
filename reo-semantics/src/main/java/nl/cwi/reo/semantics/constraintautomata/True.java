package nl.cwi.reo.semantics.constraintautomata;

import java.util.Collection;
import java.util.Map;

import nl.cwi.reo.interpret.Scope;
import nl.cwi.reo.interpret.ports.Port;
import nl.cwi.reo.util.Monitor;

public final class True implements Formula {
	
	public True() {
	}

	@Override
	public Formula evaluate(Scope s, Monitor m) {
		return null;
	}

	@Override
	public String getInternalName(Collection<? extends Port> intface) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term findAssignment(String variable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Formula substitute(String variable, Term expression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Formula rename(Map<Port, Port> links) {
		// TODO Auto-generated method stub
		return null;
	}
}
