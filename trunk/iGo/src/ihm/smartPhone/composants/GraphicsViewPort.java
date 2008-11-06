package ihm.smartPhone.composants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

public class GraphicsViewPort {

	/**
	 * Le buffer de l'image
	 */
	protected Graphics buffer = null;

	/**
	 * L'image en elle même
	 */
	protected Image image = null;

	/**
	 * l'abscisse
	 */
	protected int x;

	/**
	 * L'ordonnée
	 */
	protected int y;

	/**
	 * La hauteur
	 */
	protected int heigth;

	/**
	 * La largueur
	 */
	protected int width;

	/**
	 * La hauteur du dessin
	 */
	protected int heigthImage;

	/**
	 * La largueur du dessin
	 */
	protected int widthImage;

	/**
	 * Si un nouvel affchage de l'image est nécessaire
	 */
	protected boolean neededRepaint;
	/**
	 * L'échelle de l'image
	 */
	protected float scallImg = 0.25F;

	public int getHeigthImage() {
		return heigthImage;
	}

	public int getWidthImage() {
		return widthImage;
	}

	public float getScallImg() {
		return scallImg;
	}

	public GraphicsViewPort(int width, int heigth) {
		super();
		if (width == 0)
			width = 10;
		if (heigth == 0)
			heigth = 10;
		this.heigth = heigth;
		this.width = width;
	}

	/**
	 * retourne un boolean indiquant si un réaffochage est nécessaire.
	 * 
	 * @return true si on doit de nouveau déssiner l'image.
	 */
	public boolean isNeededRepaint() {
		return neededRepaint;
	}

	public void scallImg(float coef) {
		scallImg *= coef;
		neededRepaint = true;
		// TODO
	}

	public Graphics getBuffer() {
		return buffer;
	}

	public Image getImage() {
		return image;
	}

	public void move(int dx, int dy) {
		x += dx;
		y += dy;
		neededRepaint = true;

		// TODO
		int tmp;

		tmp = (getHeight() - heigthImage);
		if (y < tmp) {
			y = tmp;
		}
		tmp = (getWidth() - widthImage);
		if (x < tmp) {
			x = tmp;
		}
		if (x > 0) {
			x = 0;
		}
		if (y > 0) {
			y = 0;
		}

	}

	public void setSizeViewPort(int width, int heigth) {
		neededRepaint = true;
		this.heigth = heigth;
		this.width = width;
		this.image = createImage(this.width, this.heigth);
		this.buffer = this.image.getGraphics();
	}

	public void setSizeImage(int width, int heigth) {
		neededRepaint = true;
		this.heigthImage = heigth;
		this.widthImage = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public int getWidth() {
		return width;
	}

	public void fillPolygon(Polygon polygon) {
		buffer.fillPolygon(polygon);
	}

	public void setColor(Color color) {
		buffer.setColor(color);
	}

	public void drawPolygon(Polygon polygon) {
		buffer.drawPolygon(polygon);
	}

	public void setFont(Font font) {
		buffer.setFont(font);
	}

	public Font getFont() {
		return buffer.getFont();
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		buffer.drawLine(x1, y1, x2, y2);
	}

	public void fillRect(int x, int y, int width, int height) {
		buffer.drawRect(x, y, width, height);
	}

	public void drawRect(int x, int y, int width, int height) {
		buffer.fillRect(x, y, width, height);
	}

	public void drawString(String str, int x, int y) {
		buffer.drawString(str, x, y);
	}

	public void fillOval(int x, int y, int width, int height) {
		buffer.fillOval(x, y, width, height);
	}

	public void drawOval(int x, int y, int width, int height) {
		buffer.drawOval(x, y, width, height);
	}
}