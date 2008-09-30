package graphNetwork;


public class InterStation extends InterStation {

	/**
	 * @uml.property  name="timeBetweenStations"
	 */
	private String timeBetweenStations = "";

	/**
	 * Getter of the property <tt>timeBetweenStations</tt>
	 * @return  Returns the timeBetweenStations.
	 * @uml.property  name="timeBetweenStations"
	 */
	public String getTimeBetweenStations() {
		return timeBetweenStations;
	}

	/**
	 * Setter of the property <tt>timeBetweenStations</tt>
	 * @param timeBetweenStations  The timeBetweenStations to set.
	 * @uml.property  name="timeBetweenStations"
	 */
	public void setTimeBetweenStations(String timeBetweenStations) {
		this.timeBetweenStations = timeBetweenStations;
	}

	/**
	 * @uml.property  name="stationA"
	 * @uml.associationEnd  inverse="interStation:graphNetwork.Station"
	 */
	private Station stationA;

	/**
	 * Getter of the property <tt>stationA</tt>
	 * @return  Returns the stationA.
	 * @uml.property  name="stationA"
	 */
	public Station getStationA() {
		return stationA;
	}

	/**
	 * Setter of the property <tt>stationA</tt>
	 * @param stationA  The stationA to set.
	 * @uml.property  name="stationA"
	 */
	public void setStationA(Station stationA) {
		this.stationA = stationA;
	}

	/**
	 * @uml.property  name="stationB"
	 * @uml.associationEnd  inverse="interStation1:graphNetwork.Station"
	 */
	private Station stationB;

	/**
	 * Getter of the property <tt>stationB</tt>
	 * @return  Returns the stationB.
	 * @uml.property  name="stationB"
	 */
	public Station getStationB() {
		return stationB;
	}

	/**
	 * Setter of the property <tt>stationB</tt>
	 * @param stationB  The stationB to set.
	 * @uml.property  name="stationB"
	 */
	public void setStationB(Station stationB) {
		this.stationB = stationB;
	}

}
