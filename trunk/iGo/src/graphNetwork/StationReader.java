package graphNetwork;

/**
 *  
 * @author iGo
 */
public interface StationReader {

	/**
	 * get the station's id
	 * 
	 * @return the id
	 */
	public abstract int getId();

	/**
	 * return all the intersection linked to this station
	 * 
	 * @return an array of the inter
	 */
	public abstract InterReader[] getInterchangeR();

	/**
	 * get the station's name
	 * 
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * give an array of all the routes in the station.
	 * 
	 * @return array of all the route. there's no information of order.
	 */
	public abstract RouteReader[] getRoutesR();

	/**
	 * give all the services avaibles in the station (coffe, sauna,...)
	 * 
	 * @return array of the services avaibles
	 */
	public abstract ServiceReader[] getServiceR();

	/**
	 * Allow to know if the station is enable, that mean if you can stepp down on this station
	 * 
	 * @return true if you can steep down
	 */
	public boolean isEnable();

}
