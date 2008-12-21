package graphNetwork;

import iGoMaster.Algo.CriteriousForLowerPath;

public class PathInGraphResultBuilder {

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
	protected PathInGraphResultBuilder(PathInGraph currentPathInGraph) {
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
	 * ajout au debut du chemin de l'inter, dans le PathInGrah courant
	 * 
	 * @param junction
	 *            la jonction a ajouter au debut du chemin
	 */
	public void addFront(Junction junction) {
		currentPathInGraph.junctions.addFirst(junction);
	}

	/**
	 * ajout en fin de chemin de l'inter, dans le PathInGrah courant
	 * 
	 * @param junction
	 *            la jonction a ajouter a la fin du chemin
	 */
	public void addLast(Junction junction) {
		currentPathInGraph.junctions.addLast(junction);
	}

	/**
	 * En travaillant sur le PathInGraph courant, vide le chemin de ses inter.
	 * 
	 * @return void
	 */
	protected void flush() {
		currentPathInGraph.junctions.clear();
	}

	/**
	 * retire la première jonction dans le PathInGraph courant
	 */
	public void removeFirst() {
		currentPathInGraph.junctions.removeFirst();
	}

	/**
	 * retire la dernière jonction dans le PathInGraph courant
	 */
	public void removeLast() {
		currentPathInGraph.junctions.removeLast();
	}

	/**
	 * Mutateur définissant le chemin comme résolu
	 */
	public void setPathInGraphResolved() {
		currentPathInGraph.resolved = true;
	}

	/**
	 * Permet de savoir si le chemin est valide pour la résolution.
	 * 
	 * @return true si on peut le résoudre
	 */
	public boolean isValideForSolving() {
		if (currentPathInGraph.destination == null)
			return false;
		if (currentPathInGraph.origin == null)
			return false;
		if (currentPathInGraph.mainCriterious == CriteriousForLowerPath.NOT_DEFINED)
			return false;
		if (currentPathInGraph.minorCriterious == CriteriousForLowerPath.NOT_DEFINED)
			return false;
		return true;
	}

	/**
	 * efface les jonctions contenu dans la {@link PathInGraph} 
	 */
	public void resetJunctions() {
		currentPathInGraph.junctions.clear();
	}
}