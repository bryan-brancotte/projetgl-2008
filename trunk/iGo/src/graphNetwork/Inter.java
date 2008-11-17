package graphNetwork;

/**
 * 
 * @author iGo
 */
public class Inter implements InterReader {

	/**
	 * @uml.property name="cost"
	 */
	private float cost;

	/**
	 * @uml.property name="enable"
	 */
	private boolean enable = true;

	/**
	 * @uml.property name="pedestrian"
	 */
	private boolean pedestrian = false;

	/**
	 * @uml.property name="routeA"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Route"
	 */
	private Route routeA;

	/**
	 * @uml.property name="routeB"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Route"
	 */
	private Route routeB;

	/**
	 * @uml.property name="stationB"
	 * @uml.associationEnd inverse="interchange:graphNetwork.Station"
	 */
	private Station stationB;

	/**
	 * @uml.property name="stationA"
	 * @uml.associationEnd inverse="inter:graphNetwork.Station"
	 */
	private Station stationA;

	/**
	 * @uml.property name="timeBetweenStations"
	 */
	private int timeBetweenStations;

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.InterR#getCost()
	 */
	@Override
	public float getCost() {
		return cost;
	}

	/**
	 * return the route of other station of an inter. You have to give one station to give the other
	 * 
	 * @param me
	 *            the station you know in the inter
	 * @return the route of other station, or null of the specified station isn't one of the two station
	 */
	public Route getOtherRoute(StationReader me) {
		if (me.getId() == this.getStationA().getId())
			return this.getRouteB();
		if (me.getId() == this.getStationB().getId())
			return this.getRouteA();
		return null;
	}

	/**
	 * return the route of the other station of an inter in readOnly mode. You have to give one station to give the
	 * other
	 * 
	 * @param me
	 *            the station or stationReader you know in the inter
	 * @return the route of other stationReader, or null of the specified station isn't one of the two station
	 */
	public RouteReader getOtherRouteR(StationReader me) {
		return this.getOtherRoute(me);
	}

	/**
	 * return the other station of an inter. You have to give one station to give the other
	 * 
	 * @param me
	 *            the station you know in the inter
	 * @return the other station, or null of the specified station isn't one of the two station
	 */
	public Station getOtherStation(StationReader me) {
		if (me.getId() == this.getStationA().getId())
			return this.getStationB();
		if (me.getId() == this.getStationB().getId())
			return this.getStationA();
		return null;
	}

	/**
	 * return the other station of an inter in readOnly mode. You have to give one station to give the other
	 * 
	 * @param me
	 *            the station or stationReader you know in the inter
	 * @return the other stationReader, or null of the specified station isn't one of the two station
	 */
	public StationReader getOtherStationR(StationReader me) {
		return this.getOtherStation(me);
	}

	/**
	 * Getter of the property <tt>routeA</tt>
	 * 
	 * @return Returns the routeA.
	 * @uml.property name="routeA"
	 */
	public Route getRouteA() {
		return routeA;
	}

	/**
	 * Getter of the property <tt>routeB</tt>
	 * 
	 * @return Returns the routeB.
	 * @uml.property name="routeB"
	 */
	public Route getRouteB()

	{
		return routeB;
	}

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
	 * Getter of the property <tt>stationB</tt>
	 * 
	 * @return Returns the station.
	 * @uml.property name="stationB"
	 */
	public Station getStationB() {
		return stationB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graphNetwork.InterR#getTimeBetweenStations()
	 */
	@Override
	public int getTimeBetweenStations() {
		return timeBetweenStations;
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	@Override
	public boolean isPedestrian() {
		return pedestrian;
	}

	/**
	 * Setter of the property <tt>cost</tt>
	 * 
	 * @param cost
	 *            The cost to set.
	 * @uml.property name="cost"
	 */
	public void setCost(float cost) {
		this.cost = cost;
	}

	/**
	 * Setter of the property <tt>enable</tt>
	 * 
	 * @param enable
	 *            The enable to set.
	 * @uml.property name="enable"
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * Setter of the property <tt>pedestrian</tt>
	 * 
	 * @param pedestrian
	 *            The pedestrian to set.
	 * @uml.property name="pedestrian"
	 */
	public void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	/**
	 * Setter of the property <tt>routeA</tt>
	 * 
	 * @param routeA
	 *            The routeA to set.
	 * @uml.property name="routeA"
	 */
	public void setRouteA(Route routeA) {
		this.routeA = routeA;
	}

	/**
	 * Setter of the property <tt>routeB</tt>
	 * 
	 * @param routeB
	 *            The routeB to set.
	 * @uml.property name="routeB"
	 */
	public void setRouteB(Route routeB) {
		this.routeB = routeB;
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
	 * Setter of the property <tt>stationB</tt>
	 * 
	 * @param stationB
	 *            The station to set.
	 * @uml.property name="stationB"
	 */
	public void setStationB(Station stationB) {
		this.stationB = stationB;
	}

	/**
	 * Setter of the property <tt>timeBetweenStations</tt>
	 * 
	 * @param timeBetweenStations
	 *            The timeBetweenStations to set. It must be positive or equal to zero
	 * @uml.property name="timeBetweenStations"
	 */
	public void setTimeBetweenStations(int timeBetweenStations) {
		this.timeBetweenStations = timeBetweenStations;
	}

	/**
	 * a variable to know if the inter is rail between two station or an inter were you have to step down from you
	 * train/subway/...
	 * 
	 * @uml.property name="routeLink"
	 */
	private boolean routeLink = false;

	@Override
	public boolean isRouteLink() {
		return routeLink;
	}

	/**
	 * Setter of the property <tt>routeLink</tt>
	 * 
	 * @param routeLink
	 *            The routeLink to set.
	 * @uml.property name="routeLink"
	 */
	public void setRouteLink(boolean routeLink) {
		this.routeLink = routeLink;
	}

	public Inter(Station stationA, Route routeA, Station stationB, Route routeB) {
		super();
		this.routeA = routeA;
		this.routeB = routeB;
		this.stationB = stationB;
		this.stationA = stationA;
	}

}
