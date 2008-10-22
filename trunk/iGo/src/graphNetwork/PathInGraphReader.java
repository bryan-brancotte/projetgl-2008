package graphNetwork;

public interface PathInGraphReader {

	/**
	 * Export in a string the path represented by the object
	 * 
	 * @return the path
	 */
	public abstract String exportPath();

	/**
	 * Return the cost of the travel
	 * 
	 * @return the cost
	 */
	public float getCost();

	/**
	 * Retorne the graph where the path was built
	 * 
	 * @return l'object graph en lecture seul
	 */
	public abstract GraphNetworkReader getGraphR();

	/**
	 * Return all the inter who are the path, in an ordered way.
	 * 
	 * @return
	 */
	public InterReader[] getInterR();

	/**
	 * Returne the time of the travel
	 * 
	 * @return the time
	 */
	public int getTime();

	/**
	 * Return a string describing the path, station y station
	 * 
	 * @return chaine equivalent
	 */
	public String toString();

	/**
	 * Check that we still can use this path.
	 * 
	 * @return true if all intersection and change are still activated
	 */
	public abstract boolean isStillAvaible();

	/**
	 * Check that we still can use this path from where we are.
	 * 
	 * @return true if all intersection an change are still activated after this intersection
	 */
	public abstract boolean isStillAvaible(InterReader inter);

	/**
	 * Return the first inter in the path which after this one, you can use still use the path without problem
	 * 
	 * @return the first inter or null if the final station or final inter isn't avaible
	 */
	public abstract InterReader getFirstInterInTheLastAvaiblePart();

}
