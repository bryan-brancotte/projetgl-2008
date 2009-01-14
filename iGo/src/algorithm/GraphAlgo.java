package algorithm;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.StationNotOnRoadException;
import iGoMaster.Algo.CriteriousForLowerPath;
import iGoMaster.exception.NoRouteForStationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import algorithm.exception.NodeNotFoundException;
import algorithm.exception.NonValidDestinationException;
import algorithm.exception.NonValidOriginException;
import algorithm.exception.NullCriteriousException;
import algorithm.exception.NullStationException;

public class GraphAlgo {

	private PathInGraph p;
	private ArrayList<Node> graph;
	private Station[] avoidStations;
	private Service[] always;

	/**
	 * Rafraichi le réseau avant un calcul
	 * 
	 * @param p
	 * @throws NoRouteForStationException
	 * @throws StationNotOnRoadException
	 * @throws NodeNotFoundException
	 * @throws NonValidOriginException 
	 * @throws NonValidDestinationException 
	 * @throws NullStationException 
	 * @throws NullCriteriousException 
	 */
	protected void refreshGraph(PathInGraph _p) throws NoRouteForStationException, StationNotOnRoadException, NodeNotFoundException, NonValidOriginException, NonValidDestinationException, NullStationException, NullCriteriousException {
		
		p = _p;
		if (p.getOrigin()==null || p.getDestination()==null)
			throw new NullStationException();
		if (p.getMainCriterious()==CriteriousForLowerPath.NOT_DEFINED || p.getMinorCriterious()==CriteriousForLowerPath.NOT_DEFINED)
			throw new NullCriteriousException();
		avoidStations = p.getAvoidStationsArray();
		always = p.getServicesAlwaysArray();
		// Initialisation du graph
		graph = new ArrayList<Node>();
		Station s = p.getOrigin();
		Node n;
		Iterator<Route> itRoute = s.getRoutes(); 
		Route r = itRoute.next();
		boolean nonValidRoute = isRefusedRoute(r); 
		while (nonValidRoute && itRoute.hasNext()) {
			r=itRoute.next();
			nonValidRoute = isRefusedRoute(r);
		}
		if (isRefusedRoute(r)) r=null; 
		if (r!=null)
			n = new Node(s, r);
		else
			throw new NoRouteForStationException(s);
		if (allServicesIn(p.getOrigin()) && allServicesIn(p.getDestination())) {
			graph.add(n);
			addLink(n);
		}
		else {
			// Verification que l'origine et la destination répondent bien aux contraintes "toujours"
			if (!allServicesIn(p.getOrigin())) {
				ArrayList<Service> list = new ArrayList<Service>();
				for(Service service : always) {
					boolean found=false;
					Iterator<Service> it = p.getOrigin().getServices();
					while (it.hasNext()) {
						if (it.next()==service) {
							found=true;
							break;
						}
					}
					if (!found) list.add(service);
				}
				throw new NonValidOriginException(list);
			}
			else {
				ArrayList<Service> list = new ArrayList<Service>();
				for(Service service : always) {
					boolean found=false;
					Iterator<Service> it = p.getDestination().getServices();
					while (it.hasNext()) {
						if (it.next()==service) {
							found=true;
							break;
						}
					}
					if (!found) list.add(service);
				}
				throw new NonValidDestinationException(list);
			}
		}
	}

	/**
	 * A partir du graph chargé, calcul récursif des différents noeuds et arc en
	 * partant d'un noeud
	 * 
	 * @param n
	 *            La racine du graph
	 * @throws StationNotOnRoadException
	 * @throws NodeNotFoundException
	 */
	private void addLink(Node n) throws StationNotOnRoadException {
		Station station = n.getStation();
		Iterator<Junction> itInter;
		itInter = station.getJunctions(n.getRoute());
		while (itInter.hasNext()) {
			Junction j = itInter.next();
			if (validChange(n, j)) {
				Node newNode;
				newNode = getNode(j.getOtherStation(station), j.getOtherRoute(station));
				if (newNode == null) {
					newNode = new Node(j.getOtherStation(station), j.getOtherRoute(station));
					graph.add(newNode);
					addLink(newNode);
				}
				if (!Links.contains(n.getToIter(), j))
					n.addTo(j, newNode);
			}
		}
	}

	/**
	 * Verification de la présence d'une station dans un tableau de station
	 * 
	 * @param s
	 *            la station recherchée
	 * @param list
	 *            la liste de l'ensemble des stations
	 * @return true si la station est présente dans la liste
	 */
	private boolean isStationIn(Station s, Station[] list) {
		if (list != null)
			for (int i = 0; i < list.length; i++)
				if (list[i] == s)
					return true;
		return false;
	}

	/**
	 * Verification de la présence d'un service dans une liste de services
	 * 
	 * @param s
	 *            le service recherché
	 * @param it
	 *            l'itérateur des services
	 * @return true si le service est présent dans la liste
	 */
	private boolean isServiceIn(Service s, Iterator<Service> it) {
		while (it.hasNext())
			if (s.getId() == it.next().getId())
				return true;
		return false;
	}

