package streamInFolder.graphReaderFolder;

import iGoMaster.AvailableNetwork;

import java.io.File;

public class AvailableNetworkInFolder implements AvailableNetwork {

	private File fichier;
	
	public AvailableNetworkInFolder(String n, String p) {
		name = n;
		path = p;
		fichier = new File(path);
	}
	
	/**
	 * Nom du reseau
	 */
	private String name = "";

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Setter of the property <tt>name</tt>
	 * 
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Description du Reseau
	 * Non utile tant qu'il n'y a pas de description dans le fichier XML
	 */
	private String description = "";

	/**
	 * Getter of the property <tt>description</tt>
	 * 
	 * @return Returns the description.
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * Setter of the property <tt>description</tt>
	 * 
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Path du fichier contenant le reseau
	 */
	private String path = "";

	/**
	 * Getter of the property <tt>path</tt>
	 * 
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter of the property <tt>path</tt>
	 * 
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public File getFichier() {
		return fichier;
	}

	public void setFichier(File fichier) {
		this.fichier = fichier;
	}
}
