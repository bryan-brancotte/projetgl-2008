package graphNetwork;

import iGoMaster.Algo.CriteriousForLowerPath;

public class PathInGraphConstraintBuilder {

	// System.getProperty("user.name").compareTo("Icarius") == 0
	protected boolean dispSysout = true;

	/**
	 * Le trajet actuellement traité
	 */
	protected PathInGraph currentPathInGraph;

	/**
	 * le graph dans lequel le monteur de PathInGraph est cree
	 * 
	 * @param graph
	 *            graph
	 * @param currentPathInGraph
	 *            chemin dans ce graph
	 */
	protected PathInGraphConstraintBuilder(PathInGraph currentPathInGraph) {
		super();
		this.currentPathInGraph = currentPathInGraph;
		this.currentPathInGraph.resolved = false;
	}

	/**
	 * 
	 * Ajout à la liste des stations à éviter la station passée en paramètre
	 * 
	 * @param station
	 */
	public void addAvoidStations(Station station) {
		if (dispSysout)
			System.out.println("addAvoidStations(" + station + ")");
		this.currentPathInGraph.resolved = false;
		// if (!currentPathInGraph.avoidStations.contains(station))
		currentPathInGraph.avoidStations.add(station);
	}

	/**
	 * Ajout le type de route passé en paramètre à la liste des kind que l'on ne peut prendre
	 * 
	 * @param kind
	 *            le type en question
	 */
	public void addRefusedKindRoute(KindRoute kind) {
		if (dispSysout)
			System.out.println("addRefusedKindRoute(" + kind + ")");
		// if (!currentPathInGraph.refusedKindRoute.contains(kind))
		this.currentPathInGraph.refusedKindRoute.add(kind);
	}

	/**
	 * Ajout à la liste des service à avoir à au moins une station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceAlways(Service service) {
		if (dispSysout)
			System.out.println("addSeviceAlways(" + service + ")");
		this.currentPathInGraph.resolved = false;
		// if (!currentPathInGraph.servicesAlways.contains(service))
		currentPathInGraph.servicesAlways.add(service);
	}

	/**
	 * Ajout à la liste des service à avoir à chaque station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceOnce(Service service) {
		if (dispSysout)
			System.out.println("addSeviceOnce(" + service + ")");
		this.currentPathInGraph.resolved = false;
		// if (!currentPathInGraph.servicesOnce.contains(service))
		currentPathInGraph.servicesOnce.add(service);
	}

	/**
	 * Ajout à la liste des points de passages obligatoire la station passée en paramètre
	 * 
	 * @param station
	 */
	public void addStepStations(Station station) {
		if (dispSysout)
			System.out.println("addStepStations(" + station + ")");
		this.currentPathInGraph.resolved = false;
		// if (!currentPathInGraph.steps.contains(station))
		currentPathInGraph.steps.add(station);
	}

	/**
	 * retourne le trajet actuellement etudie
	 * 
	 * @return le trajet etudie
	 */
	public PathInGraph getCurrentPathInGraph() {
		return currentPathInGraph;
	}

	/**
	 * En travaillant sur le PathInGraph courant, cree le chemin depuis la chaine passe en parametre. On ecrase le
	 * contenue precedent du chemin
	 * 
	 * @param pathInGraph
	 *            la chaine representant le graph a importer
	 * @return void
	 */
	public void importPath(String pathInString) {
		this.currentPathInGraph.importPath(pathInString);
		this.currentPathInGraph.resolved = false;
	}

	/**
	 * Permet de savoir si le chemin est valide pour la résolution.
	 * 
	 * @return true si on peut le résoudre
	 */
	public boolean isValideForSolving() {
		return currentPathInGraph.isValideForSolving();
	}

	/**
	 * 
	 * Ajout à la liste des stations à éviter la station passée en paramètre
	 * 
	 * @param station
	 */
	public void removeAvoidStations(Station station) {
		if (dispSysout)
			System.out.println("removeAvoidStations(" + station + ")");
		this.currentPathInGraph.resolved = false;
	}

	/**
	 * Retire le type de route passé en paramètre à la liste des kind que l'on ne peut prendre
	 * 
	 * @param kind
	 *            le type en question
	 */
	public void removeRefusedKindRoute(KindRoute kind) {
		if (dispSysout)
			System.out.println("removeRefusedKindRoute(" + kind + ")");
		this.currentPathInGraph.refusedKindRoute.remove(kind);
	}

	/**
	 * Ajout à la liste des service à avoir à au moins une station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void removeSeviceAlways(Service service) {
		if (dispSysout)
			System.out.println("removeSeviceAlways(" + service + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.servicesAlways.remove(service);
	}

	/**
	 * Ajout à la liste des service à avoir à chaque station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void removeSeviceOnce(Service service) {
		if (dispSysout)
			System.out.println("removeSeviceOnce(" + service + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.servicesOnce.remove(service);
	}

	/**
	 * Retire de la liste des points de passages obligatoire la station passée en paramètre
	 * 
	 * @param station
	 */
	public void removeStepStations(Station station) {
		if (dispSysout)
			System.out.println("removeStepStations(" + station + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.steps.remove(station);
	}

	/**
	 * Mutateur de la station de destination
	 * 
	 * @param destination
	 *            la station en question
	 */
	public void setDestination(Station destination) {
		if (dispSysout)
			System.out.println("setDestination(" + destination + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.destination = destination;
	}

	/**
	 * Accesseur pour le critère principale dans la résolution de l'algo
	 * 
	 * @return
	 */
	public void setMainCriterious(CriteriousForLowerPath c) {
		if (dispSysout)
			System.out.println("setMainCriterious(" + c + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.mainCriterious = c;
	}

	/**
	 * Accesseur pour le critère secondaire dans la résolution de l'algo
	 * 
	 * @return
	 */
	public void setMinorCriterious(CriteriousForLowerPath c) {
		if (dispSysout)
			System.out.println("setMinorCriterious(" + c + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.minorCriterious = c;
	}

	/**
	 * Mutateur de la station d'origine
	 * 
	 * @param origin
	 *            la station en question
	 */
	public void setOrigin(Station origin) {
		if (dispSysout)
			System.out.println("setOrigin(" + origin + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.origin = origin;
	}
}
