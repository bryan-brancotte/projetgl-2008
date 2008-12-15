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
	
	/**
	 * Fonction de test n'ayant pas d'interet une fois implémentée
	 * 
	 * @param g
	 * @param o
	 * @param f
	 * @return
	 */
	public ArrayList<Junction> findPath(GraphAlgo g,Station o, Station f) {
		graph = g;
		return algo(graph,o,f);
	}
	
	public PathInGraph findPath(PathInGraphBuilder _path) {

		PathInGraph pathInGraph = _path.getInstance(); 
		
		graph = GraphAlgo.getInstance();
		graph.refreshGraph(pathInGraph);

		//TODO A faire la récupération des criterious
		
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
		//TODO penser à enlever une partie des contraintes et pas une par une
		path = new LinkedList<Junction>();
		while (path.size()==0 && once.size()!=0) {
			for (int i=0;i<steps.size()-1;i++) {
				graph.defaultNodes();
				path.addAll(algo(graph,steps.get(i),steps.get(i+1)));
			}
			if (path.size() == 0) once.remove(once.size()-1);
		}
		
		// Création du pathInGraph 
		Iterator<Junction> it = path.iterator();
		while(it.hasNext()){
			Junction i = it.next();
			_path.addLast(i);
		}
		
		return pathInGraph;
	}
	
	/**
	 * Fonction de dijkstra implémentée
	 * 
	 * @param graph
	 * @param depart
	 * @param arrivee
	 * @return
	 */
	private ArrayList<Junction> algo (GraphAlgo graph, Station depart, Station arrivee) {

		Iterator<Node> g;
		Node n,n1;
		Link l;
		ArrayList<Node> pasEncoreVu = graph.getListClone();
		
		g = graph.getList();
		while (g.hasNext()) {
			n = g.next();
			if (depart == n.getStation()) {
				n.setTime(0);
				break;
			}
		}
		while (pasEncoreVu.size()!=0) {
			n1 = getMinimumNode(pasEncoreVu);
			pasEncoreVu.remove(n1);
			
			Iterator<Link> it = n1.getToIter();
			while (it.hasNext()) {
				l = it.next();
				betterWay(l, n1);
			}
		}
		//TODO test de verif a virer
		/*for (int i=0;i<graph.getListClone().size();i++) {
			System.out.print(graph.getListClone().get(i).getStation().getName()+" dans "+graph.getListClone().get(i).getRoute()+" -> ");
			if (graph.getListClone().get(i).getFrom()!=null) 
					System.out.print(graph.getListClone().get(i).getFrom().getNode().getStation().getName() +" dans "+ graph.getListClone().get(i).getFrom().getJunction().getRouteA());
			else System.out.print("null");
			System.out.println();
		}*/
		return extractJunctions(arrivee);
	}
	
	/**
	 * Extrait l'ensemble des junctions d'une liste
	 * 
	 * @param arrivee
	 * @return
	 */
	private ArrayList<Junction> extractJunctions (Station arrivee) {
		ArrayList<Junction> junctions = new ArrayList<Junction>();
		Node n = graph.getNode(arrivee, arrivee.getRoutes().next());
		while (n.getFrom()!=null) {
			junctions.add(n.getFrom().getJunction());
			n = n.getFrom().getNode();
		}
		return junctions;
	}
	
	/**
	 * Retourne le noeud ayant la plus petite distance au point de départ
	 * 
	 * @param list
	 * @return
	 */
	private Node getMinimumNode (ArrayList<Node> list) {
		Node n = list.get(0);
		for (int i=1;i<list.size();i++) {
			if (list.get(i).getCost()< n.getCost()) n=list.get(i);
		}
		return n;
	}
	
	/**
	 * Fonction d'évaluation permettant de comparer un nouveau chemin avec l'existant
	 * 
	 * @param l le nouveau lien à comparer
	 * @param n l'ancien noeud
	 */
	private void betterWay (Link l,Node n) {
		//TODO faire les conditions
		Node newN = l.getNode();
		Junction j = l.getJunction();
		// TIME
		int newTime = n.getTime() + j.getTimeBetweenStations();
		int diffTime = newTime - newN.getTime();
		// CHANGE
		int newChange=n.getChanges();
		if (l.isChanging()) newChange+=1;
		int diffChange = newChange - newN.getChanges();
		// COST
		float newCost=n.getCost();
		if (l.isChanging()) newCost+=l.getJunction().getCost();
		float diffCost = newCost - newN.getCost();	
		
		boolean better=false;
		switch (criterious1) {
			case TIME: 
				if (diffTime<0) better=true;
				else if (diffTime==0) {
						switch (criterious2) {
							case CHANGE: 
								if (diffChange<0 || (diffChange==0 && diffCost<0)) better=true;
								break;
							case COST:
								if (diffCost<0 || (diffCost==0 && diffChange<0))better=true;
								break;
						}
				}
				break;
			case CHANGE:
				if (diffChange<0) better=true;
				else if (diffChange==0) {
						switch (criterious2) {
							case TIME: 
								if (diffTime<0 || (diffTime==0 && diffCost<0))better=true;
								break;
							case COST:
								if (diffCost<0 || (diffCost==0 && diffTime<0)) better=true;
								break;
						}
				}
				break;
			case COST:
				if (diffCost<0) better=true;
				else if (diffCost==0) {
						switch (criterious2) {
							case CHANGE: 
								if (diffChange<0 || (diffChange==0 && diffTime<0)) better=true;
								break;
							case TIME:
								if (diffTime<0 || (diffTime==0 && diffChange<0)) better=true;
								break;
						}
				}
				break;
		}
		if (better) newN.setAll(newTime,newChange,newCost,0,n,j);
	}
}