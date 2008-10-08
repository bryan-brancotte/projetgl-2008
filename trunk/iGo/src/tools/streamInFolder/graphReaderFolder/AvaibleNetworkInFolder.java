package tools.streamInFolder.graphReaderFolder;

import iGoMaster.AvaibleNetwork;


public class AvaibleNetworkInFolder implements AvaibleNetwork {

	/**
	 * @uml.property  name="name"
	 */
	private String name = "";

	/**
	 * Getter of the property <tt>name</tt>
	 * @return  Returns the name.
	 * @uml.property  name="name"
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Setter of the property <tt>name</tt>
	 * @param name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @uml.property  name="description"
	 */
	private String description = "";

	/**
	 * Getter of the property <tt>description</tt>
	 * @return  Returns the description.
	 * @uml.property  name="description"
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * Setter of the property <tt>description</tt>
	 * @param description  The description to set.
	 * @uml.property  name="description"
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @uml.property  name="path"
	 */
	private String path = "";

	/**
	 * Getter of the property <tt>path</tt>
	 * @return  Returns the path.
	 * @uml.property  name="path"
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter of the property <tt>path</tt>
	 * @param path  The path to set.
	 * @uml.property  name="path"
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
