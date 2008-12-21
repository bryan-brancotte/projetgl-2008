package iGoMaster;

import java.io.File;

/**
 * @author iGo
 */
public interface AvailableNetwork {

	/**
	 * Accesseur qui permet d'obtenir le nom du réseau disponible
	 * @return le nom du réseau
	 */
	public abstract String getName();

	/**
	 * Accesseur qui permet d'obtenir une description du réseau disponible
	 * @return la description du réseau
	 */
	public abstract String getDescription();
		

	/**
	 * Accesseur qui permet d'accéder au fichier qui contient le réseau
	 * @return le fichier qui contient le réseau
	 */
	public abstract File getFichier();

}
