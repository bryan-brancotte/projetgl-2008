package ihm.smartPhone.libPT;

import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.ImageLoader;

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

	protected static ImageIcon imageButtonAdd;

	protected static ImageIcon imageButtonLess;

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
		int heigthStr = PanelDoubleBufferingSoftwear.getHeightString(text, g);
		g.setColor(colorInside);
		g.fillRect(area.x, area.y, area.width, area.height);
		g.setColor(colorLetter);
		g.drawRect(area.x, area.y, area.width, area.height);
		g.drawString(text, area.x + (area.x >> 1), area.y + heigthStr);
		ImageIcon imageButton;
		if (collapsed) {
			if (imageButtonAdd == null || imageButtonAdd.getIconHeight() != font.getSize()) {
				imageButtonAdd = ImageLoader.getRessourcesImageIcone("button_add", font.getSize(), font.getSize());
			}
			imageButton = imageButtonAdd;
		} else {
			if (imageButtonLess == null || imageButtonLess.getIconHeight() != font.getSize()) {
				imageButtonLess = ImageLoader.getRessourcesImageIcone("button_less", font.getSize(), font.getSize());
			}
			imageButton = imageButtonLess;
		}
		buttonAddLess.update(g, area.width + area.x - (area.x >> 1) - font.getSize(), area.y + (heigthStr >> 2)
				- (heigthStr >> 3), imageButton);
	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!this.isEnable())
			return null;
		if (text != null)
			this.text = text;
		int heigth = PanelDoubleBufferingSoftwear.getHeightString(text, g, font);
		int max = y + heigth;
		Rectangle areaComponent;
		for (PTComponent c : this.components)
			if ((areaComponent = c.getArea()) != null)
				if (areaComponent.y + areaComponent.height > max)
					max = areaComponent.y + areaComponent.height;
		area.setBounds(x, y, father.getWidth() - 3 * x, max - y + (heigth >> 1));
		return area;
	}

	public int getFirstOrdonneForComponents(Graphics g, int x, int y, String text, Font font) {
		int heigthStr = PanelDoubleBufferingSoftwear.getHeightString(text, g, font);
		return y + (heigthStr << 1);
	}
}
