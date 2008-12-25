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
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
		if (!enable)
			return;
		g.setFont(font);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, font.getSize(), font.getSize());
		if (clicked) {
			// g.drawImage(ImageLoader.getRessourcesImageIcone("button_ok", (int)(font.getSize() * 1.2),
			// (int)(font.getSize() * 1.2))
			// .getImage(), area.x - (int)(font.getSize() * 0.1), area.y - (int)(font.getSize() * 0.1), null);

			g.setColor(colorRound);
			g.fillRect(area.x + (font.getSize() >> 2), area.y + (font.getSize() >> 2), font.getSize() - 2
					* (font.getSize() >> 2) + 1, font.getSize() - 2 * (font.getSize() >> 2) + 1);
			g.setColor(colorLetter);
			g.drawRect(area.x + (font.getSize() >> 2), area.y + (font.getSize() >> 2), font.getSize() - 2
					* (font.getSize() >> 2), font.getSize() - 2 * (font.getSize() >> 2));
		}
		g.drawString(text, area.x + (font.getSize() >> 1) + font.getSize(), area.y + PanelDoubleBufferingSoftwear.getHeightString(text, g));

	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!enable)
			return null;
		if (text != null)
			this.text = text;
		if (text.compareTo("") == 0)
			area.setBounds(x, y, font.getSize(), font.getSize());
		else
			area.setBounds(x, y, PanelDoubleBufferingSoftwear.getWidthString(text, g, font) + (font.getSize() << 1), PanelDoubleBufferingSoftwear.getHeightString(
					text, g, font));
		return area;
	}
}
