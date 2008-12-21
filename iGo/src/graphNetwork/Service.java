package graphNetwork;

/**
 * 
 * @author iGo
 */
public class Service {

	private int id;// @uml.property name="id"
	private String name;

	public String getName() {
		return name;
	}

	private String shortDescription = "";// @uml.property name="shortDescription"

	/**
	 * constructeur d'un service
	 * 
	 * @param id
	 *            id du service a creer
	 * @param sortDescritption
	 *            description du service a creer
	 * @return void
	 */
	protected Service(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	protected Service(int id, String name, String shortDescription) {
		super();
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
	}

	/**
	 * retourne l'id du service
	 * 
	 * @return id du service
	 */
	public int getId() {
		return id;
	}

	/**
	 * retourne la description du service
	 * 
	 * @return description du service
	 * 
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * setter de l'id d'un service
	 * 
	 * @param id
	 *            id du service a modifier
	 * @return void
	 */
	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * setter de la description d'un service
	 * 
	 * @param shortDescription
	 *            la description a modifier
	 * @return void
	 */
	protected void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String toString() {
		if (shortDescription == null)
			return name + "(" + id + ")";
		return name + "(" + id + "):" + shortDescription;
	}

}
