package graphNetwork;

import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;

/**
 * 
 * @author iGo
 */
public class Junction {

	private float cost = 0F;
	private boolean enable = true;
	private boolean pedestrian = false;
	private Route routeA;
	private Route routeB;
	private Station stationB;
	private Station stationA;
	private int timeBetweenStations = 0;
	/**
	 * Permet de savoir si c'est un jonction entre deux station qui sont sur la même ligne (et donc où vous rester dans
	 * votre train), ou une jonction où vous devez descendre du train pour prendre un autre ligne
	 */
	private boolean routeLink = false;

	protected void setTimeBetweenStations(int timeBetweenStations) {
		this.timeBetweenStations = timeBetweenStations;
	}

	/**
	 * Construcuteur d'un jonction, il propose de fournir l'ensemble des informations au sujet de la jonction en une
	 * seul fois. La jonction est par défaut active (the Junction.isEnable()==true).
	 * 
	 * @param routeA
	 *            la première route
	 * @param stationA
	 *            la station sur la première route
	 * @param routeB
	 *            la seconde route
	 * @param stationB
	 *            la station sur la second route
	 * @param cost
	 *            le coût pour emprunter cette jonction
	 * @param timeBetweenStations
	 *            le temps nécessaire pour franchir cette jonction
	 * @param routeLink
	 *            est-ce un lien entre deux station de la même ligne, c'est à dire que l'on reste dans le wagon
	 * @param pedestrian
	 *            si le lien n'est routeLink, on doit déscendre du wagon, mais devont nous aussi sortir de la sation
	 *            pour aller à une autre?
	 * @throws StationNotOnRoadException
	 *             si une des station fournit n'est pas sur la ligne où elle devrait être
	 */
	protected Junction(Route routeA, Station stationA, Route routeB, Station stationB, float cost,
			int timeBetweenStations, boolean routeLink, boolean pedestrian) throws StationNotOnRoadException,
			ImpossibleValueException {
		super();
		this.cost = cost;
		this.pedestrian = pedestrian;
		this.routeA = routeA;
		this.routeB = routeB;
		this.stationB = stationB;
		this.stationA = stationA;
		this.timeBetweenStations = timeBetweenStations;
		this.routeLink = routeLink;
	}

