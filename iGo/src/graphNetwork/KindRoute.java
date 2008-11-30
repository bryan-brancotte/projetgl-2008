package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */

public class KindRoute {

	private KindRoute route;
	protected static LinkedList<KindRoute> kinds;
	/**
	 * get the object KindRoute of the kind given in parameter.
	 * @param kind, name of the kind we are looking for
	 * @return the KindRoute if we found it, else we return null;
	 */
	protected static KindRoute getKindFromString(String kindOf) {
		KindRoute k = null;
		int i=0;
		while (k==null && i<kinds.size()) {
			if (kinds.get(i).getKindOf().compareTo(kindOf)==0) k=kinds.get(i);
			i++;
		}
		return k;
	}


	/**
	 * Add a new kind to the collection of kind if this kind doesn't existe.
	 * @param kind, the name of the new kind
	 * @return true if we could add the kind to the collection. If the kind already existe, we hadn't add it.
	 */
	protected static KindRoute addKind(String kindOf) {
        if (kinds==null) kinds = new LinkedList<KindRoute>();
		KindRoute k = getKindFromString(kindOf);
		if(k!=null) return k;
		else return new KindRoute(kindOf);

	}

	/**
	 * Getter of the property <tt>kinds</tt>
	 * @return Returns the kinds.
	 * @uml.property name="kinds"
	 */
	public static Iterator<KindRoute> getKinds() {
		return kinds.iterator();
	}

	/**
	 * Reset all the kind, erase all the kind known, and get ready for having new kind.
	 */
	public static void reset() {
		kinds.clear();
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
	private KindRoute(String _kindOf) {
		kindOf = _kindOf;
        //TODO Ajouté à l'arrache par Tony le 30 novembre
        kinds.add(this);
        //TODO Fin de l'ajout

	}

	/**
	 * get the textual kind
	 * 
	 * @return the kind
	 */
	public String getKindOf() {
		return kindOf;
	}
	

	/**
	 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
	 */
	public boolean equals(Object obj) {
		if ((obj instanceof KindRoute) || (obj instanceof KindRoute)) {
			return (((KindRoute) obj).getKindOf().compareTo(this.getKindOf()) == 0);
		}
		return false;
	}

}
