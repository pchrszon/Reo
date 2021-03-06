package nl.cwi.reo.interpret.terms;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

import nl.cwi.reo.interpret.Scope;
import nl.cwi.reo.interpret.components.Component;
import nl.cwi.reo.interpret.components.ComponentExpression;
import nl.cwi.reo.interpret.variables.Identifier;
import nl.cwi.reo.semantics.Semantics;
import nl.cwi.reo.util.Monitor;

/**
 * Interpretation of a component definition expression as a term.
 * 
 * @param <T>
 *            Reo semantics type
 */
public class ComponentTermExpression<T extends Semantics<T>> implements TermExpression {

	/**
	 * Component definition.
	 */
	private ComponentExpression<T> component;

	/**
	 * Constructs a new component definition term
	 * 
	 * @param component
	 *            component definition
	 */
	public ComponentTermExpression(ComponentExpression<T> component) {
		if (component == null)
			throw new NullPointerException();
		this.component = component;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public List<Term> evaluate(Scope s, Monitor m) {
		Component<T> comp = this.component.evaluate(s, m);
		if (comp == null)
			return null;
		return Arrays.asList(comp);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Identifier> getVariables() {
		return component.getVariables();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return component.toString();
	}
}
