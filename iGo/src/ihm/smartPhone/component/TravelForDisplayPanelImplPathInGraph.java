package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class TravelForDisplayPanelImplPathInGraph implements TravelForDisplayPanel {

	protected PathInGraphConstraintBuilder path;

	protected PathInGraphConstraintBuilder pathClone;

	protected Station origin;

	protected Station destination;

	protected SectionOfTravel nextStop;

	protected int remainingTime;

	protected float totalCost;

	protected float entryCost;

	protected int totalTime;

	protected boolean isFav;

	LinkedList<SectionOfTravel> travel;

	LinkedList<SectionOfTravel> travelDone;

	public TravelForDisplayPanelImplPathInGraph(PathInGraphConstraintBuilder path,
			PathInGraphConstraintBuilder pathClone, boolean isFav) {
		super();
		this.path = path;
		this.pathClone = pathClone;
		this.isFav = isFav;
		origin = path.getCurrentPathInGraph().getOrigin();
		destination = path.getCurrentPathInGraph().getDestination();
		travel = new LinkedList<SectionOfTravel>();
		travelDone = new LinkedList<SectionOfTravel>();

		LinkedList<Service> serviceToFind = new LinkedList<Service>();
		Iterator<Service> itS;
		Iterator<Junction> itJ = path.getCurrentPathInGraph().getJunctions();
		Junction junction = itJ.next();
		Junction nextJunction = null;
		Station station;
		totalCost = path.getCurrentPathInGraph().getCost();
		totalTime = path.getCurrentPathInGraph().getTime();
		entryCost = path.getCurrentPathInGraph().getEntryCost();
		Route route = junction.getOtherRoute(junction.getOtherRoute(origin));
		SectionOfTravelImplPathInGraph section = new SectionOfTravelImplPathInGraph(route, origin);

		// pré traitement, on retire les services l'on trouvera dans les changement
		itS = path.getCurrentPathInGraph().getServicesOnceIter();
		while (itS.hasNext())
			serviceToFind.add(itS.next());
		// retrait des services ou départ
		itS = origin.getServices();
		while (itS.hasNext())
			serviceToFind.remove(itS.next());
		// retrait des service à l'arrivé
		itS = destination.getServices();
		while (itS.hasNext())
			serviceToFind.remove(itS.next());
		// retrait des services sur les changement
		station = origin;
		itJ = path.getCurrentPathInGraph().getJunctions();
		while (itJ.hasNext() && !serviceToFind.isEmpty()) {
			station = (junction = itJ.next()).getOtherStation(station);
			if (!junction.isRouteLink()) {
				itS = station.getServices();
				while (itS.hasNext())
					serviceToFind.remove(itS.next());
			}
		}

		// création du parcourt
		station = origin;
		itJ = path.getCurrentPathInGraph().getJunctions();
		remainingTime = 0;
		while (itJ.hasNext()) {
			if (nextJunction != null) {
				junction = nextJunction;
				nextJunction = null;
			} else {
				junction = itJ.next();
			}
			remainingTime += junction.getTimeBetweenStations();
			section.addJunction(junction);
			station = junction.getOtherStation(station);
			// coupure si on trouve un service qui n'est pas sur les changements
			if (serviceToFind.size() > 0 && itJ.hasNext()) {
				nextJunction = itJ.next();
				itS = station.getServices();
				while (itS.hasNext())
					if (serviceToFind.remove(itS.next()))
						if (section.getEnddingChangementTime() == -1 && nextJunction.isRouteLink()) {
							section.enddingChangementTime = 0;
							section.enddingChangementCost = 0;
						}
			}
			// coupure si on est sur une étape
			if (section.getEnddingChangementTime() == -1 && path.getCurrentPathInGraph().containsSteps(station)) {
				section.enddingChangementTime = 0;
				section.enddingChangementCost = 0;
			}
			// coupure si on change de ligne
			if (section.getEnddingChangementTime() != -1) {
				travel.add(section);
				route = junction.getOtherRoute(route);
				section = new SectionOfTravelImplPathInGraph(route, station);
			}
		}
		travel.add(section);
		if (section.getEnddingChangementTime() == -1)
			section.enddingChangementTime = 0;
		remainingTime = getTotalTime();
	}

	@Override
	public String getDestination() {
		return destination.getName();
	}

	@Override
	public String getOrigine() {
		return origin.getName();
	}

	@Override
	public int getRemainingTime() {
		return remainingTime;
	}

	@Override
	public float getTotalCost() {
		return totalCost;
	}

	@Override
	public int getTotalTime() {
		return totalTime;
	}

	@Override
	public Iterator<SectionOfTravel> getTravelToDo() {
		return travel.iterator();
	}

	@Override
	public Iterator<SectionOfTravel> getTravelDone() {
		return travelDone.iterator();
	}

	@Override
	public String getNextStop() {
		if (travel.size() == 0)
			return "";
		return travel.getFirst().getNameChangement();
	}

	@Override
	public void next() {
		remainingTime -= (travel.getFirst().getTimeSection() + travel.getFirst().getEnddingChangementTime());
		pathClone.removeStepStations(travel.getFirst().getChangement());
		if (pathClone.getCurrentPathInGraph().getServicesOnceCount() > 0) {
			Iterator<Service> itService = travel.getFirst().getChangement().getServices();
			while (itService.hasNext())
				pathClone.removeSeviceOnce(itService.next());
		}
		travelDone.addLast(travel.removeFirst());
	}

	@Override
	public void previous() {
		if (travelDone.size() == 0)
			return;
		travel.addFirst(travelDone.removeLast());
		remainingTime += (travel.getFirst().getTimeSection() + travel.getFirst().getEnddingChangementTime());
	}

	@Override
	public boolean isValideFromWhereIAm() {
		for (SectionOfTravel t : travel)
			if (!((SectionOfTravelImplPathInGraph) t).isValide())
				return false;
		return true;
	}

	@Override
	public boolean prepareToSolveAsBestAsICan() {
		if (travel.isEmpty())
			this.pathClone.setOrigin(this.pathClone.getCurrentPathInGraph().getDestination());
		else if (travelDone.size() == 0)
			this.pathClone.setOrigin(this.pathClone.getCurrentPathInGraph().getOrigin());
		else if (((SectionOfTravelImplPathInGraph) travel.getFirst()).isValide())
			this.pathClone.setOrigin(travel.getFirst().getChangement());
		else
			this.pathClone.setOrigin(null);
		// else if (travelDone.size() > 0 && !((SectionOfTravelImplPathInGraph) travelDone.getLast()).isValide()) {
		// this.pathClone.setOrigin(travelDone.getLast().getChangement());
		// }
		if (this.pathClone.getCurrentPathInGraph().getOrigin() != null
				&& this.pathClone.getCurrentPathInGraph().getOrigin().isEnable())
			return true;
		return false;
	}

	@Override
	public boolean hasNext() {
		return !travel.isEmpty();
	}

	@Override
	public Station getOrigineStation() {
		return origin;
	}

	@Override
	public float getEntryCost() {
		return entryCost;
	}

	@Override
	public PathInGraphConstraintBuilder getPath() {
		return path;
	}

	@Override
	public PathInGraphConstraintBuilder getPathClone() {
		return pathClone;
	}

	@Override
	public boolean hasPrevious() {
		return !travelDone.isEmpty();
	}

	@Override
	public boolean isFavorite() {
		return isFav;
	}
}
