package main;

import iGoMaster.IGoMaster;

/**
 * Classe principale chargée de lancer le master
 * 
 * @author elodie
 * 
 */
public class Main {

	/**
	 * Chemin du dossier qui va contenir les réseaux disponibles
	 */
	final static String NETWORK = System.getProperty("user.dir")+"\\ressources\\xml";

	/**
	 * Chemin du fichier xml qui va recenser les mises à jour du réseau
	 */
	final static String EVENTS = System.getProperty("user.dir")+"\\ressources\\xml\\TravelAltertGL2008.xml";

	/**
	 * Main
	 */
	public static void main(String[] args) {
		new IGoMaster(NETWORK, EVENTS);
	}

}
