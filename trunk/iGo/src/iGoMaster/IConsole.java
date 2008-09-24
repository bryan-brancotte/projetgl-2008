package iGoMaster;

/**
 * Interface permettant d'afficher du texte dans un console IConsole
 * 
 * @author Brancotte Bryan
 * @see IRequerantConsole
 */
public interface IConsole {

	/**
	 * Affiche une chaine de caractère dans la console sans faire de retour à la ligne
	 * @param msg
	 */
	public void print(String msg);

	/**
	 * Affiche un entier dans la console sans faire de retour à la ligne
	 * @param msg
	 */
	public void print(int msg);

	/**
	 * Affiche un float dans la console sans faire de retour à la ligne
	 * @param msg
	 */
	public void print(float msg);

	/**
	 * Affiche un Object dans la console sans faire de retour à la ligne
	 * @param msg
	 */
	public void print(Object msg);

	/**
	 * Affiche un String dans la console en revenant à la ligne
	 * @param msg
	 */
	public void println(String msg);

	/**
	 * Affiche un entier dans la console en revenant à la ligne
	 * @param msg
	 */
	public void println(int msg);

	/**
	 * Affiche un float dans la console en revenant à la ligne
	 * @param msg
	 */
	public void println(float msg);

	/**
	 * Affiche un Object dans la console en revenant à la ligne
	 * @param msg
	 */
	public void println(Object msg);

}
