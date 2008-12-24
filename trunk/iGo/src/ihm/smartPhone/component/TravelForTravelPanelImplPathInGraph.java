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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDestination() {
		return path.getDestination().getName();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFavorite(boolean isFav) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		father.setCurrentState(IhmReceivingStates.COMPUT_TRAVEL);
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

}
