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

	protected void addJunction(Junction junction) {//Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter une Junction manuellement à votre risque et péril. 
		this.junctions.add(junction);
	}

	protected void addRoute(Route route) {//Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter une Route manuellement à votre risque et péril.
		this.routes.add(route);
	}

	protected void addService(Service service) {//Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter un Service manuellement à votre risque et péril.
		this.services.add(service);
	}

	public int getId() {//retourne l'id de la station
		return id;
	}

	public Iterator<Junction> getJunctions() {//retourne un iterator sur la collection des jonctions
		return this.junctions.iterator();
	}

	public String getName() {//retourne le nom de la jonction
		return name;
	}

	public Iterator<Route> getRoutes() {//retourne un iterator sur les routes
		return routes.iterator();
	}

	public Iterator<Service> getServices() {//retourne un iterator sur les services
		return services.iterator();
	}

	public boolean isEnable() {//retourne l'etat enable d'une station
		return enable;
	}

	public void setEnable(boolean enable) {//setter de l'etat enable d'un station
		this.enable = enable;
	}

	protected void setId(int id) {//setter de l'id d'une station
		this.id = id;
	}

	protected void setName(String name) {//setter du nom d'une station
		this.name = name;
	}
}
