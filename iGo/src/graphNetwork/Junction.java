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

	private Route routeDestination;

	/**
	 * Permet de savoir si c'est une jonction entre deux stations qui sont sur la même ligne (et donc où vous restez
	 * dans votre train), ou une jonction où vous devez descendre du train pour prendre un autre ligne
	 */
	private boolean routeLink = false;
	private Route routeOrigin;
	private Station stationDestination;
	private Station stationOrigin;
	private int timeBetweenStations = 0;

	/**
	 * Constructeur d'une jonction, il propose de fournir l'ensemble des informations au sujet de la jonction en une
	 * seule fois. La jonction est par défaut active (the Junction.isEnable()==true).
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
	 *            si le lien n'est pas routeLink, on doit descendre du wagon, mais devons nous aussi sortir de la
	 *            station pour aller à une autre?
	 * @throws StationNotOnRoadException
	 *             si une des stations fournies n'est pas sur la ligne où elle devrait être
	 */
	protected Junction(Route routeA, Station stationA, Route routeB, Station stationB, float cost,
			int timeBetweenStations, boolean routeLink, boolean pedestrian) throws StationNotOnRoadException,
			ImpossibleValueException {
		super();
		this.cost = cost;
		this.pedestrian = pedestrian;
		this.routeOrigin = routeA;
		this.routeDestination = routeB;
		this.stationDestination = stationB;
		this.stationOrigin = stationA;
		this.timeBetweenStations = timeBetweenStations;
		this.routeLink = routeLink;
	}

	/**
	 * Permet de savoir si la jonction correspond bien à la jonction hypothétique passée en paramètre. Dans l'ordre A=>B
	 * 
	 * @param routeA
	 * @param stationA
	 * @param routeB
	 * @param stationB
	 * @return true si la jonction lie le couple A=>B
	 */
	public boolean equals(Route routeA, Station stationA, Route routeB, Station stationB) {
		return (routeA == this.routeOrigin && stationA == this.stationOrigin && routeB == this.routeDestination && stationB == this.stationDestination);
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
	 * Retourne l'autre route de la jonction.
	 * 
	 * @param me
	 *            la route qui vous sert de point de repère
	 * @return l'autre route, ou null si la route passée en paramètre n'est pas dans la jonction
	 */
	public Route getOtherRoute(Route me) {
		if (me.getId().compareTo(routeOrigin.getId()) == 0)
			return routeDestination;
		if (me.getId().compareTo(routeDestination.getId()) == 0)
			return routeOrigin;
		return null;
	}

	/**
	 * Retourne l'autre route de la jonction.
	 * 
	 * @param me
	 *            la route qui vous sert de point de repère
	 * @return l'autre route, ou null si la route passée en paramètre n'est pas dans la jonction
	 */
	public Route getMyRoute(Station me) {
		if (me.getId() == stationOrigin.getId())
			return routeOrigin;
		if (me.getId() == stationDestination.getId())
			return routeDestination;
		return null;
	}

	/**
	 * Retourne la route sur laquelle est l'autre station. Attention si jamais le lien a la même station des deux cotés,
	 * cette méthode retournera toujours la routeB.
	 * 
	 * @param me
	 *            la station qui sert de point de repère
	 * @return l'autre route, ou null si la station passée en paramètre n'est pas dans la jonction
	 */
	public Route getOtherRoute(Station me) {
		if (me.getId() == stationOrigin.getId())
			return routeDestination;
		if (me.getId() == stationDestination.getId())
			return routeOrigin;
		return null;
	}

	/**
	 * Retourne l'autre station de la jonction.
	 * 
	 * @param me
	 *            la station qui sert de point de repère
	 * @return l'autre station, ou null si la station passée en paramètre n'est pas dans la jonction
	 */
	public Station getOtherStation(Station me) {
		if (me.getId() == stationOrigin.getId())
			return stationDestination;
		if (me.getId() == stationDestination.getId())
			return stationOrigin;
		return null;
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
	 * Permet de savoir si la station dont on passe la route en paramètre est un des cotés de la jonction
	 * 
	 * @param route
	 * @param station
	 * @return
	 */
	public boolean haveOnASide(Route route, Station station) {
		return (route == this.routeOrigin && station == this.stationOrigin || route == this.routeDestination
				&& station == this.stationDestination);
	}

	/**
	 * informe de l'utilisabilité de la jonction : voici les critères : <br/>
	 * <ul>
	 * <li>Si la jonction est active</li>
	 * <li>Si la route d'origine est active</li>
	 * <li>Si la route de destination est active</li>
	 * <li>Si la station d'origine est active, elle même ou sur la route d'origine</li>
	 * <li>Si la station de destination est active, elle même ou sur la route de destination</li>
	 * </ul>
	 * 
	 * @return true si on peut la traverser
	 */
	public boolean isEnable() {
		return (this.enable && routeOrigin.isEnable() && routeDestination.isEnable()
				&& routeOrigin.isStationEnable(stationOrigin) && routeOrigin.isStationEnable(stationDestination));
	}

	/**
	 * informe que le lien entre les deux cotés implique de sortir de la première station pour aller à la seconde
	 * 
	 * @return
	 */
	public boolean isPedestrian() {
		return this.pedestrian;
	}

	/**
	 * Accesseur permettant de savoir si la jonction relie deux stations sur la même ligne, ou si on doit sortir du
	 * train pour passer d'un coté à l'autre de la jonction
	 * 
	 * @return true si on reste dans notre train pour passer d'un coté à l'autre.</br> false si c'est un
	 *         changement.</br> Le retour à true implique donc que le coût est à 0, que les routes sont les mêmes et que
	 *         les stations sont différentes.</br>
	 */
	public boolean isRouteLink() {
		return routeLink;
	}

	protected void setCost(float cost) {
		this.cost = cost;
	}

	protected void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	protected void setTimeBetweenStations(int timeBetweenStations) {
		this.timeBetweenStations = timeBetweenStations;
	}

	/**
	 * Setteur de l'état d'un jonction
	 * 
	 * @param enable
	 */
	public void setToEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * Surcharge de toString, retourne un résultat de la forme : </br> "Massy(RerC)=>Massy(RerB) : 0$ in 3minutes"
	 */
	public String toString() {
		String s;
		if (isRouteLink())
			s = "R_";
		else
			s = "C_";
		s += stationOrigin.getName() + "(" + routeOrigin.getId() + ")";
		if (routeOrigin == routeDestination)
			s += "<";
		s += "=>";
		s += stationDestination.getName() + "(" + routeDestination.getId() + ") : " + cost + "$ in "
				+ timeBetweenStations + " minutes";
		return s;
	}
}
