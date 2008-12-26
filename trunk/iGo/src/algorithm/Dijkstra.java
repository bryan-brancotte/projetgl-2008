package algorithm;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphResultBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.StationNotOnRoadException;
import iGoMaster.Algo;
import iGoMaster.exception.NoRouteForStationException;
import iGoMaster.exception.ServiceNotAccessibleException;
import iGoMaster.exception.StationNotAccessibleException;
import iGoMaster.exception.VoidPathException;

import java.util.ArrayList;
import java.util.Iterator;

import algorithm.GraphAlgo.Link;
import algorithm.GraphAlgo.Node;
import algorithm.exception.NodeNotFoundException;
import algorithm.exception.NonValidPathException;

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

	public void findPath(PathInGraphResultBuilder prb) throws NoRouteForStationException, VoidPathException, ServiceNotAccessibleException, StationNotAccessibleException, StationNotOnRoadException, NonValidPathException {

		try {
			// Initialisation des contraintes
			initConstraints(prb);

			// Création de l'ensemble des étapes obligatoires
			ArrayList<ArrayList<Station>> allSteps = createAllSteps();

			if (allSteps.size() == 0)
				betterPath = new ArrayList<Junction>(algo(origin, destination));
			else
				// for (int i = 0; i < allSteps.size(); i++)
				algoComb(new ArrayList<ArrayList<Station>>(), allSteps, 0);

		} catch (NodeNotFoundException e) {
			System.err.println("Le noeud du couple " + e.getStation() + "-" + e.getRoute() + " n'existe pas");
		}

		if (betterPath == null)
			throw new VoidPathException();

		// Création du pathInGraph
		Iterator<Junction> it = betterPath.iterator();
		Junction j = it.next();
		Route r = j.getMyRoute(origin);
		prb.addLast(j);
		prb.setEntryCost(p.getGraph().getEntryCost(r.getKindRoute()));
		float cost = p.getGraph().getEntryCost(r.getKindRoute());
		int time=j.getTimeBetweenStations();
		while (it.hasNext()) {
			j = it.next();
			cost+=j.getCost();
			time+=j.getTimeBetweenStations();
			prb.addLast(j);
		}
		prb.setTime(time);
		prb.setCost(cost);
		prb.setPathInGraphResolved();

		this.setChanged();
		this.notifyObservers(p);
		// System.out.println(compteur + " Dijkstra effectués");
	}

	/**
	 * Initialisation du Graph ainsi que des contraintes
	 * 
	 * @param prb
	 *            le pathInGraphResultBuilder fourni par iGoMaster
	 * @throws NoRouteForStationException
	 * @throws ServiceNotAccessibleException
	 * @throws StationNotAccessibleException
	 * @throws StationNotOnRoadException
	 * @throws NodeNotFoundException
	 * @throws NonValidPathException 
	 */
	private void initConstraints(PathInGraphResultBuilder prb) throws NoRouteForStationException, ServiceNotAccessibleException, StationNotAccessibleException, StationNotOnRoadException, NodeNotFoundException, NonValidPathException {
		p = prb.getCurrentPathInGraph();
		graph = GraphAlgo.getInstance(p);

		// Raffraichissement du graph en fonction des nouvelles contraintes
		graph.refreshGraph(p);

		// Récupération des contraintes
		if (graph.getFirstNode(p.getOrigin()) == null)
			throw new StationNotAccessibleException(p.getOrigin());
		else
			origin = p.getOrigin();
		if (graph.getFirstNode(p.getDestination()) == null)
			throw new StationNotAccessibleException(p.getDestination());
		else
			destination = p.getDestination();
		steps = p.getStepsArray();
		for (int i = 0; i < steps.length; i++) {
			if (graph.getFirstNode(steps[i]) == null)
				throw new StationNotAccessibleException(steps[i]);
		}
		once = new ArrayList<Service>();
		for (int i = 0; i < p.getServicesOnceArray().length; i++) {
			Service s = p.getServicesOnceArray()[i];
			if (Tools.isAccessibleService(s, graph.getList()))
				once.add(s);
			else
				throw new ServiceNotAccessibleException(s);
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
	 * @throws NoRouteForStationException
	 * @throws NodeNotFoundException
	 */
	private void algoComb(ArrayList<ArrayList<Station>> v, ArrayList<ArrayList<Station>> vTot, int prof) throws NodeNotFoundException, NoRouteForStationException {
		if (prof == 0)
			timeComb = System.currentTimeMillis();
		if (prof > 0 && System.currentTimeMillis() - timeComb > 1000 && betterPath != null && betterPath.size() != 0)
			return;
		if (v == null || vTot == null || vTot.size() == 0)
			return;
		if (v.size() == vTot.size()) {
			ArrayList<Junction> currentPath = new ArrayList<Junction>();

			currentPath.addAll(getMinimumDest(origin, v.get(0)));
			for (int i = 1; i < v.size(); i++) {
				currentPath.addAll(getMinimumDest(currentPosition, v.get(i), false));
			}

			currentPath.addAll(algo(currentPosition, destination));
			if (Tools.betterPath(currentPath, betterPath, p.getMainCriterious(), p.getMinorCriterious())) {
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
	 * @throws NoRouteForStationException
	 */
	private ArrayList<Junction> getMinimumDest(Node origin, ArrayList<Station> listStation, boolean isGoingIn) throws NoRouteForStationException {
		Node n, best;
		best = n = graph.getFirstNode(listStation.get(0));
		ArrayList<Junction> list = algo(origin, n, isGoingIn), newList;
		for (int i = 1; i < listStation.size(); i++) {
			n = graph.getFirstNode(listStation.get(i));
			newList = algo(origin, n, isGoingIn);
			if (Tools.betterPath(newList, list, p.getMainCriterious(), p.getMinorCriterious())) {
				best = n;
				list = newList;
			}
		}
		currentPosition = best;
		return list;
	}

	private ArrayList<Junction> getMinimumDest(Station origin, ArrayList<Station> listStation) throws NodeNotFoundException, NoRouteForStationException {
		Iterator<Route> itOrigin = origin.getRoutes();
		ArrayList<Junction> returnPath = null;
		Node best = null;
		while (itOrigin.hasNext()) {
			ArrayList<Junction> currentPath = new ArrayList<Junction>(getMinimumDest(graph.getNode(origin, itOrigin.next()), listStation, true));
			if (Tools.betterPath(currentPath, returnPath, p.getMainCriterious(), p.getMinorCriterious())) {
				best = currentPosition;
				returnPath = currentPath;
			}
		}
		currentPosition = best;
		return returnPath;
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
	 * Ne sert que dans le cas ou il n'y a aucune contrainte donc chemin direct
	 * donc isGoingIn vaut true
	 * 
	 * @param depart
	 * @param arrivee
	 * @return
	 * @throws NoRouteForStationException
	 * @throws NodeNotFoundException
	 */
	private ArrayList<Junction> algo(Station depart, Station arrivee) throws NoRouteForStationException, NodeNotFoundException {
		Iterator<Route> itDepart = depart.getRoutes();
		Iterator<Route> itArrivee = arrivee.getRoutes();
		ArrayList<Junction> returnPath = null;
		while (itDepart.hasNext()) {
			Route rDepart = itDepart.next();
			// System.out.println(rDepart);
			while (itArrivee.hasNext()) {
				Route rArrivee = itArrivee.next();
				// System.out.println(rArrivee);
				// System.out.println(depart+" - "+rDepart+" -> "+graph.getNode(depart,
				// rDepart));
				// System.out.println(arrivee+" - "+rArrivee+" -> "+graph.getNode(arrivee,
				// rArrivee)+" | "+graph.getFirstNode(arrivee).getRoute());
				ArrayList<Junction> currentPath = new ArrayList<Junction>(algo(graph.getNode(depart, rDepart), graph.getNode(arrivee, rArrivee), true));
				if (Tools.betterPath(currentPath, returnPath, p.getMainCriterious(), p.getMinorCriterious())) {
					returnPath = currentPath;
				}
			}
		}
		return returnPath;
	}

	/**
	 * Algo ne servant que pour la derniere station donc isGoingIn vaut false
	 * 
	 * @param depart
	 * @param arrivee
	 * @return
	 * @throws NodeNotFoundException
	 */
	private ArrayList<Junction> algo(Node depart, Station arrivee) throws NodeNotFoundException {
		Iterator<Route> itArrivee = arrivee.getRoutes();
		ArrayList<Junction> returnPath = null;
		while (itArrivee.hasNext()) {
			ArrayList<Junction> currentPath = new ArrayList<Junction>();
			currentPath.addAll(algo(depart, graph.getNode(arrivee, itArrivee.next()), false));
			if (Tools.betterPath(currentPath, returnPath, p.getMainCriterious(), p.getMinorCriterious())) {
				returnPath = currentPath;
			}
		}
		return returnPath;
	}

	/**
	 * 
	 * @param depart
	 * @param arrivee
	 * @param enter
	 *            mettre a true si l'utilisateur entre dans le reseau
	 * @return
	 */
	private ArrayList<Junction> algo(Node depart, Node arrivee, boolean isGoingIn) {
		if (depart.getStation() == arrivee.getStation())
			return new ArrayList<Junction>();
		compteur++;
		// if (compteur%100==0) System.out.println(compteur);

		Node n, n1;
		Link l;
		ArrayList<Node> pasEncoreVu = graph.getListClone();

		graph.defaultNodes();

		// Initialisation de la ville de départ
		n = depart;
		n.setTime(0);
		n.setChanges(0);
		if (isGoingIn)
			n.setCost((float) p.getGraph().getEntryCost(depart.getRoute().getKindRoute()));
		else
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

}