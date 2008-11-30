package graphNetwork;

import java.util.Iterator;

/**
 * 
 * @author iGo
 */
public class PathInGraphBuilder {

	private PathInGraph actualPathInGraph;		//le trajet actuellement trait�
	private GraphNetwork univer;

	protected PathInGraphBuilder(GraphNetwork graph) {//Constructeur par defaut du monteur
		this.univer = graph;
	}

	protected PathInGraphBuilder(GraphNetwork graph, PathInGraph actualPathInGraph) {//le graph dans lequel le monteur de PathInGraph est cr��.
		this.univer = graph;
		this.actualPathInGraph = actualPathInGraph;
	}


	/**
	 * En travaillant sur le PathInGraph courant, ajout au début du chemin l'inter passé en paramètre
	 * 
	 * @param junction
	 */
	public void addFront(Junction junction) {

	}



	/**
	 * En travaillant sur le PathInGraph courant, ajoute à la fin du chemin l'inter passé en paramètre
	 * 
	 * @param junction
	 */
	public void addLast(Junction junction) {
		actualPathInGraph.junctions.addLast(junction);

	}

	protected void flush() {			//En travaillant sur le PathInGraph courant, vide le chemin de ses inter.
		actualPathInGraph.junctions.clear();
	}

	public PathInGraph getActualPathInGraph() {//retourne le trajet actuellement �tudi�
		return actualPathInGraph;
	}

	public PathInGraph getInstance() {			//retourne une nouvelle instance de chemin pour le GraphNetwork qui a g�n�r� le PathInGraphBuilder.
		return new PathInGraph(univer);
	}

	protected void importPath(String pathInString) {//En travaillant sur le PathInGraph courant, cr�� le chemin depuis la chaine pass� en parametre. On �crase le contenue pr�c�dent du chemin
		// TODO Auto-generated method stub
	}

	public void setActualPathInGraph(PathInGraph actualPathInGraph) {//d�finit le trajet actuellement �tudi� par le trajet pass� en parametre
		this.actualPathInGraph = actualPathInGraph;
	}

}
