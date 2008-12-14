package graphNetwork;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;

import java.util.Iterator;
import java.util.MissingResourceException;

public class GraphNetworkBuilder	 {//Classe suivant les design patterns Factory (création controlé d'objet) et Builder (modification controlé d'objet).

	protected GraphNetwork actualGraphNetwork = null;//le GraphNetwork courant, celui où on est actuellement en travail

	public GraphNetworkBuilder() {//constructeur du monteur de GraphNetwork
		super();
		actualGraphNetwork = new GraphNetwork();
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé une nouvelle route et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant de la route, c'est aussi son nom
	 * @param kinfOf
	 *            sont type
	 * @return la nouvelle route créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Route addRoute(String id, String kindOf) throws ViolationOfUnicityInIdentificationException {
		Route r1 = new Route(id,KindRoute.getKindFromString(kindOf));
		actualGraphNetwork.routes.add(r1);
		return r1;
		
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé un nouveau 	service et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant du service
	 * @param name
	 *            le nom du service
	 * @return le nouveau service créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Service addService(int id, String name) throws ViolationOfUnicityInIdentificationException {
		Service s1 = new Service(id,null);
		actualGraphNetwork.services.add(s1);
		return s1;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé un nouveau service et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant du service
	 * @param name
	 *            le nom du service
	 * @param description
	 * 				description rapide du service
	 * @return le nouveau service créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Service addService(int id, String name,String description) throws ViolationOfUnicityInIdentificationException {
		Service s1 = new Service(id,description);
		actualGraphNetwork.services.add(s1);
		return s1;
	}
	
	/**
	 * En travaillant sur le GraphNetwork courant, on ajout un service à un station. Si le service est déja présent, on ne fait
	 * rien.
	 * 
	 * @param station
	 *            la station en question
	 * @param serviceToAdd
	 *            le service en question
	 * @return true si le service n'était pas présent, et l'est désormais.
	 */
	public boolean addServiceToStation(Station station, Service serviceToAdd) {
		if(station==null || serviceToAdd==null || !actualGraphNetwork.stations.contains(station))
			return false;
		
		Iterator<Station> it = actualGraphNetwork.stations.iterator();
		while(it.hasNext()){
			if(it.next().equals(station)){								//pour la station en question
				Iterator<Service> itS = it.next().getServices();
				while(itS.hasNext()){
					if(itS.next().equals(serviceToAdd))					//recherche si le service existe deja
							return true;								
				}
				it.next().addService(serviceToAdd);						//ajout si il n'existe pas
				return true;	
			}
		}
		return false;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on créé une nouvelle station et l'ajoute au GraphNetwork courant. Si
	 * l'identifiant passé existe déjà on jete une exception.
	 * 
	 * @param id
	 *            l'identifiant de la station
	 * @param name
	 *            le nom de la station
	 * @return la station route créé
	 * @throws ViolationOfUnicityInIdentificationException
	 *             le type d'exception jeté si l'identifiant est déja utilisé.
	 */
	public Station addStation(int id, String name) throws ViolationOfUnicityInIdentificationException {
		Station stationAdd = new Station(id,name);
		actualGraphNetwork.stations.add(stationAdd);
		return stationAdd;
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on ajout une station à un route. Si la station est déja présente, on ne fait
	 * rien.
	 * 
	 * @param route
	 *            la route en question
	 * @param stationToAdd
	 *            la station en question
	 * @param time
	 *            le temps entre la précédente station et celle ci
	 * @return true si la station n'était pas présent, et l'est désormais.
	 */
	public boolean addStationToRoute(Route route, Station stationToAdd, int time) {
		// TODO que faire avec time?
		if(route == null || stationToAdd == null)
			return false;
		
		Iterator<Route> it = actualGraphNetwork.routes.iterator();
		while(it.hasNext()){
			if(it.next().equals(route)){								//pour la route en question
				Iterator<Station> itS = it.next().getStations();
				while(itS.hasNext()){
					if(itS.next().equals(stationToAdd))					//recherche si la station existe deja
							return true;								
				}
				it.next().addStation(stationToAdd);					//ajout si elle n'existe pas
				return true;	
			}
		}
		
		return false;
	}

	/**
	 * Définit le coût d'entrée pour accéder depuis l'exterieur à une ligne de ce type
	 * 
	 * @param kind
	 *            le type de la ligne
	 * @param cost
	 *            le coût pour y accéder
	 */
	public void defineEntryCost(KindRoute kind, int cost) {
		Iterator<KindRoute> itK = KindRoute.getKinds();
		while(itK.hasNext()){
			if(itK.next().equals(kind)){
				itK.next().setKindCost(cost);
				return;
			}
		}
	}

	/**
	 * retourne l'actuelle GraphNetwork.
	 * 
	 * @return le GraphNetwork courant ou null si'il n'y en a pas.
	 */
	protected GraphNetwork getActualGraphNetwork() {
		return actualGraphNetwork;
	}

	/**
	 * Créé initialise et retourne une instance de GraphNetwork.
	 * 
	 * @return une nouvelle instance de GraphNetwork
	 */
	public GraphNetwork getInstance() {
		
		return new GraphNetwork();
	}

	/**
	 * En travaillant sur le GraphNetwork courant, on lie deux station entre elles par un lien à faire à pied. On spécifie sur
	 * quelles routes sont les stations. On spécifie le cout monétaire et en temps pour emprunter le changement. On
	 * spécifie de plus si le lien est "long" c'est à dire fait resortir de la station pour rejoindre la seconde, sans
	 * pour autant impliquer une surtaxe.
	 * 
	 * @param routeOrigin
	 *            la route origine
	 * @param stationOrigin
	 *            la station origine
	 * @param routeDestination
	 *            la route destination
	 * @param stationDestination
	 *            la station destination
	 * @param cost
	 *            le coût pour emprunter le changement (positif ou null)
	 * @param time
	 *            le temps necessaire pour parcourir le chemin (positif ou null)
	 * @param pedestrian
	 *            ce lien est-il long?
	 * @throws StationNotOnRoadException
	 *             jeté si une station n'est pas sur la route que l'on suppose.
	 * @throws MissingResourceException
	 *             jeté si un des objet est null, ou si une valeur est incorrect.
	 * @throws ImpossibleValueException 
	 */
	public void linkStation(Route routeOrigin, Station stationOrigin, Route routeDestination,
			Station stationDestination, float cost, int time, boolean pedestrian) throws StationNotOnRoadException,
			MissingResourceException, ImpossibleValueException {
		Iterator<Route> itR = actualGraphNetwork.routes.iterator();
		Junction jOrigine 		= new Junction(routeOrigin,stationOrigin,routeDestination,stationDestination,cost,time,true,pedestrian);
		Junction jDestination 	= new Junction(routeDestination,stationDestination,routeOrigin,stationOrigin,cost,time,true,pedestrian);
		
		while(itR.hasNext()){
			if(itR.next().equals(routeOrigin)){			// dans la route origine
				Iterator<Station> itS = itR.next().getStations();
				while(itS.hasNext()){
					if(itS.next().equals(stationOrigin)){//pour la station origine
						itS.next().addJunction(jOrigine);//ajout d'une jonction vers la station dest
					}
				}
			}
			if(itR.next().equals(routeDestination)){			// dans la route destination
				Iterator<Station> itS = itR.next().getStations();
				while(itS.hasNext()){
					if(itS.next().equals(stationDestination)){//pour la station destination
						itS.next().addJunction(jDestination);//ajout d'une jonction vers la station origine
					}
				}
			}
		}
	}

	/**
	 * Réinitialise le contenue de l'objet courant
	 */
	public void reset() {
		actualGraphNetwork = null;
	}

	/**
	 * définit le GraphNetwork passé en paramètre comme le GraphNetwork courant.
	 * 
	 * @param actualGraphNetwork
	 *            le futur GraphNetwork courant.
	 */
	protected void setActualGraphNetwork(GraphNetwork actualGraphNetwork) {
		this.actualGraphNetwork = actualGraphNetwork;
	}
}
