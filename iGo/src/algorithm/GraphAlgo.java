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
	
	public void refreshGraph(PathInGraph p) {
		avoidStations = p.getAvoidStationsArray();
		always = p.getSevicesAlwaysArray();
		// Initialisation du graph
		graph = new ArrayList<Node>();
		Station s = p.getOrigin();
		Node n = new Node(s,s.getRoutes().next());
		graph.add(n);
		addLink(n);
	}
	
	private	void addLink (Node n) {
		// Si la station n'est pas à éviter
		Station station = n.getStation();
		Iterator<Junction> itInter = station.getJunction();
		while(itInter.hasNext()){
			Junction j = itInter.next();
			// Si la transition est possible
			if (validChange(station,j)) {
				Node newNode = getNode(n.getStation(), n.getRoute());
				if (newNode == null) newNode = new Node(j.getOtherStation(station),j.getOtherRoute(station));
				n.addTo(j,newNode);
				addLink(newNode);
			}
		}
		
	}
	
	private boolean isStationIn (Station s, Station[] list) {
		for (int i=0 ; i<list.length ; i++) { if (list[i] == s) return true; }
		return false;
	}

	private boolean isServiceIn (Service s, Iterator<Service> it) {
		while(it.hasNext()){ if (s.getId() == it.next().getId()) return true; }
		return false;
	}
	
	private boolean allServicesIn (Station station){
		for (int i=0 ; i<always.length ;i++) {
			if (!isServiceIn(always[i],station.getService())) return false;
		}
		return true;		
	}
	
	private boolean validChange (Station station,Junction junction) {
		boolean retour = true;
		Station otherStation = junction.getOtherStation(station);
		
		if (
				// La station d'arrivée n'est pas valide ou désactivée
				isStationIn(otherStation,avoidStations) || !otherStation.isEnable() ||
				// La route d'arrivée est differente de celle de départ ET
				junction.getRoute(station) != junction.getOtherRoute(station) &&
					// la station de départ est différente de celle de départ OU sinon qu'elle ne remplit pas les contraintes always
					(station != junction.getOtherStation(station) ||  station == junction.getOtherStation(station) && !allServicesIn(station))
				
			) return false;
		else {
			return true;
		}
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
	
	// Implémentation du singleton
	private static GraphAlgo instance;
	private GraphAlgo() {}
	public static GraphAlgo getInstance() {
        if (null == instance) { // Premier appel
            instance = new GraphAlgo();
        }
        return instance;
    }
	
	/*****************************************************************/
	
	protected class Node {
		
		private Node from;
		private Station station;
		private Route route;
		private LinkedList<Link> to;
		private int relevance;
		private int time;
		private int changes;
		private int cost;
		private int reach;

		Node(Station s, Route r) {
			from = null;
			station = s;
			route = r;
			relevance = 0;
			time = Integer.MAX_VALUE;
			changes = 0;
			cost = 0;
			to = new LinkedList<Link>();
		}

		public void addTo (Junction j, Node n) {
			to.add(new Link(j,n));
		}

		public void removeTo (Link l) {
			to.remove(l);
		}

		public Station getStation (){ return station; }
		public int getCost ()		{ return cost; }
		public Route getRoute () 	{ return route; }
		public int getTime ()		{ return time;	}
		public int getChanges ()	{ return changes; }
		public Node getFrom ()		{ return from; }
		public int getRelevance ()	{ return relevance;	}
		
		public void setCost (int _cost)			{ cost=_cost; }
		public void setTime (int _time)			{ time=_time; }
		public void setChanges (int _changes)	{ changes=_changes; }
		public void setFrom (Node _from)		{ from=_from; }
		public void setRelevance (int _relevance){ relevance=_relevance; }

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