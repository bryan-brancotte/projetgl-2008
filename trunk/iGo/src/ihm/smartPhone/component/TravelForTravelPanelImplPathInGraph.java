package ihm.smartPhone.component;

import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Station;
import ihm.smartPhone.interfaces.TravelForTravelPanel;

import java.util.Iterator;

public abstract class TravelForTravelPanelImplPathInGraph implements TravelForTravelPanel {

	protected String intermediateStation = null;

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

	@Override
	public boolean update() {
		// TODO TFT.update
		return false;
	}

}
