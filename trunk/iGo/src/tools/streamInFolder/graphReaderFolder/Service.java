package tools.streamInFolder.graphReaderFolder;


public class Service {

	/**
	 * @uml.property  name="ID"
	 */
	private int id;

	/**
	 * Getter of the property <tt>ID</tt>
	 * @return  Returns the id.
	 * @uml.property  name="ID"
	 */
	public int getID() {
		return id;
	}

	/**
	 * Setter of the property <tt>ID</tt>
	 * @param ID  The id to set.
	 * @uml.property  name="ID"
	 */
	public void setID(int id) {
		this.id = id;
	}

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

}
