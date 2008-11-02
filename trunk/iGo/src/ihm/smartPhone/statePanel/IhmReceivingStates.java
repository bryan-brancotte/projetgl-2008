package ihm.smartPhone.statePanel;


public enum IhmReceivingStates {
	UNKNOWN(-20),
	SPLASH_SCREEN(-10),
	MAIN_INTERFACE(0), 
	NEW_TRAVEL(10), 
	LOAD_TRAVEL(20), 
	SETTINGS(30),
	PREVISU_TRAVEL(40),
	EXPERIMENT_TRAVEL(50),
	GRAPHIC_MODE(1),
	TEXT_MODE(2),
	PREVISU_TRAVEL_GRAPHIC_MODE(PREVISU_TRAVEL.getValue()+GRAPHIC_MODE.getValue()),
	EXPERIMENT_TRAVEL_GRAPHIC_MODE(EXPERIMENT_TRAVEL.getValue()+GRAPHIC_MODE.getValue()),
	PREVISU_TRAVEL_TEXT_MODE(PREVISU_TRAVEL.getValue()+TEXT_MODE.getValue()),
	EXPERIMENT_TRAVEL_TEXT_MODE(EXPERIMENT_TRAVEL.getValue()+TEXT_MODE.getValue()),
	FAVORITES(6);

	protected int val;

	private IhmReceivingStates(int val) {
		this.val = val;
	}

	protected int getValue() {
		return this.val;
	}

	/**
	 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
	 */
	public boolean equals(IhmReceivingStates ev) {
		return (this.getValue() == ev.getValue());
	}
}
