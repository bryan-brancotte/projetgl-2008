package algorithm;

import graphNetwork.Junction;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo.CriteriousForLowerPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import algorithm.GraphAlgo.Link;
import algorithm.GraphAlgo.Node;

public class Tools {

	// ////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Extrait l'ensemble des junctions d'une liste
	 * 
	 * @param arrivee
	 * @return
	 */
	protected static ArrayList<Junction> extractJunctions(Node arrivee) {
		ArrayList<Junction> junctions = new ArrayList<Junction>();
		Node n = arrivee;
		while (n.getFrom() != null) {
			junctions.add(n.getFrom().getJunction());
			n = n.getFrom().getNode();
		}
		// TODO Penser a supprimer les débuts et fin avec changement au départ
		Collections.reverse(junctions);
		return junctions;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Retourne le noeud ayant la plus petite distance au point de départ
	 * 
	 * @param list
	 * @return
	 */
	protected static Node getMinimumNode(ArrayList<Node> list, CriteriousForLowerPath c) {
		Node n = list.get(0);
		switch (c) {
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

	// ////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Retourne vrai si le service est présent au moins une fois dans le réseau
	 * courant
	 * 
	 * @param s
	 *            Le service recherché
	 * @param list
	 *            La liste des noeuds du graph
	 * @return True si le service existe au moins une fois, false sinon
	 */
	protected static boolean isAccessibleService(Service s, Iterator<Node> list) {
		Iterator<Node> itNode = list;
		while (itNode.hasNext()) {
			Iterator<Service> itServices = itNode.next().getStation().getServices();
			while (itServices.hasNext())
				if (itServices.next().equals(s))
					return true;
		}
		return false;
	}

	/**
	 * 
	 * @param s
	 * @param list
	 */
	protected static void removeServicesFromStation(Station s, Vector<Service> list) {
		Iterator<Service> it = s.getServices();
		while (it.hasNext()) {
			Service serv = it.next();
			if (list.contains(serv))
				list.remove(serv);
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Fonction d'évaluation permettant de comparer un nouveau chemin avec
	 * l'existant
	 * 
	 * @param l
	 *            le nouveau lien à comparer
	 * @param n
	 *            l'ancien noeud
	 */
	protected static void betterWay(Link l, Node n, CriteriousForLowerPath c1, CriteriousForLowerPath c2) {
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
		switch (c1) {
		case TIME:
			if (diffTime < 0)
				better = true;
			else if (diffTime == 0) {
				switch (c2) {
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
				switch (c2) {
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
				switch (c2) {
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

	// ////////////////////////////////////////////////////////////////////////////////////////

}
