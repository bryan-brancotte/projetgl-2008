package graphNetwork;

import iGoMaster.Algo.CriteriousForLowerPath;

public class PathInGraphConstraintBuilder {

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
	 * retourne le trajet actuellement etudie
	 * 
	 * @return le trajet etudie
	 */
	public PathInGraph getCurrentPathInGraph() {
		return currentPathInGraph;
	}

	/**
	 * En travaillant sur le PathInGraph courant, crée le chemin depuis la chaine passée en paramètre. On écrase le
	 * contenu précédent du chemin
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
	 * Accesseur pour le critère principal dans la résolution de l'algo
	 * 
	 * @return
	 */
	public void setMainCriterious(CriteriousForLowerPath c) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
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
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("setMinorCriterious(" + c + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.minorCriterious = c;
	}

	/**
	 * 
	 * Ajout à la liste des stations à éviter la station passée en paramètre
	 * 
	 * @param station
	 */
	public void addAvoidStations(Station station) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("addAvoidStations(" + station + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.avoidStations.add(station);
	}

	/**
	 * Ajout à la liste des points de passage obligatoire la station passée en paramètre
	 * 
	 * @param station
	 */
	public void addStepStations(Station station) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("addStepStations(" + station + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.steps.add(station);
	}

	/**
	 * Ajout à la liste des services qu'il faut avoir à chaque station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceOnce(Service service) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("addSeviceOnce(" + service + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.servicesOnce.add(service);
	}

	/**
	 * Ajout à la liste des services qu'il faut avoir au moins pour une station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceAlways(Service service) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("addSeviceAlways(" + service + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.servicesAlways.add(service);
	}

	/**
	 * Mutateur de la station de destination
	 * 
	 * @param destination
	 *            la station en question
	 */
	public void setDestination(Station destination) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("setDestination(" + destination + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.destination = destination;
	}

	/**
	 * Mutateur de la station d'origine
	 * 
	 * @param origin
	 *            la station en question
	 */
	public void setOrigin(Station origin) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("setOrigin(" + origin + ")");
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.origin = origin;
	}

	/**
	 * Ajout le type de route passé en paramètre à la liste des kind que l'on ne peut prendre
	 * 
	 * @param kind
	 *            le type en question
	 */
	public void addRefusedKindRoute(KindRoute kind) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("addRefusedKindRoute(" + kind + ")");
		this.currentPathInGraph.refusedKindRoute.add(kind);
	}

	/**
	 * Retire le type de route passé en paramètre à la liste des kind que l'on ne peut prendre
	 * 
	 * @param kind
	 *            le type en question
	 */
	public void removeRefusedKindRoute(KindRoute kind) {
		if (System.getProperty("user.name").compareTo("Icarius") == 0)
			System.out.println("removeRefusedKindRoute(" + kind + ")");
		this.currentPathInGraph.refusedKindRoute.remove(kind);
	}

	/**
	 * Permet de savoir si le chemin est valide pour la résolution.
	 * 
	 * @return true si on peut le résoudre
	 */
	public boolean isValideForSolving() {
		return currentPathInGraph.isValideForSolving();
	}
}
