package graphNetwork;


public interface PathInGraphReader {

	/**
	 * Exporte sous forme textuelle le chemin d�crit dans l'objet
	 * 
	 * @return le chemin
	 */
	public abstract String exportPath();

	/**
	 * Retourne le co�t du trajet
	 * 
	 * @return le co�t
	 */
	public float getCost();

	/**
	 * Retourne le graph dans lequel le path est valide
	 * 
	 * @return l'object graph en lecture seul
	 */
	public abstract GraphNetworkReader getGraphR();

	/**
	 * Retourne l'ensemble des inter qui constitue le chemin, dans l'ordre � utiliser
	 * 
	 * @return
	 */
	public InterReader[] getInterR();

	/**
	 * Retourne la dur�e du trajet
	 * 
	 * @return le temps
	 */
	public int getTime();

	/**
	 * Retourne un chaine d�crivant le chemin � faire station par station
	 * 
	 * @return chaine equivalent
	 */
	public String toString();

	/**
	 * Check that we still can use this path.
	 * 
	 * @return true if all intersection an change are still activated
	 */
	public abstract boolean isStillAvaible();

	/**
	 * Return the first inter in the path which after this one, you can use still use the path without problem
	 * 
	 * @return the first inter or null if the final station or final inter isn't avaible
	 */
	public abstract InterReader getFirstInterInTheLastAvaiblePart();

}
