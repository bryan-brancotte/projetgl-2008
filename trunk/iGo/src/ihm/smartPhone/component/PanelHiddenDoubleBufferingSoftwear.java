package ihm.smartPhone.component;

import iGoMaster.IHMGraphicQuality;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;

public abstract class PanelHiddenDoubleBufferingSoftwear extends Panel {

	private Graphics buffer = null;

	private Image image = null;

	protected Panel me = this;

	/**
	 * La qualité du dessin
	 */
	private IHMGraphicQuality quality = IHMGraphicQuality.HIGHER_QUALITY;

	/*******************************************************************************************************************
	 * private IHMGraphicQuality quality = IHMGraphicQuality.AS_FAST_AS_WE_CAN;/
	 ******************************************************************************************************************/

	public IHMGraphicQuality getQuality() {
		return quality;
	}

	public void setQuality(IHMGraphicQuality quality) {
		this.quality = quality;
	}

	/**
	 * On redéfinit cette fonction car son fonctionnement normal provoque un sintillement. Il semblerai que cette
	 * fonction efface par défaut l'objet g avant d'appeller paint, d'om le sintillement.
	 * 
	 * @param g
	 *            l'objet représentant la zone dessinable d'un panel.
	 * 
	 */
	public void update(Graphics g) {
		System.out.println(this.getClass() + " : "
				+ ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())));
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			if (this.getQuality().getValue() >= IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue()) {
				buffer = (Graphics2D) image.getGraphics();
				((Graphics2D) this.buffer).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				if (this.getQuality().getValue() >= IHMGraphicQuality.FULL_ANTI_ANTIALIASING.getValue()) {
					((Graphics2D) this.buffer).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					if (this.getQuality().getValue() >= IHMGraphicQuality.HIGHER_QUALITY.getValue()) {
						((Graphics2D) this.buffer).setRenderingHint(RenderingHints.KEY_RENDERING,
								RenderingHints.VALUE_RENDER_QUALITY);
						((Graphics2D) this.buffer).setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
								RenderingHints.VALUE_COLOR_RENDER_QUALITY);
						((Graphics2D) this.buffer).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
								RenderingHints.VALUE_FRACTIONALMETRICS_ON);
						((Graphics2D) this.buffer).setRenderingHint(RenderingHints.KEY_DITHERING,
								RenderingHints.VALUE_DITHER_ENABLE);
					}
				}
			} else {
				buffer = image.getGraphics();
			}
			displayableAreaResized(buffer);
		}
		paint(buffer);
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Cette fonction est appellé lorsque l'on veux redessiner le composant, mais que se taille a varier par rapport à
	 * la dernière fois.
	 */
	public abstract void displayableAreaResized(Graphics g);

	/**
	 * Surdéfinition de la fonction de redessinement de l'objet.
	 */
	public abstract void paint(Graphics g);

	/**
	 * Retourne pour un chaine donnée sa hauteur dessinée.
	 * 
	 * @param s
	 *            la chaine
	 * @param g
	 *            le Graphics où elle sera dessiné
	 * @param f
	 *            la police utilisé
	 * @return la hauteur dessiné de la chaine
	 */
	protected int getHeigthString(String s, Graphics g, Font f) {
		// return (int) g.getFontMetrics(f).getStringBounds(s, g).getHeight(); /*size of string
		return (int) (f.getSize() * 0.85);/**/
	}

	/**
	 * Retourne pour un chaine donnée sa hauteur dessinée.
	 * 
	 * @param s
	 *            la chaine
	 * @param g
	 *            le Graphics où elle sera dessiné
	 * @return la hauteur dessiné de la chaine
	 */
	protected int getHeigthString(String s, Graphics g) {
		// return (int) g.getFontMetrics(f).getStringBounds(s, g).getHeight(); /*size of string
		return g.getFont().getSize();/**/
	}

	/**
	 * Retourne pour un chaine donnée sa longueur dessinée.
	 * 
	 * @param s
	 *            la chaine
	 * @param g
	 *            le Graphics où elle sera dessiné
	 * @param f
	 *            la police utilisé
	 * @return la longueur dessiné de la chaine
	 */
	protected int getWidthString(String s, Graphics g, Font f) {
		// return (int) (s.length() * f.getSize() * 0.45);/*
		return (int) g.getFontMetrics(f).getStringBounds(s, g).getWidth(); // size of string/**/

	}

	/**
	 * Retourne pour un chaine donnée sa longueur dessinée.
	 * 
	 * @param s
	 *            la chaine
	 * @param g
	 *            le Graphics où elle sera dessiné
	 * @return la longueur dessiné de la chaine
	 */
	protected int getWidthString(String s, Graphics g) {
		// return (int) (s.length() * f.getSize() * 0.45);/*
		return (int) g.getFontMetrics(g.getFont()).getStringBounds(s, g).getWidth(); // size of string/**/

	}

	protected String decomposeMinutesIntoHourMinutes(int minutes, String sHour, String sMinutes) {
		int i = (int) (minutes * 0.01667);
		if (i > 0)
			return i + " " + sHour + (minutes - i * 60) + " " + sMinutes;
		return minutes + " " + sMinutes;
	}

	/**
	 * Retourne l'image contenant le dessin du panel, si la classe implémentante s'en sert effectivement comme un panel
	 * avec double buffering softwear
	 * 
	 * @return l'image où est dessiner l'affichage du panel
	 */
	public Image getImage() {
		return image;
	}
}
