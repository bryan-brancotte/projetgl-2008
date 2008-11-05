package iGoMaster;
/**
 *  
 * @author iGo
 */
public interface Master {

	/**
	 * Retourne pour une cl� donn�e la valeur dans la langue s�lectionn� de cette cl�
	 * 
	 * @param key
	 *            cl� de la chaine voulut dans la langue active
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
