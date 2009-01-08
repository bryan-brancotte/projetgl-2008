package streamInFolder.graphReaderFolder;

import iGoMaster.AvailableNetwork;

import java.io.File;


/**
 * Objet permettant de stocker un réseau disponible
 * 
 * @author iGo
 */
public class AvailableNetworkInFolder implements AvailableNetwork {

	/**
	 * Fichier contenant le reseau
	 */
	private File fichier;
	
	/**
	 * Nom du reseau
	 */
	private String name = "";
	
	/**
	 * Path du fichier contenant le reseau
	 */
	private String path = "";
	
	/**
	 * Description du Reseau
	 * Non utile tant qu'il n'y a pas de description dans le fichier XML
	 */
	private String description = "";
	
	/**
	 * Constructeur de AvailableNetworkInFolder
	 * @param n Nom du fichier
	 * @param p Path du fichier
	 */
	public AvailableNetworkInFolder(String n, String p) {
		name = n;
		path = p;
		fichier = new File(path);
	}

	/**
	 * @see AvailableNetwork#getName()
	 */ 
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @see AvailableNetwork#getDescription()
	 */ 
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @see AvailableNetwork#getPath()
	 */ 
	@Override
	public String getPath() {
		return path;
	}

	/**
	 * @see AvailableNetwork#getFichier()
	 */ 
	@Override
	public File getFichier() {
		return fichier;
	}
}
