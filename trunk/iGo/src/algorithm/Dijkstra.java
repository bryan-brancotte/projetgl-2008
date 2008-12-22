package algorithm;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphResultBuilder;
import graphNetwork.Service;
import graphNetwork.Station;
import iGoMaster.Algo;

import java.util.ArrayList;
import java.util.Iterator;

import algorithm.GraphAlgo.Link;
import algorithm.GraphAlgo.Node;

public class Dijkstra extends Algo {

	/***************************************************************/

	private PathInGraph p;
	private GraphAlgo graph;
	private Station origin, destination;
	private Station[] steps;
	private ArrayList<Service> once;
	private int compteur = 0;
	private long timeComb;

	/***************************************************************/

	private Node currentPosition;
	private ArrayList<Junction> betterPath;

	public void findPath(PathInGraphResultBuilder prb) {

		// Initialisation des contraintes
		initConstraints(prb);

		// Création de l'ensemble des étapes obligatoires
		ArrayList<ArrayList<Station>> allSteps = createAllSteps();

		for (int i=0;i<allSteps.size();i++)
			//System.out.println(allSteps.get(i));
		// Création du chemin
		if (allSteps.size() == 0)
			betterPath.addAll(algo(graph.getFirstNode(origin), graph.getFirstNode(destination)));
		else {
			algoComb(new ArrayList<ArrayList<Station>>(), allSteps, 0);
		}

		// TODO System.out.println(betterPath.size());
		// Création du pathInGraph
		Iterator<Junction> it = betterPath.iterator();
		while (it.hasNext()) {
			prb.addLast(it.next());
		}
		System.out.println(compteur + " Dijkstra effectués");
	}

	/**
	 * Initialisation du Graph ainsi que des contraintes
	 * 
	 * @param prb
	 *            le pathInGraphResultBuilder fourni par iGoMaster
	 */
	private void initConstraints(PathInGraphResultBuilder prb) {
		p = prb.getCurrentPathInGraph();
		graph = GraphAlgo.getInstance(p);

		// Raffraichissement du graph en fonction des nouvelles contraintes
		graph.refreshGraph(p);

		// Récupération des contraintes
		origin = p.getOrigin();
		destination = p.getDestination();
		steps = p.getStepsArray();
		once = new ArrayList<Service>();
		for (int i = 0; i < p.getServicesOnceArray().length; i++) {
			Service s = p.getServicesOnceArray()[i];
			if (Tools.isAccessibleService(s, graph.getList()))
				once.add(s);
			// TODO else NOTIFIER QUE LA CONTRAINTE NE PEUT ETRE REMPLIE
		}
		Tools.removeServicesFromStation(origin, once);
		Tools.removeServicesFromStation(destination, once);
	}

	/**
	 * 
	 * @return un ArrayList de l'ensemble des étapes obligatoires contenant dans
	 *         chaque, l'ensemble des possibilités de chemin empruntables
	 */
	private ArrayList<ArrayList<Station>> createAllSteps() {
		ArrayList<ArrayList<Station>> allSteps = new ArrayList<ArrayList<Station>>();
		for (int i = 0; i < steps.length; i++) {
			ArrayList<Station> v = new ArrayList<Station>();
			v.add(steps[i]);
			allSteps.add(v);
			Tools.removeServicesFromStation(steps[i], once);
		}
		for (int i = 0; i < once.size(); i++) {
			ArrayList<Station> v = new ArrayList<Station>();
			v.addAll(getAllStationswithService(once.get(i), graph.getList()));
			allSteps.add(v);
		}
		return allSteps;
	}

	/**
	 * 
	 * @param v
	 * @param vTot
	 * @return
	 */
	private void algoComb(ArrayList<ArrayList<Station>> v, ArrayList<ArrayList<Station>> vTot, int prof) {
		if (prof==0) timeComb =System.currentTimeMillis();
		if (prof>0 && System.currentTimeMillis()-timeComb>1000 && betterPath!=null && betterPath.size()!=0) return;
		if (v == null || vTot == null || vTot.size() == 0) return;
		if (v.size() == vTot.size()) {
			ArrayList<Junction> currentPath = new ArrayList<Junction>();

			currentPath.addAll(getMinimumDest(graph.getFirstNode(origin), v.get(0)));
			for (int i = 1; i < v.size(); i++) {
				currentPath.addAll(getMinimumDest(currentPosition, v.get(i)));
			}

			currentPath.addAll(algo(currentPosition, graph.getFirstNode(destination)));
			if (betterPath(currentPath, betterPath)) {
				betterPath = currentPath;
			}
		} else {
			for (int i = 0; i < vTot.size(); i++) {
				if (!v.contains(vTot.get(i))) {
					// Conditions d'arret
					v.add(vTot.get(i));
					algoComb(v, vTot, prof + 1);
					v.remove(vTot.get(i));
				}
			}
		}
		return;
	}

