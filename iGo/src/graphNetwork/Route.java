package graphNetwork;


import java.util.LinkedList;

public class Route implements RouteR {

	/** 
	 * @uml.property name="stations"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="routes:graphNetwork.Station"
	 */
	private LinkedList stations;

	/**
	 * @uml.property name="id"
	 */
	private String id = "";

	/*
	 * (non-Javadoc)
	 * @see graphNetwork.RouteReader#getId()
	 */
	/*
	 * (non-Javadoc)
	 * @see graphNetwork.RouteR#getId()
	 */
	public String getId() {
		return id;
	}

	public Route(String id) {
		super();
		this.id = id;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * @param id
	 *        The id to set.
	 * @uml.property name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	public StationR[] getStationsR() {
		return this.getStations().toArray(new StationR[0]);
	}

	/** 
	 * Getter of the property <tt>stations</tt>
	 * @return  Returns the station.
	 * @uml.property  name="stations"
	 */
	public LinkedList getStations() {
		return stations;
	}

	/** 
	 * Setter of the property <tt>stations</tt>
	 * @param stations  The station to set.
	 * @uml.property  name="stations"
	 */
	public void setStations(LinkedList stations) {
		this.stations = stations;
	}

	/**
	 * @uml.property  name="kindRoute"
	 * @uml.associationEnd  inverse="route:graphNetwork.KindRoute"
	 */
	private KindRoute kindRoute;

	/**
	 * Getter of the property <tt>kindRoute</tt>
	 * @return  Returns the kindRoute.
	 * @uml.property  name="kindRoute"
	 */
	public KindRoute getKindRoute() {
		return kindRoute;
	}

	/**
	 * Setter of the property <tt>kindRoute</tt>
	 * @param kindRoute  The kindRoute to set.
	 * @uml.property  name="kindRoute"
	 */
	public void setKindRoute(KindRoute kindRoute) {
		this.kindRoute = kindRoute;
	}

}
