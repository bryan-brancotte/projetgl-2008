package interpretationFichierXML;


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
	 * @uml.property  name="ShortDescription"
	 */
	private String shortDescription = "";

	/**
	 * Getter of the property <tt>ShortDescription</tt>
	 * @return  Returns the shortDescription.
	 * @uml.property  name="ShortDescription"
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * Setter of the property <tt>ShortDescription</tt>
	 * @param ShortDescription  The shortDescription to set.
	 * @uml.property  name="ShortDescription"
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

}
