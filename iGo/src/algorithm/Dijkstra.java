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
import algorithm.exception.NonValidDestinationException;
import algorithm.exception.NonValidOriginException;

public class Dijkstra extends Algo {

	/***************************************************************/

	private PathInGraph p;
	private GraphAlgo graph;
	private Station origin, destination;
	private Station[] steps;
	private ArrayList<Service> once;
	private long timeComb;
	private boolean isAborted;
	private int TimeMaxPerConstraintsInMs = 1000;

	/***************************************************************/

	private Node currentPosition;
	private ArrayList<Junction> betterPath;

	public void findPath(PathInGraphResultBuilder prb) throws NoRouteForStationException, VoidPathException, ServiceNotAccessibleException, StationNotAccessibleException, StationNotOnRoadException, NonValidOriginException, NonValidDestinationException {

		prb.flush();
		isAborted = false;
		betterPath = null;
		currentPosition = null;
		try {
			// Initialisation des contraintes
			initConstraints(prb);
/*
			System.out.println("--------------");
			for (int i=0;i<graph.getListClone().size();i++) {
				Node nTmp = graph.getListClone().get(i);
				System.out.print(nTmp.getStation()+" | ");
				Iterator<Link> it = nTmp.getToIter();
				while (it.hasNext()){
					System.out.print(it.next().getJunction()+" || ");
				}
				System.out.println();
			}
*/			
			// Création de l'ensemble des étapes obligatoires
			ArrayList<ArrayList<Station>> allSteps = createAllSteps();

			if (allSteps.size() == 0)
				betterPath = new ArrayList<Junction>(algo(origin, destination));
			else
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
		int time = j.getTimeBetweenStations();
		while (it.hasNext()) {
			j = it.next();
			cost += j.getCost();
			time += j.getTimeBetweenStations();
			prb.addLast(j);
		}
		// Mise à jour du temps de parcours du trajet
		prb.setTime(time);
		// Mise à jour du cout de trajet
		prb.setCost(cost);
		// Information que le trajet est résolu
		prb.setPathInGraphResolved();

		// Notification d'iGoMaster que le calcul est terminé
		this.setChanged();
		this.notifyObservers(p);
		steps = null;
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
	 * @throws NonValidDestinationException
	 * @throws NonValidOriginException
	 */
	private void initConstraints(PathInGraphResultBuilder prb) throws NoRouteForStationException, ServiceNotAccessibleException, StationNotAccessibleException, StationNotOnRoadException, NodeNotFoundException, NonValidOriginException, NonValidDestinationException {
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
		// Suppression des services étant remplis par l'origine ou la
		// destination
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
	 * Fonction récursive permettant de trouver un chemin entre l'origine et la
	 * destination en testant l'ensemble des chemins possibles dans un temps
	 * défini par la variable TimeMaxPerConstraintsInMs fixée à 1 seconde par
	 * défaut (prévision d'un choix de l'utilisateur ultérieur pour une
	 * configuration avancée)
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
		if ((prof > 0 && System.currentTimeMillis() - timeComb > TimeMaxPerConstraintsInMs && betterPath != null && betterPath.size() != 0) || isAborted)
			return;
		if (v == null || vTot == null || vTot.size() == 0)
			return;
		if (v.size() == vTot.size()) {
			ArrayList<Junction> currentPath = new ArrayList<Junction>();

			currentPath.addAll(getMinimumDest(origin, v.get(0)));
			for (int i = 1; i < v.size(); i++)
				currentPath.addAll(getMinimumDest(currentPosition, v.get(i), false));

			currentPath.addAll(algo(currentPosition, destination));
			if (Tools.betterPath(currentPath, betterPath, p.getMainCriterious(), p.getMinorCriterious()))
				betterPath = currentPath;
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
	 * Retourne le chemin le plus court entre un couple station,route et une
	 * liste de station
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
		if (isAborted)
			return null;
		Iterator<Route> itDepart = depart.getRoutes();
		ArrayList<Junction> returnPath = null;
		while (itDepart.hasNext()) {
			Route rDepart = itDepart.next();
			Iterator<Route> itArrivee = arrivee.getRoutes();
			while (itArrivee.hasNext()) {
				Route rArrivee = itArrivee.next();
				Node nd = graph.getNode(depart, rDepart);
				Node na = graph.getNode(arrivee, rArrivee);
				if (na!=null && nd!=null) {
					ArrayList<Junction> currentPath = new ArrayList<Junction>(algo(nd, na, true));
					if (Tools.betterPath(currentPath, returnPath, p.getMainCriterious(), p.getMinorCriterious())) {
						returnPath = currentPath;
					}
				}
			}
			System.out.println("route suivante");
		}
		return returnPath;
	}

	public void abort() {
		isAborted = true;
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
	 * Algorithme de calcul pour le trajet
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