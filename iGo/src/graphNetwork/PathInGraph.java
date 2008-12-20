package graphNetwork;

import iGoMaster.Algo.CriteriousForLowerPath;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */
public class PathInGraph {

	/**
	 * GraphNetwork dans lequel le trajet a un sens, une existance
	 */
	protected GraphNetwork univers;
	/**
	 * Liste des stations à éviter
	 */
	protected LinkedList<Station> avoidStations;

	/**
	 * Le chemin à t'il été résolu?
	 */
	protected boolean resolved;
	/**
	 * le coût du chemin
	 */
	protected float cost;
	/**
	 * la durée du chemin
	 */
	protected int time;
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
	 * Critère principale pour la résolution de l'algo
	 */
	protected CriteriousForLowerPath mainCriterious;
	/**
	 * Critère secondaire pour la résolution de l'algo
	 */
	protected CriteriousForLowerPath minorCriterious;

	public boolean isResolved() {
		return resolved;
	}

	/**
	 * Accesseur pour le critère principale dans la résolution de l'algo
	 * 
	 * @return
	 */
	public CriteriousForLowerPath getMainCriterious() {
		return mainCriterious;
	}

	/**
	 * Accesseur pour le critère secondaire dans la résolution de l'algo
	 * 
	 * @return
	 */
	public CriteriousForLowerPath getMinorCriterious() {
		return minorCriterious;
	}

	/**
	 * constructeur d'un trajet
	 */
	private PathInGraph() {
		junctions = new LinkedList<Junction>();
		sevicesAlways = new LinkedList<Service>();
		sevicesOnce = new LinkedList<Service>();
		steps = new LinkedList<Station>();
		avoidStations = new LinkedList<Station>();
		resolved=false;
		cost=Float.NaN;
		time=0;
	}

	/**
	 * Constructeur specifiant dans quel univers le trajet est cree.
	 * 
	 * @param graph
	 * @throws NullPointerException
	 *             si le paramètre est null
	 */
	protected PathInGraph(GraphNetwork graph) {
		this();
		if (graph == null)
			throw new NullPointerException();
		this.univers = graph;
	}

	/**
	 * Transcrit le trajet en une chaine qui pourra ensuite etre relue pour creer de nouveau trajet
	 * 
	 * @return
	 */
	public String exportPath() {
		// TODO exportPath
		return "";
	}

	/**
	 * Retourne un tableau avec l'ensemble des stations à éviter
	 * 
	 * @return le tableau avec l'ensemble des stations a éviter, ou un tableau vide s'il n'y en a pas.
	 */
	public Station[] getAvoidStationsArray() {
		return avoidStations.toArray(new Station[0]);
	}

	/**
	 * Retourne un iterateur décrivant l'ensemble des stations à éviter
	 * 
	 * @return l'iterateur, ou un iterateur terminé s'il n'y a pas de station à éviter
	 */
	public Iterator<Station> getAvoidStationsIter() {
		return avoidStations.iterator();
	}

	/**
	 * retourne le coût du trajet
	 * 
	 * @return le coût
	 */
	public float getCost() {
		return this.cost;
	}

	/**
	 * Retourne la station d'arrivé du trajet.
	 * 
	 * @return
	 */
	public Station getDestination() {
		return destination;
	}

	/**
	 * Retourne la station d'origine/de départ du chemin
	 * 
	 * @return
	 */
	public Station getOrigin() {
		return origin;
	}

	/**
	 * Retourne la première jonction a partir de laquel on peut toujours atteindre la fin du trajet.
	 * 
	 * @return l'intersection, ou null si la destination n'est pa satteignable
	 */
	public Junction getFirstJunctionInTheLastAvaiblePart() {
		// Rémi : trop long et trop couteux : O(2n) et création d'un vector (beurk)
		// Iterator<Junction> j1 = junctions.iterator();
		// Vector<Junction> jonctionInv = new Vector<Junction>();
		// Junction jonctionTrouve = null;
		// int nbJonction = junctions.size();
		//
		// while (j1.hasNext()) {
		// jonctionInv.add(--nbJonction, j1.next());
		// }
		// for (int i = 0; i < junctions.size(); i++) {
		// if (jonctionInv.elementAt(i).isEnable())
		// jonctionTrouve = jonctionInv.elementAt(i);
		// else
		// return jonctionTrouve;
		// }
		// return jonctionTrouve;
		// Voila un meilleurs solution (sans vector et avec un complexite en O(n)
		Iterator<Junction> itJ = junctions.iterator();
		Junction last = null;
		Junction current;
		while (itJ.hasNext()) {
			// on prend l'item courrant
			current = itJ.next();
			// on regarde si la dernière utilisable est définit, si ce n'est pas le cas on la définit à la station
			// courante
			if (last == null)
				last = current;
			// si la station courante n'est pas utilisable, alors la dernière station utilisable que l'on gardait en
			// mémoire n'est pas valide, on la met donc à null.
			// Cela aura pour concéquence que la prochaine station sera mise dans last, permetant ainsi de trouver la
			// solution
			if (!current.isEnable())
				current = null;
		}
		return last;
	}

