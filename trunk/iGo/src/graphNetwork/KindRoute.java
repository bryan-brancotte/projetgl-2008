package graphNetwork;

import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class KindRoute extends KindRouteReader {

	/**
	 * @uml.property name="kinds"
	 */
	protected static LinkedList<KindRoute> kinds;

	/**
	 * Add a new kind to the collection of kind if this kind doesn't existe.
	 * 
	 * @param kind
	 *            the name of the new kind
	 * @return true if we could add the kind to the collection. If the kind already existe, we hadn't add it.
	 */
	public static boolean addKind(String kind) {
		return true;
	}

	/**
	 * Getter of the property <tt>kinds</tt>
	 * 
	 * @return Returns the kinds.
	 * @uml.property name="kinds"
	 */
	public static Collection<KindRoute> getKinds() {
		return kinds;
	}

	/**
	 * Reset all the kind, erase all the kind known, and get ready for having new kind.
	 */
	public static void reset() {
	}

	/**
	 * @uml.property name="kindOf"
	 */
	private String kindOf = "";

	/**
	 * Default Construcutor. It's defined as private to prevente from uncontrolled allocation of KindRoute
	 */
	private KindRoute() {
	}

	/**
	 * Build a new KindRoute and add it to the collection of kind
	 */
	private KindRoute(String kindOf) {
		kinds.add(this);
	}

	/**
	 * get the textual kind
	 * 
	 * @return the kind
	 */
	@Override
	public String getKindOf() {
		return kindOf;
	}

}
