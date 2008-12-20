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
	}

	/**
	 * Accesseur pour le critère principale dans la résolution de l'algo
	 * 
	 * @return
	 */
	public void setMainCriterious(CriteriousForLowerPath c) {
		currentPathInGraph.mainCriterious = c;
	}

	/**
	 * Accesseur pour le critère secondaire dans la résolution de l'algo
	 * 
	 * @return
	 */
	public void setMinorCriterious(CriteriousForLowerPath c) {
		currentPathInGraph.minorCriterious = c;
	}

	/**
	 * Ajout à la liste des stations à éviter la station à évité
	 * 
	 * @return le tableau avec l'ensemble des stations a éviter, ou un tableau vide s'il n'y en a pas.
	 */
	public void addAvoidStationsArray(Station station) {
		currentPathInGraph.avoidStations.add(station);
	}

	/**
	 * Définit que le chemin est à résoudre
	 */
	public void setPathInGraphNotResolved() {
		currentPathInGraph.resolved = false;
	}

	/**
	 * Mutateur de la station de destination
	 * @param destination la station en question
	 */
	public void setDestination(Station destination) {
		currentPathInGraph.destination = destination;
	}

	/**
	 * Mutateur de la station d'origine
	 * @param origin la station en question
	 */
	public void setOrigin(Station origin) {
		currentPathInGraph.origin = origin;
	}

}
