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
	 * Retourne pour une clé donnée la valeur du paramètre. Utilisez mainenant getConfig
	 * 
	 * @param key
	 *            clé identifiant le paramètre
	 * @return la valeur du paramètre, ou null si le apramètre est inconnu.
	 */
	@Deprecated
	public String config(String key);

	/**
	 * Retourne pour une clé donnée la valeur du paramètre.
	 * 
	 * @param key
	 *            clé identifiant le paramètre
	 * @return la valeur du paramètre, ou null si le apramètre est inconnu.
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
	 * //TODO demander à Tony à quoi sert cette méthode...
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
