package graphNetwork;

public interface InterR {

	/**
	 * Getter of the property <tt>routeA</tt>
	 * 
	 * @return Returns the routeA.
	 * @uml.property name="routeA"
	 */
	public abstract RouteR getOtherRoute(Station station);

	/**
	 * Getter of the property <tt>timeBetweenStations</tt>
	 * 
	 * @return Returns the timeBetweenStations.
	 * @uml.property name="timeBetweenStations"
	 */
	public abstract byte getTimeBetweenStations();

	public abstract String getKindOfInter();

	public abstract Station getOtherStation(Station station);

	/**
	 * Getter of the property <tt>cost</tt>
	 * 
	 * @return Returns the cost.
	 * @uml.property name="cost"
	 */
	public abstract float getCost();

}