package tools.streamInFolder.graphReaderFolder;


public class Couple {

	/**
	 * @uml.property   name="Station"
	 * @uml.associationEnd   inverse="couple:tools.streamInFolder.graphReaderFolder.Station"
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
	 * @uml.property   name="Route"
	 * @uml.associationEnd   inverse="couple:tools.streamInFolder.graphReaderFolder.Route"
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

	/**
	 * @uml.property  name="Free"
	 */
	private boolean free;

	/**
	 * Getter of the property <tt>Free</tt>
	 * @return  Returns the free.
	 * @uml.property  name="Free"
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * Setter of the property <tt>Free</tt>
	 * @param Free  The free to set.
	 * @uml.property  name="Free"
	 */
	public void setFree(boolean free) {
		this.free = free;
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

}
