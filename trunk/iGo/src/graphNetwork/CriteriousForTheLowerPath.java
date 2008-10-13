package graphNetwork;

/**
 * Cette interface permet via son implementation de définir les critère de choix pour l'algorithme sans que ce dernier
 * n'en ai connaissance. Ces choix seront par exemple le coût, le temps, le changement, ...
 * Son utilisation est semblable au wrapper
 * 
 * @author Brancotte Bryan
 * @author Courtois Rémi
 * @author Girard Pierrick
 * @author Marie Elodie
 * @author Quinio Tony
 * 
 */
public interface CriteriousForTheLowerPath {

	/**
	 * Return the prefered cost to use on the specified InterReader.
	 * 
	 * @param inter
	 *            the inter studied
	 * @return the cost
	 */
	public abstract float getFirstCost(InterReader inter);

	/**
	 * Return the second prefered cost to use on the specified InterReader.
	 * 
	 * @param inter
	 *            the inter studied
	 * @return the cost
	 */
	public abstract float getSecondCost(InterReader inter);

}