	/**
	 * Retourne le GraphNetwork dans lequel le trajet à une existance.
	 * 
	 * @return
	 */
	public GraphNetwork getGraph() {
		return univers;
	}

	/**
	 * Retourne un iterateur décrivant les jonction qui forme le chemin dans le sens départ->fin
	 * 
	 * @return retourne un iterateur. Celui ci peut être vide, mais jamais à null
	 */
	public Iterator<Junction> getJunctions() {
		return junctions.iterator();
	}

	/**
	 * Retourne un tableau avec tout les services requis tout au long du trajet
	 * 
	 * @return
	 */
	public Service[] getSevicesAlwaysArray() {
		return sevicesAlways.toArray(new Service[0]);
	}

	/**
	 * Retourne un iterateur décrivant les services requis tout au long du trajet
	 * 
	 * @return
	 */
	public Iterator<Service> getSevicesAlwaysIter() {
		return sevicesAlways.iterator();
	}

	/**
	 * Retourne un tableau avec tout les services requis au moins une fois sur le trajet.
	 * 
	 * @return
	 */
	public Service[] getSevicesOnceArray() {
		return sevicesOnce.toArray(new Service[0]);
	}

	/**
	 * Retourne un iterateur décrivant les services requis au moins une fois sur le trajet
	 * 
	 * @return
	 */
	public Iterator<Service> getSevicesOnceIter() {
		return sevicesOnce.iterator();
	}

	/**
	 * Retourne un tableau contenant toutes les stations intermédiaire requise.
	 * 
	 * @return
	 */
	public Station[] getStepsArray() {
		return steps.toArray(new Station[0]);
	}

	/**
	 * Retourne un iterateur sur les stations intermédiaire requise.
	 * 
	 * @return
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
		return this.time;
	}

	/**
	 * créer le trajet a partir d'un chaine décrivant le trajet
	 * 
	 * @param pathInString
	 */
	protected void importPath(String pathInString) {
		// TODO importPath
	}

	/**
	 * Permet de savoir si on peut toujours utiliser le trajet de bout en bout.
	 * 
	 * @return
	 */
	public boolean isStillAvaible() {
		Iterator<Junction> j1 = junctions.iterator();
		while (j1.hasNext()) {
			if (!j1.next().isEnable())
				return false;
		}
		return true;
	}

	/**
	 * Permet de savoir si on peut toujours arpenter le trajet à partir de la jonction passé en paramètre jusqu'a la fin
	 * 
	 * @param junction
	 * @return
	 */
	public boolean isStillAvaible(Junction junction) {
		Iterator<Junction> j1 = junctions.iterator();
		boolean found = false;
		Junction tmp;
		while (j1.hasNext()) {
			if ((tmp = j1.next()).equals(junction))
				found = true;
			if (found && !tmp.isEnable())
				return false;
		}
		return true;
	}

	// /**
	// * Un desciptif du trajet
	// *
	// * @return
	// */
	// public String toMyString() { //
	// String retour = "<PathInGraph>" + cost + ";" + time + ";";
	// return retour;
	// retour.concat(univers.toMyString());
	//				
	// retour.concat("<stationList>");
	// Iterator<Station> it1=avoidStations.iterator();
	// while(it1.hasNext()){
	// retour.concat(it1.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</stationList>");
	// retour.concat(destination.toMyString());
	// retour.concat("<junctionList>");
	// Iterator<Station> it2=avoidStations.iterator();
	// while(it2.hasNext()){
	// retour.concat(it2.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</junctionList>");
	// retour.concat(origin.toMyString());
	// retour.concat("<serviceList>");
	// Iterator<Service> it3=sevicesAlways.iterator();
	// while(it3.hasNext()){
	// retour.concat(it3.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</junctionList>");
	// retour.concat("<serviceList>");
	// Iterator<Service> it4=sevicesOnce.iterator();
	// while(it4.hasNext()){
	// retour.concat(it4.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</junctionList>");
	// retour.concat("<stationList>");
	// Iterator<Station> it5=steps.iterator();
	// while(it5.hasNext()){
	// retour.concat(it5.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</stationList>");
	//				
	//				
	// retour.concat("</PathInGraph>");
	// }

}
