package graphNetwork;

import java.util.Collection;

public interface StationR {

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getRoute()
	 */
	public abstract RouteR getRoute();

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getService()
	 */
	public abstract ServiceR getService();

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getInterchange()
	 */
	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getInterchange()
	 */
	public abstract Collection getInterchange();

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getName()
	 */
	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getName()
	 */
	public abstract String getName();

	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getId()
	 */
	/* (non-Javadoc)
	 * @see graphNetwork.StationReader#getId()
	 */
	public abstract int getId();

}