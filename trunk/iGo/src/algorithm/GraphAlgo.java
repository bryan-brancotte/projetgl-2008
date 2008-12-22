package algorithm;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.StationNotOnRoadException;

import iGoMaster.exception.NoRouteForStationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;


public class GraphAlgo {

	private ArrayList<Node> graph;
	private Station[] avoidStations;
	private Service[] always;
	
	/**
	 * Rafraichi le réseau avant un calcul
	 * 
	 * @param p
	 * @throws NoRouteForStationException 
	 */
	protected void refreshGraph(PathInGraph p) throws NoRouteForStationException {
		avoidStations = p.getAvoidStationsArray();
		always = p.getServicesAlwaysArray();
		// Initialisation du graph
		graph = new ArrayList<Node>();
		Station s = p.getOrigin();
		Node n;
		if (s.getRoutes()!=null) n = new Node(s,s.getRoutes().next());
		else
			 throw new NoRouteForStationException();
		graph.add(n);
		addLink(n);
		//System.out.println(toString());
	}
	
	/**
	 * A partir du graph chargé, calcul récursif des différents noeuds et arc en partant d'un noeud
	 * 
	 * @param n La racine du graph
	 */
	private	void addLink (Node n) {
		Station station = n.getStation();
		Iterator<Junction> itInter;
		try {
			itInter = station.getJunctions(n.getRoute());
			while(itInter.hasNext()){
				Junction j = itInter.next();
				if (validChange(n,j)) {
					Node newNode = getNode(j.getOtherStation(station),j.getOtherRoute(station));
					if (newNode == null) {
						newNode = new Node(j.getOtherStation(station),j.getOtherRoute(station));
						graph.add(newNode);
						addLink(newNode);
					}
					if (!Links.contains(n.getToIter(),j)) n.addTo(j,newNode);
				}
			}
		} catch (StationNotOnRoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Verification de la présence d'une station dans un tableau de station
	 * 
	 * @param s la station recherchée
	 * @param list la liste de l'ensemble des stations
	 * @return true si la station est présente dans la liste
	 */
	private boolean isStationIn (Station s, Station[] list) {
		if (list!=null) 
			for (int i=0 ; i<list.length ; i++) 
				if (list[i] == s) return true;
		return false;
	}

	/**
	 * Verification de la présence d'un service dans une liste de services
	 * 
	 * @param s le service recherché
	 * @param it  l'itérateur des services
	 * @return true si le service est présent dans la liste
	 */
	private boolean isServiceIn (Service s, Iterator<Service> it) {
		while(it.hasNext())
			if (s.getId() == it.next().getId()) return true;
		return false;
	}
	
	/**
	 * Vérification de la présence de tous les services "always" dans une station donnée
	 * 
	 * @param station la station à vérifier
	 * @return true si la station respecte toutes les contraintes
	 */
	private boolean allServicesIn (Station station){
		for (int i=0 ; i<always.length ;i++) {
			if (!isServiceIn(always[i],station.getServices())) return false;
		}
		return true;		
	}
	
	/**
	 * Vérification que la liaison entre 2 stations est possible avec les contraintes données
	 * 
	 * @param node le noeud représentant la station de de départ
	 * @param junction la jonction à emprunter
	 * @return true si la junction est valide
	 */
	private boolean validChange (Node node,Junction junction) {
		Station station = node.getStation();
		Station otherStation = junction.getOtherStation(station);
		if (
				//TODO en attente de mise a jour réelle
				otherStation == null ||
				isStationIn(otherStation,avoidStations) ||
				!otherStation.isEnable() ||
				(!junction.isRouteLink() && (!allServicesIn(station) || !allServicesIn(otherStation)))
			) return false;
		else return true;
	}
	
	/**
	 * Accesseur du graph de l'algorithme
	 * 
	 * @return Itérateur du graph de la classe
	 */
	protected Iterator<Node> getList () {
		return graph.iterator();
	}
	
	/**
	 * Retourne le noeud d'un couple "station,route"
	 * 
	 * @param s la station concernée
	 * @param r la route empruntée
	 * @return noeud du graph correspondant
	 */
	protected Node getNode (Station s,Route r) {
		Node n;
		for (int i=0;i<graph.size();i++) {
			n = graph.get(i);
			if (s == n.getStation() && r == n.getRoute()) return n;
		}
		return null;
	}

	/**
	 * Retourne le premier noeud du graph correspondant à la station
	 * 
	 * @param s la station concernée
	 * @return premier noeud du graph correspondant
	 */
	protected Node getFirstNode (Station s) {
		Node n;
		for (int i=0;i<graph.size();i++) {
			n = graph.get(i);
			if (s == n.getStation()) return n;
		}
		return null;
	}

	/**
	 * Réinitialisation des poids de l'ensemble du graph de l'algorithme
	 */
	protected void defaultNodes () {
		for (int i=0;i<graph.size();i++) graph.get(i).initValue(); 
	}
	
	/**
	 * 
	 * @return
	 */
	protected ArrayList<Node> getListClone() {
		return new ArrayList<Node>(graph);
	}

	public String toString () {
		String s = "";
		for (int i=0;i<graph.size();i++) {
			s += graph.get(i).getStation().getName()+" - ";
			s += graph.get(i).getRoute().getId();
			for (int j=0;j<graph.get(i).getTo().size();j++) {
				s += " | "+graph.get(i).getTo().get(j).getNode().getStation().getName()+" - ";
				s += graph.get(i).getTo().get(j).getNode().getRoute();
			}
			s +="\n";
		}
		return s;
	}
	
	/*****************************************************************/
	
	// Implémentation du singleton
	private static GraphAlgo instance;
	public static GraphAlgo getInstance(PathInGraph p) {
        if (null == instance) { // Premier appel
            instance = new GraphAlgo();
        }
        return instance;
    }
	
	/*****************************************************************/
	
	protected class Node {
		
		private Link from;
		private Station station;
		private Route route;
		private Vector<Link> to;
		private int relevance;
		private int time;
		private int changes;
		private float cost;

		Node(Station s, Route r) {
			station = s;
			route = r;
			to = new Vector<Link>();
			initValue();
		}
		
		public void initValue () {
			from = null;
			relevance = 0;
			time = Integer.MAX_VALUE>>1;
			changes = Integer.MAX_VALUE>>1;
			cost = Float.MAX_VALUE;
		}

		public void addTo (Junction j, Node n) {
			to.add(new Link(j,n));
		}

		public void removeTo (Link l) {
			to.remove(l);
		}

		public Station getStation (){ return station; }
		public Route getRoute () 	{ return route; }
		public Float getCost ()		{ return cost; }
		public int getTime ()		{ return time;	}
		public int getChanges ()	{ return changes; }
		public Link getFrom ()		{ return from; }
		public int getRelevance ()	{ return relevance;	}
		
		public void setCost (Float _cost)		{ cost=_cost; }
		public void setTime (int _time)			{ time=_time; }
		public void setChanges (int _changes)	{ changes=_changes; }
		public void setFrom (Link _from)		{ from=_from; }
		public void setRelevance (int _relevance){ relevance=_relevance; }
		public void setAll(int _time, int _changes, Float _cost, int _relevance, Node n, Junction j) {
			cost=_cost;
			time=_time;
			changes=_changes;
			from = new Link(j,n);
			relevance=_relevance;
		}
		
		public Iterator<Link> getToIter (){ return to.iterator(); }
		//TODO a virer uniquement pour les tests
		public Vector<Link> getTo (){ return to; }
		
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
		

		public boolean isChanging () {
			if (junction.isRouteLink()) return false;
			else return true;
		}
	}

	/*****************************************************************/
		
	/**
	 * @author iGo
	 *
	 */
	protected static class Links {
		
		public static boolean contains (Iterator<Link> links, Junction j) {
			while (links.hasNext()) {
				Link l = links.next();
				if (j.equals(l.getJunction()))return true;
			}
			return false;
		}
	}
	
}
