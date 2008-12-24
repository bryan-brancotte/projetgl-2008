package ihm.smartPhone.libPT;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class PTScrollBar extends PTComponent {

	protected Rectangle areaCurseur;

	protected boolean draging;
	protected boolean showHand;
	protected boolean insideMe;
	protected int deroullement;
	protected int range;

	public int getDeroullement() {
		if (range == 0)
			deroullement = 0;
		return deroullement;
	}

	protected PTScrollBar(PanelTooled nvFather, Rectangle nvArea) {
		super(nvFather, nvArea);
		areaCurseur = new Rectangle();
		father.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				int delta = (area.width >> 2);
				int max = (area.height - (delta << 1));
				deroullement = e.getY() - area.y - delta;
				// deroullement € [0;max]
				deroullement = (deroullement > max) ? max : deroullement < 0 ? 0 : deroullement;
				// if (deroullement < 0)
				// deroullement = 0;
				// if (deroullement > max)
				// deroullement = max;
				deroullement = deroullement * range / max;
				father.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (showHand && areaCurseur.contains(e.getPoint())) {
					father.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					insideMe = true;
				} else {
					insideMe = false;
				}
			}
		});
		father.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// if(insideMe)
				if (e.getWheelRotation() > 0) {
					if (deroullement >= range)
						return;
					deroullement += 5;
					if (deroullement > range)
						deroullement = range;
					father.repaint();
				} else if (e.getWheelRotation() < 0) {
					if (deroullement <= 0)
						return;
					deroullement -= 5;
					if (deroullement < 0)
						deroullement = 0;
					father.repaint();
				}
			}
		});
	}

	/**
	 * draw n'a pas de sens avec ces paramètre, ne pas l'utiliser.
	 */
	@Override
	@Deprecated
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {

	}

	public void draw(Graphics g, int range, int value, Color colorInside, Color colorLetter) {
		if (!enable)
			return;
		g.drawRect(area.x, area.y, area.width, area.height);
		int delta = (area.width >> 2);
		// System.out.println("range : [0;" + range + "] value : " + value + " deroulement : " + deroullement);
		if (range <= 0) {
			this.range = 0;
			value = 0;
			showHand = false;
			areaCurseur
					.setBounds(area.x + delta, area.y + delta, area.width - (delta << 1), area.height - (delta << 1));
			g.setColor(colorInside);
		} else {
			this.range = range;
			if (value > range) {
				deroullement = range;
				value = range;
			} else if (value < 0) {
				deroullement = 0;
				value = 0;
			}
			showHand = true;
			// System.out.println("------> [0;" + range + "] value : " + value + " deroulement : " + deroullement);
			value = (int) (((float) value / (float) range) * ((float) area.height - (float) (delta << 1) - (float) (area.height >> 3)));
			// System.out.println("=====>> [0;" + range + "] value : " + value + " deroulement : " + deroullement);
			areaCurseur.setBounds(area.x + delta, area.y + delta + value, area.width - (delta << 1), area.height >> 3);
			g.setColor(colorRound);
		}
		g.fillRect(areaCurseur.x, areaCurseur.y, areaCurseur.width + 1, areaCurseur.height);
		g.setColor(colorLetter);
		g.drawRect(areaCurseur.x, areaCurseur.y, areaCurseur.width, areaCurseur.height);

	}

	/**
	 * prepareArea n'a pas de sens avec ces paramètre, ne pas l'utiliser.
	 */
	@Override
	@Deprecated
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		return null;
	}

	public Rectangle prepareArea(Graphics g, int x, int width) {
		if (!enable)
			return null;
		area.setBounds(x, 0, width, father.getHeight() - 1);
		return area;
	}

	/**
	 * update n'a pas de sens avec ces paramètre, ne pas l'utiliser.
	 */
	@Override
	@Deprecated
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		return null;
	}

	public Rectangle update(Graphics g, int x, int width, int range, int value, Color colorInside, Color colorLetter) {
		if (!enable)
			return null;
		prepareArea(g, x, width);
		draw(g, range, value, colorInside, colorLetter);
		return area;
	}

}
