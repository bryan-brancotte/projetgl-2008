package tools.streamInFolder.graphReaderFolder;

import java.util.Collection;


public class Interchange {

	/**
	 * @uml.property   name="Endlist"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="interchange:tools.streamInFolder.graphReaderFolder.Couple"
	 */
	private Collection<Couple> endlist;

	/**
	 * Getter of the property <tt>Endlist</tt>
	 * @return  Returns the endlist.
	 * @uml.property  name="Endlist"
	 */
	public Collection<Couple> getEndlist() {
		return endlist;
	}

	/**
	 * Setter of the property <tt>Endlist</tt>
	 * @param Endlist  The endlist to set.
	 * @uml.property  name="Endlist"
	 */
	public void setEndlist(Collection<Couple> endlist) {
		this.endlist = endlist;
	}

	/**
	 * @uml.property  name="Pedestrian"
	 */
	private boolean pedestrian;

	/**
	 * Getter of the property <tt>Pedestrian</tt>
	 * @return  Returns the pedestrian.
	 * @uml.property  name="Pedestrian"
	 */
	public boolean isPedestrian() {
		return pedestrian;
	}

	/**
	 * Setter of the property <tt>Pedestrian</tt>
	 * @param Pedestrian  The pedestrian to set.
	 * @uml.property  name="Pedestrian"
	 */
	public void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	/**
	 * @uml.property  name="Time"
	 */
	private byte time;

	/**
	 * Getter of the property <tt>Time</tt>
	 * @return  Returns the time.
	 * @uml.property  name="Time"
	 */
	public byte getTime() {
		return time;
	}

	/**
	 * Setter of the property <tt>Time</tt>
	 * @param Time  The time to set.
	 * @uml.property  name="Time"
	 */
	public void setTime(byte time) {
		this.time = time;
	}

	/**
	 * @uml.property   name="stationStart"
	 * @uml.associationEnd   inverse="interchange:tools.streamInFolder.graphReaderFolder.Station"
	 */
	private Station stationStart;

	/**
	 * Getter of the property <tt>stationStart</tt>
	 * @return  Returns the stationStart.
	 * @uml.property  name="stationStart"
	 */
	public Station getStationStart() {
		return stationStart;
	}

	/**
	 * Setter of the property <tt>stationStart</tt>
	 * @param stationStart  The stationStart to set.
	 * @uml.property  name="stationStart"
	 */
	public void setStationStart(Station stationStart) {
		this.stationStart = stationStart;
	}

	/**
	 * @uml.property   name="routeStart"
	 * @uml.associationEnd   inverse="interchange:tools.streamInFolder.graphReaderFolder.Route"
	 */
	private Route routeStart;

	/**
	 * Getter of the property <tt>routeStart</tt>
	 * @return  Returns the routeStart.
	 * @uml.property  name="routeStart"
	 */
	public Route getRouteStart() {
		return routeStart;
	}

	/**
	 * Setter of the property <tt>routeStart</tt>
	 * @param routeStart  The routeStart to set.
	 * @uml.property  name="routeStart"
	 */
	public void setRouteStart(Route routeStart) {
		this.routeStart = routeStart;
	}

}
