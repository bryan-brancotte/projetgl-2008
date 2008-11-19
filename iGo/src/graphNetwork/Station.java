package graphNetwork;

import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class Station implements StationReader {

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

	@Override
	public int getId() {
		return id;
	}

	public LinkedList<Inter> getInter() {
		return this.inter;
	}

	@Override
	public InterReader[] getInterR() {
		return this.getInter().toArray(new InterReader[0]);
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Getter of the property <tt>routes</tt>
	 * 
	 * @return Returns the routes.
	 * @uml.property name="routes"
	 */
	public LinkedList<Route> getRoutes() {
		return routes;
	}

	@Override
	public RouteReader[] getRoutesR() {
		return this.getRoutes().toArray(new RouteReader[0]);
	}

	/**
	 * @uml.property name="service"
	 */
	public LinkedList<Service> getService() {
		return service;
	}

	@Override
	public ServiceReader[] getServiceR() {
		return this.getService().toArray(new ServiceReader[0]);
	}

	@Override
	public boolean isEnable() {
		return enable;
	}

	/**
	 * Setter of the property <tt>enable</tt>
	 * 
	 * @param enable
	 *            true if you can steep down
	 * @uml.property name="enable"
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void addInter(Inter inter) {
		this.inter.add(inter);
	}

	public void addRoute(Route routes) {
		this.routes.add(routes);
	}

	public void addService(Service service) {
		this.service.add(service);
	}

	public Station(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
