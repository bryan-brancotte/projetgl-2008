package algorithm;
import graphNetwork.CriteriousForTheLowerPath;
<<<<<<< .mine
import graphNetwork.GraphNetworkReader;
import graphNetwork.InterReader;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.PathInGraphReader;
import graphNetwork.StationReader;
=======
import graphNetwork.GraphNetwork;
import graphNetwork.PathInGraph;
import graphNetwork.Station;
>>>>>>> .r494
import iGoMaster.AlgoAbstract;

import java.util.ArrayList;

<<<<<<< .mine
public class Dijkstra extends AlgoAbstract {


	private ArrayList<Node> graph;
	private StationReader[] avoidR;
	private StationReader[] mustR;
	private StationReader[] alwaysR;
	private ArrayList<StationReader> onceR;
	private StationReader origine;
	private StationReader destination;
	private PathInGraphBuilder path;
	
	public PathInGraphReader findPath(StationReader _origine,
			StationReader _destination, GraphNetworkReader _graph,
			PathInGraphBuilder _path,
			CriteriousForTheLowerPath _criterious) {

		// Initialisation des variables privées
		origine = _origine;
		destination = _destination;
		path = _path;
		// avoidR;
		// mustR;
		// onceR;
		// alwaysR;
		
		extractGraphWithStaticConstraints(_graph);
		
		
		return path;
	}
		
	private ArrayList<Node> extractGraphWithStaticConstraints(GraphNetworkReader g) {
		// Initialisation du graph
		graph = new ArrayList<Node>();
		
		//Parcours du graph
		for (int i=0;i<g.getStationsR().length;i++) {
			StationReader s = g.getStationsR()[i]; 
			if (!isIn(s,avoidR)) {
				Node n = new Node(s);
				for (int j=0;j<s.getInterR().length;j++) {
						InterReader inter = s.getInterR()[j];
						if (goodChange(s,inter)) {
							boolean valide = true;
							int k=0;
							while (k<s.getServiceR().length && valide) {
								if (s.getServiceR()[k] == )
							}
							for (int k=0;k<;k++) {
								
							}
							
						}
				}
			}
		}
=======
	public PathInGraph findPath(Station origine,
			Station destination, GraphNetwork  graph,
			PathInGraph pathInGraphBuilder,
			CriteriousForTheLowerPath criterious) {
		// TODO Auto-generated method stub
>>>>>>> .r494
		return null;
	}
	
	private boolean isIn (StationReader s, StationReader[] list) {
		boolean retour = false;
		for (int i=0 ; i<list.length || retour ; i++) {
			if (list[i] == s) retour=true;
		}
		return retour;
	}
	
	private boolean goodChange (StationReader s,InterReader inter) {
		boolean retour = true;
		if (inter.getRouteR(s) == inter.getOtherRouteR(s)) {
			
		}
		return retour;
	}
}
