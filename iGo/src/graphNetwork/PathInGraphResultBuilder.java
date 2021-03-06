package graphNetwork;

public class PathInGraphResultBuilder {

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
	protected PathInGraphResultBuilder(PathInGraph currentPathInGraph) {
		super();
		this.currentPathInGraph = currentPathInGraph;
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
	 * ajout au debut du chemin de la jonction, dans le PathInGrah courant
	 * 
	 * @param junction
	 *            la jonction a ajouter au debut du chemin
	 */
	public void addFront(Junction junction) {
		currentPathInGraph.junctions.addFirst(junction);
	}

	/**
	 * ajout en fin de chemin de la jonction, dans le PathInGrah courant
	 * 
	 * @param junction
	 *            la jonction a ajouter a la fin du chemin
	 */
	public void addLast(Junction junction) {
		currentPathInGraph.junctions.addLast(junction);
	}

	/**
	 * En travaillant sur le PathInGraph courant, vide le chemin de ses jonctions.
	 * 
	 * @return void
	 */
	public void flush() {
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
	 * Mutateur définissant le cout du trajet
	 * 
	 * @param cost
	 */
	public void setCost(float cost) {
		currentPathInGraph.cost = cost;
	}
	
	/**
	 * Mutateur définisant le temps de trajet
	 * 
	 * @param time
	 */
	public void setTime(int time){
		currentPathInGraph.time = time;
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
	 * efface les jonctions contenu dans la {@link PathInGraph}
	 */
	public void resetJunctions() {
		currentPathInGraph.junctions.clear();
	}

	/**
	 * mutateur de coût de début du chemin
	 * 
	 * @return
	 */
	public void setEntryCost(float entryCost) {
		currentPathInGraph.entryCost = entryCost;
	}
}
