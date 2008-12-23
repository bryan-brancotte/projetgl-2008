package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.Route;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

public class SectionOfTravelImplPathInGraph implements SectionOfTravel {

	public SectionOfTravelImplPathInGraph(Route route, Station station) {
		super();
		this.route = route;
		this.station = station;
	}

	protected Route route;

	protected Station station;

	@Override
	public float getEnddingChangementCost() {
		return enddingChangementCost;
	}

	@Override
	public int getEnddingChangementTime() {
		return enddingChangementTime;
	}

	@Override
	public String getNameChangement() {
		return station.getName();
	}

	@Override
	public String getNameRoute() {
		return route.getId();
	}

	@Override
	public Route getRoute() {
		return route;
	}

	protected int timeSection = 0;
	protected int enddingChangementTime = -1;
	protected float enddingChangementCost = 0F;

	@Override
	public int getTimeSection() {
		return timeSection;
	}

	public void addJunction(Junction j) {
		if (j.getOtherRoute(route) != route) {
			station = j.getOtherStation(station);
			enddingChangementTime = j.getTimeBetweenStations();
			enddingChangementCost = j.getCost();
		} else {
			timeSection += j.getTimeBetweenStations();
		}

	}
}
