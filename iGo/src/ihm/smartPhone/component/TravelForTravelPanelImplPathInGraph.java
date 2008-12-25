package ihm.smartPhone.component;

import graphNetwork.PathInGraph;
import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.statePanels.IhmReceivingPanelState;
import ihm.smartPhone.statePanels.IhmReceivingStates;

public class TravelForTravelPanelImplPathInGraph implements TravelForTravelPanel {

	protected PathInGraph path;
	protected IhmReceivingPanelState father;

	@Override
	public void delete() {
		// TODO TravelForTravelPanelImplPathInGraph.delete

	}

	@Override
	public void edit() {
		// TODO TravelForTravelPanelImplPathInGraph.edit

	}

	@Override
	public String getDestination() {
		return path.getDestination().getName();
	}

	@Override
	public String getName() {
		// TODO TravelForTravelPanelImplPathInGraph.getName
		return null;
	}

	@Override
	public String getOrigine() {
		return path.getOrigin().getName();
	}

	@Override
	public float getTotalCost() {
		return -1F;
	}

	@Override
	public int getTotalTime() {
		return path.getTime();
	}

	@Override
	public boolean isFavorite() {
		// TODO TravelForTravelPanelImplPathInGraph.isFavorite
		return false;
	}

	@Override
	public void setFavorite(boolean isFav) {
		// TODO TravelForTravelPanelImplPathInGraph.setFavorite

	}

	@Override
	public void setName(String name) {
		// TODO TravelForTravelPanelImplPathInGraph.setName

	}

	@Override
	public void start() {
		father.setCurrentState(IhmReceivingStates.COMPUT_TRAVEL);
	}

	@Override
	public boolean update() {
		// TODO TravelForTravelPanelImplPathInGraph.update
		return false;
	}

}
