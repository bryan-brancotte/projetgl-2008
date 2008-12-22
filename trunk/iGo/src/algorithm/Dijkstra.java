package algorithm;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphResultBuilder;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import algorithm.GraphAlgo.Link;
import algorithm.GraphAlgo.Node;

public class Dijkstra extends Algo {

	private PathInGraph p;
	private GraphAlgo graph;
	private Station origin, destination;
	private Vector<Station> steps;
	private Vector<Service> once;
	private LinkedList<Junction> path;
	private LinkedList<Station> actualStations;

	public void findPath(PathInGraphResultBuilder prb) {

		p = prb.getCurrentPathInGraph();
		graph = GraphAlgo.getInstance(p);

		// Raffraichissement du graph en fonction des nouvelles contraintes
		graph.refreshGraph();

		// Récupération des contraintes
		origin = p.getOrigin();
		destination = p.getDestination();
		steps = new Vector<Station>();
		for (int i=0;i<p.getStepsArray().length;i++)
			steps.add(p.getStepsArray()[i]);
		once = new Vector<Service>();
		for (int i = 0; i < p.getServicesOnceArray().length; i++) {
			Service s = p.getServicesOnceArray()[i];
			if (isAccessibleService(s))
				once.add(s);
			// TODO else NOTIFIER QUE LA CONTRAINTE NE PEUT ETRE REMPLIE
		}
/*
		Vector<Vector<Station>> stepsTemp = new Vector<Vector<Station>>();
		for (int i = 0; i < steps.size(); i++) {
			Vector<Station> v = new Vector<Station>();
			v.add(steps.get(i));
			stepsTemp.add(v);
		}
		for (int i = 0; i < once.size(); i++) {
			Vector<Station> v = new Vector<Station>();
			v.addAll(getAllStationswithService(once.get(i)));
		}
*/
		// Création du chemin
		path = new LinkedList<Junction>();
		if (steps.size() == 0)
			path.addAll(algo(origin, destination));
		else {
			path.addAll(algo(origin, steps.get(0)));
			for (int i = 0; i < steps.size() - 1; i++)
				path.addAll(algo(steps.get(i), steps.get(i+1)));
			path.addAll(algo(steps.get(steps.size() - 1), destination));
		}

		// Création du pathInGraph
		Iterator<Junction> it = path.iterator();
		while (it.hasNext()) {
			prb.addLast(it.next());
		}
	}
/*
	private Vector<Vector<Station>> algoComb(Vector<Vector<Station>> v, Vector<Vector<Station>> vTot) {
		if (v==null || vTot==null || vTot.size()==0) return null; 
		if (v.size() == vTot.size()) {
			ArrayList<Junction> newStations = new ArrayList<Junction>();
			newStations.addAll(getMinimumDest(origin,v.get(0)));
			for (int i=1;i<v.size();i++) {
				//TODO Verifier que la junction est dans le bon sens !
				newStations.addAll(getMinimumDest(newStations.get(newStations.size()-1).get,v.get(0)));
				newStations.add(s);
			}
			newStations.add(destination);
			
			
		}
		for (int i = 0; i < vTot.size() - v.size(); i++) {
			if (!v.contains(vTot.get(i))) {
				v.add(vTot.get(i));
				algoComb(v, vTot);
				v.remove(vTot.get(i));
			}
		}
		return null;
	}
*/
	private ArrayList<Junction> getMinimumDest(Station origin, Vector<Station> vector) {
		ArrayList<Junction> list = algo(origin,vector.get(0)),newList;
		for (int i=1;i<vector.size();i++) {
			newList = algo(origin,vector.get(i));
			if (betterPath(newList, list)) list=newList;
		}
		return list;
	}

	private Vector<Station> getAllStationswithService(Service s) {
		Vector<Station> v = new Vector<Station>();
		Iterator<Node> it = graph.getList();
		while (it.hasNext()) {
			Station station = it.next().getStation();
			Iterator<Service> itServices = station.getServices();
			while (itServices.hasNext()) {
				if (itServices.next().equals(s)) {
					v.add(station);
					break;
				}
			}
		}
		return v;
	}

	private boolean isAccessibleService(Service s) {
		Iterator<Node> itNode = graph.getList();
		while (itNode.hasNext()) {
			Iterator<Service> itServices = itNode.next().getStation().getServices();
			while (itServices.hasNext())
				if (itServices.next().equals(s))
					return true;
		}
		return false;
	}

	/**
	 * Fonction de dijkstra implémentée
	 * 
	 * @param graph
	 * @param depart
	 * @param arrivee
	 * @return
	 */
	private ArrayList<Junction> algo(Station depart, Station arrivee) {

		Iterator<Node> g;
		Node n, n1;
		Link l;
		ArrayList<Node> pasEncoreVu = graph.getListClone();

		graph.defaultNodes();

		g = graph.getList();
		while (g.hasNext()) {
			n = g.next();
			if (depart == n.getStation()) {
				n.setTime(0);
				n.setChanges(0);
				n.setCost((float) 0);
				break;
			}
		}
		while (pasEncoreVu.size() != 0) {
			n1 = getMinimumNode(pasEncoreVu);
			pasEncoreVu.remove(n1);

			Iterator<Link> it = n1.getToIter();
			while (it.hasNext()) {
				l = it.next();
				betterWay(l, n1);
			}
		}
		return extractJunctions(arrivee);
	}

