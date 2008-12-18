package ihm.smartPhone.component;

import java.awt.Color;

public class iGoSmartPhoneSkin {
	/**
	 * 
	 */
	public static iGoSmartPhoneSkin WHITE_WITH_LINE = new iGoSmartPhoneSkin(new Color(115, 115, 115), new Color(141,
			141, 141), new Color(251, 251, 251), true);
	/**
	 * thème bleu et noir avec ligne séparatrice
	 */
	public static iGoSmartPhoneSkin BLUE_WITH_LINE = new iGoSmartPhoneSkin(new Color(22, 78, 147), new Color(141, 141,
			141), new Color(251, 251, 251), true);
	/**
	 * thème bleu et noir sans ligne séparatrice
	 */
	public static iGoSmartPhoneSkin BLUE_WITHOUT_LINE = new iGoSmartPhoneSkin(new Color(22, 78, 147), null, new Color(
			4, 4, 4), false);
	/**
	 * 
	 */
	public static iGoSmartPhoneSkin PURPLE_LIGHT_WITH_LINE = new iGoSmartPhoneSkin(new Color(144, 30, 196), new Color(
			141, 141, 141), new Color(248, 235, 255), true);
	/**
	 * 
	 */
	public static iGoSmartPhoneSkin ORANGE_WITH_LINE = new iGoSmartPhoneSkin(new Color(196, 119, 30), new Color(232,
			189, 99), new Color(248	, 245, 208),Color.black, true);
	/**
	 * 
	 */
	public static iGoSmartPhoneSkin WHITE_WITHOUT_LINE = new iGoSmartPhoneSkin(new Color(115, 115, 115), null,
			new Color(251, 251, 251), false);
	/**
	 * 
	 */
	public static iGoSmartPhoneSkin BLACK_WITH_LINE = new iGoSmartPhoneSkin(new Color(115, 115, 115), new Color(141,
			141, 141), new Color(4, 4, 4), true);
	/**
	 * 
	 */
	public static iGoSmartPhoneSkin BLACK_WITHOUT_LINE = new iGoSmartPhoneSkin(new Color(115, 115, 115), null,
			new Color(4, 4, 4), false);

	protected int id;
	protected static int cpt = 0;
	protected Color colorOutside;
	protected Color colorLine;
	protected Color colorInside;
	protected Color colorSubAreaInside;
	protected Color colorLetter;
	protected boolean displayLine;

	public Color getColorOutside() {
		return colorOutside;
	}

	/**
	 * Couleur pour les lignes spéparatrices
	 * 
	 * @return
	 */
	public Color getColorLine() {
		return colorLine;
	}

	/**
	 * Couleur de l'arrière plan
	 * 
	 * @return
	 */
	public Color getColorInside() {
		return colorInside;
	}

	/**
	 * Couleur moins intense que l'arrière plan, mais toujours dans la même optique
	 * 
	 * @return
	 */
	public Color getColorSubAreaInside() {
		return colorSubAreaInside;
	}

	/**
	 * Affiche t'on les lignes séparatrices?
	 * 
	 * @return
	 */
	public boolean isDisplayLine() {
		return displayLine;
	}

	/**
	 * La couleur des lettre dans l'ihm. C'est l'opposé RVB de la couleur de fond
	 * 
	 * @return
	 */
	public Color getColorLetter() {
		return colorLetter;
	}

	private iGoSmartPhoneSkin(Color colorOutside, Color colorLine, Color colorInside, Color colorLetter,
			boolean displayLine) {
		id = iGoSmartPhoneSkin.cpt++;
		this.colorOutside = colorOutside;
		this.colorLine = colorLine;
		this.colorInside = colorInside;
		this.colorLetter = colorLetter;
		if (colorInside.getRed() < 128)
			this.colorSubAreaInside = new Color(255 - (int) (colorLetter.getRed() * 0.9), 255 - (int) (colorLetter
					.getGreen() * 0.9), 255 - (int) (colorLetter.getBlue() * 0.9));
		else
			this.colorSubAreaInside = new Color((int) (colorInside.getRed() * 0.9),
					(int) (colorInside.getGreen() * 0.9), (int) (colorInside.getBlue() * 0.9));
		this.displayLine = displayLine;

	}

	private iGoSmartPhoneSkin(Color colorOutside, Color colorLine, Color colorInside, boolean displayLine) {
		this(colorOutside, colorLine, colorInside, new Color(255 - colorInside.getRed(), 255 - colorInside.getGreen(),
				255 - colorInside.getBlue()), displayLine);
	}

	/**
	 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
	 */
	public boolean equals(iGoSmartPhoneSkin ev) {
		return this.id == ev.id;
	}
}
