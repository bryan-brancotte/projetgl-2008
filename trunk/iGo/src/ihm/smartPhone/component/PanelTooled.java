package ihm.smartPhone.component;

import ihm.smartPhone.tools.CodeExecutor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public abstract class PanelTooled extends PanelDoubleBufferingSoftwear {

	public void drawCheckBox(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter,
			CodeExecutor action) {

	}

	public void drawRadioBox(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter,
			CodeExecutor action) {
		g.drawOval(x, y, font.getSize(), font.getSize());

	}

	public void drawButton(Graphics g, int x, int y, String text, Font font, Color colorInside, Color colorLetter,
			CodeExecutor action) {

	}

	public void drawButton(Graphics g, int x, int y, ImageIcon image, Font font, CodeExecutor action) {

	}

	// TODO champs avec completion

	// TODO combo box ou pas?

	// fentre des choix comme chrome?

}
