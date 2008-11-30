package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class Station {


	private boolean enable = true;
	private int id;
	private LinkedList<Junction> junctions;
	private String name = "";
	private LinkedList<Route> routes;
	private LinkedList<Service> services;

	protected Station(int id, String name) {
		//TODO Voir l'utilité
		super();
		//TODO Ajouté à l'arrache pour un minimum de fonctionnalité par Tony le 30 novembre
		//TODO par Remi check que cet attribut est deja en private
		junctions = new LinkedList<Junction>();
		//TODO par Remi check que cet attribut est deja en private
		//TODO Ajouté à l'arrache pour un minimum de fonctionnalité par Tony le 30 novembre
		routes = new LinkedList<Route>();
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
		
		this.junctions.add(junction);
	}

	/**
	 * Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le
	 * fait, et de façon sûr. Ajouter une Route manuellement à votre risque et péril.
	 * 
	 * @param routes
	 */
	protected void addRoute(Route route) {
		
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

	public Iterator<Service> getServices() {
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
