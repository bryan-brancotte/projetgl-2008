package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class Route {

	private boolean enable = true;//@uml.property name="enable"
	private String id = "";//@uml.property name="id"
	private KindRoute kindRoute;//@uml.property name="kindRoute"
	private LinkedList<Station> stations;//@uml.property name="stations"
	//private LinkedList<Station> stationsDisabled;
	
	/**
	 * Constructeur d'un nouvelle objet avec un id spécifique et un type spécifique. L'unicité de l'id auprès des autre
	 * instance n'est pas vérifié.
	 * 
	 * @param id
	 *            l'identifiant de cette ligne
	 * @param kindRoute
	 *            le type de cette ligne
	 * @throws NullPointerException
	 *             une exception est jetté si un des deux paramètre est null
	 */
	protected Route(String id, KindRoute kindRoute) throws NullPointerException {
		super();
		this.id = id;
		this.kindRoute = kindRoute;
		this.stations = new LinkedList<Station>();
		//this.stationsDisabled = new LinkedList<Station>();
	}

	/**
	 * retourne l'id de la route
	 * 
	 * @return l'id de la route
	 */
	public String getId() {
		return id;
	}

	/**
	 * retourne le type de la route
	 * 
	 * @return le type de la route
	 */
	public KindRoute getKindRoute() {
		return kindRoute;
	}

	/**
	 * retourne les stations d'une route
	 * 
	 * @return un iterateur sur les station de la route
	 */
	public Iterator<Station> getStations() {
		return stations.iterator();
	}

	/**
	 * retourne l'etat enable d'une route
	 * 
	 * @return etat enable de la route
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * setter de l'etat enable d'une route
	 * 
	 * @param enable
	 *         nouvel etat a appliquer a la route
	 * @return void
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * setter de l'id d'une route
	 * 
	 * @param id
	 *            le service a jouter
	 * @return void
	 */
	protected void setId(String id) {
		this.id = id;
	}

	/**
	 * setter du type de route
	 * 
	 * @param kindRoute
	 *            type de la route
	 * @return void
	 */
	protected void setKindRoute(KindRoute kindRoute) {
		this.kindRoute = kindRoute;
	}

	/**
	 * ajoute une station a la route. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter une Station manuellement à votre risque et péril.
	 * 
	 * @param station
	 *       	la station a ajouter
	 * @return void
	 */
	protected void addStation(Station station) {
		this.stations.add(station);
	}

	/**
	 * modifie l'etat enable d'une station
	 * 
	 * @param id
	 *          id de la station a modifier
	 * @param stationEnable
	 * 			nouvel etat enable de la station
	 * @return void
	 */
	public void setStationEnable(int idStation, boolean stationEnable) {
		Iterator<Station> s1 = stations.iterator();
		while(s1.hasNext()){
			Station temp=s1.next();
			if(temp.getId() == idStation)
				temp.setEnable(stationEnable);
		}
	}
	
	/**
	 * retourne l'etat enable d'une station
	 * 
	 * @param id
	 *          id de la station recherchee
	 * @return etat enable de la station
	 */
	public boolean isStationEnable(int idStation) {
		Iterator<Station> s1 = stations.iterator();
		while(s1.hasNext()){
			Station temp = s1.next();
			if(temp.getId() == idStation)
				return temp.isEnable();
		}
		return false;
	}
	protected String toMyString(){
		String retour;
		retour = "<route>"+id+";"+enable+";"+kindRoute.toMyString();
		Iterator<Station> it= stations.iterator();
		retour.concat("<stationList>");
			while(it.hasNext()){
				retour.concat(it.next().toMyString());
				retour.concat(",");
			}
		retour.concat("</stationList>");
		retour.concat("</route>");
		return retour;
	}
	

}
