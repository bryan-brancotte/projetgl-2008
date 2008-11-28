package graphNetwork;

/**
 * Cette interface permet via son implementation de définir les critère de choix pour l'algorithme sans que ce dernier
 * n'en ai connaissance. Ces choix seront par exemple le coût, le temps, le changement, ...
 * Son utilisation est semblable au wrapper
 * 
 * @author iGo
 */
public interface CriteriousForTheLowerPath {

	/**
	 * Return the prefered cost to use on the specified Junction.
	 * 
	 * @param inter
	 *            the inter studied
	 * @return the cost
	 */
	public abstract float getFirstCost(Junction junction);

	/**
	 * Return the second prefered cost to use on the specified Junction.
	 * 
	 * @param inter
	 *            the inter studied
	 * @return the cost
	 */
	public abstract float getSecondCost(Junction junction);

}
