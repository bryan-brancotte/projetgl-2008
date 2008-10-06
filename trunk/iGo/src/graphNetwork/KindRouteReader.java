package graphNetwork;

import java.util.Collection;

public abstract class KindRouteReader {

	/**
	 * @uml.property name="kinds"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="kindRouteReader1:graphNetwork.KindRouteReader"
	 */
	protected static Collection<KindRouteReader> kinds;

	/**
	 * get the object KindRoute of the kind given in parameter.
	 * 
	 * @param kind
	 *            name of the kind we are looking for
	 * @return the KindRoute if we found it, else we return null;
	 */
	public static KindRoute getKindFromString(String kindOf) {
		return null;
	}

	/**
	 * Surcharge de equals pour s'assur� que la comparaison sera bien faite.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof KindRoute) {
			return (((KindRoute) obj).getKindOf().compareTo(this.getKindOf()) == 0);

		}
		return super.equals(obj);
	}

	/**
	 * get the textual kind
	 * 
	 * @return the kind
	 */
	public abstract String getKindOf();

	/**
	 * Getter of the property <tt>kinds</tt>
	 * 
	 * @return Returns the kinds.
	 * @uml.property name="kinds"
	 */
	public KindRouteReader[] getKinds() {
		return kinds.toArray(new KindRouteReader[0]);
	}

}
