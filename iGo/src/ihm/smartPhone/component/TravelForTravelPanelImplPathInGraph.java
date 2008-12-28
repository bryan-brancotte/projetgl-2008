package ihm.smartPhone.component;

import graphNetwork.PathInGraphConstraintBuilder;
import ihm.smartPhone.interfaces.TravelForTravelPanel;

public abstract class TravelForTravelPanelImplPathInGraph implements TravelForTravelPanel {

	public TravelForTravelPanelImplPathInGraph(PathInGraphConstraintBuilder path) {
		super();
		this.path = path;
	}

	protected PathInGraphConstraintBuilder path;

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
		return null;
	}

	@Override
	public String getOrigine() {
		return path.getCurrentPathInGraph().getOrigin().getName();
	}

	@Override
	public float getTotalCost() {
		return -1F;
	}

	@Override
	public int getTotalTime() {
		return path.getCurrentPathInGraph().getTime();
	}

	@Override
	public boolean isFavorite() {
		// TODO isFavorite
		return false;
	}

	@Override
	public void setFavorite(boolean isFav) {
		// TODO setFavorite

	}

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
