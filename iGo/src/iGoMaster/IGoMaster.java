package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.KindRoute;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.StationNotOnRoadException;

import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;
import iGoMaster.exception.ImpossibleStartingException;
import iGoMaster.exception.NoNetworkException;
import iGoMaster.exception.NoRouteForStationException;
import iGoMaster.exception.ServiceNotAccessibleException;
import iGoMaster.exception.StationNotAccessibleException;
import iGoMaster.exception.VoidPathException;

import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.tools.ExecMultiThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import pathsAndFavorites.RecentsAndFavoritesPathsInGraphReceiver;

import streamInFolder.event.EventInfoNetworkWatcherInFolder;
import streamInFolder.graphCostReaderHardWritten.GraphNetworkCostReceiverHardWritten;
import streamInFolder.graphReaderFolder.AvailableNetworkInFolder;
import streamInFolder.graphReaderFolder.GraphNetworkReceiverFolder;
import streamInFolder.recentsAndFavoritesGraphs.RecentsAndFavoritesPathsInGraphReaderInFolder;

import xmlFeature.ConfigurationXML;
import xmlFeature.LanguageXML;

import algorithm.Dijkstra;
import algorithm.exception.NonValidDestinationException;
import algorithm.exception.NonValidOriginException;

/**
 * Enumération : "NetworkOk" si le réseau a bien été récupéré. "NetworkDoesntExist" si aucun réseau n'a été trouvé.
 * "ConstructionFailed" si la transformation du réseau en graphNetwork a échoué. "ReceptionFailed" si le réseau a été
 * mal réceptionné.
 */
enum StateNetwork {
	NetworkOk, NetworkDoesntExist, ConstructionFailed, ReceptionFailed
};

/**
 * Classe qui se charge de la coordination de tous les modules du logiciel
 * 
 * @author iGo
 */
public class IGoMaster implements Master, Observer {

	private IHM ihm;
	private Algo algo;
	private Language lg;
	private Configuration config;
	private AvailableNetwork network;
	private GraphNetworkBuilder graphBuilder;
	private GraphNetworkReceiver graphReceiver;
	private EventInfoNetworkWatcher eventInfoNetwork;
	private PathInGraphCollectionBuilder collectionBuilder;
	private GraphNetworkCostReceiver graphNetworkCostReceiver;
	private RecentsAndFavoritesPathsInGraph pathInGraphsToRemember;

	/**
	 * Etat du réseau
	 */
	private StateNetwork stateNetwork;

	/**
	 * Identification du thread actif utilisé pour le calcul d'un chemin
	 */
	private Thread currentAlgo = null;

	/**
	 * Liste des evenements à l'origine de la dernière mise à jour du réseau
	 */
	private ArrayList<EventInfo> recentEventInfo = new ArrayList<EventInfo>();

	/**
	 * Précise si il reste des évènements dont l'ihm n'a pas encore pris connaissance dans la liste des évènements
	 * récents
	 */
	private boolean eventsJustArrived = false;

	/**
	 * Pour tester l'affichage du chargement des différents modules avec l'ihm, il faudra augmenter la valeur de
	 * THREAD_LENGTH
	 */
	private final int THREAD_LENGTH = 0;

	/******************************************************************************/
	/***************************** CONSTRUCTEUR ***********************************/
	/******************************************************************************/

	/**
	 * Constructeur de IgoMaster, prend en paramètre les informations nécessaires pour trouver le réseau et ses mises à
	 * jour.
	 */
	public IGoMaster(String network, String event) {
		super();

		this.algo = new Dijkstra();
		this.config = new ConfigurationXML();
		this.lg = new LanguageXML(config.getValue(SettingsKey.LANGUAGE.toString()));
		this.ihm = new IGoIhmSmartPhone(this);
		this.graphBuilder = new GraphNetworkBuilder();
		this.graphReceiver = new GraphNetworkReceiverFolder(network);
		this.eventInfoNetwork = new EventInfoNetworkWatcherInFolder(event);
		this.graphNetworkCostReceiver = new GraphNetworkCostReceiverHardWritten();

		this.pathInGraphsToRemember = new RecentsAndFavoritesPathsInGraphReceiver(this.graphBuilder,
				new RecentsAndFavoritesPathsInGraphReaderInFolder());

		this.collectionBuilder = this.graphBuilder.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder();

		this.process();
	}

	/******************************************************************************/
	/********************************** THREADS ***********************************/
	/******************************************************************************/

