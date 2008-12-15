package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PTCheckBox extends PTComponent {

	protected boolean clicked = false;

	protected static Color colorRound = new Color(141, 207, 80);

	protected PTCheckBox(PanelTooled father, Rectangle area) {
		super(father, area);
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public void changeClicked() {
		this.clicked ^= true;
	}

	@Override
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return;
		g.setFont(font);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, font.getSize(), font.getSize());
		if (clicked) {
			// g.drawImage(ImageLoader.getRessourcesImageIcone("button_ok", (int)(font.getSize() * 1.2),
			// (int)(font.getSize() * 1.2))
			//					.getImage(), area.x - (int)(font.getSize() * 0.1), area.y - (int)(font.getSize() * 0.1), null);
			
			 g.setColor(colorRound);
			 g.fillRect(area.x + (font.getSize() >> 2), area.y + (font.getSize() >> 2), font.getSize() - 2
			 * (font.getSize() >> 2) + 1, font.getSize() - 2 * (font.getSize() >> 2) + 1);
			 g.setColor(colorLetter);
			 g.drawRect(area.x + (font.getSize() >> 2), area.y + (font.getSize() >> 2), font.getSize() - 2
			 * (font.getSize() >> 2), font.getSize() - 2 * (font.getSize() >> 2));
		}
		g.drawString(text, area.x + (font.getSize() >> 2) + font.getSize(), area.y + father.getHeigthString(text, g));

	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!enable)
			return null;
		if (text != null)
			this.text = text;
		g.setFont(font);
		area.setBounds(x, y, father.getWidthString(text, g) + (font.getSize() << 1), father.getHeigthString(text, g));
		return null;
	}

}
