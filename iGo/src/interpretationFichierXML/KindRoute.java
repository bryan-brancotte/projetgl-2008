package interpretationFichierXML;


public enum KindRoute {

	Regional_Rail(1), Metro(2), Trolley(3);

	/** L'attribut qui contient la valeur associ� � l'enum */
	private final int value;

	/** Le constructeur qui associe une valeur � l'enum */
	private KindRoute(int value) {
		this.value = value;
	}

	/** La m�thode accesseur qui renvoit la valeur de l'enum */
	public int getValue() {
		return this.value;
	}
};