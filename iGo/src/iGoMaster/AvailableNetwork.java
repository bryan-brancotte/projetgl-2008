package iGoMaster;

import java.io.File;

/**
 *  
 * @author iGo
 */
public interface AvailableNetwork {

	/**
	 * getter of the name of this avaible network
	 * @return the name of the avaible network
	 */
	public abstract String getName();

	/**
	 * getter of the descrition of this avaible network
	 * @return the description of the avaible network
	 */
	public abstract String getDescription();
		

	/**
	 * getter of the file
	 * @return the file which contains the network
	 */
	public abstract File getFichier();

}
