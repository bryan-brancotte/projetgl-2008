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

	public String getId() {//retourne l'id d'une route
		return id;
	}

	public KindRoute getKindRoute() {//retourne le kindRoute d'une route
		return kindRoute;
	}

	public Iterator<Station> getStations() {//retourne un iterator sur la collection des route
		return stations.iterator();
	}

	public boolean isEnable() {//retourne l'etat enable d'un route
		return enable;
	}

	public void setEnable(boolean enable) {//setter de l'etat enable d'un route
		this.enable = enable;
	}

	protected void setId(String id) {//setter de l'id d'une route
		this.id = id;
	}

	protected void setKindRoute(KindRoute kindRoute) {//setter du kindRoute d'une route
		this.kindRoute = kindRoute;
	}

	public void addStation(Station station) {//Ajoute un station à cette ligne. Vous ne devriez pas utiliser cette function car GarphNetworkBuilder le fait, et de façon sûr. Ajouter une Station manuellement à votre risque et péril.
		this.stations.add(station);
	}

	public void setStationEnable(int idStation, boolean stationEnable) {//modifie l'etat enable d'une station selon son id
		Iterator<Station> s1 = stations.iterator();
		while(s1.hasNext()){
			if(s1.next().getId() == idStation)
				s1.next().setEnable(stationEnable);
		}
	}

	public boolean isStationEnable(int idStation) {//retourne l'etat enable d'une route
		Iterator<Station> s1 = stations.iterator();
		while(s1.hasNext()){
			if(s1.next().getId() == idStation)
				return s1.next().isEnable();
		}
		return false;
	}

}
