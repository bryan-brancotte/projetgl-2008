package graphNetwork;

import java.util.Collection;

public interface RouteReader {

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * @uml.property name="id"
	 */
	public abstract String getId();

	/**
	 */
	public abstract Collection<StationReader> getStationReader();

}