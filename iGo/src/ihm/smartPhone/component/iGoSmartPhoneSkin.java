package ihm.smartPhone.component;

import java.awt.Color;

public enum iGoSmartPhoneSkin {
	/**
	 * 
	 */
	WHITE(

	new Color(115, 115, 115),

	new Color(141, 141, 141),

	new Color(251, 251, 251)),
	/**
	 * 
	 */
	PURPLE_LIGHT(

	new Color(144, 30, 196),

	new Color(141, 141, 141),

	new Color(248, 235, 255)),
	/**
	 *
	 */
	BLUE(

	new Color(22, 78, 147),

	new Color(141, 141, 141),

	new Color(251, 251, 251)),
	/**
	 * 
	 */
	PINK(

	new Color(220, 41, 181),

	new Color(232, 99, 232),

	new Color(245, 220, 239)),
	/**
	 * 
	 */
	ORANGE(

	new Color(196, 119, 30),

	new Color(232, 189, 99),

	new Color(248, 245, 208),

	Color.black),
	/**
	 * 
	 */
	BLACK(

	new Color(115, 115, 115),

	new Color(141, 141, 141),

	new Color(4, 4, 4));

	protected Color colorOutside;
	protected Color colorLine;
	protected Color colorInside;
	protected Color colorSubAreaInside;
	protected Color colorLetter;

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
	 * La couleur des lettre dans l'ihm. C'est l'opposé RVB de la couleur de fond
	 * 
	 * @return
	 */
	public Color getColorLetter() {
		return colorLetter;
	}

	/**
	 * Constructeur de l'enum skin
	 * 
	 * @param colorOutside
	 *            couleur à laquelle début de la dégradé (depuis l'exterrieur)
	 * @param colorLine
	 *            couleur des ligne séparatrice
	 * @param colorInside
	 *            couleur à laquelle se finit de la dégradé (à l'interrieur)
	 * @param colorLetter
	 *            couleur de la police
	 * @param displayLine
	 *            affiche t'on les lignes
	 */
	private iGoSmartPhoneSkin(Color colorOutside, Color colorLine, Color colorInside, Color colorLetter) {
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
	}

	/**
	 * Constructeur de l'enum skin
	 * 
	 * @param colorOutside
	 *            couleur à laquelle début de la dégradé (depuis l'exterrieur)
	 * @param colorLine
	 *            couleur des ligne séparatrice
	 * @param colorInside
	 *            couleur à laquelle se finit de la dégradé (à l'interrieur)
	 * @param displayLine
	 *            affiche t'on les lignes
	 */
	private iGoSmartPhoneSkin(Color colorOutside, Color colorLine, Color colorInside) {
		this(colorOutside, colorLine, colorInside, new Color(255 - colorInside.getRed(), 255 - colorInside.getGreen(),
				255 - colorInside.getBlue()));
	}
}
