package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class PTCollapsableArea extends PTComponent {

	protected LinkedList<PTComponent> components;

	protected boolean collapsed;

	protected boolean imageButtonAddLessState = false;

	protected ImageIcon imageButtonAddLess;

	protected PTButton buttonAddLess;

	public boolean isCollapsed() {
		return collapsed;
	}

	protected PTCollapsableArea(PanelTooled father, Rectangle area) {
		super(father, area);
		collapsed = false;
		this.components = new LinkedList<PTComponent>();
		buttonAddLess = father.makeButton(new CodeExecutor1P<PTCollapsableArea>(this) {
			@Override
			public void execute() {
				this.origine.changeCollapseState();
				this.origine.father.repaint();
			}
		});
	}

	public void addComponent(PTComponent ptc) {
		if (ptc == null)
			return;
		ptc.setEnable(!collapsed);
		this.components.add(ptc);
	}

	public void clearComponent() {
		this.components.clear();
	}

	public String getTitle() {
		return text;
	}

	public void setTitle(String text) {
		this.text = text;
	}

	public void changeCollapseState() {
		for (PTComponent c : components)
			c.setEnable(collapsed);
		collapsed ^= true;
	}

	@Override
	public void draw(Graphics g, Font font, Color colorInside, Color colorLetter) {
		if (!this.isEnable())
			return;
		g.setFont(font);
		int heigthStr = father.getHeigthString(text, g);
		g.setColor(colorInside);
		g.fillRect(area.x, area.y, area.width, area.height);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, area.width, area.height);
		g.drawString(text, area.x + (area.x >> 1), area.y + heigthStr);
		if (imageButtonAddLess == null || imageButtonAddLessState != collapsed
				|| imageButtonAddLess.getIconHeight() != font.getSize()) {
			imageButtonAddLessState = collapsed;
			if (collapsed)
				imageButtonAddLess = ImageLoader.getRessourcesImageIcone("button_add", font.getSize(), font.getSize());
			else
				imageButtonAddLess = ImageLoader.getRessourcesImageIcone("button_less", font.getSize(), font.getSize());
		}
		buttonAddLess.update(g, area.width + area.x - (area.x >> 1) - font.getSize(), area.y + (heigthStr >> 2)
				- (heigthStr >> 3), imageButtonAddLess);
	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!this.isEnable())
			return null;
		if (text != null)
			this.text = text;
		int heigth = father.getHeigthString(text, g, font);
		int max = y + heigth;
		Rectangle areaComponent;
		for (PTComponent c : this.components)
			if ((areaComponent = c.getArea()) != null)
				if (areaComponent.y + areaComponent.height > max)
					max = areaComponent.y + areaComponent.height;
		area.setBounds(x, y, father.getWidth() - 2 * x, max - y + (heigth>>1));
		return area;
	}

	public int getFirstOrdonneForComponents(Graphics g, int x, int y, String text, Font font) {
		int heigthStr = father.getHeigthString(text, g, font);
		return y + (heigthStr << 1);
	}
}
