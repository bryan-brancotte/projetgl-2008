package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class PTCollapsableArea extends PTComponent {

	protected LinkedList<PTComponent> components;

	protected String title;

	protected boolean collapseState = false;

	protected PTCollapsableArea(PanelTooled father, Rectangle area) {
		super(father, area);
		this.components = new LinkedList<PTComponent>();
	}

	public void addComponent(PTComponent ptc) {
		if (ptc != null)
			this.components.add(ptc);
	}

	public void clearComponent() {
		this.components.clear();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void changeCollapseState() {
		collapseState ^= true;
		for (PTComponent c : components)
			c.setEnable(collapseState);
	}

	@Override
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		if (!this.isEnable())
			return null;
		int heigthStr = father.getHeigthString(text, g);
		g.setFont(font);
		g.setColor(colorInside);
		g.fillRect(x, y, father.getWidth() - 2 * x, father.getHeigthString(text, g));
		g.setColor(colorLetter);
		g.drawRect(x, y, father.getWidth() - 2 * x, father.getHeigthString(text, g));
		g.drawString(text, x, y+2*heigthStr);
		return null;
	}

}
