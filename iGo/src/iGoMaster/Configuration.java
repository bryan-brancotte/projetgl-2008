package iGoMaster;

import java.util.Collection;

/**  
 * @author iGo
 */
public interface Configuration {

	/**
	 * Retourne le language défini dans le fichier de configuration
	 * 
	 * @return language
	 */
	public String getLanguage();

	/**
	 * Pour un paramètre donné, retourne sa valeur, ou une chaine vide si ce paramètre est inconnu
	 * 
	 * @param key
	 *            	Nom du paramètre
	 * @return 
	 * 				Valeur du paramètre ou chaine vide si la paramètre est inconnu
	 */
	public String getValue(String key);

	/**
	 * Pour un paramètre donné, définit sa valeur. Ce paramètre sera ensuite enregistré dans le fichier de
	 * configuration
	 * 
	 * @param key
	 *            Nom du paramètre
	 * @param value
	 *            Valeur du paramètre
	 */
	public void setValue(String key, String value);

	/**
	 * Retourne l'ensemble des paramètres définis dans l'application.
	 * 
	 * @return Collection de paramètres
	 */
	public Collection<String> getParamsKey();

	/**
	 * Enregistre dans le fichier de configuration les paramètres
	 */
	public void save();
}
