package graphNetwork;

import java.util.Collection;
import java.util.LinkedList;


public class Route implements RouteR {

	/** 
	 * @uml.property name="station"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="route:graphNetwork.Station"
	 */
	private Collection<Station> station;

	/* (non-Javadoc)
	 * @see graphNetwork.RouteR#getStation()
	 */
	public Collection<Station> getStation() {
		return station;
	}

	/** 
	 * Setter of the property <tt>station</tt>
	 * @param station  The station to set.
	 * @uml.property  name="station"
	 */
	public void setStation(Collection<Station> station) {
		this.station = station;
	}

	/** 
	 * @uml.property name="id"
	 */
	private String id = "";

	/* (non-Javadoc)
	 * @see graphNetwork.RouteReader#getId()
	 */
	/* (non-Javadoc)
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
	 * @param id  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Collection<StationR> getStationsR() {
		return new LinkedList<StationR>(station);
	}

}
