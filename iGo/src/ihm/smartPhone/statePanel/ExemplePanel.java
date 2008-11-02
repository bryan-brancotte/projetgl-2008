package ihm.smartPhone.statePanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

public class ExemplePanel extends PanelState {
	
	private static final long serialVersionUID = 1L;

	public ExemplePanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
		super(ihm, upperBar, lowerBar);
	}

	@Override
	public void paint(Graphics g) {
		g.drawString(this.getClass().getName(), 0, this.getHeight());
	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setCenterIcone("iGo");
		upperBar.setLeftCmd("Lost", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("upperBar.LEFT_CMD_ACTION_LISTENER");
			}
		});
		upperBar.setRightCmd("Next", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("upperBar.RIGHT_CMD_ACTION_LISTENER");
			}
		});
		upperBar.setLeftSubTitle(father.lg("iGoVersion"), FontSizeKind.SMALL);
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setActualState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.setLeftCmd("No idea", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("lowerBar.LEFT_CMD_ACTION_LISTENER");
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
