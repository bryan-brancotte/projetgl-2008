package streamInFolder.graphReaderFolder;


public class Service {

	/** 
	 * Getter of the property <tt>ShortDescription</tt>
	 * @return  Returns the shortDescription.
	 * @uml.property  name="shortDescription"
	 */
	public String getShortDescription() {
		return description;
	}

	/** 
	 * Setter of the property <tt>ShortDescription</tt>
	 * @param ShortDescription  The shortDescription to set.
	 * @uml.property  name="shortDescription"
	 */
	public void setShortDescription(String shortDescription) {
		description = shortDescription;
	}

	/**
	 * @uml.property  name="shortDescription"
	 */
	private String description = "";
	/**
	 * @uml.property  name="id"
	 */
	private int id1;

	/** 
	 * Getter of the property <tt>ID</tt>
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id1;
	}

	/** 
	 * Setter of the property <tt>ID</tt>
	 * @param ID  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		id1 = id;
	}

}
