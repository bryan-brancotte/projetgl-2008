package iGoMaster;

import graphNetwork.KindRoute;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.Service;
import graphNetwork.Station;

import java.util.Iterator;
import java.util.Observable;

/**
 * 
 * @author iGo
 */
public interface Master {

	/**
	 * Retourne pour une clé donnée la valeur dans la langue sélectionnée de cette clé
	 * 
	 * @param key
	 *            clé de la chaine voulue dans la langue active
	 * @return la chaine dans la langue voulue
	 */
	public String lg(String key);

	/**
	 * Lance l'arrêt du logiciel. Il est probable que l'appel de cette méthode oblige le master à lancer des
	 * procédures équivalentes sur les acteurs qu'il peut arreter. Protection du master contre les appels en
	 * boucle.
	 */
	public void stop();

	/**
	 * Retourne pour une clé donnée la valeur du paramètre. Utilisez maintenant getConfig
	 * 
	 * @param key
	 *            clé identifiant le paramètre
	 * @return la valeur du paramètre, ou null si le paramètre est inconnu.
	 */
	@Deprecated
	public String config(String key);

	/**
	 * Retourne pour une clé donnée la valeur du paramètre.
	 * 
	 * @param key
	 *            clé identifiant le paramètre
	 * @return la valeur du paramètre, ou null si le paramètre est inconnu.
	 */
	public String getConfig(String key);

	/**
	 * Retourne pour une clé donnée la valeur du paramètre.
	 * 
	 * @param key
	 *            clé identifiant le paramètre
	 * @param value
	 *            la valeur du paramètre
	 * @return true si le paramètre à bien été enregistré dans la configuration. false si le master refuse la
	 *         modification de cette clé.
	 */
	public boolean setConfig(String key, String value);

	/**
	 * L'arrivée d'un nouvel évènement ou de la terminaison de l'algorithme de calcul seront
	 * signalés au master par l'intermédiaire de cette méthode.
	 * 
	 * @param o
	 * 				l'observable qui va informer le master d'une modification
	 * @param arg
	 * 				si l'observable est de type EventInfoWatcher, l'argument sera null.
	 * 				si il est de type Algo, demander à tony.			
	 */
	public abstract void update(Observable o, Object arg);

	/**
	 * Demande au master de lui fournir un trajet respectant les contraintes passés en paramtères.
	 * 
	 * @return true si la demande a bien été enregistrer
	 */
	// TODO modéliser les contraintes
	public boolean askForATravel(PathInGraphBuilder pathInGraphBuidable);

	/**
	 * Retourne un itérateur décrivant l'ensemble des services présent sur le réseau
	 * 
	 * @return l'iterateur sur les services. Ce dernier pourra être vide, mais ne sera jamais à null.
	 */
	public Iterator<Service> getServices();

	/**
	 * Retourne un itérateur décrivant l'ensemble des services présent sur le réseau
	 * 
	 * @return l'iterateur sur les services. Ce dernier pourra être vide, mais ne sera jamais à null.
	 */
	public Iterator<Station> getStations();

	/**
	 * Retourne un iterateur décrivant l'ensemble des types de route présent sur le réseau
	 * 
	 * @return l'iterateur sur les routes. Ce dernier pourra être vide, mais ne sera jamais à null.
	 */
	public Iterator<KindRoute> getKindRoutes();

	/**
	 * Retourne un iterateur avec l'ensemble des langues disponible pour le logiciel dans leur langue respective
	 * 
	 * @return l'iterateur sur les langues.
	 */
	public Iterator<String> getLanguages();
}
