package interpretationFichierXML;


public class Couple {

	/**
	 * @uml.property  name="Station"
	 * @uml.associationEnd  inverse="couple:interpretationFichierXML.Station"
	 */
	private Station station;

	/**
	 * Getter of the property <tt>Station</tt>
	 * @return  Returns the station.
	 * @uml.property  name="Station"
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * Setter of the property <tt>Station</tt>
	 * @param Station  The station to set.
	 * @uml.property  name="Station"
	 */
	public void setStation(Station station) {
		this.station = station;
	}

	/**
	 * @uml.property  name="Route"
	 * @uml.associationEnd  inverse="couple:interpretationFichierXML.Route"
	 */
	private Route route;

	/**
	 * Getter of the property <tt>Route</tt>
	 * @return  Returns the route.
	 * @uml.property  name="Route"
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * Setter of the property <tt>Route</tt>
	 * @param Route  The route to set.
	 * @uml.property  name="Route"
	 */
	public void setRoute(Route route) {
		this.route = route;
	}

}
