package ihm.smartPhone.statePanels;

import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;

public class VoidPanel extends PanelState { 

	public VoidPanel() {
		super(null,null,null);
	} 

	public VoidPanel(IhmReceivingPanelState father) {
		super(father, null, null);
	}

	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
		Image img = ImageLoader.getRessourcesImageIcone("time", 32, 32).getImage();
		g.drawImage(img, this.getWidth() / 2 - img.getWidth(null) / 2, this.getHeight() / 2 - img.getHeight(null) / 2,
				null);
	}

	@Override
	public void giveControle() {
		// TODO Auto-generated method stub
		
	}
}
