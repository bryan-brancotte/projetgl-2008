package iGoMaster;

/**
 * 
 * @author iGo
 */
public enum EventInfoNetWorkWatcherStatus {
	/**
	 * Aucune nouvelle mise à jour detectee, ou alors la mise à jour est toujours en cours de traitement.Cet état
	 * implique que l'on le veilleur est démarré.
	 */
	NO_NEW_UPDATE(1),
	/**
	 * Une nouvelle mise à jour est disponible.Cet état implique que l'on le veilleur est démarré.
	 */
	NEW_UPDATE(2),
	/**
	 * Aucune nouvelle mise à jour detectee, ou alors la mise à jour est toujours en cours de traitement.Cet état
	 * implique que l'on le veilleur est arrété.
	 */
	NO_NEW_UPDATE_STOPPED(6),
	/**
	 * Une nouvelle mise à jour est disponible.Cet état implique que l'on le veilleur est arrété.
	 */
	NEW_UPDATE_STOPPED(7),
	/**
	 * Le EventInfoNetWorkWatcher est demarre, il regarde afin de trouver d'autre mises à jour.
	 */
	STARTED(3),
	/**
	 * Le EventInfoNetWorkWatcher est arrete, il ne regarde pas pour trouver d'autre mises à jour.
	 */
	STOPPED(4),
	/**
	 * Status inconnu
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
	 * Surcharge de equals pour s'assurer que la comparaison sera bien faite.
	 */
	public boolean equals(EventInfoNetWorkWatcherStatus ev) {
		return (this.getValue() == ev.getValue());
	}
}
