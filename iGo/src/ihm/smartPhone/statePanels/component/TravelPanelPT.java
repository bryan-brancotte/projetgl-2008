package ihm.smartPhone.statePanels.component;

import java.awt.Rectangle;

import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.libPT.PTButton;

public class TravelPanelPT {

	public TravelPanelPT(PTButton cmdFav, PTButton cmdDel, PTButton cmdEdit, Rectangle area,
			TravelForTravelPanel pathBuilder) {
		super();
		this.cmdFav = cmdFav;
		this.cmdDel = cmdDel;
		this.cmdEdit = cmdEdit;
		this.area = area;
		this.pathBuilder = pathBuilder;
		this.isInMe=false;
	}
	public PTButton cmdFav;
	public PTButton cmdDel;
	public PTButton cmdEdit;
	public Rectangle area;
	public boolean isInMe;
	public TravelForTravelPanel pathBuilder;

}
