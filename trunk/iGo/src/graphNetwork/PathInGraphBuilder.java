package graphNetwork;

/**
 * 
 * @author iGo
 */
public class PathInGraphBuilder {

	/**
	 * le trajet actuellement travaillé
	 */
	private PathInGraph actualPathInGraph;

	/**
	 * le trajet actuellement travaillé
	 */
	private GraphNetwork univer;

	/**
	 * Constructeur par defaut du monteur
	 * 
	 * @param graph
	 *            le graph dans lequel le monteur de PathInGraph est créé.
	 */
	protected PathInGraphBuilder(GraphNetwork graph) {
		this.univer = graph;
	}

	/**
	 * 
	 * @param graph
	 *            le graph dans lequel le monteur de PathInGraph est créé.
	 * @param actualPathInGraph
	 *            le chemin qui sera le chemin actuelle de ce monteur
	 */
	protected PathInGraphBuilder(GraphNetwork graph, PathInGraph actualPathInGraph) {
		this.univer = graph;
	}

	/**
	 * En travaillant sur le PathInGraph courant, ajout au début du chemin l'inter passé en paramètre
	 * 
	 * @param inter
	 */
	protected void addFront(Inter inter) {
	}

	/**
	 * En travaillant sur le PathInGraph courant, ajoute à la fin du chemin l'inter passé en paramètre
	 * 
	 * @param inter
	 */
	protected void addLast(Inter inter) {
		actualPathInGraph.inter.addLast(inter);
	}

	/**
	 * En travaillant sur le PathInGraph courant, vide le chemin de ses inter.
	 */
	protected void flush() {
	}

	/**
	 * retourne le trajet actuellement étudié
	 * 
	 * @return
	 */
	public PathInGraph getActualPathInGraph() {
		return actualPathInGraph;
	}

	/**
	 * retourn une nouvelle instance de chemin pour le GraphNetwork qui a généré le PathInGraphBuilder.
	 * 
	 * @return une nouvelle instance de PathInGraph
	 * 
	 */
	public PathInGraph getInstance() {
		return new PathInGraph(univer);
	}

	/**
	 * En travaillant sur le PathInGraph courant, créé le chemin depuis la chaine passé en paramètre. On écrase le
	 * contenue précédent du chemin
	 * 
	 * @param pathInString
	 */
	protected void importPath(String pathInString) {
		// TODO Auto-generated method stub
	}

	/**
	 * définit le trajet actuellement étudié par le trajet passé en paramètre
	 * 
	 * @param actualPathInGraph
	 */
	public void setActualPathInGraph(PathInGraph actualPathInGraph) {
		this.actualPathInGraph = actualPathInGraph;
	}

}
