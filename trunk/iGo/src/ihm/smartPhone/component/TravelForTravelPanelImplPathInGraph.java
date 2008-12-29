package ihm.smartPhone.component;

import graphNetwork.PathInGraphConstraintBuilder;
import ihm.smartPhone.interfaces.TravelForTravelPanel;

public abstract class TravelForTravelPanelImplPathInGraph implements TravelForTravelPanel {

	public TravelForTravelPanelImplPathInGraph(PathInGraphConstraintBuilder path, boolean isFav) {
		super();
		this.path = path;
		this.isFav = isFav;
	}

	protected PathInGraphConstraintBuilder path;

	protected boolean isFav = false;

	@Override
	public abstract void delete();

	@Override
	public abstract void edit();

	@Override
	public String getDestination() {
		return path.getCurrentPathInGraph().getDestination().getName();
	}

	@Override
	public String getName() {
		// TODO getName
		return "";
	}

	@Override
	public String getOrigine() {
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
