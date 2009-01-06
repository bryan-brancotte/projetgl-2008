package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class VoidPanel extends PanelState {

	private String title;

	private LinkedList<String[]> messages;

	public VoidPanel() {
		super(null, null, null);
	}

	public VoidPanel(IhmReceivingPanelState father) {
		super(father, null, null);
		this.messages = new LinkedList<String[]>();
	}

	public VoidPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar, String title) {
		super(father, upperBar, lowerBar);
		this.title = title;
		this.messages = new LinkedList<String[]>();
		// messages.add(new String[] { "*", "le", "message", "du", "haut", "le", "message", "du", "haut", "le",
		// "message",
		// "du", "haut", "le", "message", "du", "haut", "le", "message", "du", "haut" });
		// messages.add(new String[] { "*", "l'autre", "qui", "est", "en", "dessous" });
	}

	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		int yToDraw;
		int h;
		graphicsTunning(buffer);
		Image img = ImageLoader.getRessourcesImageIcone("time", getWidth() >> 3, getWidth() >> 3).getImage();
		if (messages.size() == 0) {
			g.drawImage(img, this.getWidth() - img.getWidth(null) >> 1, this.getHeight() - img.getHeight(null) >> 1,
					null);
			return;
		}
		String tmp;
		int i;
		g.drawImage(img, this.getWidth() - img.getWidth(null) >> 1, yToDraw = ((this.getHeight() >> 1)
				- img.getHeight(null) >> 1), null);
		g.setFont(father.getSizeAdapteur().getSmallFont());
		h = getHeightString("E", g);
		h += h >> 1;
		yToDraw += img.getHeight(null) + h;
		for (String[] message : messages) {
			i = 0;
			while (i < message.length) {
				tmp = "";
				while (i < message.length && getWidthString(tmp + message[i], g) < getWidth()) {
					tmp += " " + message[i];
					i++;
				}
				// if (i < message.length) {
				if (i == 1)
					tmp += " " + message[1];
				g.drawString(tmp, getWidth() - getWidthString(tmp, g) >> 1, yToDraw);
				yToDraw += h;
				// }
			}
			yToDraw += h;
		}

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
			lowerBar.setCenterIcone("button_cancel", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					father.cancel();
				}
			});
			lowerBar.repaint();
		}
	}

	public void addMessage(String message) {
		messages.add(message.split(" "));
	}
}
