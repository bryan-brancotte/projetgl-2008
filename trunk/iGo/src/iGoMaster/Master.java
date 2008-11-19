package iGoMaster;

import java.util.Observable;

/**
 * 
 * @author iGo
 */
public interface Master {

	/**
	 * Retourne pour une clé donnée la valeur dans la langue sélectionné de cette clé
	 * 
	 * @param key
	 *            clé de la chaine voulut dans la langue active
	 * @return la chaine dans la langue voulut
	 */
	public String lg(String key);

	/**
	 * lance l'arrêt du logiciel. Il est probable que l'appelle de cette méthode oblige le master à lancer des
	 * procédures équivalents sur les acteurs qu'il peut arreté. pensez donc à protégé le master contre des appelles en
	 * boucle.
	 */
	public void stop();

	/**
	 * Retourne pour une clé donnée la valeur du paramètre.
	 * 
	 * @param key
	 *            clé identifiant le paramètre
	 * @return la valeur du paramètre, ou null si le apramètre est inconnu.
	 */
	public String config(String key);

		
		/**
		 */
		public abstract void update(Observable o, Object arg);
		

}
