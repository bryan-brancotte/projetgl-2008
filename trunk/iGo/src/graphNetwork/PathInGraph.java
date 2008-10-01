package graphNetwork;

public interface PathInGraph   {

	/**
	 * Retourne un chaine d�crivant le chemin � faire station par station
	 */
	public String toString();

	/**
	 * @return Returns the inter.
	 * @uml.property name="inter"
	 * @uml.associationEnd readOnly="true" multiplicity="(0 -1)" ordering="true" container="java.util.List"
	 *                     inverse="pathInGraph:graphNetwork.Inter"
	 */
	public Inter[] getInter();

	/**
	 * Retourne le co�t du trajet
	 * 
	 * @return le co�t
	 */
	public float getCost();

	/**
	 * Retourne la dur�e du trajet
	 * 
	 * @return le temps
	 */
	public byte getTime();

}
