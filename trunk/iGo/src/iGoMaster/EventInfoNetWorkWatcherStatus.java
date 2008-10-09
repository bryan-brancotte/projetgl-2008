package iGoMaster;

public enum EventInfoNetWorkWatcherStatus {
	NO_NEW_UPDATE(1), STOPPED(2), STARTED(3), OFFLINE(4), NEW_UPDATE(5);

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