	/**
	 * Lancement du thread implicite qui surveillera les mises à jour du réseau tout au long de l'application Retourne
	 * faux si échec concernant l'initialisation de l'objet qui observe les mises à jour.
	 */
	private boolean watchEvent() {
		try {
			eventInfoNetwork.startWatching();
			return true;
		} catch (ImpossibleStartingException e) {
		} catch (Exception e) {
		}

		System.err.println("Attention les mises à jour du réseau ne seront pas prises en charge");
		return false;
	}

	/**
	 * Thread qui va lancer le calcul d'un trajet Relache les contraintes uniques sur les services
	 */
	private void launchAlgo() {
		new Thread() {
			boolean exception = true;

			public void dealWithExceptions(AlgoKindOfException kindOfException, Service service, Route route,
					Station station, KindRoute kindRoute) {
				ihm.returnPathAsked(null, kindOfException, service, route, station, kindRoute);
				currentAlgo = null;

				exception = false;
			}

			public void dealWithRelaxation(AlgoKindOfRelaxation kindOfRelaxation, Service service, Route route,
					Station station, KindRoute kindRoute) {
				if (service != null) {
					ihm.infoPathAsked(kindOfRelaxation, service, route, station, kindRoute);

					if (kindOfRelaxation == AlgoKindOfRelaxation.ServiceRelaxation)
						collectionBuilder.getPathInGraphConstraintBuilder().removeSeviceOnce(service);

					exception = true;
				}
			}

			public void run() {
				currentAlgo = currentThread();

				while (exception) {
					try {
						algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
						exception = false;
					} catch (VoidPathException e) {
						dealWithExceptions(AlgoKindOfException.NoSolution, null, null, null, null);
					} catch (NoRouteForStationException e) {
						dealWithExceptions(AlgoKindOfException.RoutesNotAccessible, null, null, e.getStation(), null);
					} catch (StationNotOnRoadException e) {
						dealWithExceptions(AlgoKindOfException.StationNotOnGraphNetworkRoad, null, null, null, null);
					} catch (NonValidOriginException e) {
						dealWithExceptions(AlgoKindOfException.NonValidOrigin, null, null, null, null);
					} catch (NonValidDestinationException e) {
						dealWithExceptions(AlgoKindOfException.NonValidDestination, null, null, null, null);
					} catch (ServiceNotAccessibleException e) {
						dealWithRelaxation(AlgoKindOfRelaxation.ServiceRelaxation, e.getService(), null, null, null);
					} catch (StationNotAccessibleException e) {
						dealWithExceptions(AlgoKindOfException.StationNotAccessible, null, null, e.getStation(), null);
					} catch (Exception e) {
						dealWithExceptions(AlgoKindOfException.UndefinedError, null, null, null, null);
					}
				}
			}
		}.start();
	}

	/******************************************************************************/
	/********************************** PRIVATE ***********************************/
	/******************************************************************************/

	/**
	 * Définit si le réseau a bien été receptionné et transformé en GraphNetwork.
	 */
	private void setStateNetwork(StateNetwork state) {
		this.stateNetwork = state;
	}

	/**
	 * Description de l'état du réseau.
	 * 
	 * @return son état
	 */
	private StateNetwork getStateNetwork() {
		return stateNetwork;
	}