	/**
	 * Extrait l'ensemble des junctions d'une liste
	 * 
	 * @param arrivee
	 * @return
	 */
	private ArrayList<Junction> extractJunctions(Station arrivee) {
		ArrayList<Junction> junctions = new ArrayList<Junction>();
		Node n = graph.getNode(arrivee, arrivee.getRoutes().next());
		while (n.getFrom() != null) {
			junctions.add(n.getFrom().getJunction());
			n = n.getFrom().getNode();
		}
		Collections.reverse(junctions);
		return junctions;
	}

	/**
	 * Retourne le noeud ayant la plus petite distance au point de départ
	 * 
	 * @param list
	 * @return
	 */
	private Node getMinimumNode(ArrayList<Node> list) {
		Node n = list.get(0);
		switch (p.getMainCriterious()) {
		case TIME:
			for (int i = 1; i < list.size(); i++)
				if (list.get(i).getTime() < n.getTime())
					n = list.get(i);
			break;
		case CHANGE:
			for (int i = 1; i < list.size(); i++)
				if (list.get(i).getChanges() < n.getChanges())
					n = list.get(i);
			break;
		case COST:
			for (int i = 1; i < list.size(); i++)
				if (list.get(i).getCost() < n.getCost())
					n = list.get(i);
			break;
		}
		return n;
	}

	/**
	 * Fonction d'évaluation permettant de comparer un nouveau chemin avec
	 * l'existant
	 * 
	 * @param l
	 *            le nouveau lien à comparer
	 * @param n
	 *            l'ancien noeud
	 */
	private void betterWay(Link l, Node n) {
		// TODO faire les conditions
		Node newN = l.getNode();
		Junction j = l.getJunction();

		// TIME
		int newTime = n.getTime() + j.getTimeBetweenStations();
		int diffTime = newTime - newN.getTime();
		// CHANGE
		int newChange = n.getChanges();
		if (l.isChanging())
			newChange += 1;
		int diffChange = newChange - newN.getChanges();
		// COST
		float newCost = n.getCost();
		if (l.isChanging())
			newCost += l.getJunction().getCost();
		float diffCost = newCost - newN.getCost();

		boolean better = false;
		switch (p.getMainCriterious()) {
		case TIME:
			if (diffTime < 0)
				better = true;
			else if (diffTime == 0) {
				switch (p.getMinorCriterious()) {
				case CHANGE:
					if (diffChange < 0 || (diffChange == 0 && diffCost < 0))
						better = true;
					break;
				case COST:
					if (diffCost < 0 || (diffCost == 0 && diffChange < 0))
						better = true;
					break;
				}
			}
			break;
		case CHANGE:
			if (diffChange < 0)
				better = true;
			else if (diffChange == 0) {
				switch (p.getMinorCriterious()) {
				case TIME:
					if (diffTime < 0 || (diffTime == 0 && diffCost < 0))
						better = true;
					break;
				case COST:
					if (diffCost < 0 || (diffCost == 0 && diffTime < 0))
						better = true;
					break;
				}
			}
			break;
		case COST:
			if (diffCost < 0)
				better = true;
			else if (diffCost == 0) {
				switch (p.getMinorCriterious()) {
				case CHANGE:
					if (diffChange < 0 || (diffChange == 0 && diffTime < 0))
						better = true;
					break;
				case TIME:
					if (diffTime < 0 || (diffTime == 0 && diffChange < 0))
						better = true;
					break;
				}
			}
			break;
		}
		if (better) {
			newN.setAll(newTime, newChange, newCost, 0, n, j);
		}
	}

	private boolean betterPath(ArrayList<Junction> j1,ArrayList<Junction> j2) {
		if (j1==null) return false;
		if (j2==null) return true;
		
		// TIME
		int newTime = 0,oldTime = 0,diffTime,newChange = 0,oldChange = 0,diffChange;
		float newCost = 0,oldCost = 0,diffCost;
		for (int i=0;i<j1.size();i++) {
			Junction j = j1.get(i); 
			newTime+=j.getTimeBetweenStations();
			newCost+=j.getCost();
			if (!j.isRouteLink()) newChange++;
		}
		for (int i=0;i<j2.size();i++) {
			Junction j = j2.get(i); 
			oldTime+=j.getTimeBetweenStations();
			oldCost+=j.getCost();
			if (!j.isRouteLink()) oldChange++;
		}
		diffTime = newTime-oldTime;
		diffCost = newCost-oldCost;
		diffChange = newChange-oldChange;
		
		boolean better = false;
		switch (p.getMainCriterious()) {
		case TIME:
			if (diffTime < 0)
				better = true;
			else if (diffTime == 0) {
				switch (p.getMinorCriterious()) {
				case CHANGE:
					if (diffChange < 0 || (diffChange == 0 && diffCost < 0))
						better = true;
					break;
				case COST:
					if (diffCost < 0 || (diffCost == 0 && diffChange < 0))
						better = true;
					break;
				}
			}
			break;
		case CHANGE:
			if (diffChange < 0)
				better = true;
			else if (diffChange == 0) {
				switch (p.getMinorCriterious()) {
				case TIME:
					if (diffTime < 0 || (diffTime == 0 && diffCost < 0))
						better = true;
					break;
				case COST:
					if (diffCost < 0 || (diffCost == 0 && diffTime < 0))
						better = true;
					break;
				}
			}
			break;
		case COST:
			if (diffCost < 0)
				better = true;
			else if (diffCost == 0) {
				switch (p.getMinorCriterious()) {
				case CHANGE:
					if (diffChange < 0 || (diffChange == 0 && diffTime < 0))
						better = true;
					break;
				case TIME:
					if (diffTime < 0 || (diffTime == 0 && diffChange < 0))
						better = true;
					break;
				}
			}
			break;
		}
		return better;
	}
}