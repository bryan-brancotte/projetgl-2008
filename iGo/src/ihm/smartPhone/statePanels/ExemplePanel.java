package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExemplePanel extends PanelState {
	
	private static final long serialVersionUID = 1L;

	public ExemplePanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
		super(ihm, upperBar, lowerBar);
	}

	@Override
	public void paint(Graphics g) {
		if(currentQuality!=PanelDoubleBufferingSoftwear.getQuality()){
			graphicsTunning(this.buffer);
			currentQuality=PanelDoubleBufferingSoftwear.getQuality();
		}
		(g).drawString(this.getClass().getName(), 0, this.getHeight());
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
				father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
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
