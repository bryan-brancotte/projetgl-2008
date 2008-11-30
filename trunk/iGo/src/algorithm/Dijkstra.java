package algorithm;
import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import algorithm.GraphAlgo.Link;
import algorithm.GraphAlgo.Node;

public class Dijkstra extends Algo {

	private GraphAlgo graph;
	private LinkedList<Station> steps;
	private ArrayList<Service> once;
	private LinkedList<Junction> path;
	
	public PathInGraph findPath(PathInGraphBuilder _path) {

		PathInGraph pathInGraph = _path.getInstance(); 
		
		graph = new GraphAlgo(pathInGraph);
		
		// Création des étapes 
		steps = new LinkedList<Station>();
		steps.add(pathInGraph.getOrigin());
		Iterator<Station> it_step = pathInGraph.getStepsIter();
		Station s;
		while(it_step.hasNext()){
			s = it_step.next();
			steps.add(s);
		}
		steps.add(pathInGraph.getDestination());
		
		// Création du chemin 
		path = new LinkedList<Junction>();
		while (path.size()==0 && once.size()!=0) {
			for (int i=0;i<steps.size()-1;i++) {
				path.addAll(algo(graph,steps.get(i),steps.get(i+1)));
			}
			once.remove(once.size()-1);
		}
		
		// Création du pathInGraph 
		Iterator<Junction> it = path.iterator();
		while(it.hasNext()){
			Junction i = it.next();
			_path.addLast(i);
		}
		
		return pathInGraph;
	}
	
	// Pseudo code de la fonction de dijkstra
	private ArrayList<Junction> algo (GraphAlgo graph, Station depart, Station arrivee) {

		Iterator<Node> g;
		Node n,n1;
		Link l;
		ArrayList<Node> pasEncoreVu = graph.getListClone();
		
		g = graph.getList();
		while (g.hasNext()) {
			n = g.next();
			if (depart == n.getStation()) {
				n.setCost(0);
				break;
			}
		}
		//TODO penser au cas du graph non complet
		while (pasEncoreVu.size()!=0) {
			n1 = getMinimumNode(pasEncoreVu);
			pasEncoreVu.remove(n1);
			
			Iterator<Link> it = n1.getToIter();
			while (it.hasNext()) {
				l = it.next();
				betterWay(l, n1);
			}
		}
		return null;
	}
	
	private Node getMinimumNode (ArrayList<Node> list) {
		Node n = list.get(0);
		for (int i=1;i<list.size();i++) {
			if (list.get(i).getCost()< n.getCost()) list.get(i);
		}
		return n;
	}
	
	public void betterWay (Link l,Node n) {
		//TODO faire les conditions
		int newTime = n.getCost() + l.getJunction().getTimeBetweenStations();
		if (l.getNode().getCost() > newTime) {
			l.getNode().setCost(newTime);
			l.getNode().setFrom(n);
		}
	}
}