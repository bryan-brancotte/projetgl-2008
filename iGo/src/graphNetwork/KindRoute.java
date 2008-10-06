package graphNetwork;

import java.util.LinkedList;

public class KindRoute extends KindRouteReader {

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
	private KindRoute() {
	}

	/**
	 * @uml.property name="kindOf"
	 */
	private String kindOf = "";

	/**
	 * get the textual kind
	 * 
	 * @return the kind
	 */
	@Override
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
	 * Reset all the kind, erase all the kind known, and get ready for having new kind.
	 */
	public static void reset() {
	}

}
