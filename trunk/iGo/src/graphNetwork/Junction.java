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
	private boolean routeLink = false; // pour savoir if the junction is rail between two station or an junction were you have to step down from you TODO: moi pas parler anglais comme ca
	
	protected void setCost(float cost) {
		this.cost = cost;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	protected void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	public float getCost() {
		return this.cost;
	}

	public boolean isEnable() {
		return this.enable;
	}

	public boolean isPedestrian() {
		return this.pedestrian;
	}

	public int getTimeBetweenStations() {
		return this.timeBetweenStations;
	}

	public Route getRoute(Station me) {//retourne la route d'une station pour cette jonction ou null si la station n'est pas sur la jonction
		if (me.getId() == this.getStationA().getId())
			return this.getRouteA();
		if (me.getId() == this.getStationB().getId())
			return this.getRouteB();
		return null;
	}

	public Route getOtherRoute(Station me) {//retourne la route d'une jonction en fonction de la station ou null si la station n'est pas sur cette jonction
		if (me.getId() == this.getStationA().getId())
			return this.getRouteB();
		if (me.getId() == this.getStationB().getId())
			return this.getRouteA();
		return null;
	}

	public Route getOtherRoute(Route me) {//retourne la route d'une jontion en fonction de l'autre route ou null si la route n'est pas sur cette jonction
		if (me.getId() == this.getRouteA().getId())
			return this.getRouteB();
		if (me.getId() == this.getRouteB().getId())
			return this.getRouteA();
		return null;
	}

	public Route getOtherRoute(Station s,Route r) {//retourne la route d'une jontion en fonction de l'autre route ou null si la route n'est pas sur cette jonction
		if (s.getId() == getStationA().getId() && r.getId() == getRouteA().getId())	return getRouteB();
		if (s.getId() == getStationB().getId() && r.getId() == getRouteB().getId())	return getRouteA();
		return null;
	}

	public Station getOtherStation(Station s,Route r) {//retourne la station d'une jonction en fonction de l'autre station et de la route qui lui est raccordee, ou null si la station n'est pas sur cette jonction
		if (s.getId() == getStationA().getId() && r.getId() == getRouteA().getId())	return getStationB();
		if (s.getId() == getStationB().getId() && r.getId() == getRouteB().getId())	return getStationA();
		return null;
	}

	public Station getOtherStation(Station me) {//retourne la station d'une jonction en fonction de l'autre station ou null si la station n'est pas sur cette jonction
		if (me.getId() == this.getStationA().getId())
			return this.getStationB();
		if (me.getId() == this.getStationB().getId())
			return this.getStationA();
		return null;
	}

	public Route getRouteA() {//retourne la premiere route de la jonction
		return routeA;
	}

	public Route getRouteB(){//retourne la deuxieme route de la jonction
		return routeB;
	}

	public Station getStationA() {//retourne la premiere station de la jonction
		return stationA;
	}

	public Station getStationB() {// retourne la deuxiemme station de la jonction
		return stationB;
	}

	protected void setRouteA(Route routeA) {//Mutateur de routeA. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon sûr. Définisez la Route manuellement à votre risque et péril.
		this.routeA = routeA;
	}

	protected void setRouteB(Route routeB) {//Mutateur de routeB. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon sûr. Définisez la Route manuellement à votre risque et péril.
		this.routeB = routeB;
	}

	protected void setStationA(Station stationA) {//Mutateur de stationA. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon sûr. Définisez la Station manuellement à votre risque et péril. 
		this.stationA = stationA;
	}

	protected void setStationB(Station stationB) {//Mutateur de stationB. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon sûr. Définisez la Station manuellement à votre risque et péril.
		this.stationB = stationB;
	}

	protected void setTimeBetweenStations(int timeBetweenStations) throws ImpossibleValueException {//setter du temps entre deux stations
		this.timeBetweenStations = timeBetweenStations;
	}

	public boolean isRouteLink() {
		return routeLink;
	}

	protected void setRouteLink(boolean routeLink) {//Mutateur de routeLink. Vous ne devriez pas utiliser cette fonction car GarphNetworkBuilder le fait, et de façon sûr. Définisez son etat routeLink manuellement à votre risque et péril.
		this.routeLink = routeLink;
	}

	/**
	 * Construcuteur d'un jonction, il propose de fournir l'ensemble des informations au sujet de la jonction en
	 * une seul fois. La jonction est par défaut active (the Junction.isEnable()==true).
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
	
	protected boolean equals (Junction j) {
		if (
				(j.getRouteA().getId() == routeA.getId() && 
				j.getRouteB().getId() == routeB.getId() &&
				j.getStationA().getId() == stationA.getId() && 
				j.getStationB().getId() == stationB.getId()) ||
				(j.getRouteA().getId() == routeB.getId() && 
				j.getRouteB().getId() == routeA.getId() &&
				j.getStationA().getId() == stationB.getId() && 
				j.getStationB().getId() == stationA.getId())
			) 
			return true;
		
		return false;
	}
}
