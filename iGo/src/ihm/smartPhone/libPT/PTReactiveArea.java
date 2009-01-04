package ihm.smartPhone.libPT;

import ihm.smartPhone.libPT.MouseListenerClickAndMoveInArea.AreaAndCodeExecutor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PTReactiveArea extends PTComponent {

	/**
	 * Constructeur par défaut d'un PTButton, on se doit de fournir un objet Rectangle qui représentera la zone où il
	 * sera dessiné. De part se nature c'est aussi la zone où qui sera cliquable.
	 * 
	 * @param father
	 *            le panel qui héberge le PTButton
	 * @param area
	 *            sa zone
	 */
	protected PTReactiveArea(PanelTooled father, AreaAndCodeExecutor area) {
		super(father, area);
	}

	@Override
	@Deprecated
	public void draw(Graphics g, Color colorInside, Color colorLetter) {
	}

	@Override
	@Deprecated
	public Rectangle prepareArea(Graphics g, int x, int y, String text, Font font) {
		return null;
	}

	@Override
	@Deprecated
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		return null;
	}

	public Rectangle update(int x, int y, int width, int height) {
		area.setBounds(x, y, width, height);
		return area;
	}
}
