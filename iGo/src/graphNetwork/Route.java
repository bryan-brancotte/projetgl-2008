package graphNetwork;

import java.util.Collection;
import java.util.LinkedList;


public class Route implements RouteReader {

	/** 
	 * @uml.property name="station"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="route:graphNetwork.Station"
	 */
	private Collection<Station> station;

	/** 
	 * Getter of the property <tt>station</tt>
	 * @return  Returns the station.
	 * @uml.property  name="station"
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

	@Override
	public Collection<StationReader> getStationReader() {
		return new LinkedList<StationReader>(station);
	}

}
