package graphNetwork;

public abstract class Inter {

	private boolean lock = false;

	/**
	 * @uml.property name="stationB"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Station"
	 */
	private StationReader station;

	/**
	 * @uml.property name="stationA"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Station"
	 */
	private StationReader stationA;

	/**
	 * Getter of the property <tt>stationA</tt>
	 * 
	 * @return Returns the stationA.
	 * @uml.property name="stationA"
	 */
	public StationReader getStationA() {
		return stationA;
	}

	/**
	 * Setter of the property <tt>stationA</tt>
	 * 
	 * @param stationA
	 *            The stationA to set.
	 * @uml.property name="stationA"
	 */
	public void setStationA(StationReader stationA) {
		this.stationA = stationA;
	}

	/**
	 * Getter of the property <tt>stationB</tt>
	 * 
	 * @return Returns the station.
	 * @uml.property name="stationB"
	 */
	public StationReader getStationB() {
		return station;
	}

	/**
	 * Setter of the property <tt>stationB</tt>
	 * 
	 * @param stationB
	 *            The station to set.
	 * @uml.property name="stationB"
	 */
	public void setStationB(StationReader stationB) {
		station = stationB;
	}

	/**
	 * @uml.property name="routeA"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Route"
	 */
	private RouteReader routeA;

	/**
	 * Getter of the property <tt>routeA</tt>
	 * 
	 * @return Returns the routeA.
	 * @uml.property name="routeA"
	 */
	public RouteReader getRouteA() {
		return routeA;
	}

	/**
	 * Setter of the property <tt>routeA</tt>
	 * 
	 * @param routeA
	 *            The routeA to set.
	 * @uml.property name="routeA"
	 */
	public void setRouteA(RouteReader routeA) {
		this.routeA = routeA;
	}

	/**
	 * @uml.property name="routeB"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Route"
	 */
	private RouteReader routeB;

	/**
	 * Getter of the property <tt>routeB</tt>
	 * 
	 * @return Returns the routeB.
	 * @uml.property name="routeB"
	 */
	public RouteReader getRouteB() {
		return routeB;
	}

	/**
	 * Setter of the property <tt>routeB</tt>
	 * 
	 * @param routeB
	 *            The routeB to set.
	 * @uml.property name="routeB"
	 */
	public void setRouteB(RouteReader routeB) {
		this.routeB = routeB;
	}

	/**
	 * @uml.property name="timeBetweenStations"
	 */
	private byte timeBetweenStations;

	/**
	 * Getter of the property <tt>timeBetweenStations</tt>
	 * 
	 * @return Returns the timeBetweenStations.
	 * @uml.property name="timeBetweenStations"
	 */
	public byte getTimeBetweenStations() {
		return timeBetweenStations;
	}

	/**
	 */
	public abstract String getKindOfInter();

	/**
	 */
	public StationReader getOtherStation(StationReader station) {
		return null;
	}

	/**
	 * @uml.property name="cost"
	 */
	private float cost;

	/**
	 * Getter of the property <tt>cost</tt>
	 * 
	 * @return Returns the cost.
	 * @uml.property name="cost"
	 */
	public float getCost() {
		return cost;
	}

	/**
	 * Setter of the property <tt>timeBetweenStations</tt>
	 * 
	 * @param timeBetweenStations
	 *            The timeBetweenStations to set.
	 * @uml.property name="timeBetweenStations"
	 */
	public void setTimeBetweenStations(byte timeBetweenStations) {
		if (lock)
			return;
		this.timeBetweenStations = timeBetweenStations;
	}

	/**
	 * Setter of the property <tt>cost</tt>
	 * 
	 * @param cost
	 *            The cost to set.
	 * @uml.property name="cost"
	 */
	public void setCost(float cost) {
		if (lock)
			return;
		this.cost = cost;
	}

}
