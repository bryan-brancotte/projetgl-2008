package streamInFolder.graphReaderFolder;


public class Couple {

	/**
	 * @uml.property   name="Station"
	 * @uml.associationEnd   inverse="couple:streamInFolder.graphReaderFolder.Station"
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
	 * @uml.associationEnd   inverse="couple:streamInFolder.graphReaderFolder.Route"
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
	 * Getter of the property <tt>Free</tt>
	 * @return  Returns the free.
	 * @uml.property  name="free"
	 */
	public boolean isFree() {
		return free1;
	}

	/** 
	 * Setter of the property <tt>Free</tt>
	 * @param Free  The free to set.
	 * @uml.property  name="free"
	 */
	public void setFree(boolean free) {
		free1 = free;
	}

	/** 
	 * Getter of the property <tt>Pedestrian</tt>
	 * @return  Returns the pedestrian.
	 * @uml.property  name="pedestrian"
	 */
	public boolean isPedestrian() {
		return pedestrian1;
	}

	/** 
	 * Setter of the property <tt>Pedestrian</tt>
	 * @param Pedestrian  The pedestrian to set.
	 * @uml.property  name="pedestrian"
	 */
	public void setPedestrian(boolean pedestrian) {
		pedestrian1 = pedestrian;
	}

	/** 
	 * Getter of the property <tt>Time</tt>
	 * @return  Returns the time.
	 * @uml.property  name="time"
	 */
	public int getTime() {
		return time1;
	}

	/** 
	 * Setter of the property <tt>Time</tt>
	 * @param Time  The time to set.
	 * @uml.property  name="time"
	 */
	public void setTime(int time) {
		time1 = time;
	}

	/**
	 * @uml.property  name="free"
	 */
	private boolean free1;
	/**
	 * @uml.property  name="pedestrian"
	 */
	private boolean pedestrian1;
	/**
	 * @uml.property  name="time"
	 */
	private int time1;

}
