package tools.streamInFolder.graphReaderFolder;

import java.util.Collection;
import java.util.LinkedList;


public class Section {

	/**
	 * @uml.property   name="SectionStationsList"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" inverse="section:tools.streamInFolder.graphReaderFolder.Station"
	 */
	private LinkedList sectionStationsList;

	/** 
	 * Getter of the property <tt>SectionStationsList</tt>
	 * @return  Returns the sectionStationsList.
	 * @uml.property  name="SectionStationsList"
	 */
	public LinkedList getSectionStationsList() {
		return sectionStationsList;
	}

	/** 
	 * Getter of the property <tt>TimeBetweenStations</tt>
	 * @return  Returns the timeBetweenStations.
	 * @uml.property  name="timeBetweenStations"
	 */
	public byte getTimeBetweenStations() {
		return betweenStations;
	}

	/** 
	 * Setter of the property <tt>TimeBetweenStations</tt>
	 * @param TimeBetweenStations  The timeBetweenStations to set.
	 * @uml.property  name="timeBetweenStations"
	 */
	public void setTimeBetweenStations(byte timeBetweenStations) {
		betweenStations = timeBetweenStations;
	}

	/** 
	 * Setter of the property <tt>SectionStationsList</tt>
	 * @param SectionStationsList  The sectionStationsList to set.
	 * @uml.property  name="SectionStationsList"
	 */
	public void setSectionStationsList(LinkedList sectionStationsList) {
		this.sectionStationsList = sectionStationsList;
	}

	/**
	 * @uml.property  name="timeBetweenStations"
	 */
	private byte betweenStations;

}
