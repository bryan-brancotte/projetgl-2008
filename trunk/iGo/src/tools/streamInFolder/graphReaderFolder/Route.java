package tools.streamInFolder.graphReaderFolder;

import java.util.LinkedList;


public class Route {

	/**
	 * @uml.property   name="RouteSectionsList"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" inverse="route:tools.streamInFolder.graphReaderFolder.Section"
	 */
	private LinkedList<Route> routeSectionsList;

	/** 
	 * Getter of the property <tt>RouteSectionsList</tt>
	 * @return  Returns the routeSectionsList.
	 * @uml.property  name="RouteSectionsList"
	 */
	public LinkedList<Route> getRouteSectionsList() {
		return routeSectionsList;
	}

	/** 
	 * Setter of the property <tt>RouteSectionsList</tt>
	 * @param RouteSectionsList  The routeSectionsList to set.
	 * @uml.property  name="RouteSectionsList"
	 */
	public void setRouteSectionsList(LinkedList<Route> routeSectionsList) {
		this.routeSectionsList = routeSectionsList;
	}

	/**
	 * @uml.property  name="id"
	 */
	private String id = "";

	/** 
	 * Getter of the property <tt>ID</tt>
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}

	/** 
	 * Setter of the property <tt>ID</tt>
	 * @param ID  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @uml.property  name="kindRoute"
	 * @uml.associationEnd  inverse="route:tools.streamInFolder.graphReaderFolder.KindRoute"
	 */
	private KindRoute kindRoute;

	/**
	 * Getter of the property <tt>kindRoute</tt>
	 * @return  Returns the kindRoute.
	 * @uml.property  name="kindRoute"
	 */
	public KindRoute getKindRoute() {
		return kindRoute;
	}

	/**
	 * Setter of the property <tt>kindRoute</tt>
	 * @param kindRoute  The kindRoute to set.
	 * @uml.property  name="kindRoute"
	 */
	public void setKindRoute(KindRoute kindRoute) {
		this.kindRoute = kindRoute;
	}

}
