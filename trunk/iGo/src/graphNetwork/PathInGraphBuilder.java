package graphNetwork;


/**
 * 
 * @author iGo
 */
public class PathInGraphBuilder {

	private PathInGraph currentPathInGraph;		//le trajet actuellement trait�
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
	 *        graph
	 * @param currentPathInGraph
	 * 			chemin dans ce graph
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
	 *        la jonction a ajouter au debut du chemin
	 * @return void
	 */
	public void addFront(Junction junction) {
		currentPathInGraph.junctions.addFirst(junction);
	}

	/**
	 * ajout en fin de chemin de l'inter, dans le PathInGrah courant
	 * 
	 * @param junction
	 *        la jonction a ajouter a la fin du chemin
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
	 * retourne une nouvelle instance de chemin pour le GraphNetwork qui a genere le PathInGraphBuilder.
	 * 
	 * @return une instance de chemin
	 */
	public PathInGraph getInstance() {			
		return new PathInGraph(univer);
	}

	/**
	 * En travaillant sur le PathInGraph courant, cree le chemin depuis la chaine passe en parametre. On ecrase le contenue precedent du chemin
	 * 
	 * @param pathInGraph
	 *        la chaine representant le graph a importer
	 * @return void
	 */
	public void importPath(String pathInString) {
		// TODO Auto-generated method stub
	}

	/**
	 * @deprecated Use setCurrentPathInGraph()
	 */
	protected void setActualPathInGraph(PathInGraph currentPathInGraph) {
		this.currentPathInGraph = currentPathInGraph;
	}
	
	/**
	 * definit le trajet actuellement etudie en fonction du trajet passe en parametre
	 * 
	 * @param currentPathInGraph
	 *        le trajet actuel
	 * @return void
	 */
	protected void setCurrentPathInGraph(PathInGraph currentPathInGraph) {
		this.currentPathInGraph = currentPathInGraph;
	}

}
