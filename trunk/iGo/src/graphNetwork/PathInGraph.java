package graphNetwork;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;


/**
 * 
 * @author iGo
 */
public class PathInGraph {


	protected GraphNetwork univers;				//GraphNetwork dans lequel le trajet a un sens, une existance
	protected LinkedList<Station> avoidStations;//Liste des stations à éviter

	private float cost;
	private int time;
	
	protected Station destination;//La station de destination
	protected LinkedList<Junction> junctions;//Liste des jonctions formant le trajet
	protected Station origin;//La station d'origine
	protected LinkedList<Service> sevicesAlways;//Liste des services obligatoire sur l'ensemble des stations intermédiaire et extrèmes du trajet.
	protected LinkedList<Service> sevicesOnce;//Liste des services à recontrer au moins une fois sur le trajet.
	protected LinkedList<Station> steps;//Liste des stations intermédiaires obligatoire.


	private PathInGraph() {//constructeur d'un trajet
		junctions = new LinkedList<Junction>();
	}

	protected PathInGraph(GraphNetwork graph) {	//Constructeur specifiant dans quel univers le trajet est cree
		this();
		this.univers=graph;
	}

	public String exportPath() {				//Transcrit le trajet en une chaine qui pourra ensuite etre relue pour creer de nouveau le trajet
		return "";
		//TODO
	}

	public Station[] getAvoidStationsArray() {//Retourne un tableau avec l'ensemble des stations à éviter
		return avoidStations.toArray(new Station[0]);
	}

	public Iterator<Station> getAvoidStationsIter() {//Retourne un iterateur décrivant l'ensemble des stations à éviter
		return avoidStations.iterator();
	}

	public float getCost() {//retourne le coût du trajet
		return this.cost;
	}

	public Station getDestination() {//Retourne la station de départ du trajet.
		return destination;
	}

	public Junction getFirstJunctionInTheLastAvaiblePart() {//Retourne la première jonction a partir de laquel on peut toujours atteindre la fin du trajet.
		Iterator<Junction> j1 = junctions.iterator();
		Vector<Junction> jonctionInv = new Vector<Junction>();
		Junction jonctionTrouve=null;
		int nbJonction=junctions.size();
		
		while(j1.hasNext()){
			jonctionInv.add(--nbJonction, j1.next());
		}
		for(int i=0;i<junctions.size();i++){
			if(jonctionInv.elementAt(i).isEnable())
				jonctionTrouve=jonctionInv.elementAt(i);
			else 
				return jonctionTrouve;
		}
		return jonctionTrouve;
	}

	public GraphNetwork getGraph() {//Retourne le GraphNetwork dans lequel le trajet à une existance.
		return univers;
	}

	public Iterator<Junction> getJunctions() {//Retourne un iterateur décrivant les jonction qui forme le chemin dans le sens départ->fin
		return junctions.iterator();
	}

	public Station getOrigin() {//Retourne la station d'origine du chemin
		return origin;
	}

	public Service[] getSevicesAlwaysArray() {//Retourne un tableau avec tout les services requis tout au long du trajet
		return sevicesAlways.toArray(new Service[0]);
	}

	public Iterator<Service> getSevicesAlwaysIter() {//Retourne un iterateur décrivant les services requis tout au long du trajet
		return sevicesAlways.iterator();
	}

	public Service[] getSevicesOnceArray() {//Retourne un tableau avec tout les services requis au moins une fois sur le trajet.
		return sevicesOnce.toArray(new Service[0]);
	}

	public Iterator<Service> getSevicesOnceIter() {//Retourne un iterateur décrivant les services requis au moins une fois sur le trajet
		return sevicesOnce.iterator();
	}

	public Station[] getStepsArray() {//Retourne un tableau contenant toutes les stations intermédiaire requise.
		return steps.toArray(new Station[0]);
	}

	public Iterator<Station> getStepsIter() {//Retourne un iterateur sur les stations intermédiaire requise.
		return steps.iterator();
	}

	public int getTime() {//Retourne la durée du trajet
		return this.time;
	}

	protected void importPath(String pathInString) {	//cr�er le trajet a partir d'un chaine d�crivant le trajet
		// TODO Auto-generated method stub
	}

	public boolean isStillAvaible() {			//Permet de savoir si on peut toujours utiliser le trajet de bout en bout.
		Iterator<Junction> j1 = junctions.iterator();
		while(j1.hasNext()){
			if(!j1.next().isEnable())
				return false;
		}
		return true;
	}

	public boolean isStillAvaible(Junction junction) {//Permet de savoir si on peut toujours arpenter le trajet à partir de la jonction passé en paramètre jusqu'a la fin
		Iterator<Junction> j1 = junctions.iterator();
		boolean found=false;
		while(j1.hasNext()){
			Junction temp=j1.next();
			if(temp.equals(junction))
				found=true;
			if(found && !temp.isEnable())
				return false;
		}
		return true;
	}

	public String toMyString() {					//Un desciptif du trajet
		String retour="<PathInGraph>"+cost+";"+time+";";
		retour.concat(univers.toMyString());
		
		retour.concat("<stationList>");
			Iterator<Station> it1=avoidStations.iterator();
			while(it1.hasNext()){
				retour.concat(it1.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</stationList>");
		retour.concat(destination.toMyString());
		retour.concat("<junctionList>");
			Iterator<Station> it2=avoidStations.iterator();
			while(it2.hasNext()){
				retour.concat(it2.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</junctionList>");
		retour.concat(origin.toMyString());
		retour.concat("<serviceList>");
			Iterator<Service> it3=sevicesAlways.iterator();
			while(it3.hasNext()){
				retour.concat(it3.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</junctionList>");
		retour.concat("<serviceList>");
			Iterator<Service> it4=sevicesOnce.iterator();
			while(it4.hasNext()){
				retour.concat(it4.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</junctionList>");
		retour.concat("<stationList>");
		Iterator<Station> it5=steps.iterator();
			while(it5.hasNext()){
				retour.concat(it5.next().toMyString());
				retour.concat(";");
			}
		retour.concat("</stationList>");
		
		
		retour.concat("</PathInGraph>");
		return retour;
		
	}

}
