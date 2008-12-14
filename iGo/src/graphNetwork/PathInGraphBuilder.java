package graphNetwork;

import java.util.Iterator;

/**
 * 
 * @author iGo
 */
public class PathInGraphBuilder {

	private PathInGraph actualPathInGraph;		//le trajet actuellement trait�
	private GraphNetwork univer;

	/**
	 * Constructeur par defaut du monteur
	 * 
	 */
	protected PathInGraphBuilder(GraphNetwork graph) {
		this.univer = graph;
	}

	/**
	 * le graph dans lequel le monteur de PathInGraph est cree
	 * 
	 * @param graph
	 *        graph
	 * @param actualPathInGraph
	 * 			chemin dans ce graph
	 */
	protected PathInGraphBuilder(GraphNetwork graph, PathInGraph actualPathInGraph) {
		this.univer = graph;
		this.actualPathInGraph = actualPathInGraph;
	}

	/**
	 * ajout au debut du chemin de l'inter, dans le PathInGrah courant
	 * 
	 * @param junction
	 *        la jonction a ajouter au debut du chemin
	 * @return void
	 */
	public void addFront(Junction junction) {
		actualPathInGraph.junctions.addFirst(junction);
	}

	/**
	 * ajout en fin de chemin de l'inter, dans le PathInGrah courant
	 * 
	 * @param junction
	 *        la jonction a ajouter a la fin du chemin
	 * @return void
	 */
	public void addLast(Junction junction) {
		actualPathInGraph.junctions.addLast(junction);

	}

	/**
	 * En travaillant sur le PathInGraph courant, vide le chemin de ses inter.
	 * 
	 * @return void
	 */
	protected void flush() {
		actualPathInGraph.junctions.clear();
	}

	/**
	 * retourne le trajet actuellement etudie
	 * 
	 * @return le trajet etudie
	 */
	public PathInGraph getActualPathInGraph() {
		return actualPathInGraph;
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
	protected void importPath(String pathInString) {
		// TODO Auto-generated method stub
	}

	/**
	 * definit le trajet actuellement etudie en fonction du trajet passe en parametre
	 * 
	 * @param actualPathInGraph
	 *        le trajet actuel
	 * @return void
	 */
	public void setActualPathInGraph(PathInGraph actualPathInGraph) {
		this.actualPathInGraph = actualPathInGraph;
	}

}
