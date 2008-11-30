package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class Station {

	/**
	 * @uml.property name="enable"
	 */
	private boolean enable = true;

	/**
	 * @uml.property name="id" readOnly="true"
	 */
	private int id;

	/**
	 * @uml.property name="junctions"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="stationA:graphNetwork.Junction"
	 */
	private LinkedList<Junction> junctions;

	/**
	 * @uml.property name="name" readOnly="true"
	 */
	private String name = "";

	/**
	 * @uml.property name="routes"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="stations:graphNetwork.Route"
	 */
	private LinkedList<Route> routes;

	/**
	 * @uml.property name="service"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="station:graphNetwork.Service"
	 */
	private LinkedList<Service> services;

	protected Station(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le
	 * fait, et de façon sûr. Ajouter une Junction manuellement à votre risque et péril.
	 * 
	 * @param junction
	 */
	protected void addJunction(Junction junction) {
		//TODO Ajouté à l'arrache pour un minimum de fonctionnalité par Tony le 30 novembre
		if (junctions==null) junctions = new LinkedList<Junction>();
		
		this.junctions.add(junction);
	}

	/**
	 * Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le
	 * fait, et de façon sûr. Ajouter une Route manuellement à votre risque et péril.
	 * 
	 * @param routes
	 */
	protected void addRoute(Route route) {
		//TODO Ajouté à l'arrache pour un minimum de fonctionnalité par Tony le 30 novembre
		if (routes==null) routes = new LinkedList<Route>();
		
		this.routes.add(route);
		
	}

	/**
	 * Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le
	 * fait, et de façon sûr. Ajouter un Service manuellement à votre risque et péril.
	 * 
	 * @param service
	 */
	protected void addService(Service service) {
		this.services.add(service);
	}

	public int getId() {
		return id;
	}

	public Iterator<Junction> getJunction() {
		return this.junctions.iterator();
	}

	public String getName() {
		return name;
	}

	public Iterator<Route> getRoutes() {
		return routes.iterator();
	}

	public Iterator<Service> getService() {
		return services.iterator();
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected void setName(String name) {
		this.name = name;
	}

}