	/**
	 * 
	 * @param origin
	 * @param listStation
	 * @return
	 */
	private ArrayList<Junction> getMinimumDest(Node origin, ArrayList<Station> listStation) {
		Node n, best;
		best = n = graph.getFirstNode(listStation.get(0));
		ArrayList<Junction> list = algo(origin, n), newList;
		for (int i = 1; i < listStation.size(); i++) {
			n = graph.getFirstNode(listStation.get(i));
			newList = algo(origin, n);
			if (betterPath(newList, list)) {
				best = n;
				list = newList;
			}
		}
		currentPosition = best;
		return list;
	}

	/**
	 * Retourne la liste des Noeuds contenant le service passé en parametre
	 * 
	 * @param s
	 *            le service nécessaire
	 * @param graph
	 *            la liste des noeuds du graph
	 * @return ArrayList de l'ensemble des noeuds répondant à la contrainte
	 */
	private ArrayList<Station> getAllStationswithService(Service s, Iterator<Node> graph) {
		ArrayList<Station> v = new ArrayList<Station>();
		Iterator<Node> it = graph;
		while (it.hasNext()) {
			Station station = it.next().getStation();
			if (!v.contains(station)) {
				Iterator<Service> itServices = station.getServices();
				while (itServices.hasNext()) {
					if (itServices.next().equals(s)) {
						v.add(station);
						break;
					}
				}
			}
		}
		return v;
	}

	/**
	 * 
	 * @param depart
	 * @param arrivee
	 * @return
	 */
	private ArrayList<Junction> algo(Node depart, Node arrivee) {
		if (depart.getStation() == arrivee.getStation()) return new ArrayList<Junction>();
		compteur++;
		// if (compteur%100==0) System.out.println(compteur);

		Node n, n1;
		Link l;
		ArrayList<Node> pasEncoreVu = graph.getListClone();

		graph.defaultNodes();

		// Initialisation de la ville de départ
		n = graph.getNode(depart.getStation(), depart.getRoute());
		n.setTime(0);
		n.setChanges(0);
		n.setCost((float) 0);

		while (pasEncoreVu.size() != 0) {
			n1 = Tools.getMinimumNode(pasEncoreVu, p.getMainCriterious());
			pasEncoreVu.remove(n1);

			Iterator<Link> it = n1.getToIter();
			while (it.hasNext()) {
				l = it.next();
				Tools.betterWay(l, n1, p.getMainCriterious(), p.getMinorCriterious());
			}
		}
		return Tools.extractJunctions(arrivee);
	}

	private void affichage(ArrayList<Junction> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " - ");
		}
		System.out.println();
		System.out.println(list.size());
	}

	/**
	 * 
	 * @param j1
	 * @param j2
	 * @return
	 */
	private boolean betterPath(ArrayList<Junction> j1, ArrayList<Junction> j2) {
		if (j1 == null)
			return false;
		if (j2 == null)
			return true;

		// TIME
		int newTime = 0, oldTime = 0, diffTime, newChange = 0, oldChange = 0, diffChange;
		float newCost = 0, oldCost = 0, diffCost;
		for (int i = 0; i < j1.size(); i++) {
			Junction j = j1.get(i);
			newTime += j.getTimeBetweenStations();
			newCost += j.getCost();
			if (!j.isRouteLink())
				newChange++;
		}
		for (int i = 0; i < j2.size(); i++) {
			Junction j = j2.get(i);
			oldTime += j.getTimeBetweenStations();
			oldCost += j.getCost();
			if (!j.isRouteLink())
				oldChange++;
		}
		diffTime = newTime - oldTime;
		diffCost = newCost - oldCost;
		diffChange = newChange - oldChange;

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