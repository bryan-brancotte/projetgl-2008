package iGoMaster;

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

}
