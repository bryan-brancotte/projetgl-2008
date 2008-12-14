package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class PTCollapsableArea extends PTComponent {

	protected LinkedList<PTComponent> components;

	protected String title;

	protected boolean collapseState = false;

	protected PTCollapsableArea(PanelTooled father, Rectangle area) {
		super(father, area);
		this.components = new LinkedList<PTComponent>();
		father.clickAndMoveWarningAndArray.addInteractiveArea(area, new CodeExecutor1P<PTCollapsableArea>(this) {
			@Override
			public void execute() {
				this.origine.changeCollapseState();
				this.origine.father.repaint();
			}
		});
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
		g.fillRect(x, y, father.getWidth() - 2 * x, y + (heigthStr >> 1)+(collapseState?0:20));
		g.setColor(colorLetter);
		g.drawRect(x, y, father.getWidth() - 2 * x, y + (heigthStr >> 1)+(collapseState?0:20));
		g.drawString(text, x + (heigthStr >> 1), y + heigthStr);
		ImageIcon image;
		if (collapseState)
			image = ImageLoader.getRessourcesImageIcone("button_add", font.getSize(), font.getSize());
		else
			image = ImageLoader.getRessourcesImageIcone("button_less", font.getSize(), font.getSize());
		g.drawImage(image.getImage(), father.getWidth() - 2 * x - (heigthStr >> 2), y + (heigthStr >> 2), null);
		area.setBounds(father.getWidth() - 2 * x - (heigthStr >> 2), y, image.getIconWidth(), image.getIconHeight());
		return null;
	}
}
