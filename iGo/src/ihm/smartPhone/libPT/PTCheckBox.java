package ihm.smartPhone.libPT;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PTCheckBox extends PTComponent {

	public static final boolean DEFAULT_CLICKED = false;

	protected boolean clicked = DEFAULT_CLICKED;

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
	public void draw(Graphics g, Color colorInside, Color colorLetter) {
		if (!enable)
			return;
		g.setFont(lastFont);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, lastFont.getSize(), lastFont.getSize());
		if (clicked) {
			g.setColor(colorRound);
			g.fillRect(area.x + (lastFont.getSize() >> 2), area.y + (lastFont.getSize() >> 2), lastFont.getSize() - 2
					* (lastFont.getSize() >> 2) + 1, lastFont.getSize() - 2 * (lastFont.getSize() >> 2) + 1);
			g.setColor(colorLetter);
			g.drawRect(area.x + (lastFont.getSize() >> 2), area.y + (lastFont.getSize() >> 2), lastFont.getSize() - 2
					* (lastFont.getSize() >> 2), lastFont.getSize() - 2 * (lastFont.getSize() >> 2));
		}
		g.drawString(text, area.x + (lastFont.getSize() >> 1) + lastFont.getSize(), area.y + PanelDoubleBufferingSoftwear.getHeightString(text, g));

	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!enable)
			return null;
		if (text != null)
			this.text = text;
		lastFont=font;
		if (text.compareTo("") == 0)
			area.setBounds(x, y, font.getSize(), font.getSize());
		else
			area.setBounds(x, y, PanelDoubleBufferingSoftwear.getWidthString(text, g, font) + (font.getSize() << 1), PanelDoubleBufferingSoftwear.getHeightString(
					text, g, font));
		return area;
	}
}
