package nl.cwi.reo.interpret;

import java.io.File;
import java.util.List;

import org.stringtemplate.v4.ST;

import nl.cwi.reo.interpret.statements.Conjunction;
import nl.cwi.reo.interpret.values.Value;
import nl.cwi.reo.interpret.variables.Identifier;
import nl.cwi.reo.semantics.Semantics;
import nl.cwi.reo.util.Location;
import nl.cwi.reo.util.Monitor;

/**
 * Interpretation of a Reo source file.
 * 
 * @param <T>
 *            Reo semantics type
 */
public final class ReoFile<T extends Semantics<T>> {

	/**
	 * File name.
	 */
	private final String filename;

	/**
	 * Section.
	 */
	private final String section;

	/**
	 * List of fully qualified names of imported components.
	 */
	private final List<String> imports;

	/**
	 * Name of main component.
	 */
	private final String main;

	/**
	 * Component definitions.
	 */
	private final Conjunction definitions;

	/**
	 * Location of main component name.
	 */
	private final Location location;

	/**
	 * Constructs a new Reo source file.
	 * 
	 * @param section
	 *            section name
	 * @param imports
	 *            list of imported components
	 * @param name
	 *            file name without extension
	 * @param definitions
	 *            component definitions
	 */
	public ReoFile(String section, List<String> imports, String filename, Conjunction definitions, Location location) {
		if (section == null || imports == null || filename == null || definitions == null || location == null)
			throw new NullPointerException();
		this.filename = filename;
		this.section = section;
		this.imports = imports;
		this.main = new File(filename).getName().split("\\.")[0];
		this.definitions = definitions;
		this.location = location;
	}

	/**
	 * Gets the list of imported components.
	 * 
	 * @return list of imports
	 */
	public List<String> getImports() {
		return imports;
	}

	/**
	 * Gets the fully qualified name of this component.
	 * 
	 * @return fully qualified name
	 */
	public String getName() {
		return section.equals("") ? main : section + "." + main;
	}
	

	/**
	 * Gets the location of the main component.
	 * 
	 * @return location of main component
	 */
	public Location getMainLocation() {
		return location;
	}
	
	/**
	 * 
	 * @return
	 */
	public Conjunction getDefinitions() {
		return definitions;
	}

	/**
	 * Evaluates this Reo source file and either adds a new component definition
	 * to the scope, or an error message to the monitor.
	 * 
	 * @param s		variable assignment
	 * @param m		message container
	 */
	public void evaluate(Scope s, Monitor m) {
		List<Scope> list = definitions.evaluate(s, m);
		if (list != null && list.size() == 1) {
			Value mainComp = list.get(0).get(new Identifier(main));

			if (mainComp != null) 
				s.put(new Identifier(getName()), mainComp);
			else
				m.add(filename + " must define a component " + main + ".");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		ST st = new ST("<section><definitions>");
		st.add("section", section.equals("") ? "" : "section " + section + ";\n");
//		st.add("imports", imports);
		st.add("definitions", definitions);
		return st.render();
	}
}
