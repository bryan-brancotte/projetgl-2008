package algorithm;
import graphNetwork.CriteriousForTheLowerPath;
import graphNetwork.GraphNetwork;
import graphNetwork.Inter;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphBuilder;
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
	private Service[] must;
	private LinkedList<Inter> path;
	
	public PathInGraphBuilder findPath(Station _origine, Iterator<Station> _steps, Station _destination,
			GraphNetwork _graph,
			PathInGraphBuilder _path,
			CriteriousForTheLowerPath _criterious) {

		// Cr�ation des �tapes 
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
		
		// Cr�ation du graph 
		graph = extractGraphWithStaticConstraints(_graph);
		
		// Cr�ation du chemin 
		path = new LinkedList<Inter>();
		for (int i=0;i<steps.size()-1;i++) {
			path.addAll(algo(graph,steps.get(i),steps.get(i+1)));
		}
		
		// Cr�ation du pathInGraph 
		Iterator<Inter> it = path.iterator();
		while(it.hasNext()){
			Inter i = it.next();
			_path.addLast(i);
		}
		
		return _path;
	}
		
	private ArrayList<Node> extractGraphWithStaticConstraints(GraphNetwork g) {
		// Initialisation du graph 
		graph = new ArrayList<Node>();
		
		// Parcours du graph 
		Iterator<Station> it_from = g.getStations();
		while(it_from.hasNext()){
			Station s = it_from.next();
			
			// Si la station n'est pas � �viter 
			if (!isIn(s,avoidStations)) {
				Node n = new Node(s);
				graph.add(n);
				
				Iterator<Inter> it_inter = s.getInter();
				while(it_inter.hasNext()){
					Inter i = it_inter.next();
					
					// Si la transition est possible 
					if (goodChange(s,i)) n.addTo(i.getOtherStation(s));
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
	
	private boolean goodChange (Station station,Inter inter) {
		boolean retour = true;
		
		// Si la station d'arriv�e n'est pas � �viter 
		if (isIn(inter.getOtherStation(station),avoidStations)) return false;
		
		// S'il y a un changement de ligne tous les services "always" doivent etre remplis 
		if (inter.getRoute(station) != inter.getOtherRoute(station)) {
			
			for (int i=0 ; i<always.length && !retour ;i++) {
				retour=false;
				
				Iterator<Service> it_service = station.getService();
				while(it_service.hasNext() && !retour){
					Service s = it_service.next();
					
					if (s.getId() == always[i].getId()) retour=true;
				}
			}	
		}
		return retour;
	}
	
	private ArrayList<Inter> algo (ArrayList<Node> graph, Station depart, Station arrivee) {
		return null;
	}
}
