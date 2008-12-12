package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PTRadioBox extends PTComponent {

	protected boolean clicked = false;

	/**
	 * Constructeur de PTRadioBox, toutes classe en hors du package devrait appellé la méthode makeRadioBox de
	 * PanelTooled pour créé un PTRadioBox
	 * 
	 * @param father
	 *            le panel où est le PTRadioBox
	 * @param area
	 *            la zone qui représentera le PTRadioBox
	 */
	protected PTRadioBox(PanelTooled father, Rectangle area) {
		super(father, area);
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	@Override
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return null;
		// update PTRadioBox
		return area;
	}

}
