package nl.cwi.reo.automata;

import nl.cwi.pr.misc.IdObjectSpec;
import nl.cwi.reo.automata.AutomatonFactory.Automaton;
import nl.cwi.reo.automata.LiteralFactory.LiteralSet;

public class ConstraintSpec implements IdObjectSpec {
	private final Automaton automaton;
	private final LiteralSet literals;

	//
	// CONSTRUCTORS
	//

	public ConstraintSpec(Automaton automaton, LiteralSet literals) {
		if (automaton == null)
			throw new NullPointerException();
		if (literals == null)
			throw new NullPointerException();

		this.automaton = automaton;
		this.literals = literals;
	}

	//
	// METHODS - PUBLIC
	//

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			throw new NullPointerException();

		return obj instanceof ConstraintSpec && equals((ConstraintSpec) obj);
	}

	public boolean equals(ConstraintSpec spec) {
		if (spec == null)
			throw new NullPointerException();

		return automaton.equals(spec.automaton)
				&& literals.equals(spec.literals);
	}

	public Automaton getAutomaton() {
		return automaton;
	}
	
	public LiteralSet getLiterals() {
		return literals;
	}

	@Override
	public int hashCode() {
		return literals.hashCode();
	}
}