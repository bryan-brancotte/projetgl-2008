package graphNetwork;

public interface PathInGraphReader {

	/**
	 * Retourne un chaine décrivant le chemin à faire station par station
	 */
	public String toString();
	
	/**
	 * 
	 * @return
	 */
	public InterReader[] getInterR();

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

	/**
	 * Exporte sous forme textuelle le chemin décrit dans l'objet
	 * @return le chemin
	 */
	public abstract String exportPath();
	
	public abstract GraphNetworkReader getGraphR();

}
