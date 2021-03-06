/**
 * 
 */
package nl.cwi.reo.compile.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.cwi.reo.interpret.ports.Port;

/**
 * Compiled atomic component that is independent of the target language.
 */
public final class Atomic implements Component {
	
	/**
	 * Flag for string template.
	 */
	public final boolean atomic = true;

	private final String name;
	
	private final List<String> params;

	private final Set<Port> ports;
	
	private final Map<Port,Integer> listPort = new HashMap<>();
	
	private final String call;

	public Atomic(String name, List<String> params, Set<Port> ports, String call) {
		this.name = name;
		this.params = params;
		this.ports = ports;
		this.call = call;
	}

	public String getName() {
		return name;
	}
	
	public List<String> getParameters() {
		return params;
	}

	public Map<Port,Integer> getListPort() {
		int i=0;
		for(Port p : ports){
			listPort.put(p,i);
			i++;
		}
		return listPort;
	}
	
	public Set<Port> getPorts() {
		return ports;
	}
	
	public String getCall() {
		return call;
	}
}
