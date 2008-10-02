package iGoMaster;

public enum EventInfoNetWorkStatus {
	NO_NEW_UPDATE(1), STOPPED(2), STARTED(3), OFFLINE(4);

	protected int val;

	private EventInfoNetWorkStatus(int val) {
		this.val = val;
	}

	public int getValue() {
		return this.val;
	}
}
