package libPT;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PTArea extends PTComponent {

	protected PTArea(PanelTooled father, Rectangle area) {
		super(father, area);
	}

	public void draw(Graphics g, Color colorInside, Color colorLetter) {
		if (!this.enable)
			return;
		if (colorLetter != null) {
			if (colorInside != null) {
				g.setColor(colorInside);
				g.fillRect(area.x, area.y, area.width, area.height);
			}
			g.setColor(colorLetter);
			g.drawRect(area.x, area.y, area.width, area.height);
		}
	}

	public Rectangle prepareArea(Graphics g, int x, int y, int height, int width) {
		if (!this.enable)
			return null;
		area.setBounds(x, y, width, height);
		return area;
	}

	public Rectangle update(Graphics g, int x, int y, int height, int width, Color colorInside, Color colorLetter) {
		if (!this.enable)
			return null;
		prepareArea(g, x, y, height, width);
		draw(g, colorInside, colorLetter);
		return area;
	}

	@Override
	@Deprecated
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		return null;
	}

	@Override
	@Deprecated
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
	}

	@Deprecated
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font, boolean horizontalCentered,
			boolean verticalCentered) {

		return null;
	}

	@Deprecated
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		return null;
	}

}
