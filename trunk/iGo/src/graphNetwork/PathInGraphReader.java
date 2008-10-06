package graphNetwork;

public interface PathInGraphReader {

	/**
	 * Retourne un chaine d�crivant le chemin � faire station par station
	 */
	public String toString();
	
	/**
	 * 
	 * @return
	 */
	public InterReader[] getInterR();

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

	/**
	 * Exporte sous forme textuelle le chemin d�crit dans l'objet
	 * @return le chemin
	 */
	public abstract String exportPath();
	
	public abstract GraphNetworkReader getGraphR();

}
