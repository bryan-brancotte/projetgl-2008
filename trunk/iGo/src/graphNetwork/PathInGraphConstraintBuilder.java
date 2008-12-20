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
	 * Accesseur pour le critère principale dans la résolution de l'algo
	 * 
	 * @return
	 */
	public void setMainCriterious(CriteriousForLowerPath c) {
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.mainCriterious = c;
	}

	/**
	 * Accesseur pour le critère secondaire dans la résolution de l'algo
	 * 
	 * @return
	 */
	public void setMinorCriterious(CriteriousForLowerPath c) {
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
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.avoidStations.add(station);
	}

	/**
	 * Ajout à la liste des points de passages obligatoire la station passée en paramètre
	 * 
	 * @param station
	 */
	public void addStepStations(Station station) {
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.steps.add(station);
	}

	/**
	 * Ajout à la liste des service à avoir à chaque station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceOnce(Service service) {
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.servicesOnce.add(service);
	}

	/**
	 * Ajout à la liste des service à avoir à au moins une station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceAlways(Service service) {
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
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.origin = origin;
	}

}
