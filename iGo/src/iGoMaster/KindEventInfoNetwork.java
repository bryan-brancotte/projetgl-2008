package iGoMaster;


public enum KindEventInfoNetwork {
	PROBLEM(1), SOLUTION(2), OTHER(3);

	protected int val;

	private KindEventInfoNetwork(int val) {
		this.val = val;
	}

	public int getValue() {
		return this.val;
	}

	/**
	 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
	 */
	public boolean equals(KindEventInfoNetwork ev) {
		return (this.getValue() == ev.getValue());
	}

}
