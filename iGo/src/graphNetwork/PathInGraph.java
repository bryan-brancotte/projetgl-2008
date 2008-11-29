package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class PathInGraph {

	/**
	 * Liste des stations à éviter
	 */
	protected LinkedList<Station> avoidStations;
	/**
	 * La station de destination
	 */
	protected Station destination;
	/**
	 * Liste des jonctions formant le trajet
	 */
	protected LinkedList<Junction> junctions;
	/**
	 * La station d'origine
	 */
	protected Station origin;
	/**
	 * Liste des services obligatoire sur l'ensemble des stations intermédiaire et extrèmes du trajet.
	 */
	protected LinkedList<Service> sevicesAlways;
	/**
	 * Liste des services à recontrer au moins une fois sur le trajet.
	 */
	protected LinkedList<Service> sevicesOnce;
	/**
	 * Liste des stations intermédiaires obligatoire.
	 */
	protected LinkedList<Station> steps;

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
	 * Retourne un tableau avec l'ensemble des stations à éviter
	 * 
	 * @return un tableau avec tous ces stations
	 */
	public Station[] getAvoidStationsArray() {
		return avoidStations.toArray(new Station[0]);
	}

	/**
	 * Retourne un iterateur décrivant l'ensemble des stations à éviter
	 * 
	 * @return un iterateur sur ces stations
	 */
	public Iterator<Station> getAvoidStationsIter() {
		return avoidStations.iterator();
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
	 * Retourne la station de départ du trajet.
	 * 
	 * @return la station de départ du trajet.
	 */
	public Station getDestination() {
		return destination;
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
	public GraphNetwork getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retourne un iterateur décrivant les jonction qui forme le chemin dans le sens départ->fin
	 * 
	 * @return l'iterateur
	 */
	public Iterator<Junction> getJunctions() {
		return junctions.iterator();
	}

	/**
	 * Rteourne la station d'origine du chemin
	 * 
	 * @return
	 */
	public Station getOrigin() {
		return origin;
	}

	/**
	 * Retourne un tableau avec tout les services requis tout au long du trajet
	 * 
	 * @return un tableau avec tout ces services.
	 */
	public Service[] getSevicesAlwaysArray() {
		return sevicesAlways.toArray(new Service[0]);
	}

	/**
	 * Retourne un iterateur décrivant les services requis tout au long du trajet
	 * 
	 * @return un iterateur sur ces services.
	 */
	public Iterator<Service> getSevicesAlwaysIter() {
		return sevicesAlways.iterator();
	}

	/**
	 * Retourne un tableau avec tout les services requis au moins une fois sur le trajet.
	 * 
	 * @return un tableau avec tout ces services.
	 */
	public Service[] getSevicesOnceArray() {
		return sevicesOnce.toArray(new Service[0]);
	}

	/**
	 * Retourne un iterateur décrivant les services requis au moins une fois sur le trajet
	 * 
	 * @return un iterateur sur ces services.
	 */
	public Iterator<Service> getSevicesOnceIter() {
		return sevicesOnce.iterator();
	}

	/**
	 * Retourne un tableau contenant toutes les stations intermédiaire requise.
	 * 
	 * @return un tableau avec ces stations
	 */
	public Station[] getStepsArray() {
		return steps.toArray(new Station[0]);
	}

	/**
	 * Retourne un iterateur sur les stations intermédiaire requise.
	 * 
	 * @return un tableau avec ces stations
	 */
	public Iterator<Station> getStepsIter() {
		return steps.iterator();
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
	 * Permet de savoir si on peut toujours arpenter le trajet à partir de la jonction passé en paramètre jusqu'a la
	 * fin.
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
