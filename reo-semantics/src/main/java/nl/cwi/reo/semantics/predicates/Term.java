package nl.cwi.reo.semantics.predicates;

import java.util.Map;
import java.util.Set;

import nl.cwi.reo.interpret.ports.Port;
import nl.cwi.reo.interpret.typetags.TypeTag;

public interface Term {

	public boolean hadOutputs();

	public Term rename(Map<Port, Port> links);

	/**
	 * Substitutes a term t for every occurrence of a variable x in this term.
	 * 
	 * @param t
	 *            substituted term
	 * @param x
	 *            free variable
	 * @return substituted term.
	 */
	public Term Substitute(Term t, Variable x);

	/**
	 * Gets the set of all free variables in this term.
	 * 
	 * @return set of all free variables.
	 */
	public Set<Variable> getFreeVariables();

	/**
	 * Gets the type tag of this term.
	 * 
	 * @return type tag of this term.
	 */
	public TypeTag getTypeTag();
}
