package ihm.smartPhone.component;

import graphNetwork.PathInGraph;
import ihm.smartPhone.interfaces.TravelForTravelPanel;

public abstract class TravelForTravelPanelImplPathInGraph implements TravelForTravelPanel {

	public TravelForTravelPanelImplPathInGraph(PathInGraph path,boolean isFav) {
		super();
		this.path = path;
		this.isFav = isFav;
	}

	protected PathInGraph path;

	protected boolean isFav = false;

	@Override
	public abstract void delete();

	@Override
	public abstract void edit();

	@Override
	public String getDestination() {
		return path.getDestination().getName();
	}

	@Override
	public String getName() {
		// TODO getName
		return "";
	}

	@Override
	public String getOrigine() {
		return path.getOrigin().getName();
	}

	@Override
	public float getTotalCost() {
		return  path.getCost();
	}

	@Override
	public int getTotalTime() {
		return path.getTime();
	}

	@Override
	public boolean isFavorite() {
		return isFav;
	}

	@Override
	public abstract void setFavorite(boolean isFav);

	@Override
	public void setName(String name) {
		// TODO setName

	}

	@Override
	public abstract void start();

	@Override
	public boolean update() {
		// TODO update
		return false;
	}

}
