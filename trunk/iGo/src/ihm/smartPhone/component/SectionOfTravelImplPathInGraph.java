package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.StationNotOnRoadException;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class SectionOfTravelImplPathInGraph implements SectionOfTravel {

	protected float enddingChangementCost = 0F;
	protected int enddingChangementTime = -1;

	protected LinkedList<Junction> junctions;

	protected Vector<String> stationName;

	protected String[] stationNameArray;

	protected Route route;

	protected ArrayList<Service> services;

	protected Station station;

	protected Station direction;

	protected int timeSection = 0;

	public SectionOfTravelImplPathInGraph(Route route, Station station) {
		super();
		this.route = route;
		this.station = station;
		this.junctions = new LinkedList<Junction>();
		this.stationName = new Vector<String>();
		this.direction = null;
	}

	public void addJunction(Junction j) {
		services = null;
		if (this.direction == null)
			try {
				this.direction = this.route.getDirection(station, j.getOtherStation(station));
			} catch (StationNotOnRoadException e) {
				this.direction = null;
			}
		junctions.add(j);
		stationName.add(station.getName());
		station = j.getOtherStation(station);
		// System.out.println(j + "\t\t" + j.getOtherRoute(route) + " == " + route);
		if (j.getOtherRoute(route) != route) {
			enddingChangementTime = j.getTimeBetweenStations();
			enddingChangementCost = j.getCost();
		} else {
			timeSection += j.getTimeBetweenStations();
		}
	}

	@Override
	public Station getChangement() {
		return station;
	}

	@Override
	public Station getDirection() {
		if (this.direction == null)
			return station;
		return direction;
	}

	@Override
	public float getEnddingChangementCost() {
		return enddingChangementCost;
	}

	@Override
	public Iterator<Service> getEnddingChangementServices() {
		if (station != null)
			return station.getServices();
		return (new ArrayList<Service>()).iterator();
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

	@Override
	public int getStationInSectionCount() {
		return junctions.size() - 1;
	}

	@Override
	public String[] getStationInSection() {
		if (stationNameArray == null || stationNameArray.length != stationName.size())
			stationNameArray = stationName.toArray(new String[0]);
		return stationNameArray;
	}

	@Override
	public int getTimeSection() {
		return timeSection;
	}

	public boolean isValide() {
		for (Junction j : junctions)
			if (!j.isEnable())
				return false;
		return true;
	}
}
