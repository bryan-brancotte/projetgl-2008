package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

import java.util.ArrayList;
import java.util.Iterator;

public class SectionOfTravelImplPathInGraph implements SectionOfTravel {

	protected ArrayList<Junction> junctions;
	protected ArrayList<Service> services;

	public SectionOfTravelImplPathInGraph(Route route, Station station) {
		super();
		this.route = route;
		this.station = station;
		this.junctions = new ArrayList<Junction>();
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
	public Station getChangement() {
		return station;
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
		services = null;
		junctions.add(j);
		station = j.getOtherStation(station);
		if (j.getOtherRoute(route) != route) {
			enddingChangementTime = j.getTimeBetweenStations();
			enddingChangementCost = j.getCost();
		} else {
			timeSection += j.getTimeBetweenStations();
		}
	}

	public boolean isValide() {
		for (Junction j : junctions)
			if (!j.isEnable())
				return false;
		return true;
	}

	@Override
	public int getStationInSection() {
		return junctions.size();
	}

	@Override
	public Iterator<Service> getEnddingChangementServices() {
		if (station != null)
			return station.getServices();
		return (new ArrayList<Service>()).iterator();
	}
}
