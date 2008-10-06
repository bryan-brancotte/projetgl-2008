package iGoMaster;

import java.util.Collection;

/**
 * 
 * @author Brancotte Bryan
 * 
 */
public interface Configuration {

	/**
	 * Retourne le language d�finit dans la config
	 * 
	 * @return language
	 */
	public String getLanguage();

	/**
	 * Pour un param�tre donn�e, retourne sa valeur, ou ma chaine vide si ce param�tre est inconnu
	 * 
	 * @param key
	 *            Nom du param�tre
	 * @return valeur du param�tre ou chaine vide si la param�tre est inconnu
	 */
	public String getValue(String key);

	/**
	 * Pour un param�tre donn�e, d�finit sa valeur. Ce param�tre sera ensuite enregistrer dans le fichier de
	 * configuration
	 * 
	 * @param key
	 *            Nom du param�tre
	 * @param value
	 *            valeur du param�tre
	 */
	public void setValue(String key, String value);

	/**
	 * Obtient l'ensemble des param�tre d�finit dans le programme
	 * 
	 * @return Collection de param�tre
	 */
	public Collection<String> getParamsKey();

	/**
	 * Enregistre dans le fichier de configuration les param�tres
	 */
	public void save();
}
