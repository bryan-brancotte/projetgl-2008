package iGoMaster;

import java.util.Collection;

/**
 * Interface permetant d'utiliser le programme en plusieur langue
 * 
 * @author Brancotte Bryan
 * 
 */
public interface Language {

	/**
	 * Langue par defaut
	 */
	public static final String DEFAULT_LANGUAGE = "English";

	/**
	 * Pas encore de langue
	 */
	public static final String NOT_YET_A_LANGUAGE = "Loading...";

	/**
	 * Retourne pour une cl� donn�e la valeur dans la langue s�lectionn� de cette cl�
	 * 
	 * @param key
	 *            cl� de la chaine voulut dans la langue active
	 * @return la chaine dans la langue voulut
	 */
	public String lg(String key);

	/**
	 * D�finit la langue active. Si la langue n'est pas connut, la langue DEFAULT_LANGUAGE sera utilis�
	 * 
	 * @param Name
	 *            Nom de la langue
	 * @return true si on a bien changer la langue
	 */
	public boolean setLanguage(String Name);

	/**
	 * Retourne la langue actuellement active
	 * 
	 * @return langue active
	 */
	public String getLanguage();

	/**
	 * Retourne les langues disponible
	 * 
	 * @return collection des langue connus
	 */
	public Collection<String> getLanguages();
}
