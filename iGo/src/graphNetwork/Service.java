package graphNetwork;

public class Service implements ServiceReader {

	/**
	 * @uml.property name="shortDescription"
	 */
	private String shortDescription = "";

	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @uml.property name="id"
	 */
	private int id;

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 * @uml.property name="id"
	 */
	public int getId() {
		return id;
	}

	public Service(String shortDescription, int id) {
		super();
		this.shortDescription = shortDescription;
		this.id = id;
	}

	/**
	 * Setter of the property <tt>shortDescription</tt>
	 * 
	 * @param shortDescription
	 *            The shortDescription to set.
	 * @uml.property name="shortDescription"
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * 
	 * @param id
	 *            The id to set.
	 * @uml.property name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}

}
