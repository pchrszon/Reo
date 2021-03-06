package nl.cwi.reo.semantics.predicates;

import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

import nl.cwi.reo.interpret.Scope;
import nl.cwi.reo.interpret.ports.Port;
import nl.cwi.reo.util.Monitor;

public interface Formula {

//	/**
//	 * Computes the condition on input ports by existentially quantifying over
//	 * output ports.
//	 * 
//	 * @return existential quantification of this constraint over all output
//	 *         ports.
//	 */
//	@Deprecated
//	public Formula getGuard();
//
//	/**
//	 * Computes an assignment of data terms to output ports that satisfies this
//	 * data constraint.
//	 * 
//	 * @return Assignment of data terms to output ports.
//	 */
//	@Deprecated
//	public Map<Variable, Term> getAssignment();

	public @Nullable Formula evaluate(Scope s, Monitor m);

	public Set<Port> getInterface();

	public Formula rename(Map<Port, Port> links);

	/**
	 * Transforms the formula in negation normal form.
	 * 
	 * @return equivalent formula in negation normal form.
	 */
	public Formula NNF();

	/**
	 * Transforms the formula into disjunctive normal form.
	 * 
	 * @return equivalent formula in disjunctive normal form.
	 */
	public Formula DNF();

	/**
	 * Tries to eliminate quantifiers via substitution.
	 * 
	 * @return equivalent formula, possibly without any quantifiers.
	 */
	public Formula QE();

	/**
	 * Substitutes a term t for every free occurrence of a variable x in this
	 * formula.
	 * 
	 * @param t
	 *            substituted term
	 * @param x
	 *            free variable
	 * @return substituted formula.
	 */
	public Formula Substitute(Term t, Variable x);

	/**
	 * Gets the set of all free variables in this formula.
	 * 
	 * @return set of all free variables.
	 */
	public Set<Variable> getFreeVariables();

	/**
	 * Tries to determine which variables in this formula must evaluate to null
	 * and which variables must evaluate to a non-null datum.
	 * 
	 * @return map that assigns 0 to a variable, if that variable must evaluate
	 *         to null, or 1, if that variable must evaluate to a non-null
	 *         datum, or nothing, otherwise.
	 */
	public Map<Variable, Integer> getEvaluation();

}
