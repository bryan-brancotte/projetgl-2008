package ihm.smartPhone.statePanels;

import graphNetwork.KindRoute;
import graphNetwork.Service;
import ihm.smartPhone.component.iGoSmartPhoneSkin;
import ihm.smartPhone.tools.SizeAdapteur;

import java.util.Iterator;

public interface IhmReceivingPanelState {

	/**
	 * Retourne la charte graphique actuelle.
	 * 
	 * @return la charte graphique actuelle
	 */
	public iGoSmartPhoneSkin getSkin();

	/**
	 * retourne le size adapteur actuel. Il donne ainsi accès au différente taille étalon, ainsi qu'au police mise à ses
	 * tailles étalon.
	 * 
	 * @return
	 */
	public SizeAdapteur getSizeAdapteur();

	/**
	 * Demande l'arret de l'application. On laisse ensuite la classe implémentant IhmReceivingPanelState de tenir compte
	 * ou pas de notre demande.
	 */
	public void stop();

	/**
	 * Demande l'annulation de l'action en cours.
	 */
	public void cancel();

	/**
	 * Retourne pour une clé donnée la valeur dans la langue sélectionné de cette clé
	 * 
	 * @param key
	 *            clé de la chaine voulut dans la langue active
	 * @return la chaine dans la langue voulut
	 */
	public String lg(String key);

	/**
	 * Demande de passé à l'état passé en paramètre. l'application de cette demande est laissé à la discretion de la
	 * classe implémentante. Si la classe implémentante refuse, elle retourne false, sinon vrai.
	 * 
	 * @param actualState
	 *            le nouvelle état;
	 * @return true si on a bien appliqué le mutateur.
	 */
	public boolean setActualState(IhmReceivingStates actualState);

	/**
	 * Retourne l'état actuel
	 * 
	 * @return l'état actuel.
	 */
	public IhmReceivingStates getActualState();

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
	 * Retourne un itérateur décrivant l'ensemble des services présent sur le réseau
	 * 
	 * @return l'iterateur sur les services. Ce dernier pourra être vide, mais ne sera jamais à null.
	 */
	public Iterator<Service> getServices();

	/**
	 * Retourne un iterateur décrivant l'ensemble des types de route présent sur le réseau
	 * 
	 * @return l'iterateur sur les routes. Ce dernier pourra être vide, mais ne sera jamais à null.
	 */
	public Iterator<KindRoute> getKindRoutes();
}
