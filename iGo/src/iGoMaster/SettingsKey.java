package iGoMaster;

public enum SettingsKey {
	/**
	 * Clef à laquel sera concaténé le nom du service
	 */
	SERVICES("SERVICES_"),
	/**
	 * Clef à laquel sera concaténé le moyen de transport
	 */
	TRAVEL_MODE("TRAVEL_MODE_"),
	/**
	 * Clef à laquel sera concaténé le moyen de transport
	 */
	TRAVEL_CRITERIA("TRAVEL_CRITERIA");

	/**
	 * Valeur entière représentant l'enum
	 */
	protected String value;

	public String getValue() {
		return value;
	}

	/**
	 * Constructeur
	 * 
	 * @param value
	 *            valeur du paramètre
	 */
	private SettingsKey(String value) {
		this.value = value;
	}

	public boolean equals(SettingsKey s) {
		return (s.value.compareTo(this.value) == 0);
	}

	public String toString() {
		return value;
	}
}
