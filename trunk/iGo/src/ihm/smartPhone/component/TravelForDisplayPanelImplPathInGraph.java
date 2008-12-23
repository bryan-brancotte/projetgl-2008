package ihm.smartPhone.component;

import graphNetwork.Junction;
import graphNetwork.PathInGraph;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;

import java.util.Iterator;
import java.util.LinkedList;

public class TravelForDisplayPanelImplPathInGraph implements TravelForDisplayPanel {

	protected Station origin;

	protected Station destination;

	protected SectionOfTravel nextStop;

	protected int remainingTime;

	protected float totalCost;

	protected int totalTime;

	LinkedList<SectionOfTravel> travel;

	public TravelForDisplayPanelImplPathInGraph(PathInGraph path) {
		super();
		origin = path.getOrigin();
		destination = path.getDestination();
		Iterator<Junction> itJ = path.getJunctions();
		Junction junction;
		SectionOfTravel section=new SectionOfTravel;
		while (itJ.hasNext()) {
			junction = itJ.next();

		}
	}

	@Override
	public String getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrigine() {
		// TODO Auto-generated method stub
		return null;
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

}
