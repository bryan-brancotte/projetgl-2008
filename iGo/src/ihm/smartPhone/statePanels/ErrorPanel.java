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
		graphicsTunning(g);
		g.setFont(father.getSizeAdapteur().getIntermediateFont());
		Image img = ImageLoader.getRessourcesImageIcone("button_cancel", getWidth() >> 3, getWidth() >> 3).getImage();
		g.drawImage(img, this.getWidth() - img.getWidth(null) >> 1, (this.getHeight() >> 1) - img.getHeight(null) >> 1,
				null);
		if (message == null || message.compareTo("") == 0)
			return;
		String[] cut = decoupeChaine(message, g, (int) (getWidth() * 0.8));
		int heigth = (this.getHeight() >> 2) + img.getHeight(null);
		int heigthTmp = getHeightString(message, g);
		for (String tmp : cut) {
			g.drawString(tmp, getWidth() - getWidthString(tmp, g) >> 1, heigth + heigthTmp);
			heigth += heigthTmp << 1;
		}

		// String tmp;
		// String[] cut = message.split(" ");
		// int heigth = (this.getHeight() >> 2) + img.getHeight(null);
		// int heigthTmp;
		// int mw = (int) (getWidth() * 0.8);
		// int i = 0;
		// g.setFont(father.getSizeAdapteur().getIntermediateFont());
		//
		// while (i < cut.length) {
		// tmp = "";
		// while (i < cut.length && getWidthString(tmp + " " + cut[i], g) < mw) {
		// tmp += " " + cut[i++];
		// }
		// if (tmp.compareTo("") == 0) {
		// tmp = cut[i].substring(0, cut[i].length() >> 1);
		// cut[i] = cut[i].substring(cut[i].length() >> 1);
		// }
		// g.drawString(tmp, getWidth() - getWidthString(tmp, g) >> 1, heigth
		// + (heigthTmp = getHeightString(message, g)));
		// heigth += heigthTmp << 1;
		// }
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
