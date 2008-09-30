package graphNetwork;

import java.util.Collection;


public class Route {

	/** 
	 * @uml.property name="station"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="route:graphNetwork.Station"
	 */
	private Collection station;

	/** 
	 * Getter of the property <tt>station</tt>
	 * @return  Returns the station.
	 * @uml.property  name="station"
	 */
	public Collection getStation() {
		return station;
	}

	/** 
	 * Setter of the property <tt>station</tt>
	 * @param station  The station to set.
	 * @uml.property  name="station"
	 */
	public void setStation(Collection station) {
		this.station = station;
	}

}
