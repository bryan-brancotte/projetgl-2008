package streamInFolder.graphReaderFolder;

import java.util.LinkedList;

public class KindRoute {

	/**
	 */
	public static void addKind(String kind) {
	}

	/**
	 * @uml.property name="kinds"
	 */
	protected static LinkedList<String> kinds;

	/**
	 */
	public static java.util.Collection<String> getKinds() {
		return null;
	}

	/**
	 */
	private KindRoute() {
	}

	/**
	 * @uml.property name="kindOf"
	 */
	private String kindOf = "";

	/**
	 * Getter of the property <tt>kind</tt>
	 * 
	 * @return Returns the kind.
	 * @uml.property name="kindOf"
	 */
	public String getKindOf() {
		return kindOf;
	}

	/**
	 * Setter of the property <tt>kind</tt>
	 * 
	 * @param kind
	 *            The kind to set.
	 * @uml.property name="kindOf"
	 */
	public void setKindOf(String kindOf) {
		this.kindOf = kindOf;
	}

	/**
	 * get the object KindRoute of the kind given in parameter.
	 * 
	 * @param kind
	 *            name of the kind we are looking for
	 * @return the KindRoute if we found it, else we return null;
	 */
	public static KindRoute getKind(String kind) {
		return null;
	}

	/**
	 * Reset all the kind, erase all the kind known, and get ready for having new kind.
	 */
	public static void reset() {
	}

}
