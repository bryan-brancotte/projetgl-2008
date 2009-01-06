package ihm.smartPhone.libPT;

import iGoMaster.IHMGraphicQuality;
import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.util.Vector;

public abstract class PanelDoubleBufferingSoftwear extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4387520409545843805L;

	/**
	 * La qualité du dessin
	 */
	private static IHMGraphicQuality quality = IGoIhmSmartPhone.defaultQualityForIhm;

	protected Graphics buffer = null;

	protected IHMGraphicQuality currentQuality = quality;

	protected Image image = null;

	protected Panel me = this;

	/**
	 * Accesseur static de la qualité des instance de pannel double bufferisé
	 * 
	 * @return
	 */
	public static IHMGraphicQuality getQuality() {
		return PanelDoubleBufferingSoftwear.quality;
	}

	/**
	 * Mutateur permetant de redéfinit la qualité graphic des panels, et forcé le redessinement du panel courant.
	 * 
	 * @return
	 */
	public void setQuality(IHMGraphicQuality quality) {
		if (PanelDoubleBufferingSoftwear.quality == quality)
			return;
		PanelDoubleBufferingSoftwear.setStaticQuality(quality);
		this.buffer = null;
	}

	/**
	 * Mutateur permetant de redéfinit la qualité graphic des panels.
	 * 
	 * @return
	 */
	public static void setStaticQuality(IHMGraphicQuality quality) {
		ImageLoader.setFastLoadingOfImages(quality == IHMGraphicQuality.AS_FAST_AS_WE_CAN);
		PanelDoubleBufferingSoftwear.quality = quality;
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
		paint(g);
	}

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
	protected static int getHeightString(String s, Graphics g, Font f) {
		// System.out.println(g.getFontMetrics(f).getStringBounds(s, g).getHeight() + " VS " + f.getSize() * 0.85 +
		// " VS "
		// + f.getSize() * 0.95 + " VS " + f.getSize());
		// return (int) g.getFontMetrics(f).getStringBounds(s, g).getHeight(); /*size of string
		return (int) (f.getSize() * 0.9);/**/
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
	protected static int getHeightString(String s, Graphics g) {
		// return (int) g.getFontMetrics().getStringBounds(s, g).getHeight(); /*size of string
		return (int) (g.getFont().getSize() * 0.95);/**/
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
	protected static int getWidthString(String s, Graphics g, Font f) {
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
	protected static int getWidthString(String s, Graphics g) {
		// return (int) (s.length() * f.getSize() * 0.45);
		return (int) g.getFontMetrics().getStringBounds(s, g).getWidth();

	}

	protected String[] decoupeChaine(String s, Graphics g, int widthMax) {
		if (getWidthString(s, g) <= widthMax)
			return new String[] { s };
		if (s.length() == 0)
			return new String[0];

		String[] cut = s.split(" ");
		String tmp;
		Vector<String> retV = new Vector<String>();
		int i = 0;

		while (i < cut.length) {
			tmp = "";
			while (i < cut.length && getWidthString(tmp + " " + cut[i], g) < widthMax) {
				tmp += " " + cut[i++];
			}
			if (tmp.compareTo("") == 0) {
				tmp = cut[i].substring(0, cut[i].length() >> 1);
				cut[i] = cut[i].substring(cut[i].length() >> 1);
			}
			retV.add(tmp);
		}
		return retV.toArray(new String[0]);
	}

	/**
	 * 
	 * @param minutes
	 * @param sHour
	 * @param sMinutes
	 * @return
	 */
	protected static String decomposeMinutesIntoHourMinutes(int minutes, String sHour, String sMinutes) {
		int i = (int) (minutes * 0.01667);
		if (i > 0)
			return i + sHour + (minutes - i * 60) + " " + sMinutes;
		return minutes + " " + sMinutes;
	}

	/**
	 * 
	 * @param minutes
	 * @param sHour
	 * @param sMinutes
	 * @return
	 */
	protected static String decomposeMinutesIntoHourMinutes(int minutes, String sHour, String sMinutes,
			String sMiniMinutes) {
		int i = (int) (minutes * 0.01667);
		if (i > 0)
			return i + sHour + (minutes - i * 60) + sMiniMinutes;
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

	public static void graphicsTunning(Graphics buffer) {
		if (buffer == null)
			return;
		graphicsTunning((Graphics2D) buffer);
	}

	public static void graphicsTunning(Graphics2D buffer) {
		if (buffer == null)
			return;
		if (PanelDoubleBufferingSoftwear.getQuality().getValue() >= IHMGraphicQuality.TEXT_ANTI_ANTIALIASING.getValue()) {
			buffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			if (PanelDoubleBufferingSoftwear.getQuality().getValue() >= IHMGraphicQuality.FULL_ANTI_ANTIALIASING
					.getValue()) {
				buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (PanelDoubleBufferingSoftwear.getQuality().getValue() >= IHMGraphicQuality.HIGHER_QUALITY.getValue()) {
					buffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					buffer.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
							RenderingHints.VALUE_COLOR_RENDER_QUALITY);
					buffer.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
							RenderingHints.VALUE_FRACTIONALMETRICS_ON);
					buffer.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
				}
			}
		} else {
			/** Désactivation de l'anti-aliasing */
			buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			buffer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			/** Demande de rendu rapide */
			buffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			buffer.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
			buffer.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			buffer.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		}
	}
}
