package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorPanel extends PanelState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2498019488605606802L;
	/**
	 * le message à afficher
	 */
	protected String message;
	/**
	 * le titre de la frame
	 */
	protected String title;

	public ErrorPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar) {
		super(father, upperBar, lowerBar);
		this.message = "";
		this.title = "";
	}

	public ErrorPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar, String title) {
		super(father, upperBar, lowerBar);
		this.title = title;
		this.message = "";
	}

	public ErrorPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar, String message, String title) {
		super(father, upperBar, lowerBar);
		this.message = message;
		this.title = title;
	}

	@Override
	public void giveControle() {
		if (upperBar != null) {
			upperBar.clearMessage();
			upperBar.setMainTitle(title);
			upperBar.repaint();
		}
		if (lowerBar != null) {
			lowerBar.clearMessage();
			lowerBar.setCenterIcone("home", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
				}
			});
			lowerBar.repaint();
		}
	}

	@Override
	public void paint(Graphics g) {
		graphicsTunning(buffer);
		Image img = ImageLoader.getRessourcesImageIcone("time", getWidth() >> 3, getWidth() >> 3).getImage();
		g.drawImage(img, this.getWidth() - img.getWidth(null) >> 1, this.getHeight() - img.getHeight(null) >> 1, null);
	}

}
