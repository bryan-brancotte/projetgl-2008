package iGoMaster;

/**
 * 
 * @author "iGo"
 * 
 */
public enum IHMGraphicQuality {

	/**
	 * Qualité de base, on dessine aussi vite que l'on peut
	 */
	AS_FAST_AS_WE_CAN(10),
	/**
	 * On autorise un antialiasing sur les composants qui pourraient en avoir besoin.
	 */
	TEXT_ANTI_ANTIALIASING(20),
	/**
	 * L'anti aliasing est appliqué sur les textes, mais aussi sur les dessins.
	 */
	FULL_ANTI_ANTIALIASING(30),
	/**
	 * La qualité est maximale. Anti Aliasing, dithering (tramage), ... tout ce qui peut être activé pour
	 * améliorer la qualité graphique est activé.
	 */
	HIGHER_QUALITY(40);

	protected byte val;

	private IHMGraphicQuality(int val) {
		this.val = (byte) val;
	}

	public int getValue() {
		return this.val;
	}

	/**
	 * Surcharge de equals pour s'assurer que la comparaison sera bien faite.
	 */
	public boolean equals(IHMGraphicQuality ev) {
		return (this.getValue() == ev.getValue());
	}
}
