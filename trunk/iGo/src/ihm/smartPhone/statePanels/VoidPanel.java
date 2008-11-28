package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VoidPanel extends PanelState {

	public VoidPanel() {
		super(null, null, null);
	}

	public VoidPanel(IhmReceivingPanelState father) {
		super(father, null, null);
	}

	public VoidPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar) {
		super(father, upperBar, lowerBar);
	}

	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		Image img = ImageLoader.getRessourcesImageIcone("time", getWidth()>>3, getWidth()>>3).getImage();
		g.drawImage(img, this.getWidth() - img.getWidth(null) >> 1, this.getHeight() - img.getHeight(null) >> 1, null);
	}

	@Override
	public void giveControle() {
		if (upperBar != null) {
			upperBar.clearMessage();
			upperBar.repaint();
		}
		if (lowerBar != null) {
			lowerBar.clearMessage();
			lowerBar.setCenterIcone("button_cancel", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					father.cancel();
				}
			});
			lowerBar.repaint();
		}
	}
}
