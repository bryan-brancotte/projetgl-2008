package iGoMaster;

public enum SettingsValue {
	/**
	 * Valeur que prend un paramètre s'il est desactivé
	 */
	DISABLE(0),
	/**
	 * Valeur que prend un paramètre s'il est activé
	 */
	ENABLE(1),
	/**
	 * Valeur que prend un paramètre pour signifier qu'il faut l'utiliser au moins un fois. Typiquement on s'en servira
	 * pour les services.
	 */
	Idle(0),
	/**
	 * Valeur que prend un paramètre pour signifier qu'il faut l'utiliser à chaque fois . Typiquement on s'en servira
	 * pour les services
	 */
	Once(1),
	/**
	 * Valeur que prend un paramètre pour signifier qu'il nous importe peu que l'on s'en serve ou pas. Typiquement on
	 * s'en servira pour les services.
	 */
	Always(2),
	/**
	 * Utilisez Algo.CriteriousForLowerPath.COST
	 */
	@Deprecated
	CHEAPER(0),
	/**
	 * Utilisez Algo.CriteriousForLowerPath.TIME
	 */
	@Deprecated
	FASTER(1),
	/**
	 * Utilisez Algo.CriteriousForLowerPath.CHANGE
	 */
	@Deprecated
	FEWER_CHANGES(2);

	/**
	 * Valeur entière représentant l'enum
	 */
	protected int value;

	public int getValue() {
		return value;
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
