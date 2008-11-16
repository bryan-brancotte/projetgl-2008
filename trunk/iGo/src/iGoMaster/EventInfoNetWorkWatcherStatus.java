package iGoMaster;
/**
 *  
 * @author iGo
 */
public enum EventInfoNetWorkWatcherStatus {
	/**
	 * Aucune nouvelle mise a  jour detectee, ou alors la mise a  jour est toujours en cours de traitement.
	 */
	NO_NEW_UPDATE(1),
	/**
	 * Une nouvelle mise a jour est disponible.
	 */
	NEW_UPDATE(2),
	/**
	 * Le EventInfoNetWorkWatcher est demarre, il regarde afin de trouver d'autre mises a  jour.
	 */
	STARTED(3),
	/**
	 * Le EventInfoNetWorkWatcher est arrete, il ne regarde pas pour trouver d'autre mises a  jour.
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
