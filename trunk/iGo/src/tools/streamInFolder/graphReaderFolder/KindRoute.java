package tools.streamInFolder.graphReaderFolder;

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
	 * 
	 * @param kind nom du type que l'on veut
	 * @return l'instance du type s'il existe, ou null s'il n'existe pas
	 */
	public static KindRoute getKind(String kind) {
		return null;
	}

}
