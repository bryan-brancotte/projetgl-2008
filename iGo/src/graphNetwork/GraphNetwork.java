package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class GraphNetwork {

	protected LinkedList<Route> routes;//Liste des routes présente dans le GraphNetwork
	protected LinkedList<Service> services;//Liste des services présente dans le GraphNetwork
	protected LinkedList<Station> stations;//Liste des stations présente dans le GraphNetwork

	
	protected GraphNetwork() {//Construteur d'un objet GraphNetwork
		super();
		routes = new LinkedList<Route>();
		services = new LinkedList<Service>();
		stations = new LinkedList<Station>();
	}

	public float getEntryCost(KindRoute kind)throws NullPointerException {//retourne le coût d'entrée pour accéder depuis l'exterieur à une ligne de ce type
		return kind.cost;
	}

	public PathInGraphBuilder getInstancePathInGraphBuilder() {//Créé initialise et retourne une instance de monteur de PathInGraph : un PathInGraphBuilder déjà initialisé avec un nouveau trajet.
		return new PathInGraphBuilder(this);
	}

	public PathInGraphBuilder getInstancePathInGraphBuilder(PathInGraph path) {//Créé initialise et retourne une instance de monteur de PathInGraph : un PathInGraphBuilder initialisé avec le trajet passé en paramètre.
		PathInGraphBuilder b =  new PathInGraphBuilder(this);
		b.setActualPathInGraph(path);
		return b;
	}

	/**
	 * Retourne les changements présente entre les deux stations passé en paramètres.
	 * 
	 * @param stationA
	 *            la première station
	 * @param stationB
	 *            la seconde station
	 * @return un iterateur sur les changements existant
	 */
	public Iterator<Junction> getJunctions(Station stationA, Station stationB) {
		Iterator<Junction> it1 = stationA.getJunctions();
		Iterator<Junction> it2 = stationB.getJunctions();
		LinkedList<Junction> jonction = new LinkedList<Junction>();
		
		while(it1.hasNext()){
			while(it2.hasNext()){
				if(it1.next().equals(it2.next())){
					jonction.add(it1.next());
				}
			}
		}
		
		return jonction.iterator();
	}

	/**
	 * Obtient l'objet représentant le type de route dont le nom est passé en paramètre
	 * 
	 * @param kindOf
	 *            le nom du type de route
	 * @return le type de route, ou null s'il n'existe aps
	 */
	public KindRoute getKindFromString(String kindOf) {
		return KindRoute.getKindFromString(kindOf);
	}

	/**
	 * retourne les types de route existants dans le réseau
	 * 
	 * @return un iterateur sur les types de route
	 */
	public Iterator<KindRoute> getKinds() {
		Iterator<Route> r1 = routes.iterator();
		LinkedList<KindRoute> kindroute = new LinkedList<KindRoute>();
		while(r1.hasNext()){
			kindroute.add(r1.next().getKindRoute());
		}
		
		return kindroute.iterator();
	}
	
	public Route getRoute(String i) {//etourne la route dont on connait l'identifiant ou null si elle n'existe pas 
		Iterator<Route> it=routes.iterator();
		while(it.hasNext()){
			Route r1 = (Route)it.next();
			if(r1.getId()==i){
				return r1;
			}
		}
		return null;
	}

	public Iterator<Route> getRoutes() {//retourne un iterateur sur les routes existantes dans le réseau
		return routes.iterator();
	}

	public Service getService(int id) {//retourne le service dont on connait l'identifiant ou null s'il n'existe pas 
		Iterator<Service> it = services.iterator();
		while(it.hasNext()){
			Service s1 = (Service)it.next();
			if(s1.getId() == id){
				return s1;
			}
		}
		return null;
	}

	public Iterator<Service> getServices() {//@return un iterateur sur les services existants dans le reseau
		return services.iterator();
	}

	public Station getStation(int id) {//retourne la station dont on connait l'identifiant, null sinon
		if(id<stations.size()+1){
			return stations.get(id);
		}
		else{return null;}
	}

	public Iterator<Station> getStations() {//retourne les stations existants dans le réseau
		return stations.iterator();
	}

	public void resetEnables() {//remet les composants du réseau comme actif.
		Iterator<Route> itRoute		= routes.iterator();
		Iterator<Station> itStation	= stations.iterator();
		
		while(itRoute.hasNext()){
			Route r1 = (Route)itRoute.next();
			r1.setEnable(true);
		}

		while(itStation.hasNext()){
			Station s1 = (Station)itStation.next();
			s1.setEnable(true);
		}
	}
	
	protected String toMyString(){
		String retour="<graphNetwork>";
		retour.concat("<routeList>");
			Iterator<Route> it1=routes.iterator();
			while(it1.hasNext()){
				retour.concat(it1.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</routeList>");
		retour.concat("<serviceList>");
			Iterator<Service> it2=services.iterator();
			while(it2.hasNext()){
				retour.concat(it2.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</serviceList>");
		retour.concat("<StationList>");
			Iterator<Station> it3=stations.iterator();
			while(it3.hasNext()){
				retour.concat(it3.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</routeList>");
		
		retour.concat("</graphNetwork>)");
		return retour;
	}
}
