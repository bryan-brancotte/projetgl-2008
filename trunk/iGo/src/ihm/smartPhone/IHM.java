package ihm.smartPhone;

/**
 * Interface décrivant les fonctionnalités de l'IHM. Tout IHM voulant prétendre à être utiliser par le master doit être
 * conforme à ce cahier des charges.
 * 
 * @author Brancotte Bryan
 * 
 */
public interface IHM {
	/**
	 * Démarre l'interface. On spécifie si on veut passer par l'interface de chargement, ou directement acceder à
	 * l'interface principale. On spécifie aussi le nombre d'étape dans le l'interface de chargement. Ce paramètre est
	 * sans importance si on a spécifié que l'on ne voulais pas passer par l'interface de chargement
	 * 
	 * @param bySplashScreen
	 *            true si on veut passer par l'écran de chargement.
	 * @param step
	 *            le nombre d'étape du chargement. Un nombre d'étape inferrieur ou égal à zero sera équivalent à
	 *            l'appelle de la méthode sans paramètre.
	 */
	public void start(boolean bySplashScreen,int step); 
	/**
	 * Démarre l'interface. On spécifie si on veut passer par l'interface de chargement, ou directement acceder à
	 * l'interface principale.
	 * 
	 * @param bySplashScreen
	 *            true si on veut passer par l'écran de chargement.
	 */
	public void start(boolean bySplashScreen); 

	/**
	 * Redéfinit le nombre d'étape effectué dans l'écran de chargement. Si l'interface de chargement n'est pas/plus
	 * affichée, l'appelle de la méthode sera null.
	 * 
	 * @param step
	 *            le nombre d'étape effectué. Si l'étape est inferrieur à 0, ou supérieur au maxStepInSlashScreen, on ne
	 *            changera pas l'étape actuelle.
	 */
	public void setStepInSplashScreen(int step);

	/**
	 * Retourne le nombre d'étape achevé, ou -1 s'il n'est pas définit.
	 * 
	 * @return le nombre d'étape.
	 */
	public int getStepInSplashScreen();

	/**
	 * Redéfinit le nombre maximume d'étape dans l'écran de chargement. Le nombre d'étape actuelle sera modifier si'il
	 * sort des nouvelle borne. Si l'interface de chargement n'est pas/plus affichée, l'appelle de la méthode sera null.
	 * 
	 * @param step
	 *            le nouveau maximume. Si la valeur est inferrieur à 0 l'interface passera dans un état équivalent à si
	 *            elle avait été lancé sans fournir le nombre d'étapes.
	 */
	public void setMaxStepInSplashScreen(int step);

	/**
	 * Retourne le nombre maximal d'étape actuelle, ou -1 s'il n'est pas définit.
	 * 
	 * @return le nombre max d'étape.
	 */
	public int getMaxStepInSplashScreen();

	/**
	 * Si l'ihm se trouve à l'écran de chargement alors on le termine, et passe à l'interface principale Si l'interface
	 * de chargement n'est pas/plus affichée, l'appelle de la méthode sera null.
	 */
	public void endSplashScreen();

	/**
	 * Affiche le message dans l'interface de chargement et compte un étape suplémentaire. Si l'interface de chargement
	 * n'est plus affiché, l'appelle de la méthode sera null.
	 * 
	 * @param message
	 *            le message à afficher.
	 */
	public void showMessageSplashScreen(String message);

	/**
	 * Arrete l'inteface sans demander de confirmation à l'utilisateur.
	 */
	public void stop();

}
