package tools.streamInFolder.graphReaderFolder;

import java.util.Collection;


public class Station {

	/** 
	 * Getter of the property <tt>Name</tt>
	 * @return  Returns the name.
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name1;
	}

	/** 
	 * Setter of the property <tt>Name</tt>
	 * @param Name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		name1 = name;
	}

	/**
	 * @uml.property   name="StationServicesList"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="station:tools.streamInFolder.graphReaderFolder.Service"
	 */
	private Collection<Service> stationServicesList;

	/** 
	 * Getter of the property <tt>StationServicesList</tt>
	 * @return  Returns the stationServicesList.
	 * @uml.property  name="StationServicesList"
	 */
	public Collection<Service> getStationServicesList() {
		return stationServicesList;
	}

	/** 
	 * Setter of the property <tt>StationServicesList</tt>
	 * @param StationServicesList  The stationServicesList to set.
	 * @uml.property  name="StationServicesList"
	 */
	public void setStationServicesList(Collection<Service> stationServicesList) {
		this.stationServicesList = stationServicesList;
	}

	/**
	 * @uml.property  name="name"
	 */
	private String name1 = "";
	/**
	 * @uml.property  name="id"
	 */
	private int id1;

	/** 
	 * Getter of the property <tt>ID</tt>
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id1;
	}

	/** 
	 * Setter of the property <tt>ID</tt>
	 * @param ID  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		id1 = id;
	}

}
