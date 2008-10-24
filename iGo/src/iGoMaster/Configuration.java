package iGoMaster;

import java.util.Collection;

/**
 * 
 * @author Brancotte Bryan
 * 
 */
public interface Configuration {

	/**
	 * Retourne le language définit dans la config
	 * 
	 * @return language
	 */
	public String getLanguage();

	/**
	 * Pour un paramètre donnée, retourne sa valeur, ou ma chaine vide si ce paramètre est inconnu
	 * 
	 * @param key
	 *            Nom du paramètre
	 * @return valeur du paramètre ou chaine vide si la paramètre est inconnu
	 */
	public String getValue(String key);

	/**
	 * Pour un paramètre donnée, définit sa valeur. Ce paramètre sera ensuite enregistrer dans le fichier de
	 * configuration
	 * 
	 * @param key
	 *            Nom du paramètre
	 * @param value
	 *            valeur du paramètre
	 */
	public void setValue(String key, String value);

	/**
	 * Obtient l'ensemble des paramètre définit dans le programme
	 * 
	 * @return Collection de paramètre
	 */
	public Collection<String> getParamsKey();

	/**
	 * Enregistre dans le fichier de configuration les paramètres
	 */
	public void save();
}
