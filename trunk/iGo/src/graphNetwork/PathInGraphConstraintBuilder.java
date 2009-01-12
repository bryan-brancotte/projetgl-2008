package graphNetwork;

import iGoMaster.Algo.CriteriousForLowerPath;

public class PathInGraphConstraintBuilder {

	/**
	 * Le trajet actuellement traité
	 */
	protected PathInGraph currentPathInGraph;

	/**
	 * le graph dans lequel le monteur de PathInGraph est créé
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
		this.currentPathInGraph.resolved = false;
		if (!currentPathInGraph.avoidStations.contains(station))
			currentPathInGraph.avoidStations.add(station);
	}

	/**
	 * Ajoute le type de route passé en paramètre à la liste des kind que l'on ne peut pas prendre
	 * 
	 * @param kind
	 *            le type en question
	 */
	public void addRefusedKindRoute(KindRoute kind) {
		if (!currentPathInGraph.refusedKindRoute.contains(kind))
			this.currentPathInGraph.refusedKindRoute.add(kind);
	}

	/**
	 * Ajout à la liste des services à avoir à au moins une station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceAlways(Service service) {
		this.currentPathInGraph.resolved = false;
		if (!currentPathInGraph.servicesAlways.contains(service))
			currentPathInGraph.servicesAlways.add(service);
	}

	/**
	 * Ajout à la liste des service à avoir à chaque station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void addSeviceOnce(Service service) {
		this.currentPathInGraph.resolved = false;
		if (!currentPathInGraph.servicesOnce.contains(service))
			currentPathInGraph.servicesOnce.add(service);
	}

	/**
	 * Ajout à la liste des points de passage obligatoire la station passée en paramètre
	 * 
	 * @param station
	 */
	public void addStepStations(Station station) {
		this.currentPathInGraph.resolved = false;
		if (!currentPathInGraph.steps.contains(station))
			currentPathInGraph.steps.add(station);
	}

	/**
	 * retourne le trajet actuellement etudié
	 * 
	 * @return le trajet etudie
	 */
	public PathInGraph getCurrentPathInGraph() {
		return currentPathInGraph;
	}

	/**
	 * En travaillant sur le PathInGraph courant, créé le chemin depuis la chaine passée en paramètre. On écrase le
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
	 * En travaillant sur le PathInGraph courant, créé le chemin depuis le chemin passé en parametre. On écrase le
	 * contenu précédent du chemin
	 * 
	 * @param pathInGraph
	 *            la chaine representant le graph a importer
	 * @return void
	 */
	public void importPath(PathInGraph path) {
		this.currentPathInGraph.importPath(path);
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
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.avoidStations.remove(station);
	}

	/**
	 * Retire le type de route passé en paramètre à la liste des kind que l'on ne peut prendre
	 * 
	 * @param kind
	 *            le type en question
	 */
	public void removeRefusedKindRoute(KindRoute kind) {
		this.currentPathInGraph.refusedKindRoute.remove(kind);
	}

	/**
	 * Ajout à la liste des services à avoir à au moins une station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void removeSeviceAlways(Service service) {
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.servicesAlways.remove(service);
	}

	/**
	 * Ajout à la liste des service à avoir à chaque station où l'on pose le pied, le service passé en paramètre
	 * 
	 * @param service
	 */
	public void removeSeviceOnce(Service service) {
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.servicesOnce.remove(service);
	}

	/**
	 * Retire de la liste des points de passage obligatoire, la station passée en paramètre
	 * 
	 * @param station
	 */
	public void removeStepStations(Station station) {
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
		this.currentPathInGraph.resolved = false;
		currentPathInGraph.destination = destination;
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
