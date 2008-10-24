package iGoMaster;

public enum EventInfoNetWorkWatcherStatus {
	/**
	 * Aucune nouvelle mise à jours detecté, où la mise à jours est toujours en cours de traitement.
	 */
	NO_NEW_UPDATE(1),
	/**
	 * Une nouvelle mise à jours est disponible.
	 */
	NEW_UPDATE(2),
	/**
	 * Le EventInfoNetWorkWatcher est démarré, il regarde afin de trouver d'autre mise à jours.
	 */
	STARTED(3),
	/**
	 * Le EventInfoNetWorkWatcher est arreté, il ne regarde pas pour trouver d'autre mise à jours.
	 */
	STOPPED(4),
	/**
	 * Statu inconnu. Il traduit une complète ignorance de l'état actuel.
	 */
	UNKNOWN_STATUS(5);

	protected int val;

	private EventInfoNetWorkWatcherStatus(int val) {
		this.val = val;
	}

	public int getValue() {
		return this.val;
	}

	/**
	 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
	 */
	public boolean equals(EventInfoNetWorkWatcherStatus ev) {
		return (this.getValue() == ev.getValue());
	}
}
