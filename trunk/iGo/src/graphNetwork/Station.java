package graphNetwork;

import java.util.LinkedList;
import java.util.Collection;

public class Station implements StationReader {

	/** 
	 * @uml.property name="service"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="station:graphNetwork.Service"
	 */
	private LinkedList<Service> service;

	/** 
	 * @uml.property  name="service"
	 */
	public LinkedList<Service> getService() {
		return service;
	}

	/**
	 * @uml.property name="inter"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="stationA:graphNetwork.Inter"
	 */
	private LinkedList<Inter> inter;

	/**
	 * @uml.property   name="name" readOnly="true"
	 */
	private String name = "";

	public String getName() {
		return name;
	}

	/**
	 * @uml.property   name="id" readOnly="true"
	 */
	private int id;

	public int getId() {
		return id;
	}

	public InterReader[] getInterchangeR() {
		return this.getInter().toArray(new InterReader[0]);
	}

	@Override
	public ServiceReader[] getServiceR() {
		return this.getService().toArray(new ServiceReader[0]);
	}

	public LinkedList<Inter> getInter() {
		return this.inter;
	}

	public void setInter(LinkedList<Inter> inter) {
		this.inter = inter;
	}

	public RouteReader[] getRoutesR() {
		return this.getRoutes().toArray(new RouteReader[0]);
	}

	/**
	 * @uml.property   name="routes"
	 * @uml.associationEnd   multiplicity="(0 -1)" ordering="true" inverse="stations:graphNetwork.Route"
	 */
	private LinkedList<Route> routes;

	/**
	 * Getter of the property <tt>routes</tt>
	 * 
	 * @return Returns the routes.
	 * @uml.property name="routes"
	 */
	public LinkedList<Route> getRoutes() {
		return routes;
	}

	/**
	 * Setter of the property <tt>routes</tt>
	 * 
	 * @param routes
	 *            The routes to set.
	 * @uml.property name="routes"
	 */
	public void setRoutes(LinkedList<Route> routes) {
		this.routes = routes;
	}

	/**
	 * @uml.property  name="enable"
	 */
	private boolean enable;
	
	@Override
	public boolean isEnable() {
		return enable;
	}

	/**
	 * Setter of the property <tt>enable</tt> 
	 * @param enable  true if you can steep down
	 * @uml.property  name="enable"
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/** 
	 * Setter of the property <tt>service</tt>
	 * @param service The service to set.
	 * @uml.property  name="service"
	 */
	public void setService(LinkedList<Service> service) {
		this.service = service;
	}

}
