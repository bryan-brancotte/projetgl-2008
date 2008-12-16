package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;

import java.awt.Graphics;

public class TravelArrayDisplayPanel extends TravelDisplayPanel {

	public TravelArrayDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar, travelForDisplayPanel);

	}

	private static final long serialVersionUID = 1L;

	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawString(this.getClass().getSimpleName(), 0, this.getHeight());
		super.paint(g);
	}

	@Override
	protected void actionToDoWhenChangeStateIsClicked() {
		father.setConfig("GRAPHIC_OR_ARRAY_MODE", IhmReceivingStates.GRAPHIC_MODE.toString());
		father.setActualState(IhmReceivingStates.GRAPHIC_MODE.mergeState(actualState));
	}

	@Override
	protected String getMessageChangeState() {
		return father.lg("GoToGraphicMode");
	}
}