	/**
	 * Lancement des modules essentiels au fonctionnement de l'application Si on a pas de réseau on ne lance pas la
	 * surveillance des évènements
	 */
	private void process() {
		this.initObservers();

		ihm.start(true, 4);

		new ExecMultiThread<IHM>(ihm) {

			public void haveRest() {
				currentThread();

				try {
					Thread.sleep(THREAD_LENGTH);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			public void run() {
				this.origine.showMessageSplashScreen("Réseau en cours de chargement");

				this.haveRest();

				if (getNetwork()) {
					this.origine.showMessageSplashScreen("Le réseau a bien été trouvé");

					this.haveRest();

					if (watchEvent())
						this.origine.showMessageSplashScreen("Surveillance des mises à jour du réseau activée");
					else
						this.origine.showMessageSplashScreen("Attention mises à jour du réseau NON supportées");
				} else
					this.origine.showMessageSplashScreen("Réseau indisponible ou mal formé");

				this.haveRest();

				this.origine.showMessageSplashScreen("Chargement de l'interface principale ...");

				this.haveRest();

				this.origine.endSplashScreen();
			}
		}.start();
	}

	/**
	 * Les classes observées par le master ajoutent ce dernier à leur liste d'observeurs.
	 */
	private void initObservers() {
		eventInfoNetwork.addObserver(this);
		algo.addObserver(this);
	}

	/**
	 * Le master récupère le réseau spécifié par le fichier XML. Retourne vrai si le réseau au format XML a bien été
	 * transformé en un objet JAVA utilisable pour le calcul des trajets.
	 */
	private boolean getNetwork() {

		try {
			if (this.graphReceiver.getAvaibleNetwork().hasNext()) {
				this.network = (AvailableNetworkInFolder) (this.graphReceiver.getAvaibleNetwork().next());
			} else
				throw new NoNetworkException("Pas de réseau disponible."
						+ " L'utilisateur ne pourra pas utiliser toutes les fonctionnalités de l'application.");

			this.graphReceiver.buildNewGraphNetwork(this.graphBuilder, this.network.getName(),
					this.graphNetworkCostReceiver);

			setStateNetwork(StateNetwork.NetworkOk);
			return true;
		} catch (NoNetworkException e) {
			setStateNetwork(StateNetwork.NetworkDoesntExist);
			System.err.print(e.getMessage());
		} catch (GraphReceptionException e) {
			setStateNetwork(StateNetwork.ReceptionFailed);
			System.err.print("Erreur de réception du graphe");
		} catch (GraphConstructionException e) {
			setStateNetwork(StateNetwork.ConstructionFailed);
			System.err.print("Graphe mal formé");
		} catch (Exception e) {
			System.err.print("Attention une erreur inconnue est survenue suite à la récupération du réseau");
		}

		return false;
	}

	/******************************************************************************/
	/********************************** PUBLIC ************************************/
	/******************************************************************************/

	@Override
	/*
	 * Les observables Algo et EventWatcher avertiront le master de la terminaison du calcul de trajet ou encore de
	 * l'arrivée de nouveaux évènements avec la méthode notify qui aura pour conséquence l'appel du update ci après.
	 */
	public void update(Observable o, Object arg) {

		if (o.equals(algo) && o != null) {
			if (currentAlgo != null && arg != null && arg.equals(collectionBuilder.getPathInGraph())) {
				currentAlgo = null;

				boolean addAsRecent = true;
				Iterator<PathInGraphCollectionBuilder> itRecents = pathInGraphsToRemember.getRecentsPaths();

				while (addAsRecent && itRecents.hasNext())
					addAsRecent &= itRecents.next() != collectionBuilder;
				if (addAsRecent)
					pathInGraphsToRemember.addAsRecent(collectionBuilder);

				ihm.returnPathAsked(collectionBuilder.getPathInGraphConstraintBuilder(),
						AlgoKindOfException.EverythingFine, null, null, null, null);
			} else
				ihm.returnPathAsked(null, AlgoKindOfException.UndefinedError, null, null, null, null);
		} else if (o.equals(eventInfoNetwork) && o != null) {
			if (currentAlgo != null) {
				algo.abort();
				try {
					currentAlgo.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentAlgo = null;
			}

			if (eventInfoNetwork.getNewEventInfo() != null) {
				Iterator<EventInfo> itEvent = eventInfoNetwork.getNewEventInfo().iterator();
				recentEventInfo.clear();

				while (itEvent.hasNext())
					recentEventInfo.add((EventInfo) itEvent.next());

				eventsJustArrived = true;

				eventInfoNetwork.applyInfo(graphBuilder);

				ihm.updateNetwork();
				// TODO j'ai commenté ici
				// if (!ihm.updateNetwork())
				// System.err.print("Elo --> Mise à jour du réseau mais l'ihm n'a pas de trajet en cours de visualisation");
			}
		}
	}

	@Override
	/*
	 * Permet la fermeture de l'application. L'ihm appelera cette méthode qui sera chargée de stopper tous les autres
	 * modules encore en activité.
	 */
	public void stop() {
		try {
			eventInfoNetwork.stopWatching();
		} catch (NullPointerException e) {
			System.err.print("elo --> La surveillance des événements n'était pas activée ...");
		}

		try {
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (currentAlgo != null) {
			algo.abort();
			try {
				currentAlgo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean askForATravel(PathInGraphConstraintBuilder pathInGraphBuidable) {
		if (currentAlgo == null && pathInGraphBuidable != null) {
			if (collectionBuilder.getPathInGraphConstraintBuilder() != null) {
				if (pathInGraphBuidable.equals(collectionBuilder.getPathInGraphConstraintBuilder())) {
					this.launchAlgo();
					return true;
				}
			}
			Iterator itRecent = pathInGraphsToRemember.getRecentsPaths();

			while (itRecent.hasNext()) {
				this.collectionBuilder = (PathInGraphCollectionBuilder) itRecent.next();

				if (this.collectionBuilder.getPathInGraphConstraintBuilder().equals(pathInGraphBuidable)) {
					boolean b = pathInGraphsToRemember.isFavorite(collectionBuilder.getPathInGraph());
					pathInGraphsToRemember.removeFromRecents(collectionBuilder.getPathInGraph());
					pathInGraphsToRemember.addAsRecent(collectionBuilder);
					if (b)
						pathInGraphsToRemember.markAsFavorite(collectionBuilder.getPathInGraph());

					this.launchAlgo();
					return true;
				}
			}

			Iterator itFav = pathInGraphsToRemember.getFavoritesPaths();

			while (itFav.hasNext()) {
				this.collectionBuilder = (PathInGraphCollectionBuilder) itFav.next();

				if (this.collectionBuilder.getPathInGraphConstraintBuilder().equals(pathInGraphBuidable)) {
					pathInGraphsToRemember.addAsRecent(collectionBuilder);
					this.launchAlgo();
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public String lg(String key) {
		return lg.lg(key);
	}

	@Override
	public boolean setConfig(String key, String value) {
		config.setValue(key, value);
		if (key == SettingsKey.LANGUAGE.toString())
			lg.setLanguage(value);
		return true;
	}

	@Override
	public PathInGraphConstraintBuilder getPathInGraphConstraintBuilder() throws NoNetworkException,
			GraphReceptionException, GraphConstructionException {
		if (currentAlgo != null) {
			algo.abort();
			try {
				currentAlgo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentAlgo = null;
		}

		if (getStateNetwork() == StateNetwork.ConstructionFailed)
			throw new GraphConstructionException();
		if (getStateNetwork() == StateNetwork.NetworkDoesntExist)
			throw new NoNetworkException();
		if (getStateNetwork() == StateNetwork.ReceptionFailed)
			throw new GraphReceptionException();

		this.collectionBuilder = this.graphBuilder.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder();

		return this.collectionBuilder.getPathInGraphConstraintBuilder();
	}

	@Override
	public String config(String key) {
		return getConfig(key);
	}

	@Override
	public String getConfig(String key) {
		return this.config.getValue(key);
	}

	@Override
	public Iterator<KindRoute> getKindRoutes() {
		return this.graphBuilder.getCurrentGraphNetwork().getKinds();
	}

	@Override
	public Iterator<Service> getServices() {
		return this.graphBuilder.getCurrentGraphNetwork().getServices();
	}

	@Override
	public Iterator<Station> getStations() {
		return this.graphBuilder.getCurrentGraphNetwork().getStations();
	}

	@Override
	public Iterator<String> getLanguages() {
		return lg.getLanguages().iterator();
	}

	@Override
	public void markAsFavorite(PathInGraph pig) {
		this.pathInGraphsToRemember.markAsFavorite(pig);
	}

	@Override
	public void removeFromFavorites(PathInGraph pig) {
		this.pathInGraphsToRemember.removeFromFavorites(pig);
	}

	@Override
	public boolean isFavoritesPaths(PathInGraphCollectionBuilder path) {
		return this.pathInGraphsToRemember.isFavorite(path.getPathInGraph());
	}

	@Override
	public boolean isFavoritesPaths(PathInGraph path) {
		return this.pathInGraphsToRemember.isFavorite(path);
	}

	/******************************************************************************/
	/************************ SETTERS ET GETTERS ***********************************/
	/******************************************************************************/

	/**
	 * Getter of the property <tt>ihm</tt>
	 * 
	 * @return Returns the ihm.
	 * @uml.property name="ihm"
	 */
	public IHM getIhm() {
		return ihm;
	}

	/**
	 * Setter of the property <tt>ihm</tt>
	 * 
	 * @param ihm
	 *            The ihm to set.
	 * @uml.property name="ihm"
	 */
	public void setIhm(IHM ihm) {
		this.ihm = ihm;
	}

	/**
	 * Getter of the property <tt>graphReader</tt>
	 * 
	 * @return Returns the graphReader.
	 * @uml.property name="graphReader"
	 */
	public GraphNetworkReceiver getGraphReader() {
		return graphReceiver;
	}

	/**
	 * Setter of the property <tt>graphReader</tt>
	 * 
	 * @param graphReader
	 *            The graphReader to set.
	 * @uml.property name="graphReader"
	 */
	public void setGraphReader(GraphNetworkReceiver graphReceiver) {
		this.graphReceiver = graphReceiver;
	}

	/**
	 * Getter of the property <tt>config</tt>
	 * 
	 * @return Returns the config.
	 * @uml.property name="config"
	 */
	public Configuration getConfig() {
		return config;
	}

	/**
	 * Setter of the property <tt>config</tt>
	 * 
	 * @param config
	 *            The config to set.
	 * @uml.property name="config"
	 */
	public void setConfig(Configuration config) {
		this.config = config;
	}

	/**
	 * Getter of the property <tt>lang</tt>
	 * 
	 * @return Returns the lang.
	 * @uml.property name="lang"
	 */
	public Language getLang() {
		return this.lg;
	}

	/**
	 * Setter of the property <tt>lang</tt>
	 * 
	 * @param lang
	 *            The lang to set.
	 * @uml.property name="lang"
	 */
	public void setLang(Language lg) {
		this.lg = lg;
	}

	/**
	 * Getter of the property <tt>algo</tt>
	 * 
	 * @return Returns the algo.
	 * @uml.property name="algo"
	 */
	public Algo getAlgo() {
		return algo;
	}

	/**
	 * Setter of the property <tt>algo</tt>
	 * 
	 * @param algo
	 *            The algo to set.
	 * @uml.property name="algo"
	 */
	public void setAlgo(Algo algo) {
		this.algo = algo;
	}

	/**
	 * Getter of the property <tt>graphNetworkBuilder</tt>
	 * 
	 * @return Returns the graphNetworkBuilder.
	 * @uml.property name="graphNetworkBuilder"
	 */
	public GraphNetworkBuilder getGraphNetworkFactory() {
		return this.graphBuilder;
	}

	/**
	 * Setter of the property <tt>graphNetworkBuilder</tt>
	 * 
	 * @param graphNetworkBuilder
	 *            The graphNetworkBuilder to set.
	 * @uml.property name="graphNetworkBuilder"
	 */
	public void setGraphNetworkFactory(GraphNetworkBuilder graphNetworkBuilder) {
		this.graphBuilder = graphNetworkBuilder;
	}

	/**
	 * Getter of the property <tt>eventInfoNetwork</tt>
	 * 
	 * @return Returns the eventInfoNetwork.
	 * @uml.property name="eventInfoNetwork"
	 */
	public EventInfoNetworkWatcher getEventInfoNetwork() {
		return eventInfoNetwork;
	}

	/**
	 * Setter of the property <tt>eventInfoNetwork</tt>
	 * 
	 * @param eventInfoNetwork
	 *            The eventInfoNetwork to set.
	 * @uml.property name="eventInfoNetwork"
	 */
	public void setEventInfoNetwork(EventInfoNetworkWatcher eventInfoNetwork) {
		this.eventInfoNetwork = eventInfoNetwork;
	}

	/**
	 * Getter of the property <tt>graphNetworkCostReceiver</tt>
	 * 
	 * @return Returns the graphNetworkCostReceiver.
	 * @uml.property name="graphNetworkCostReceiver"
	 */
	public GraphNetworkCostReceiver getGraphNetworkCostReceiver() {
		return graphNetworkCostReceiver;
	}

	/**
	 * Setter of the property <tt>graphNetworkCostReceiver</tt>
	 * 
	 * @param graphNetworkCostReceiver
	 *            The graphNetworkCostReceiver to set.
	 * @uml.property name="graphNetworkCostReceiver"
	 */
	public void setGraphNetworkCostReceiver(GraphNetworkCostReceiver graphNetworkCostReceiver) {
		this.graphNetworkCostReceiver = graphNetworkCostReceiver;
	}

	@Override
	public void delete(PathInGraph pig) {
		this.pathInGraphsToRemember.removeFromFavorites(pig);
		this.pathInGraphsToRemember.removeFromRecents(pig);
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths() {
		return this.pathInGraphsToRemember.getFavoritesPaths();
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
		return this.pathInGraphsToRemember.getRecentsPaths();
	}

	public Iterator<EventInfo> getNewEventInfos() {
		eventsJustArrived = false;
		return recentEventInfo.iterator();
	}

	@Override
	public boolean hasNewEventInfos() {
		return eventsJustArrived;
	}

}
