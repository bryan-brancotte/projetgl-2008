package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class PathInGraph {

	/**
	 * Liste des jonctions formant le trajet
	 */
	protected LinkedList<Junction> junctions;

	/**
	 * GraphNetwork dans lequel le trajet à un sens, une existance
	 */
	protected GraphNetwork univers;

	/**
	 * constructeur d'un trajet
	 */
	private PathInGraph() {
		junctions = new LinkedList<Junction>();
	}

	/**
	 * Constructeur spécifiant dans quel univer le trajet est créé
	 * 
	 * @param graph
	 */
	protected PathInGraph(GraphNetwork graph) {
		this();
		this.univers = graph;
	}

	/**
	 * Transcrit le trajet en une chaine qui pourra ensuite être relu pour créé de nouveau le trajet
	 * 
	 * @return
	 */
	public String exportPath() {
		return "";
	}

	/**
	 * retourne le coût du trajet
	 * 
	 * @return
	 */
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Retourne la première jonction a partir de laquel on peut toujours atteindre la fin du trajet.
	 * 
	 * @return la première jonction, ou null si la fin de la jonction n'est plus accessible
	 */
	public Junction getFirstJunctionInTheLastAvaiblePart() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retourne le GraphNetwork dans lequel le trajet à une existance.
	 * 
	 * @return
	 */
	public GraphNetworkBuilder getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retourne un iterateur décrivant les jonction qui forme le chemin dans le sens départ->fin
	 * 
	 * @return l'iterateur
	 */
	public Iterator<Junction> getJunction() {
		return junctions.iterator();
	}

	/**
	 * Retourne la durée du trajet
	 * 
	 * @return
	 */
	public int getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Créé le trajet à partir d'une chaine décrivant le trajet.
	 * 
	 * @return l'iterateur
	 */
	protected void importPath(String pathInString) {
		// TODO Auto-generated method stub
	}

	/**
	 * Permet de savoir si on peut toujours arpenter le trajet de bout en bout.
	 * 
	 * @return true si on peut le faire de bout en bout.
	 */
	public boolean isStillAvaible() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Permet de savoir si on peut toujours arpenter le trajet à partir de la jonction passé en paramètre jusqu'a la fin.
	 * 
	 * @return true si on peut le faire depuis cette jonction.
	 */
	public boolean isStillAvaible(Junction junction) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Un desciptif du trajet
	 * 
	 * @return le descriptif
	 */
	public String toString() {
		return "";
	}

}
