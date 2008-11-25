package ihm.smartPhone.tools;

import java.awt.Color;

public enum iGoSmartPhoneSkin {
	/**
	 * 
	 */
	WHITE_WITH_LINE(new Color(115, 115, 115), new Color(141, 141, 141), new Color(251, 251, 251), true),
	/**
	 * thème bleu et noir avec ligne séparatrice
	 */
	BLUE_WITH_LINE(new Color(22, 78, 147), new Color(141, 141, 141), new Color(251, 251, 251), true),
	/**
	 * thème bleu et noir sans ligne séparatrice
	 */
	BLUE_WITHOUT_LINE(new Color(22, 78, 147), null, new Color(4, 4, 4), false),
	/**
	 * 
	 */
	PURPLE_LIGHT_WITH_LINE(new Color(144, 30, 196), new Color(141, 141, 141), new Color(248, 235, 255), true),
	/**
	 * 
	 */
	WHITE_WITHOUT_LINE(new Color(115, 115, 115), null, new Color(251, 251, 251), false),
	/**
	 * 
	 */
	BLACK_WITH_LINE(new Color(115, 115, 115), new Color(141, 141, 141), new Color(4, 4, 4), true),
	/**
	 * 
	 */
	BLACK_WITHOUT_LINE(new Color(115, 115, 115), null, new Color(4, 4, 4), false);

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

	public Color getColorLine() {
		return colorLine;
	}

	public Color getColorInside() {
		return colorInside;
	}

	public Color getColorSubAreaInside() {
		return colorSubAreaInside;
	}

	public boolean isDisplayLine() {
		return displayLine;
	}

	public Color getColorLetter() {
		return colorLetter;
	}

	private iGoSmartPhoneSkin(Color colorOutside, Color colorLine, Color colorInside, boolean displayLine) {
		id = iGoSmartPhoneSkin.cpt++;
		this.colorOutside = colorOutside;
		this.colorLine = colorLine;
		this.colorInside = colorInside;
		this.colorLetter = new Color(255 - colorInside.getRed(), 255 - colorInside.getGreen(), 255 - colorInside
				.getBlue());
		if (colorInside.getRed() < 128)
			this.colorSubAreaInside = new Color(255 - (int) (colorLetter.getRed() * 0.9), 255 - (int) (colorLetter
					.getGreen() * 0.9), 255 - (int) (colorLetter.getBlue() * 0.9));
		else
			this.colorSubAreaInside = new Color((int) (colorInside.getRed() * 0.9),
					(int) (colorInside.getGreen() * 0.9), (int) (colorInside.getBlue() * 0.9));
		this.displayLine = displayLine;
	}

	/**
	 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
	 */
	public boolean equals(iGoSmartPhoneSkin ev) {
		return this.id == ev.id;
	}
}
