package algorithm;

import graphNetwork.GraphNetwork;
import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GraphAlgo {
		
	private ArrayList<Node> graph;
	private Station[] avoidStations;
	private Service[] always;
	
	public GraphAlgo(PathInGraph p) {
		avoidStations = p.getAvoidStationsArray();
		always = p.getSevicesAlwaysArray();
		// Initialisation du graph
		graph = new ArrayList<Node>();
		
		// Parcours du graph 
		Iterator<Station> itFrom = p.getGraph().getStations();
		while(itFrom.hasNext()){
			Station s = itFrom.next();
			
			// Si la station n'est pas à éviter 
			if (!isIn(s,avoidStations) && s.isEnable()) {
	
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
					n.addTo(j,getNode(n.getStation(), n.getRoute()));
				}
			}
		}
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
	
	public Iterator<Node> getList () {
		return graph.iterator();
	}
	
	public ArrayList<Node> getListClone() {
		return new ArrayList<Node>(graph);
	}
	
	public Node getNode (Station s,Route r) {
		Node n;
		for (int i=0;i<graph.size();i++) {
			n = graph.get(i);
			if (s == n.getStation() && r == n.getRoute()) return n;
		}
		return null;
	}
	
	/*****************************************************************/
	
	protected class Node {
		
		private Node from;
		private Station station;
		private Route route;
		private LinkedList<Link> to;
		private int interet;
		private int parcouru;

		Node(Station s, Route r) {
			from = null;
			station = s;
			route = r;
			interet = 0;
			parcouru = Integer.MAX_VALUE;
			to = new LinkedList<Link>();
		}

		public void addTo (Junction j, Node n) {
			to.add(new Link(j,n));
		}

		public void removeTo (Link l) {
			to.remove(l);
		}

		public Station getStation () {return station;}
		public Route getRoute () {return route;}
		
		public int getParcouru (){ return parcouru;	}
		public void setParcouru (int _parcouru){ parcouru=_parcouru; }

		public int getInteret (){ return interet;	}
		public void setInteret (int _interet){ interet=_interet; }

		public Node getFrom (){ return from;	}
		public void setFrom (Node _from){ from=_from; }

		public Iterator<Link> getToIter (){ return to.iterator(); }
		
	}

	/*****************************************************************/
	
	protected class Link {

		private Junction junction;
		private Node node;

		public Link (Junction j, Node n) {
			junction = j;
			node = n;
		}
		
		public Junction getJunction (){ return junction; }
		public Node getNode (){ return node; }
		
	}
	
}
