package nl.cwi.reo.interpret.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

import nl.cwi.reo.interpret.Expression;
import nl.cwi.reo.interpret.Scope;
import nl.cwi.reo.interpret.terms.Range;
import nl.cwi.reo.interpret.terms.Term;
import nl.cwi.reo.interpret.terms.TermExpression;
import nl.cwi.reo.interpret.values.IntegerValue;
import nl.cwi.reo.util.Location;
import nl.cwi.reo.util.Monitor;

/**
 * Interpretation of a variable expression.
 */
public class VariableExpression implements Expression<List<? extends Identifier>> {

	/**
	 * Fully qualified name.
	 */
	private final String name;

	/**
	 * Indices of this variable.
	 */
	private final List<TermExpression> indices;

	/**
	 * Location of this variable in Reo source file.
	 */
	private final Location location;

	/**
	 * Constructs a variable list.
	 * 
	 * @param name
	 *            name of the node
	 * @param indices
	 *            list of indices
	 * @param location
	 *            location of variable in Reo source file
	 */
	public VariableExpression(String name, List<TermExpression> indices, Location location) {
		if (name == null || indices == null)
			throw new NullPointerException();
		this.name = name;
		this.indices = Collections.unmodifiableList(indices);
		this.location = location;
	}

	/**
	 * Gets the location of this variable expression in the Reo source file.
	 * 
	 * @return location of this variable.
	 */
	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public List<TermExpression> getIndices() {
		return indices;
	}

	/**
	 * Finds values of all parameters in the indices, given that the size of the
	 * evaluated variable expression equals a given size.
	 * 
	 * @param size
	 *            actual length of evaluated variable expression.
	 * @return a map that assigns each parameter name to an integer value, if
	 *         these values can be found, and null otherwise.
	 */
	public Scope findParamFromSize(int size) {
		Scope params = new Scope();

		Range rng = null;
		for (TermExpression te : indices) {
			if (te instanceof Range && rng == null)
				rng = (Range) te;
			else if (!(te instanceof IntegerValue))
				return null;
		}

		if (rng == null) {
			if (size == 1)
				return params;
			else
				return null;
		} else {
			return rng.findParamFromSize(size);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public List<? extends Identifier> evaluate(Scope s, Monitor m) {
		List<List<Term>> ranges = new ArrayList<List<Term>>();
		for (TermExpression e : indices) {
			List<Term> terms = e.evaluate(s, m);
			if (terms == null)
				return null;
			ranges.add(terms);
		}
		List<Identifier> ids = Arrays.asList(new Identifier(name));
		List<Identifier> temp;
		for (List<Term> r : ranges) {
			temp = new ArrayList<Identifier>();
			for (Term t : r) { // TODO Is this the correct order?
				for (Identifier x : ids) {
					if (t instanceof IntegerValue) {
						temp.add(new Identifier(x.name + "[" + t.toString() + "]"));
					} else {
						m.add(location,"Variable " + this.toString() + " cannot be evaluated.");
						return null;
					}
				}
			}
			ids = temp;
		}
		return ids;
	}

	/**
	 * Gets the of variables used in this expression that are not defined
	 * locally. The set need not be complete, because variable indices are
	 * ignored.
	 * 
	 * @return set of undefined variables.
	 */
	public Set<Identifier> getVariables() {
		Set<Identifier> vars = new HashSet<Identifier>();
		// TODO Check if this works
		if (indices.isEmpty()) 
			vars.add(new Identifier(name));
		return vars;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = name;
		for (TermExpression x : this.indices)
			s += "[" + x + "]";
		return s;
	}

}
