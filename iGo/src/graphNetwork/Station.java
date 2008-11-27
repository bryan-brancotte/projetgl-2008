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
	 * @uml.property name="inter"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="stationA:graphNetwork.Inter"
	 */
	private LinkedList<Inter> inter;

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
	private LinkedList<Service> service;

	protected Station(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le
	 * fait, et de façon sûr. Ajouter une Inter manuellement à votre risque et péril.
	 * 
	 * @param inter
	 */
	protected void addInter(Inter inter) {
		this.inter.add(inter);
	}

	/**
	 * Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le
	 * fait, et de façon sûr. Ajouter une Route manuellement à votre risque et péril.
	 * 
	 * @param routes
	 */
	protected void addRoute(Route routes) {
		this.routes.add(routes);
	}

	/**
	 * Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le
	 * fait, et de façon sûr. Ajouter un Service manuellement à votre risque et péril.
	 * 
	 * @param service
	 */
	protected void addService(Service service) {
		this.service.add(service);
	}

	public int getId() {
		return id;
	}

	public Iterator<Inter> getInter() {
		return this.inter.iterator();
	}

	public String getName() {
		return name;
	}
	
	public Iterator<Route> getRoutes() {
		return routes.iterator();
	}
	
	public Iterator<Service> getService() {
		return service.iterator();
	}

	public boolean isEnable() {
		return enable;
	}

	protected void setEnable(boolean enable) {
		this.enable = enable;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected void setName(String name) {
		this.name = name;
	}

}
