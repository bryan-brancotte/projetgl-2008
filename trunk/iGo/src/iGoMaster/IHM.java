package iGoMaster;

import graphNetwork.KindRoute;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;

/**
 * Interface décrivant les fonctionnalités de l'IHM. Une ihm ne peut être utilisée par le master que si elle répond à ce
 * cahier des charges.
 * 
 * @author iGo
 * 
 */
public interface IHM {
	/**
	 * Démarre l'interface. On spécifie si on veut passer par l'interface de chargement ou directement acceder à
	 * l'interface principale. On spécifie aussi le nombre d'étapes dans l'interface de chargement. Ce paramètre est
	 * sans importance si on a spécifié que l'on ne voulait pas passer par l'interface de chargement.
	 * 
	 * @param bySplashScreen
	 *            true si on veut passer par l'écran de chargement.
	 * @param step
	 *            Le nombre d'étapes du chargement. Un nombre d'étapes inférieur ou égal à zero sera équivalent à
	 *            l'appel de la méthode sans paramètres.
	 */
	public void start(boolean bySplashScreen, int step);

	/**
	 * Démarre l'interface. On spécifie si on veut passer par l'interface de chargement, ou directement acceder à
	 * l'interface principale.
	 * 
	 * @param bySplashScreen
	 *            true si on veut passer par l'écran de chargement.
	 */
	public void start(boolean bySplashScreen);

	/**
	 * Redéfinit le nombre d'étapes effectuées dans l'écran de chargement. Si l'interface de chargement n'est pas/plus
	 * affichée, l'appel de la méthode sera null.
	 * 
	 * @param step
	 *            Le nombre d'étapes effectuées. Si numéro de l'étape est inférieur à 0 ou supérieur au
	 *            maxStepInSlashScreen, l'étape courante ne sera pas modifiée
	 */
	public void setStepInSplashScreen(int step);

	/**
	 * Retourne le nombre d'étapes achevées ou -1 s'il n'est pas défini.
	 * 
	 * @return le nombre d'étapes.
	 */
	public int getStepInSplashScreen();

	/**
	 * Redéfinit le nombre maximum d'étapes dans l'écran de chargement. Le nombre d'étapes courant sera modifié s'il va
	 * au delà des nouvelles bornes. Si l'interface de chargement n'est pas ou plus affichée, l'appel de la méthode sera
	 * null.
	 * 
	 * @param step
	 *            Le nouveau maximum. Si la valeur du pas est inférieure à 0 l'interface passera dans l'état qu'on
	 *            aurait pu obtenir en lancant l'interface sans lui fournir le nombre d'étapes.
	 */
	public void setMaxStepInSplashScreen(int step);

	/**
	 * Retourne le nombre maximal d'étapes courant ou -1 s'il n'est pas défini.
	 * 
	 * @return le nombre maximal d'étapes
	 */
	public int getMaxStepInSplashScreen();

	/**
	 * Si l'ihm se trouve à l'étape de l'écran de chargement, on remplace ce dernier par l'interface principale. Si
	 * l'interface de chargement n'est pas ou plus affichée, la méthode n'aura pas d'effet.
	 */
	public void endSplashScreen();

	/**
	 * Affiche le message dans l'interface de chargement et compte un étape supplémentaire. Si l'interface de chargement
	 * n'est plus affiché, l'appel sera sans effet.
	 * 
	 * @param message
	 *            le message à afficher.
	 */
	public void showMessageSplashScreen(String message);

	/**
	 * Met fin à la visualisation sans demander de confirmation à l'utilisateur.
	 */
	public void stop();

	/**
	 * Fournit à l'IHM le builder de contraintes contenant le trajet demandé.
	 * 
	 * @param pathInGraphConstraintBuilder
	 *            le builder de contraintes
	 * @param algoKindOfException
	 *            Si l'algo a rencontré une erreur, énum qui précise le type d'erreur. Sinon null.
	 * @return true si l'IHM s'attendait a cette appel de fonction, false dans le cas contraire.
	 */
	public boolean returnPathAsked(PathInGraphConstraintBuilder pathInGraphConstraintBuilder, AlgoKindOfException algoKindOfException);

	/**
	 * Ne pas utiliser cette méthode, elle ne comporte pas assez d'information, utiliser
	 * infoPathAsked(AlgoKindOfException algoKindOfException, Service service, Route route, Station station, KindRoute
	 * kindRoute). </br><bold>Cette fonction à été vidé de sa substance, son appel est donc sans effet</bold>
	 */
	@Deprecated
	public boolean infoPathAsked(AlgoKindOfException algoKindOfException, Service service);

	/**
	 * Informe l'utilisateur des relaxation de contrainte
	 * 
	 * @param AlgoKindOfException
	 *            Les relaxation sucessive
	 * @param service
	 *            le service ayant un rappport avec le algoKindOfInformation ou null
	 * @param route
	 *            la route ayant un rappport avec le algoKindOfInformation ou null
	 * @param station
	 *            la station ayant un rappport avec le algoKindOfInformation ou null
	 * @param kindRoute
	 *            le type de route ayant un rappport avec le algoKindOfInformation ou null
	 * @return true si l'IHM s'attendait a cette appel de fonction, false dans le cas contraire.
	 */
	public boolean infoPathAsked(AlgoKindOfException algoKindOfException, Service service, Route route,
			Station station, KindRoute kindRoute);

	/**
	 * Indique à l'IHM qu'elle doit mettre à jour son graphe suite à l'arrivée de nouveaux évènements.
	 * 
	 * @return vrai si la mise à jour s'est déroulée correctement.
	 */
	public boolean updateNetwork();
}
