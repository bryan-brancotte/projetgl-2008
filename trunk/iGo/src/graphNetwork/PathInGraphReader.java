package graphNetwork;

public interface PathInGraphReader   {

	/**
	 * Retourne un chaine décrivant le chemin à faire station par station
	 */
	public String toString();

	/**
	 * @return   Returns the inter.
	 * @uml.property   name="inter"
	 * @uml.associationEnd   readOnly="true" multiplicity="(0 -1)" ordering="true" container="java.util.List" inverse="pathInGraph:graphNetwork.InterStation"
	 */
	public InterReader[] getInter();

	/**
	 * Retourne le coût du trajet
	 * 
	 * @return le coût
	 */
	public float getCost();

	/**
	 * Retourne la durée du trajet
	 * 
	 * @return le temps
	 */
	public byte getTime();

}
