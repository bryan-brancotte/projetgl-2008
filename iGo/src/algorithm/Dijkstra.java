package algorithm;
import graphNetwork.GraphNetwork;
import graphNetwork.Junction;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Dijkstra extends Algo {

	private ArrayList<Node> graph;
	private LinkedList<Station> steps;
	private Station[] avoidStations;
	private Service[] always;
	private ArrayList<Service> once;
	private LinkedList<Junction> path;
	
	public Dijkstra (GraphNetwork _graph) {
		graph = extractGraphWithStaticConstraints(_graph);
		
	}
	
	public PathInGraphBuilder findPath(Station _origine, Iterator<Station> _steps, Station _destination,
			PathInGraphBuilder _path) {

		// Création des étapes 
		steps = new LinkedList<Station>();
		steps.add(_origine);
		Iterator<Station> it_step = _steps;
		while(it_step.hasNext()){
			Station s = it_step.next();
			steps.add(s);
		}
		steps.add(_destination);
		
		// avoidStations;
		// must;
		// once;
		// always;
		
		// Création du graph 
		
		// Création du chemin 
		path = new LinkedList<Junction>();
		while (path.size()==0 && once.size()!=0) {
			for (int i=0;i<steps.size()-1;i++) {
				path.addAll(algo(graph,steps.get(i),steps.get(i+1),once));
			}
			once.remove(once.size()-1);
		}
		
		// Création du pathInGraph 
		Iterator<Junction> it = path.iterator();
		while(it.hasNext()){
			Junction i = it.next();
			_path.addLast(i);
		}
		
		return _path;
	}
		
	private ArrayList<Node> extractGraphWithStaticConstraints(GraphNetwork g) {
		// Initialisation du graph 
		graph = new ArrayList<Node>();
		
		// Parcours du graph 
		Iterator<Station> itFrom = g.getStations();
		while(itFrom.hasNext()){
			Station s = itFrom.next();
			
			// Si la station n'est pas à éviter 
			if (!isIn(s,avoidStations)) {

				// Parcours des routes
				Iterator<Route> itRoute = s.getRoutes();
				while(itRoute.hasNext()){
					Route r = itRoute.next();
					Node n = new Node(s,r);
					graph.add(n);
				}
			}
		}
		Iterator<Node> itNode = graph.iterator();
		while(itNode.hasNext()){
			Node n = itNode.next();
			
			Iterator<Junction> itInter = n.getStation().getJunction();
			while(itInter.hasNext()){
				Junction j = itInter.next();
				// Si la transition est possible 
				if (goodChange(n.getStation(),j)) {
					n.addTo(j);
				}
			}
		}
		return graph;
	}

	private boolean isIn (Station s, Station[] list) {
		boolean retour = false;
		for (int i=0 ; i<list.length || retour ; i++) {
			if (list[i] == s) retour=true;
		}
		return retour;
	}

	private boolean isIn (Service s, Iterator<Service> it) {
		boolean retour=false;
		while(it.hasNext() && !retour){
			if (s.getId() == it.next().getId()) return true;
		}
		return retour;
	}
	
	private boolean goodChange (Station station,Junction inter) {
		boolean retour = true;
		
		// Si la station d'arrivée est à éviter 
		if (isIn(inter.getOtherStation(station),avoidStations)) return false;
		
		// Si la station d'arrivée est sur la meme ligne
		if (inter.getRoute(station) == inter.getOtherRoute(station)) {
			return true;
		}
		else {
			// S'il y a un changement de ligne tous les services "always" doivent etre remplis 
			if (station == inter.getOtherStation(station)) {
				for (int i=0 ; i<always.length && !retour ;i++) {
					if (!isIn(always[i],station.getService())) return false;
				}
			}
			else {
				return false;
			}
		}
		return retour;
	}
	
	private ArrayList<Junction> algo (ArrayList<Node> graph, Station depart, Station arrivee, ArrayList<Service> once) {
		return dijkstra(graph, depart,arrivee,once);
	}

	// Pseudo code de la fonction de dijkstra
	private ArrayList<Junction> dijkstra (ArrayList<Node> graph, Station depart, Station arrivee, ArrayList<Service> once) {

		// Initialisation des stations à une distance infinie
		for (int i=0;i<graph.size();i++) {
			Node n = graph.get(i);
			n.setParcouru(Integer.MAX_VALUE);
		}
		return null;
	}
/*	n.parcouru = MAX_VALUE
	n.precedent = 0
	Fin pour

	// Initialisation du parcours à rien et ajout de l’ensemble des stations possibles
	debut.parcouru = 0
	PasEncoreVu = reseau

	// Parcourir l’ensemble des stations
	Tant que PasEncoreVu != liste vide

	// Le nœud dans PasEncoreVu avec parcouru le plus petit
	n1 = minimum(PasEncoreVu)
	PasEncoreVu.enlever(n1)

	// Parcours de l’ensemble des stations reliées à n1 par un arc restant
	Pour n2 dans fils(n1) 
	// temps (ou cout ou autre) correspond au poids de l'arc reliant n1 et n2 
	Si n2.parcouru > n1.parcouru + distance(n1, n2) – n2.interet ET (n2.interet > 0 || PasEncoreVu ne contient pas n2)
	// Enregistrement dans la liste que pour aller a n1 il faut aller à n2
	n2.precedent = n1
	Fin si
	Fin pour
	Fin tant que
	Retourner reseau
	}*/

}
