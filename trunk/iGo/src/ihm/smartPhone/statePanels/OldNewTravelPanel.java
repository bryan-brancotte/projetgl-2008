package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.PanelDoubleBufferingSoftwear;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OldNewTravelPanel extends PanelState {

	private static final long serialVersionUID = 1L;

	public OldNewTravelPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
		super(ihm, upperBar, lowerBar);
	}

	@Override
	public void paint(Graphics g) {
		if (currentQuality != PanelDoubleBufferingSoftwear.getQuality()) {
			currentQuality=PanelDoubleBufferingSoftwear.getQuality();
			buffer = null;
		}
		g.drawString(this.getClass().getSimpleName(), 0, this.getHeight());
	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("NewTravel"));
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.setRightCmd("Find a path", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("lowerBar.RIGHT_CMD_ACTION_LISTENER");
			}
		});
		lowerBar.repaint();
	}

}
