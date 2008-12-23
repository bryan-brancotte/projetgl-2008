package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.Route;
import ihm.smartPhone.interfaces.TravelForDisplayPanel.SectionOfTravel;

public class SectionOfTravelImplPathInGraph implements SectionOfTravel {

	@Override
	public float getEnddingChangementCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnddingChangementTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNameChangement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route getRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	protected int timeSection = 0;
	protected int enddingChangementTime = 0;
	protected float enddingChangementCost = 0F;

	@Override
	public int getTimeSection() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addJunction(Junction j) {
		enddingChangementCost = j.getCost();
		enddingChangementTime = j.getTimeBetweenStations();
		timeSection += enddingChangementTime;

	}
}
