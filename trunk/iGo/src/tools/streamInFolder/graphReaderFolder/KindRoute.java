package tools.streamInFolder.graphReaderFolder;

import java.util.LinkedList;

public class KindRoute {

	/**
	 */
	public static void addKind(String kind) {
	}

	/**
	 * @uml.property name="kind"
	 */
	private java.lang.String kind = "";

	/**
	 * Getter of the property <tt>kind</tt>
	 * 
	 * @return Returns the kind.
	 * @uml.property name="kind"
	 */
	public java.lang.String getKind() {
		return kind;
	}

	/**
	 * Setter of the property <tt>kind</tt>
	 * 
	 * @param kind
	 *            The kind to set.
	 * @uml.property name="kind"
	 */
	public void setKind(java.lang.String kind) {
		this.kind = kind;
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

}
