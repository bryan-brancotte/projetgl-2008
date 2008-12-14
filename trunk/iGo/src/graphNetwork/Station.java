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

	/**
	 * constructeur d'une station
	 * 
	 * @param id
	 * 			id de la station a creer
	 * @param name
	 * 			nom de la station a creer
	 * @return void
	 */
	protected Station(int id, String name) {
		//TODO Voir l'utilité
		super();
		//TODO Ajouté à l'arrache pour un minimum de fonctionnalité par Tony le 30 novembre
		//TODO par Remi check que cet attribut est deja en private
		junctions = new LinkedList<Junction>();
		//TODO par Remi check que cet attribut est deja en private
		//TODO Ajouté à l'arrache pour un minimum de fonctionnalité par Tony le 30 novembre
		routes = new LinkedList<Route>();
		services = new LinkedList<Service>();
		this.id = id;
		this.name = name;
		
	}

	/**
	 * ajoute une jonction a la liste de jonctions de la station
	 * 
	 * @param junction
	 *            la jonction a ajouter
	 * @return void
	 */
	protected void addJunction(Junction junction) {//Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter une Junction manuellement à votre risque et péril. 
		this.junctions.add(junction);
	}

	/**
	 * ajoute une route a la liste de routes de la station
	 * 
	 * @param route
	 *            la route a ajouter
	 * @return void
	 */
	protected void addRoute(Route route) {//Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter une Route manuellement à votre risque et péril.
		this.routes.add(route);
	}

	/**
	 * ajoute un service a la liste de services de la station
	 * 
	 * @param service
	 *            le service a ajouter
	 * @return void
	 */
	protected void addService(Service service) {//Ajout un route à la station courante. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter un Service manuellement à votre risque et péril.
		this.services.add(service);
	}

	/**
	 * Retourne l'id d'un station
	 * 
	 * @return l'id de la station
	 */
	public int getId() {//retourne l'id de la station
		return id;
	}
	
	/**
	 * Retourne les jonctions d'un station
	 * 
	 * @return un iterateur sur les jonctions existantes
	 */
	public Iterator<Junction> getJunctions() {//retourne un iterator sur la collection des jonctions
		return this.junctions.iterator();
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
	 * setter de l'etat enable d'une station
	 * 
	 * @param enable
	 *            etat a modifier
	 * @return void
	 */
	public void setEnable(boolean enable) {
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
	
	/**
	 * retourne une chaine representant la station
	 * 
	 * @return la chaine
	 * 
	 */
	protected String toMyString(){
		String retour;
		retour="<station>"+id+";"+name+";"+enable;
		retour.concat("<junctionList>");
			Iterator<Junction> it= junctions.iterator();
			while(it.hasNext()){
				retour.concat(it.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</junctionList>");
		retour.concat("<routeList>");
			Iterator<Route> it2= routes.iterator();
			while(it2.hasNext()){
				retour.concat(it2.next().toMyString());
				retour.concat(";");

			}
		retour.concat("</routeList>");
		retour.concat("<serviceList>");
			Iterator<Service> it3= services.iterator();
			while(it3.hasNext()){
				retour.concat(it3.next().toMyString());
				retour.concat(";");

			}
		retour.concat("</serviceList>");
		retour.concat("</station>");
		return retour;
	}
}
