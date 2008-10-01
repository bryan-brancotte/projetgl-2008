package graphNetwork;

import java.util.Collection;

public interface StationReader {

	/**
	 * Getter of the property <tt>interchange</tt>
	 * 
	 * @return Returns the interchange.
	 * @uml.property name="interchange"
	 */
	public abstract Collection<InterReader> getInterchange();

	/** 
	 * Getter of the property <tt>name</tt>
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public abstract String getName();

	/** 
	 * Getter of the property <tt>id</tt>
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public abstract int getId();

}