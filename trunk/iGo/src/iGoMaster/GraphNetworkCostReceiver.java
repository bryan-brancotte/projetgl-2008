package iGoMaster;

import graphNetwork.KindRouteReader;
/**
 *  
 * @author iGo
 */
public interface GraphNetworkCostReceiver {

	/**
	 * Return the cost to acces to the network on the kind route specified
	 * 
	 * @param to
	 *            The kindRoute targeted
	 * @return the cost
	 */
	public abstract float getCost(KindRouteReader to);

	/**
	 * Return the cost to change from a kindRoute to an other KindRoute. If the cost doesn't existe, the returne cost
	 * will be the same as float getCost(KindRouteReader to);
	 * 
	 * @param from
	 *            the kindRoute where you are
	 * @param to
	 *            the KinfRoute where you go
	 * @return the cost
	 */
	public abstract float getCost(KindRouteReader from, KindRouteReader to);

}
