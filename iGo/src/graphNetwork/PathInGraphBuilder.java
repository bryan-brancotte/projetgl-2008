package graphNetwork;

/**
 * PathInGraphBuilder sera marqué deprecated car il est peu recommandé de l'utiliser. Préfere l'utilisation de ses deux
 * interfaces PathInGraphResultBuilder pour y mettre les résultat d'un graph, et PathInGraphConstraintBuilder pour y
 * mettre les contraintes (départ, arrivé, calcule en coup,...)
 * 
 * @author iGo
 */
@Deprecated
public class PathInGraphBuilder implements PathInGraphResultBuilder, PathInGraphConstraintBuilder {

	/**
	 * Le trajet actuellement traité
	 */
	private PathInGraph currentPathInGraph;
	/**
	 * Le GN dans lequel le chemin à un sens
	 */
	private GraphNetwork univer;

	/**
	 * Constructeur par defaut du monteur
	 * 
	 */
	protected PathInGraphBuilder(GraphNetwork graph) {
		super();
		univer = graph;
		currentPathInGraph = new PathInGraph(graph);

	}

	/**
	 * le graph dans lequel le monteur de PathInGraph est cree
	 * 
	 * @param graph
	 *            graph
	 * @param currentPathInGraph
	 *            chemin dans ce graph
	 */
	protected PathInGraphBuilder(GraphNetwork graph, PathInGraph currentPathInGraph) {
		super();
		this.univer = graph;
		this.currentPathInGraph = currentPathInGraph;
	}

	/**
	 * ajout au debut du chemin de l'inter, dans le PathInGrah courant
	 * 
	 * @param junction
	 *            la jonction a ajouter au debut du chemin
	 * @return void
	 */
	public void addFront(Junction junction) {
		currentPathInGraph.junctions.addFirst(junction);
	}

	/**
	 * ajout en fin de chemin de l'inter, dans le PathInGrah courant
	 * 
	 * @param junction
	 *            la jonction a ajouter a la fin du chemin
	 * @return void
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
	 * @deprecated Use getCurrentPathInGraph()
	 */
	@Deprecated
	public PathInGraph getActualPathInGraph() {
		return currentPathInGraph;
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
	 * Sert à rien car retourné un nouveau chemin sans moyen de le modifier sert à rien...
	 * 
	 * @return une instance de chemin
	 */
	@Deprecated
	public PathInGraph getInstance() {
		return new PathInGraph(univer);
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

}
