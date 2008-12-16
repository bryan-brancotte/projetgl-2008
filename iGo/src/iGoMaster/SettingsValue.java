package iGoMaster;

public enum SettingsValue {
	/**
	 * Valeur que prend un paramètre s'il est deseactivé
	 */
	DISABLE(0),
	/**
	 * Valeur que prend un paramètre s'il est activé
	 */
	ENABLE(1),
	/**
	 * Valeur que prend un paramètre pour signifier qu'il faut l'utiliser au moins un fois. Typeiquement on s'en servira
	 * pour les services.
	 */
	Idle(0),
	/**
	 * Valeur que prend un paramètre pour signifier qu'il faut l'utiliser chaque fois . Typeiquement on s'en servira
	 * pour les services
	 */
	Once(1),
	/**
	 * Valeur que prend un paramètre pour signifier qu'il nous importe peut que l'on s'en serve ou pas. Typeiquement on
	 * s'en servira pour les services.
	 */
	Always(2),
	/**
	 * Valeur indiquant un résoluation de l'algorithme pour le coput le plus faible
	 */
	CHEAPER(0),
	/**
	 * Valeur indiquant un résoluation de l'algorithme pour le plus rapide
	 */
	FASTER(1),
	/**
	 * Valeur indiquant un résoluation de l'algorithme pour le moins de changement
	 */
	FEWER_CHANGES(2);

	/**
	 * Valeur entière représentant l'enum
	 */
	protected int value;

	public int getValue() {
		return value;
	}

	public String getStringValue() {
		return ""+value;
	}

	/**
	 * Constructeur
	 * 
	 * @param value
	 *            valeur du paramètre
	 */
	private SettingsValue(int value) {
		this.value = value;
	}
}
