package interpretationFichierXML;

import java.util.Collection;
import java.util.LinkedList;


public class Section {

	/** 
	 * @uml.property name="SectionStationsList"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="section:interpretationFichierXML.Station"
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
	 * @uml.property  name="TimeBetweenStations"
	 */
	private byte timeBetweenStations;

	/**
	 * Getter of the property <tt>TimeBetweenStations</tt>
	 * @return  Returns the timeBetweenStations.
	 * @uml.property  name="TimeBetweenStations"
	 */
	public byte getTimeBetweenStations() {
		return timeBetweenStations;
	}

	/**
	 * Setter of the property <tt>TimeBetweenStations</tt>
	 * @param TimeBetweenStations  The timeBetweenStations to set.
	 * @uml.property  name="TimeBetweenStations"
	 */
	public void setTimeBetweenStations(byte timeBetweenStations) {
		this.timeBetweenStations = timeBetweenStations;
	}

	/** 
	 * Setter of the property <tt>SectionStationsList</tt>
	 * @param SectionStationsList  The sectionStationsList to set.
	 * @uml.property  name="SectionStationsList"
	 */
	public void setSectionStationsList(LinkedList sectionStationsList) {
		this.sectionStationsList = sectionStationsList;
	}

}
