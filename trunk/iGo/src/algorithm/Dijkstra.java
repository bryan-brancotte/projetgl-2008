package algorithm;
import graphNetwork.CriteriousForTheLowerPath;
import graphNetwork.GraphNetwork;
import graphNetwork.Inter;
import graphNetwork.PathInGraph;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo;

import java.util.ArrayList;
import java.util.Iterator;

public class Dijkstra extends Algo {


	private ArrayList<Node> graph;
	private Station[] steps;
	private Station[] avoidStations;
	private Service[] always;
	private Service[] must;
	private Station origine;
	private Station destination;
	private PathInGraph path;
	
	public PathInGraph findPath(Station _origine,
			Station _destination, GraphNetwork _graph,
			PathInGraph _path,
			CriteriousForTheLowerPath _criterious) {

		// Initialisation des variables privées
		origine = _origine;
		destination = _destination;
		path = _path;
		// avoidStations;
		// must;
		// once;
		// always;
		
		extractGraphWithStaticConstraints(_graph);
		
		
		return path;
	}
		
	private ArrayList<Node> extractGraphWithStaticConstraints(GraphNetwork g) {
		// Initialisation du graph
		graph = new ArrayList<Node>();
		
		//Parcours du graph
		Iterator<Station> it_from = g.getStations();
		while(it_from.hasNext()){
			Station s = it_from.next();
			
			if (!isIn(s,avoidStations)) {
				Node n = new Node(s);
				graph.add(n);
				
				Iterator<Inter> it_inter = s.getInter();
				while(it_inter.hasNext()){
					Inter i = it_inter.next();
					
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
		
		if (isIn(inter.getOtherStation(station),avoidStations)) return false;
		
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
}
