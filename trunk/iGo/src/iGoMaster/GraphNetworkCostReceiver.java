package iGoMaster;

import graphNetwork.KindRoute;
/**
 *  
 * @author iGo
 */
public interface GraphNetworkCostReceiver {

	/**
	 * Accesseur qui va retourner le cout pour accéder au réseau avec le type de voie spécifié
	 * 
	 * @param to
	 *            	Le type de voie
	 * @return
	 * 				Le cout
	 */
	public abstract float getCost(KindRoute to);

	/**
	 * Retourne le cout a appliquer suite a un changement d'un type de voie à un autre type
	 * de voie. Si ce cout n'existe pas, l'accesseur retournera le même résultat que getCost(KindRouteReader to.
	 * 
	 * @param from
	 *            le type de voie ou l'on est
	 * @param to
	 *            le type de voie ou l'on va
	 * @return le cout applicable
	 */
	public abstract float getCost(KindRoute from, KindRoute to);

}
