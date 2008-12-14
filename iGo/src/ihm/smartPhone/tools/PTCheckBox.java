package ihm.smartPhone.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PTCheckBox extends PTComponent {

	protected boolean clicked=false;

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
		this.clicked^= true;
	}

	@Override
	public Rectangle update(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter) {
		if(!enable)return null;
		// TODO update PTCheckBox
		return area;
	}

}