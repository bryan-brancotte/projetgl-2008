package iGoMaster;
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
	 * lance l'arrÃªt du logiciel. Il est probable que l'appelle de cette mÃ©thode oblige le master Ã  lancer des
	 * procÃ©dures Ã©quivalents sur les acteurs qu'il peut arretÃ©. pensez donc Ã  protÃ©gÃ© le master contre des appelles en
	 * boucle.
	 */
	public void stop();

}
