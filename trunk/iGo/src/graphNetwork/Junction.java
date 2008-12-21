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

	/**
	 * Permet de savoir si la station dont on passe la route passées en paramètre est un des cotés de la jonction
	 * 
	 * @param route
	 * @param station
	 * @return
	 */
	public boolean haveOnASide(Route route, Station station) {
		return (route == this.routeA && station == this.stationA || route == this.routeB && station == this.stationB);
	}
}
