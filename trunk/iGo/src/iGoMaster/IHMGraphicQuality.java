package iGoMaster;

/**
 * 
 * @author "iGo"
 * 
 */
public enum IHMGraphicQuality {

	/**
	 * Qualité base, on dessine ausi vite que l'on peut
	 */
	AS_FAST_AS_WE_CAN(10),
	/**
	 * On s'autorise un antialiasing sur les composants qui pourrais en avoir besoin
	 */
	TEXT_ANTI_ANTIALIASING(20),
	/**
	 * l'anti aliasing est appliquer sur les texts, mais aussi les dessins.
	 */
	FULL_ANTI_ANTIALIASING(30),
	/**
	 * On met la qualité au maximume. Anti Aliasing, dithering (tramage), ... tous ce qui peut être acitvé pour
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
