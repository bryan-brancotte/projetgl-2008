package ihm.smartPhone.component;

import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Service;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.NetworkColorManager;
import ihm.smartPhone.interfaces.TravelForTravelPanel;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class TravelForTravelPanelImplPathInGraph implements TravelForTravelPanel {

	protected String intermediateStation = null;

	protected LinkedList<ServiceForTravelPanel> serviceOnce;

	protected LinkedList<ServiceForTravelPanel> serviceAlways;

	public TravelForTravelPanelImplPathInGraph(PathInGraphConstraintBuilder path,
			NetworkColorManager networkColorManager, boolean isFav) {
		super();
		this.path = path;
		this.isFav = isFav;
		this.serviceOnce = new LinkedList<ServiceForTravelPanel>();
		this.serviceAlways = new LinkedList<ServiceForTravelPanel>();
		Iterator<Service> itService;
		Service service;
		itService = path.getCurrentPathInGraph().getServicesAlwaysIter();
		while (itService.hasNext()) {
			serviceAlways.add(new ServiceForTravelPanelImpl((service = itService.next()).getName().substring(0, 1),
					networkColorManager.getColor(service)));
		}
		itService = path.getCurrentPathInGraph().getServicesOnceIter();
		while (itService.hasNext()) {
			serviceOnce.add(new ServiceForTravelPanelImpl((service = itService.next()).getName().substring(0, 1),
					networkColorManager.getColor(service)));
		}
	}

	protected PathInGraphConstraintBuilder path;

	protected boolean isFav = false;

	@Override
	public abstract void delete();

	@Override
	public abstract void edit();

	@Override
	public String getDestination() {
		if (path.getCurrentPathInGraph().getDestination() == null)
			return "";
		return path.getCurrentPathInGraph().getDestination().getName();
	}

	@Override
	public String getOrigine() {
		if (path.getCurrentPathInGraph().getOrigin() == null)
			return "";
		return path.getCurrentPathInGraph().getOrigin().getName();
	}

	@Override
	public float getTotalCost() {
		return path.getCurrentPathInGraph().getCost();
	}

	@Override
	public int getTotalTime() {
		return path.getCurrentPathInGraph().getTime();
	}

	@Override
	public boolean isFavorite() {
		return isFav;
	}

	@Override
	public abstract void setFavorite(boolean isFav);

	@Override
	public String getIntermediateStation() {
		if (intermediateStation == null) {
			intermediateStation = "";
			if (path.getCurrentPathInGraph().getStepsCount() == 0)
				return intermediateStation;
			else {
				Iterator<Station> itS = path.getCurrentPathInGraph().getStepsIter();
				while (itS.hasNext()) {
					intermediateStation += (itS.next()).getName();
					if (itS.hasNext())
						intermediateStation += ", ";
					else
						intermediateStation += ".";
				}
			}
		}
		return intermediateStation;
	}

	@Override
	public abstract void start();

	public Iterator<ServiceForTravelPanel> getServiceOnce() {
		return serviceOnce.iterator();
	}

	public Iterator<ServiceForTravelPanel> getServiceAlways() {
		return serviceAlways.iterator();
	}

	public class ServiceForTravelPanelImpl implements ServiceForTravelPanel {

		public ServiceForTravelPanelImpl(String letter, Color color) {
			super();
			this.letter = letter;
			this.color = color;
		}

		protected String letter;
		protected Color color;

		public String getLetter() {
			return letter;
		}

		public Color getColor() {
			return color;
		}

	}

}
