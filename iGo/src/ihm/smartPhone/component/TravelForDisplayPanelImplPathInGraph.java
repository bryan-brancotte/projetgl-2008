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

	protected int totalTime;

	LinkedList<SectionOfTravel> travel;

	LinkedList<SectionOfTravel> travelDone;

	public TravelForDisplayPanelImplPathInGraph(PathInGraph path) {
		super();
		this.path = path;
		origin = path.getOrigin();
		destination = path.getDestination();
		travel = new LinkedList<SectionOfTravel>();
		Iterator<Junction> itJ = path.getJunctions();
		Junction junction = itJ.next();
		Station station = origin;
		Route route = junction.getOtherRoute(junction.getOtherRoute(station));
		SectionOfTravelImplPathInGraph section = new SectionOfTravelImplPathInGraph(route, origin);

		itJ = path.getJunctions();
		while (itJ.hasNext()) {
			section.addJunction(junction = itJ.next());
			station = junction.getOtherStation(station);
			if (section.getEnddingChangementTime() != -1) {
				travel.add(section);	
				route = junction.getOtherRoute(route);
				section = new SectionOfTravelImplPathInGraph(route, origin);
			}
		}
		travel.add(section);
		if (section.getEnddingChangementTime() == -1)
			section.enddingChangementTime = 0;
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
	public Iterator<SectionOfTravel> getTravel() {
		return travel.iterator();
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNextStop() {
		SectionOfTravel sectionOfTravel = travel.removeFirst();
		travelDone.addLast(sectionOfTravel);
		return travel.getFirst().getNameChangement();
	}

	@Override
	public void next() {
		travelDone.addLast(travel.removeFirst());
	}

	@Override
	public boolean isValideFromWhereIAm() {
		for (SectionOfTravel t : travel)
			if (!((SectionOfTravelImplPathInGraph) t).isValide())
				return false;
		return true;
	}
}