	/**
	 * Vérification de la présence de tous les services "always" dans une
	 * station donnée
	 * 
	 * @param station
	 *            la station à vérifier
	 * @return true si la station respecte toutes les contraintes
	 */
	private boolean allServicesIn(Station station) {
		for (int i = 0; i < always.length; i++) {
			if (!isServiceIn(always[i], station.getServices()))
				return false;
		}
		return true;
	}

	/**
	 * Vérification que la liaison entre 2 stations est possible avec les
	 * contraintes données
	 * 
	 * @param node
	 *            le noeud représentant la station de de départ
	 * @param junction
	 *            la jonction à emprunter
	 * @return true si la junction est valide
	 */
	private boolean validChange(Node node, Junction junction) {
		Station station = node.getStation();
		Station otherStation = junction.getOtherStation(station);
		if (
				isRefusedRoute(junction.getOtherRoute(node.getRoute())) ||
				isStationIn(otherStation, avoidStations) || 
				!otherStation.isEnable() || 
				(!junction.isRouteLink() && (!allServicesIn(station)))
			)
			return false;
		else
			return true;
	}
	
	private boolean isRefusedRoute (Route r) {
		if (r==null) return false;
		for (int i=0;i<p.getRefusedKindRouteArray().length;i++) {
			if (r.getKindRoute()==p.getRefusedKindRouteArray()[i]) return true;
		}
		return false;
		
	}

	/**
	 * Accesseur du graph de l'algorithme
	 * 
	 * @return Itérateur du graph de la classe
	 */
	protected Iterator<Node> getList() {
		return graph.iterator();
	}

	/**
	 * Retourne le noeud d'un couple "station,route"
	 * 
	 * @param s
	 *            la station concernée
	 * @param r
	 *            la route empruntée
	 * @return noeud du graph correspondant
	 * @throws NodeNotFoundException
	 */
	protected Node getNode(Station s, Route r) {
		Node n = null;
		for (int i = 0; i < graph.size(); i++) {
			n = graph.get(i);
			if (s == n.getStation() && r == n.getRoute())
				return n;
		}
		return null;
	}

	/**
	 * Retourne le premier noeud du graph correspondant à la station
	 * 
	 * @param s
	 *            la station concernée
	 * @return premier noeud du graph correspondant
	 * @throws NoRouteForStationException
	 */
	protected Node getFirstNode(Station s) throws NoRouteForStationException {
		Node n = null;
		for (int i = 0; i < graph.size(); i++) {
			if (s == graph.get(i).getStation())
				return graph.get(i);
		}
		if (n == null)
			throw new NoRouteForStationException(s);
		return null;
	}

	/**
	 * Réinitialisation des poids de l'ensemble du graph de l'algorithme
	 */
	protected void defaultNodes() {
		for (int i = 0; i < graph.size(); i++)
			graph.get(i).initValue();
	}

	/**
	 * 
	 * @return
	 */
	protected ArrayList<Node> getListClone() {
		return new ArrayList<Node>(graph);
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
		// Argument pour une éventuelle évolution de methode de calcul avec heuristique
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

		public void initValue() {
			from = null;
			relevance = 0;
			time = Integer.MAX_VALUE/2;
			changes = Integer.MAX_VALUE/2;
			cost = Float.MAX_VALUE/2;
		}

		public void addTo(Junction j, Node n) {
			to.add(new Link(j, n));
		}

		public void removeTo(Link l) {
			to.remove(l);
		}

		public Station getStation() {
			return station;
		}

		public Route getRoute() {
			return route;
		}

		public Float getCost() {
			return cost;
		}

		public int getTime() {
			return time;
		}

		public int getChanges() {
			return changes;
		}

		public Link getFrom() {
			return from;
		}

		public int getRelevance() {
			return relevance;
		}

		public void setCost(Float _cost) {
			cost = _cost;
		}

		public void setTime(int _time) {
			time = _time;
		}

		public void setChanges(int _changes) {
			changes = _changes;
		}

		public void setFrom(Link _from) {
			from = _from;
		}

		public void setRelevance(int _relevance) {
			relevance = _relevance;
		}

		public void setAll(int _time, int _changes, Float _cost, int _relevance, Node n, Junction j) {
			cost = _cost;
			time = _time;
			changes = _changes;
			from = new Link(j, n);
			relevance = _relevance;
		}

		public Iterator<Link> getToIter() {
			return to.iterator();
		}
		
		public String toString () {
			return "["+station+","+route+"]";
		}
	}

	/*****************************************************************/

	protected class Link {

		private Junction junction;
		private Node node;

		public Link(Junction j, Node n) {
			junction = j;
			node = n;
		}

		public Junction getJunction() {
			return junction;
		}

		public Node getNode() {
			return node;
		}

		public boolean isChanging() {
			if (junction.isRouteLink())
				return false;
			else
				return true;
		}
	}

	/*****************************************************************/

	/**
	 * @author iGo
	 * 
	 */
	protected static class Links {

		public static boolean contains(Iterator<Link> links, Junction j) {
			while (links.hasNext()) {
				Link l = links.next();
				if (j.equals(l.getJunction()))
					return true;
			}
			return false;
		}
	}

}
