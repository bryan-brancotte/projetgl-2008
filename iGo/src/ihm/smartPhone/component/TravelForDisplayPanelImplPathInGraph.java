package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.Route;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;

import java.util.Iterator;
import java.util.LinkedList;

public class TravelForDisplayPanelImplPathInGraph implements TravelForDisplayPanel {

	protected PathInGraph path;

	protected Station origin;

	protected Station destination;

	protected SectionOfTravel nextStop;

	protected int remainingTime;

	protected float totalCost;

	protected float entryCost;

	protected int totalTime;

	LinkedList<SectionOfTravel> travel;

	LinkedList<SectionOfTravel> travelDone;

	public TravelForDisplayPanelImplPathInGraph(PathInGraph path) {
		super();
		this.path = path;
		origin = path.getOrigin();
		destination = path.getDestination();
		travel = new LinkedList<SectionOfTravel>();
		travelDone = new LinkedList<SectionOfTravel>();
		Iterator<Junction> itJ = path.getJunctions();
		Junction junction = itJ.next();
		Station station = origin;
		totalCost = path.getCost();
		totalTime = path.getTime();
		entryCost = path.getEntryCost();
		Route route = junction.getOtherRoute(junction.getOtherRoute(station));
		SectionOfTravelImplPathInGraph section = new SectionOfTravelImplPathInGraph(route, origin);

		itJ = path.getJunctions();
		while (itJ.hasNext()) {
			section.addJunction(junction = itJ.next());
			station = junction.getOtherStation(station);
			if (path.containsSteps(station)) {
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
		return travel.getFirst().getNameChangement();
	}

	@Override
	public void next() {
		remainingTime -= (travel.getFirst().getTimeSection() + travel.getFirst().getEnddingChangementTime());
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
	public PathInGraph getPath() {
		return path;
	}
}
