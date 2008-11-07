package ihm.smartPhone.statePanels;

public enum IhmReceivingStates {
	UNKNOWN(-20), SPLASH_SCREEN(-10), MAIN_INTERFACE(0), NEW_TRAVEL(10), LOAD_TRAVEL(20), SETTINGS(30), PREVISU_TRAVEL(
			40), EXPERIMENT_TRAVEL(50), GRAPHIC_MODE(1), ARRAY_MODE(2), PREVISU_TRAVEL_GRAPHIC_MODE(PREVISU_TRAVEL
			.getValue()
			+ GRAPHIC_MODE.getValue()), EXPERIMENT_TRAVEL_GRAPHIC_MODE(EXPERIMENT_TRAVEL.getValue()
			+ GRAPHIC_MODE.getValue()), PREVISU_TRAVEL_ARRAY_MODE(PREVISU_TRAVEL.getValue() + ARRAY_MODE.getValue()), EXPERIMENT_TRAVEL_ARRAY_MODE(
			EXPERIMENT_TRAVEL.getValue() + ARRAY_MODE.getValue()), FAVORITES(6);

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

	/**
	 * Retourne l'état qui est la combinaison de l'état courant et de celui passé ne paramètre si cela est possible. Si
	 * ces deux état ne peuvent fusionner on retourne l'état courant. L'addition est reflexive : si al fusion de a et b
	 * est possible alors : a.mergeState(b) == b.mergeState(a) == a+b
	 * 
	 * @param ev
	 *            l'état a ajouter à l'état déja existant
	 */
	public IhmReceivingStates mergeState(IhmReceivingStates ev) {
		IhmReceivingStates ret = ev.mergeStateNotRecursif(this);
		if (ret == ev)
			return this.mergeStateNotRecursif(ev);
		return ret;
	}

	/**
	 * Regarde si on peut fusion avec l'état courant l'état passé en paramètre. Cette opérateur d'addition n'est pas
	 * réflectif. Exemple : si a.mergeStateNotRecursif(b) == a+b alors b.mergeStateNotRecursif(a) = b.
	 * 
	 * @param ev
	 *            l'état a ajouter à l'état déja existant
	 */
	protected IhmReceivingStates mergeStateNotRecursif(IhmReceivingStates ev) {
		if ((ev == PREVISU_TRAVEL) && (this == GRAPHIC_MODE))
			return PREVISU_TRAVEL_GRAPHIC_MODE;
		if ((ev == PREVISU_TRAVEL) && (this == ARRAY_MODE))
			return PREVISU_TRAVEL_GRAPHIC_MODE;
		if ((ev == EXPERIMENT_TRAVEL) && (this == GRAPHIC_MODE))
			return EXPERIMENT_TRAVEL_GRAPHIC_MODE;
		if ((ev == EXPERIMENT_TRAVEL) && (this == ARRAY_MODE))
			return EXPERIMENT_TRAVEL_GRAPHIC_MODE;
		return this;
	}
}
