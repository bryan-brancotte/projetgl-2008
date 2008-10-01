package graphNetwork;

public abstract class Inter {

	private boolean lock = false;

	/**
	 * @uml.property name="stationB"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Station"
	 */
	private Station station;

	/**
	 * @uml.property name="stationA"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Station"
	 */
	private Station stationA;

	/**
	 * Getter of the property <tt>stationA</tt>
	 * 
	 * @return Returns the stationA.
	 * @uml.property name="stationA"
	 */
	public Station getStationA() {
		return stationA;
	}

	/**
	 * Setter of the property <tt>stationA</tt>
	 * 
	 * @param stationA
	 *            The stationA to set.
	 * @uml.property name="stationA"
	 */
	public void setStationA(Station stationA) {
		this.stationA = stationA;
	}

	/**
	 * Getter of the property <tt>stationB</tt>
	 * 
	 * @return Returns the station.
	 * @uml.property name="stationB"
	 */
	public Station getStationB() {
		return station;
	}

	/**
	 * Setter of the property <tt>stationB</tt>
	 * 
	 * @param stationB
	 *            The station to set.
	 * @uml.property name="stationB"
	 */
	public void setStationB(Station stationB) {
		station = stationB;
	}

	/**
	 * @uml.property name="routeA"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Route"
	 */
	private RouteR routeA;

	/**
	 * Getter of the property <tt>routeA</tt>
	 * 
	 * @return Returns the routeA.
	 * @uml.property name="routeA"
	 */
	public RouteR getRouteA() {
		return routeA;
	}

	/**
	 * Setter of the property <tt>routeA</tt>
	 * 
	 * @param routeA
	 *            The routeA to set.
	 * @uml.property name="routeA"
	 */
	public void setRouteA(RouteR routeA) {
		this.routeA = routeA;
	}

	/**
	 * @uml.property name="routeB"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Route"
	 */
	private RouteR routeB;

	/**
	 * Getter of the property <tt>routeB</tt>
	 * 
	 * @return Returns the routeB.
	 * @uml.property name="routeB"
	 */
	public RouteR getRouteB() {
		return routeB;
	}

	/**
	 * Setter of the property <tt>routeB</tt>
	 * 
	 * @param routeB
	 *            The routeB to set.
	 * @uml.property name="routeB"
	 */
	public void setRouteB(RouteR routeB) {
		this.routeB = routeB;
	}

	/**
	 * @uml.property name="timeBetweenStations"
	 */
	private byte timeBetweenStations;

	/* (non-Javadoc)
	 * @see graphNetwork.InterR#getTimeBetweenStations()
	 */
	public byte getTimeBetweenStations() {
		return timeBetweenStations;
	}

	/**
	 * @uml.property name="cost"
	 */
	private float cost;

	/* (non-Javadoc)
	 * @see graphNetwork.InterR#getCost()
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
