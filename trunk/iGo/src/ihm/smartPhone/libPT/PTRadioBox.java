package ihm.smartPhone.libPT;


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
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return;
		g.setFont(font);
		g.setColor(colorLetter);
		g.drawOval(area.x, area.y, font.getSize(), font.getSize());
		int delta = font.getSize() >> 2;
		if (clicked) {
			g.setColor(colorRound);
			g.fillOval(area.x + delta, area.y + delta, font.getSize() - (delta<<1) + 1, font.getSize() - (delta<<1) + 1);
			g.setColor(colorLetter);
			g.drawOval(area.x + delta, area.y + delta, font.getSize() - (delta << 1), font.getSize() - (delta << 1));
		}
		g.drawString(text, area.x + (font.getSize() >> 1) + font.getSize(), area.y
				+ PanelDoubleBufferingSoftwear.getHeightString(text, g));

	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!enable)
			return null;
		if (text != null)
			this.text = text;
		if (text.isEmpty())
			area.setBounds(x, y, font.getSize(), font.getSize());
		else
			area.setBounds(x, y, PanelDoubleBufferingSoftwear.getWidthString(text, g, font) + (font.getSize() << 1),
					PanelDoubleBufferingSoftwear.getHeightString(text, g, font));
		return area;
	}

}
