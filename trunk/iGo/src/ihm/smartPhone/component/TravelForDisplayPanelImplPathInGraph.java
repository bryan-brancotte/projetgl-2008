package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;

import java.util.Iterator;
import java.util.LinkedList;

public class TravelForDisplayPanelImplPathInGraph implements TravelForDisplayPanel {

	protected PathInGraphConstraintBuilder path;

	protected Station origin;

	protected Station destination;

	protected SectionOfTravel nextStop;

	protected int remainingTime;

	protected float totalCost;

	protected float entryCost;

	protected int totalTime;

	LinkedList<SectionOfTravel> travel;

	LinkedList<SectionOfTravel> travelDone;

	public TravelForDisplayPanelImplPathInGraph(PathInGraphConstraintBuilder path) {
		super();
		this.path = path;
		origin = path.getCurrentPathInGraph().getOrigin();
		destination = path.getCurrentPathInGraph().getDestination();
		travel = new LinkedList<SectionOfTravel>();
		travelDone = new LinkedList<SectionOfTravel>();

		LinkedList<Service> serviceToFind = new LinkedList<Service>();
		Iterator<Service> itS = path.getCurrentPathInGraph().getServicesOnceIter();
		while (itS.hasNext())
			serviceToFind.add(itS.next());

		itS = origin.getServices();
		while (itS.hasNext())
			serviceToFind.remove(itS.next());

		Iterator<Junction> itJ = path.getCurrentPathInGraph().getJunctions();
		Junction junction = itJ.next();
		Junction nextJunction = null;
		Station station = origin;
		totalCost = path.getCurrentPathInGraph().getCost();
		totalTime = path.getCurrentPathInGraph().getTime();
		entryCost = path.getCurrentPathInGraph().getEntryCost();
		Route route = junction.getOtherRoute(junction.getOtherRoute(station));
		SectionOfTravelImplPathInGraph section = new SectionOfTravelImplPathInGraph(route, origin);

		itJ = path.getCurrentPathInGraph().getJunctions();
		while (itJ.hasNext()) {
			if (nextJunction != null) {
				junction = nextJunction;
				nextJunction = null;
			} else {
				junction = itJ.next();
			}
			section.addJunction(junction);
			station = junction.getOtherStation(station);
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
			if (path.getCurrentPathInGraph().containsSteps(station)) {
				section.enddingChangementTime = 0;
				section.enddingChangementCost = 0;
			}
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
	public boolean update() {
		// TODO TFD.update
		return false;
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
		travelDone.addLast(travel.removeFirst());
		// path.
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
}
