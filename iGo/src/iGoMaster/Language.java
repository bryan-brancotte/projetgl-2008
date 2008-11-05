package iGoMaster;

import java.util.Collection;

/**
 * Interface permetant d'utiliser le programme en plusieurs langues
 * 
 * @author iGo
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
	 * Retourne pour une clé donnée la valeur dans la langue sélectionné de cette clé
	 * 
	 * @param key
	 *            clé de la chaine voulut dans la langue active
	 * @return la chaine dans la langue voulut
	 */
	public String lg(String key);

	/**
	 * Définit la langue active. Si la langue n'est pas connut, la langue DEFAULT_LANGUAGE sera utilisé
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
