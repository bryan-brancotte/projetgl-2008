package tools.streamInFolder.graphReaderFolder;

import java.util.Collection;
import java.util.LinkedList;


public class Route {

	/**
	 * @uml.property  name="ID"
	 */
	private String id = "";

	/**
	 * Getter of the property <tt>ID</tt>
	 * @return  Returns the id.
	 * @uml.property  name="ID"
	 */
	public String getID() {
		return id;
	}

	/**
	 * Setter of the property <tt>ID</tt>
	 * @param ID  The id to set.
	 * @uml.property  name="ID"
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 * @uml.property   name="RouteSectionsList"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" inverse="route:tools.streamInFolder.graphReaderFolder.Section"
	 */
	private LinkedList routeSectionsList;

	/** 
	 * Getter of the property <tt>RouteSectionsList</tt>
	 * @return  Returns the routeSectionsList.
	 * @uml.property  name="RouteSectionsList"
	 */
	public LinkedList getRouteSectionsList() {
		return routeSectionsList;
	}

	/**
	 * @uml.property   name="Kind"
	 * @uml.associationEnd   inverse="route:tools.streamInFolder.graphReaderFolder.KindRoute"
	 */
	private KindRoute kind;

	/**
	 * Getter of the property <tt>Kind</tt>
	 * @return  Returns the kind.
	 * @uml.property  name="Kind"
	 */
	public KindRoute getKind() {
		return kind;
	}

	/**
	 * Setter of the property <tt>Kind</tt>
	 * @param Kind  The kind to set.
	 * @uml.property  name="Kind"
	 */
	public void setKind(KindRoute kind) {
		this.kind = kind;
	}

	/** 
	 * Setter of the property <tt>RouteSectionsList</tt>
	 * @param RouteSectionsList  The routeSectionsList to set.
	 * @uml.property  name="RouteSectionsList"
	 */
	public void setRouteSectionsList(LinkedList routeSectionsList) {
		this.routeSectionsList = routeSectionsList;
	}

}
