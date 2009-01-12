package ihm.smartPhone.libPT;

import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class PTCollapsableArea extends PTComponent {

	/**
	 * En plus de terminer l'objet on demande la fin de ces composants
	 */
	@Override
	public void terminate() {
		for (PTComponent p : this.components)
			p.terminate();
		super.terminate();
	}

	protected LinkedList<PTComponent> components;

	protected boolean collapsed;

	protected boolean imageButtonAddLessState = false;

	protected static ImageIcon imageButtonAdd;

	protected static ImageIcon imageButtonLess;

	protected Rectangle buttonAddLess;

	protected boolean isOvered = false;

	public boolean isCollapsed() {
		return collapsed;
	}

	protected PTCollapsableArea(PanelTooled nvFather, Rectangle nvArea) {
		super(nvFather, nvArea);
		this.collapsed = false;
		this.components = new LinkedList<PTComponent>();
		this.buttonAddLess = new Rectangle();
		this.areaCodEx = nvFather.clickAndMoveWarningAndArray.addInteractiveArea(buttonAddLess,
				new CodeExecutor1P<PTCollapsableArea>(this) {
					@Override
					public void execute() {
						this.origine.changeCollapseState();
						this.origine.father.repaint();
					}
				});
		nvFather.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(java.awt.event.MouseEvent e) {
			};

			public void mouseMoved(java.awt.event.MouseEvent e) {
				if (isOvered ^ area.contains(e.getX(), e.getY())) {
					isOvered = !isOvered;
					father.repaint();
				}
			};
		});
	}

	/**
	 * Retourne la zone courante du dessin si ce dernier est actif, sinon on retourne null
	 * 
	 * @return la zone active si l'objet est actif, null dans le cas contraire
	 */
	public Rectangle getArea() {
		if (!this.isEnable())
			return null;
		return area;
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
	public void draw(Graphics g, Color colorInside, Color colorLetter) {
		if (!this.isEnable())
			return;
		g.setFont(lastFont);
		int heigthStr = PanelDoubleBufferingSoftwear.getHeightString(text, g);
		g.setColor(colorInside);
		g.fillRoundRect(area.x, area.y, area.width, area.height, 4, 4);
		g.setColor(colorLetter);
		g.drawRoundRect(area.x, area.y, area.width, area.height, 4, 4);
		if (isOvered)
			g.drawRect(area.x + 1, area.y + 1, area.width - 2, area.height - 2);
		g.drawString(text, area.x + (area.x >> 1), area.y + heigthStr + (area.x >> 1) - (area.x >> 2));
		ImageIcon imageButton;
		if (collapsed) {
			if (imageButtonAdd == null || imageButtonAdd.getIconHeight() != lastFont.getSize()) {
				imageButtonAdd = ImageLoader.getRessourcesImageIcone("button_add", lastFont.getSize(), lastFont
						.getSize());
			}
			imageButton = imageButtonAdd;
		} else {
			if (imageButtonLess == null || imageButtonLess.getIconHeight() != lastFont.getSize()) {
				imageButtonLess = ImageLoader.getRessourcesImageIcone("button_less", lastFont.getSize(), lastFont
						.getSize());
			}
			imageButton = imageButtonLess;
		}
		g.drawImage(imageButton.getImage(), area.width + area.x - (area.x >> 1) - lastFont.getSize(), area.y
				+ (heigthStr >> 2) - (heigthStr >> 3), null);
		// buttonAddLess.update();
	}

	@Override
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		if (!this.isEnable())
			return null;
		if (text != null)
			this.text = text;
		this.lastFont = font;
		int heigth = PanelDoubleBufferingSoftwear.getHeightString(text, g, font);
		int max = y + heigth;
		Rectangle areaComponent;
		for (PTComponent c : this.components)
			if ((areaComponent = c.getArea()) != null)
				if (areaComponent.y + areaComponent.height > max)
					max = areaComponent.y + areaComponent.height;
		area.setBounds(x, y, father.getWidth() - 3 * x, max - y + (heigth >> 1));
		buttonAddLess.setBounds(area.x, area.y, area.width, heigth << 1);
		return area;
	}

	public int getFirstOrdonneForComponents(Graphics g, int x, int y, String text, Font font) {
		int heigthStr = PanelDoubleBufferingSoftwear.getHeightString(text, g, font);
		return y + (heigthStr << 1);
	}
}
