package graphNetwork;

import graphNetwork.exception.StationNotOnRoadException;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class Station {

	private boolean enable;
	private int id;
	protected LinkedList<Junction> junctions;
	private String name;
	protected LinkedList<Route> routes;
	protected LinkedList<Service> services;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Station) {
			return ((Station) obj).getId() == this.id;
		}
		return super.equals(obj);
	}

	/**
	 * constructeur d'une station
	 * 
	 * @param id
	 *            id de la station a creer
	 * @param name
	 *            nom de la station a creer
	 * @return void
	 */
	protected Station(int id, String name) {
		super();
		junctions = new LinkedList<Junction>();
		routes = new LinkedList<Route>();
		services = new LinkedList<Service>();
		this.id = id;
		this.name = name;
		this.enable = true;

	}

	/**
	 * ajoute une jonction a la liste de jonctions de la station
	 * 
	 * @param junction
	 *            la jonction a ajouter
	 * @return void
	 */
	protected boolean addJunction(Junction junction) {
		if (this.junctions.contains(junction))
			return false;
		return this.junctions.add(junction);
	}

	/**
	 * ajoute une route a la liste de routes de la station
	 * 
	 * @param route
	 *            la route a ajouter
	 * @return void
	 */
	protected boolean addRoute(Route route) {
		if (this.routes.contains(route))
			return false;
		return this.routes.add(route);
	}

	/**
	 * ajoute un service a la liste de services de la station
	 * 
	 * @param service
	 *            le service a ajouter
	 * @return true si l'ajout à été fait
	 */
	protected boolean addService(Service service) {
		if (this.services.contains(service))
			return false;
		return this.services.add(service);
	}

	/**
	 * Retourne l'id d'un station
	 * 
	 * @return l'id de la station
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retourne un iterateur sur la collection des jonctions de la station
	 * 
	 * @return un iterateur sur les jonctions existantes. Il peut être vide, mais jamais à null
	 */
	public Iterator<Junction> getJunctions() {
		return this.junctions.iterator();
	}

	/**
	 * Retourne un iterateur sur les jonctions partantes de cette station et de la route passé en paramètre
	 * 
	 * @return un iterateur sur ces jonctions. Il peut être vide, mais jamais à null
	 */
	public Iterator<Junction> getJunctions(Route route) throws StationNotOnRoadException {
		Iterator<Junction> itJ = this.junctions.iterator();
		LinkedList<Junction> ret = new LinkedList<Junction>();
		Junction j;
		while (itJ.hasNext()) {
			if ((j = itJ.next()).haveOnASide(route, this))
				ret.add(j);
		}
		return ret.iterator();
	}

	/**
	 * Retourne le nom d'un station
	 * 
	 * @return le nom de la station
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retourne les routes d'un station
	 * 
	 * @return un iterateur sur les routes de la station
	 */
	public Iterator<Route> getRoutes() {
		return routes.iterator();
	}

	/**
	 * Retourne les services d'une station
	 * 
	 * @return un iterateur sur les services de la station
	 */
	public Iterator<Service> getServices() {
		return services.iterator();
	}

	/**
	 * Retourne l'etat enable d'un station
	 * 
	 * @return un boolean representant l'etat enable
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * Passer par le {@link GraphNetworkBuilder}
	 */
	@Deprecated
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * setter de l'etat enable d'une station
	 * 
	 * @param enable
	 *            etat a modifier
	 * @return void
	 */
	protected void setToEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * setter de l'id d'une station
	 * 
	 * @param id
	 *            id de la station
	 * @return void
	 */
	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * setter du nom d'une station
	 * 
	 * @param name
	 *            nom de la station
	 * @return void
	 */
	protected void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name + "(" + id + ")";
	}
}
