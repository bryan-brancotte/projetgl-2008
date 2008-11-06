package streamInFolder.graphReaderFolder;

import java.util.LinkedList;


public class Section {

	/**
	 * @uml.property   name="SectionStationsList"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" inverse="section:streamInFolder.graphReaderFolder.Station"
	 */
	private LinkedList<Service> sectionStationsList;

	/** 
	 * Getter of the property <tt>SectionStationsList</tt>
	 * @return  Returns the sectionStationsList.
	 * @uml.property  name="SectionStationsList"
	 */
	public LinkedList<Service> getSectionStationsList() {
		return sectionStationsList;
	}

	/** 
	 * Getter of the property <tt>TimeBetweenStations</tt>
	 * @return  Returns the timeBetweenStations.
	 * @uml.property  name="timeBetweenStations"
	 */
	public int getTimeBetweenStations() {
		return betweenStations;
	}

	/** 
	 * Setter of the property <tt>TimeBetweenStations</tt>
	 * @param TimeBetweenStations  The timeBetweenStations to set.
	 * @uml.property  name="timeBetweenStations"
	 */
	public void setTimeBetweenStations(int timeBetweenStations) {
		betweenStations = timeBetweenStations;
	}

	/** 
	 * Setter of the property <tt>SectionStationsList</tt>
	 * @param SectionStationsList  The sectionStationsList to set.
	 * @uml.property  name="SectionStationsList"
	 */
	public void setSectionStationsList(LinkedList<Service> sectionStationsList) {
		this.sectionStationsList = sectionStationsList;
	}

	/**
	 * @uml.property  name="timeBetweenStations"
	 */
	private int betweenStations;

}
