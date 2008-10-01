package graphNetwork;

public class Service implements ServiceReader {

	/** 
	 * @uml.property name="shortDescription"
	 */
	private String shortDescription = "";

	/* (non-Javadoc)
	 * @see graphNetwork.ServiceReader#getShortDescription()
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/** 
	 * @uml.property name="id"
	 */
	private String id = "";

	/* (non-Javadoc)
	 * @see graphNetwork.ServiceReader#getId()
	 */
	public String getId() {
		return id;
	}

	public Service(String shortDescription, String id) {
		super();
		this.shortDescription = shortDescription;
		this.id = id;
	}

	/**
	 * Setter of the property <tt>shortDescription</tt>
	 * @param shortDescription  The shortDescription to set.
	 * @uml.property  name="shortDescription"
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * @param id  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

}
