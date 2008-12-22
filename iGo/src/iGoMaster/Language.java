package iGoMaster;

import java.util.Collection;

/**
 * Interface permettant d'utiliser le programme en plusieurs langues
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
	 * Retourne pour une clé donnée la valeur dans la langue sélectionnée de cette clé
	 * 
	 * @param key
	 *            clé de la chaine voulue dans la langue active
	 * @return la chaine dans la langue voulue
	 */
	public String lg(String key);

	/**
	 * Définit la langue active. Si la langue n'est pas connue, la langue DEFAULT_LANGUAGE sera utilisée
	 * 
	 * @param Name
	 *            Nom de la langue
	 * @return true si on a bien changé la langue
	 */
	public boolean setLanguage(String Name);

	/**
	 * Retourne la langue actuellement active
	 * 
	 * @return la langue active
	 */
	public String getLanguage();

	/**
	 * Retourne les langues disponible
	 * 
	 * @return collection des langues connues
	 */
	public Collection<String> getLanguages();
}