	protected void setCost(float cost) {
		this.cost = cost;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	protected void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	/**
	 * retourne le coût pour traverser la jonction. Ce coup est à 0 si la méthode isRouteLink() retourne true.
	 * 
	 * @return le coût pour traverser la jonction
	 */
	public float getCost() {
		return this.cost;
	}

	/**
	 * informe de l'utilisabilité de la jonction
	 * 
	 * @return true si on peut là traverser
	 */
	public boolean isEnable() {
		return this.enable;
	}

	/**
	 * informe que le lien entre les deux coté implique de sortir de la première station pour alle à la seconde
	 * 
	 * @return
	 */
	public boolean isPedestrian() {
		return this.pedestrian;
	}

	/**
	 * Informe du temps qu'il faut pour parcourir la jonction
	 * 
	 * @return
	 */
	public int getTimeBetweenStations() {
		return this.timeBetweenStations;
	}

	/**
	 * Retourne la route sur laquel est l'autre station. Attention si jamais le lien à la même station des deux coté,
	 * cette méthode retournera toujours la routeB.
	 * 
	 * @param me
	 *            la station qui vous sert de point de repère
	 * @return l'autre route, ou null si la station passé en paramètre n'est pas dans la jonction
	 */
	public Route getOtherRoute(Station me) {
		if (me.getId() == stationA.getId())
			return routeB;
		if (me.getId() == stationB.getId())
			return routeA;
		return null;
	}

	/**
	 * Retourne l'autre route de la jonction.
	 * 
	 * @param me
	 *            la route qui vous sert de point de repère
	 * @return l'autre route, ou null si la route passé en paramètre n'est pas dans la jonction
	 */
	public Route getOtherRoute(Route me) {
		if (me.getId() == routeA.getId())
			return routeB;
		if (me.getId() == routeB.getId())
			return routeA;
		return null;
	}

	/**
	 * Retourne l'autre station de la jonction.
	 * 
	 * @param me
	 *            la station qui vous sert de point de repère
	 * @return l'autre station, ou null si la station passé en paramètre n'est pas dans la jonction
	 */
	public Station getOtherStation(Station me) {
		if (me.getId() == stationA.getId())
			return stationB;
		if (me.getId() == stationB.getId())
			return stationA;
		return null;
	}

	/**
	 * Accesseur permetant de savoir si la jonction relie deux station sur la même lignes, ou si on doit sortir du train
	 * pour passer d'un coté à l'autre de la jonction
	 * 
	 * @return true si on reste dans notre train pour passer d'un coté à l'autre.</br> false si c'est un changement.</br>
	 *         Le retour à true implique donc que le coût est à 0, que les routes sont les même et que les stations sont
	 *         différente.</br>
	 */
	public boolean isRouteLink() {
		return routeLink;
	}

	/**
	 * Surcharge de toString, retourne un résultat de la forme : </br> "Massy(RerC)=>Massy(RerB) : 0$ in 3minutes"
	 */
	public String toString() {
		String s = stationA.getName() + "(" + routeA.getId() + ")";
		if (routeA == routeB)
			s += "<";
		s += "=>";
		s += stationB.getName() + "(" + routeB.getId() + ") : " + cost + "$ in " + timeBetweenStations + " minutes";
		return s;
	}

	/**
	 * Permet de savoir si la jonction correspond bien à la jonction hypotétique passé en paramètre. Dans l'ordre A=>B
	 * 
	 * @param routeA
	 * @param stationA
	 * @param routeB
	 * @param stationB
	 * @return true si la jonction lie le couple A=>B
	 */
	public boolean equals(Route routeA, Station stationA, Route routeB, Station stationB) {
		return (routeA == this.routeA && stationA == this.stationA && routeB == this.routeB && stationB == this.stationB);
	}
	// public Route getRoute(Station me) {// retourne la route d'une station pour cette jonction ou null si la station
	// // n'est pas sur la jonction
	// if (me.getId() == this.getStationA().getId())
	// return this.getRouteA();
	// if (me.getId() == this.getStationB().getId())
	// return this.getRouteB();
	// return null;
	// }

	// public Route getOtherRoute(Station s, Route r) {// retourne la route d'une jontion en fonction de l'autre route
	// ou
	// // null si la route n'est pas sur cette jonction
	// if (s.getId() == getStationA().getId() && r.getId() == getRouteA().getId())
	// return getRouteB();
	// if (s.getId() == getStationB().getId() && r.getId() == getRouteB().getId())
	// return getRouteA();
	// return null;
	// }
	//
	// public Station getOtherStation(Station s, Route r) {// retourne la station d'une jonction en fonction de l'autre
	// // station et de la route qui lui est raccordee, ou null si la
	// // station n'est pas sur cette jonction
	// if (s.getId() == getStationA().getId() && r.getId() == getRouteA().getId())
	// return getStationB();
	// if (s.getId() == getStationB().getId() && r.getId() == getRouteB().getId())
	// return getStationA();
	// return null;
	// }

	// public Route getRouteA() {// retourne la premiere route de la jonction
	// return routeA;
	// }
	//
	// public Route getRouteB() {// retourne la deuxieme route de la jonction
	// return routeB;
	// }
	//
	// public Station getStationA() {// retourne la premiere station de la jonction
	// return stationA;
	// }
	//
	// public Station getStationB() {// retourne la deuxiemme station de la jonction
	// return stationB;
	// }

	// protected void setRouteA(Route routeA) {// Mutateur de routeA. Vous ne devriez pas utiliser cette fonction car
	// // GarphNetworkBuilder le fait, et de façon sûr. Définisez la Route
	// // manuellement à votre risque et péril.
	// this.routeA = routeA;
	// }
	//
	// protected void setRouteB(Route routeB) {// Mutateur de routeB. Vous ne devriez pas utiliser cette fonction car
	// // GarphNetworkBuilder le fait, et de façon sûr. Définisez la Route
	// // manuellement à votre risque et péril.
	// this.routeB = routeB;
	// }
	//
	// protected void setStationA(Station stationA) {// Mutateur de stationA. Vous ne devriez pas utiliser cette
	// fonction
	// // car GarphNetworkBuilder le fait, et de façon sûr. Définisez la
	// // Station manuellement à votre risque et péril.
	// this.stationA = stationA;
	// }
	//
	// protected void setStationB(Station stationB) {// Mutateur de stationB. Vous ne devriez pas utiliser cette
	// fonction
	// // car GarphNetworkBuilder le fait, et de façon sûr. Définisez la
	// // Station manuellement à votre risque et péril.
	// this.stationB = stationB;
	// }

	// protected void setTimeBetweenStations(int timeBetweenStations) throws ImpossibleValueException {// setter du
	// temps
	// // entre deux
	// // stations
	// this.timeBetweenStations = timeBetweenStations;
	// }

	// protected void setRouteLink(boolean routeLink) {// Mutateur de routeLink. Vous ne devriez pas utiliser cette
	// // fonction car GarphNetworkBuilder le fait, et de façon sûr.
	// // Définisez son etat routeLink manuellement à votre risque et
	// // péril.
	// this.routeLink = routeLink;
	// }

	// protected boolean equals (Junction j) {
	// if (
	// (j.getRouteA().getId() == routeA.getId() &&
	// j.getRouteB().getId() == routeB.getId() &&
	// j.getStationA().getId() == stationA.getId() &&
	// j.getStationB().getId() == stationB.getId()) ||
	// (j.getRouteA().getId() == routeB.getId() &&
	// j.getRouteB().getId() == routeA.getId() &&
	// j.getStationA().getId() == stationB.getId() &&
	// j.getStationB().getId() == stationA.getId())
	// )
	// return true;
	//		
	// return false;
	// }

	// protected String toMyString() {
	// String retour = "<junction>" + cost + ";" + enable + ";" + pedestrian + ";" + timeBetweenStations + ";"
	// + routeLink;
	// retour.concat(routeA.toMyString());
	// retour.concat(routeB.toMyString());
	// retour.concat(stationA.toMyString());
	// retour.concat(stationB.toMyString());
	// retour.concat("</junction>");
	// return retour;
	// }
}
