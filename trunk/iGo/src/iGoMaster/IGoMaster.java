package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.KindRoute;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;
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
import java.util.Collection;
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
 * Enumération : 
 * "NetworkOk" si le réseau a bien été récupéré. 
 * "NetworkDoesntExist" si aucun réseau n'a été trouvé. 
 * "ConstructionFailed" si la transformation du réseau en graphNetwork a échoué. 
 * "ReceptionFailed" si le réseau a été mal réceptionné. 
 */
enum StateNetwork {NetworkOk, NetworkDoesntExist, ConstructionFailed, ReceptionFailed};


/**  
 * @author iGo
 */
public class IGoMaster implements Master, Observer 
{
	
	private final int THREAD_LENGTH = (System.getProperty("user.name").compareTo("elodie") == 0)?3000:0;
	
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
	
	private StateNetwork stateNetwork;
	
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	
	private boolean test(){return System.getProperty("user.name").compareTo("elodie") == 0;}
	
	/******************************************************************************/
	/***************************** CONSTRUCTEUR ***********************************/
	/******************************************************************************/
	
	public IGoMaster(String network, String event)
	{
		super();
		
		this.algo = new Dijkstra();
		this.config = new ConfigurationXML();
		this.lg = new LanguageXML( config.getValue(SettingsKey.LANGUAGE.toString()));
		this.ihm = new IGoIhmSmartPhone(this);
		this.graphBuilder = new GraphNetworkBuilder();
		this.graphReceiver = new GraphNetworkReceiverFolder(network);
		this.eventInfoNetwork = new EventInfoNetworkWatcherInFolder(event);
		this.graphNetworkCostReceiver = new GraphNetworkCostReceiverHardWritten();
		this.pathInGraphsToRemember = new RecentsAndFavoritesPathsInGraphReceiver(
				this.graphBuilder, 
				new RecentsAndFavoritesPathsInGraphReaderInFolder());
		
		this.collectionBuilder = this.graphBuilder.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder();
		
        this.process();
	}
	
	
	/******************************************************************************/
	/********************************** THREADS ***********************************/
	/******************************************************************************/
	
	
	/**  
	 * Thread implicite qui surveillera les mises à jours du réseau tout au long de l'application
	 */
	private boolean watchEvent()
	{
		try 
		{
			eventInfoNetwork.startWatching();
			return true;
		} 
		catch (ImpossibleStartingException e) {e.printStackTrace();}
		catch (Exception e){e.printStackTrace();}
		
		return false;
	}
	
	/**  
	 * Thread qui va permettre à l'algorithme de calculer un trajet
	 */
	private void launchAlgo()
	{
		new Thread() 
		{
			boolean exception = true;
			
			public void dealWithExceptions(AlgoKindOfException kindOfException)
			{
				System.out.println("elo --> Le master a rencontré une exception venant d'algo");
				System.out.println(kindOfException.toString());
				
				ihm.returnPathAsked(null, kindOfException);
				threads.clear();
				exception = false;
			}
			
			public void dealWithExceptions(AlgoKindOfException kindOfException, Service service)
			{
				System.out.println("elo --> Le master a relaché une contrainte");
				
				ihm.infoPathAsked(kindOfException, service, null , null, null);
				
				if (kindOfException==AlgoKindOfException.ServiceNotAccessibleException)
					collectionBuilder.getPathInGraphConstraintBuilder().removeSeviceOnce(service);
				
				exception = true;
			}
			
			
			public void run() 
			{
		
				threads.add(currentThread());
				
				if (test())System.out.println("elo --> Algo lancé");
				
				while (exception)
				{	
					try 
					{
						algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
						exception = false;
						if (test())System.out.println("elo --> Aucune exception n'a été lancée par algo");
						
					}
					catch (VoidPathException e) {dealWithExceptions(AlgoKindOfException.VoidPathException);} 
					catch (NoRouteForStationException e) {dealWithExceptions(AlgoKindOfException.VoidPathException);} 	 
					catch (StationNotOnRoadException e) {dealWithExceptions(AlgoKindOfException.StationNotOnRoadException);} 
					catch (NonValidOriginException e) {dealWithExceptions(AlgoKindOfException.NonValidOriginException);} 
					catch (NonValidDestinationException e) {dealWithExceptions(AlgoKindOfException.NonValidDestinationException);} 
					catch (ServiceNotAccessibleException e) {dealWithExceptions(AlgoKindOfException.ServiceNotAccessibleException, e.getService());} 
					catch (StationNotAccessibleException e) {dealWithExceptions(AlgoKindOfException.StationNotAccessibleException);}
					catch (Exception e){dealWithExceptions(AlgoKindOfException.UnknownException);} 
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
	private void setStateNetwork(StateNetwork state) {this.stateNetwork = state;}

	/**
	 * Description de l'état du réseau.
	 * @return son état
	 */
	private StateNetwork getStateNetwork() {return stateNetwork;}
	
	/**  
	 * Lancement des modules essentiels au fonctionnement de l'application
	 * Si on a pas de réseau on ne lance pas la surveillance des évènements
	 */
	private void process()
	{
		this.initObservers();
		System.out.println("kljklj");
		
		if (test())System.out.println("elo --> Start Visu");
		ihm.start(true,4);
		
		new ExecMultiThread<IHM>(ihm) 
		{
			
			public void haveRest()
			{
				currentThread();
				
				try 
				{
					Thread.sleep(THREAD_LENGTH);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			
			public void run() 
			{
				this.origine.showMessageSplashScreen("Réseau en cours de chargement");

				this.haveRest();
				
				if (getNetwork())
				{	
					this.origine.showMessageSplashScreen("Le réseau a bien été trouvé");
					
					this.haveRest();
					
					if (watchEvent())this.origine.showMessageSplashScreen("Surveillance des mises à jour du réseau activée");
					else this.origine.showMessageSplashScreen("Attention mises à jour du réseau NON supportées");
				}
				else this.origine.showMessageSplashScreen("Réseau indisponible ou mal formé");
					
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
	private void initObservers() 
	{
		eventInfoNetwork.addObserver(this);	
	    algo.addObserver(this);
	    
	}
	
	/**  
	 * Le master récupère le réseau spécifié par le fichier XML.
	 */
	private boolean getNetwork()
	{
		
		try 
		{
			if (this.graphReceiver.getAvaibleNetwork().hasNext())
			{
				this.network = (AvailableNetworkInFolder)(this.graphReceiver.getAvaibleNetwork().next());
				
				if (test())System.out.println("elo --> Récupération de " + this.network.getName());
			}
			else throw new NoNetworkException("Pas de réseau disponible." +
					" L'utilisateur ne pourra pas utiliser toutes les fonctionnalités de l'application.");
			
			this.graphReceiver.buildNewGraphNetwork(
					this.graphBuilder,
					this.network.getName(),
					this.graphNetworkCostReceiver
			);	
			
			setStateNetwork(StateNetwork.NetworkOk);
			return true;
		} 
		catch (NoNetworkException e) 
		{
			setStateNetwork(StateNetwork.NetworkDoesntExist);
			System.err.print(e.getMessage());
		}
		catch (GraphReceptionException e) 
		{
			setStateNetwork(StateNetwork.ReceptionFailed);
			System.err.print("Erreur de réception du graphe");
		}
		catch (GraphConstructionException e) 
		{
			setStateNetwork(StateNetwork.ConstructionFailed);
			System.err.print("Graphe mal formé");
		}
		
		return false;
	}
	

	/******************************************************************************/
	/********************************** PUBLIC ************************************/
	/******************************************************************************/
	
	@Override
	public void update(Observable o, Object arg) 
	{
		if (test())System.out.println("elo --> UPPDATEEEEEEE");
		
		if (o.equals(algo) && o!=null)
		{
			if (!threads.isEmpty() && arg!=null && arg.equals(collectionBuilder.getPathInGraph()))
			{	
				if (test())System.out.println("elo --> algorithme ok, on passe à l'ihm le chemin trouvé");

				threads.clear();
				

				//TODO modifier par bryan Begin
				//avant : pathInGraphsToRemember.addAsRecent(collectionBuilder.getPathInGraph());
				//ajout du if
				boolean addAsRecent = true;
				Iterator<PathInGraphCollectionBuilder> itB ;
				itB= pathInGraphsToRemember.getRecentsPaths(); 
				while(addAsRecent&&itB.hasNext()){
					addAsRecent&=itB.next()!=collectionBuilder;
				}
				if(addAsRecent)
					pathInGraphsToRemember.addAsRecent(collectionBuilder);
				//TODO modifier par bryan End
				
				ihm.returnPathAsked(
						collectionBuilder.getPathInGraphConstraintBuilder(),
						AlgoKindOfException.EverythingFine
						);
				
			}
			else 
			{
				ihm.returnPathAsked(null, AlgoKindOfException.UnknownException);
			}
		}
		else if (o.equals(eventInfoNetwork) && o!=null)
		{
			
			if (!threads.isEmpty())
			{
				algo.abort();
				try {threads.get(0).join();} 
				catch (InterruptedException e) {e.printStackTrace();}
				threads.clear();
			}
			
			if (eventInfoNetwork.getNewEventInfo()!=null)
			{
				//eventInfoNetwork.applyInfo(graphBuilder);
				//if (!ihm.updateNetwork()) System.err.print("Elo --> L'ihm n'a pas pris en compte les mises à jour");
			}
		}
	}
	
	
	
	@Override
	public void stop() 
	{
		if (test())System.out.println("elo --> Fermeture de l'application");
		
		try{eventInfoNetwork.stopWatching();}
		catch (NullPointerException e)
		{System.err.print("elo --> La surveillance des événements n'était pas activée ...");}
	
		try{config.save();}
		catch(Exception e){e.printStackTrace();} 	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean askForATravel(PathInGraphConstraintBuilder pathInGraphBuidable) 
	{
		if (test())System.out.println("elo --> L'ihm demande un chemin");
		
		if (threads.isEmpty() && pathInGraphBuidable!=null)
		{	
			if (collectionBuilder.getPathInGraphConstraintBuilder()!=null)
			{	
				if (pathInGraphBuidable.equals(collectionBuilder.getPathInGraphConstraintBuilder()))
				{
					if (test())System.out.println("elo --> L'ihm étudie un builder normal");
					this.launchAlgo();
					return true;
				}
			}
			Iterator itRecent = pathInGraphsToRemember.getRecentsPaths();

			while (itRecent.hasNext())
			{
				if (test())System.out.println("elo --> L'ihm étudie un recent");
				
				this.collectionBuilder = (PathInGraphCollectionBuilder) itRecent.next();
				
				if(this.collectionBuilder.getPathInGraphConstraintBuilder().equals(pathInGraphBuidable))
				{
					if (test())System.out.println("elo --> recent trouvé");
					this.launchAlgo();
					return true;
				}
			}
			
			Iterator itFav = pathInGraphsToRemember.getFavoritesPaths();

			while (itFav.hasNext())
			{
				if (test())System.out.println("elo --> L'ihm étudie un favori");
				
				this.collectionBuilder = (PathInGraphCollectionBuilder) itFav.next();
				
				if(this.collectionBuilder.getPathInGraphConstraintBuilder().equals(pathInGraphBuidable))
				{
					if (test())System.out.println("elo --> favori trouvé");
					this.launchAlgo();
					return true;
				}
			}
		 }
		
		return false;
	}

	@Override
	public String lg(String key) {return lg.lg(key);}

	@Override
	public boolean setConfig(String key, String value) 
	{
		if (true/*TODO vérifier que je peux modifier les clés */)
		{
			config.setValue(key, value);
			if (key == SettingsKey.LANGUAGE.toString())
				lg.setLanguage(value);
			return true;
		}
		return false;
	}
	
	@Override
	public PathInGraphConstraintBuilder getPathInGraphConstraintBuilder() 
	throws NoNetworkException, GraphReceptionException, GraphConstructionException
	{
		if (test())System.out.println("elo --> L'ihm demande un builder de contraintes");
		
		if (!threads.isEmpty())
		{
			algo.abort();
			try {threads.get(0).join();} 
			catch (InterruptedException e) {e.printStackTrace();}
			threads.clear();
		}
		
		if (getStateNetwork() == StateNetwork.ConstructionFailed) throw new GraphConstructionException();
		if (getStateNetwork() == StateNetwork.NetworkDoesntExist) throw new NoNetworkException();
		if (getStateNetwork() == StateNetwork.ReceptionFailed) throw new GraphReceptionException();
		
		this.collectionBuilder = this.graphBuilder.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder();
		
		return this.collectionBuilder.getPathInGraphConstraintBuilder();
	}
	
	@Override
	public String config(String key) {return getConfig(key);}
	
	@Override
	public String getConfig(String key) {return this.config.getValue(key);}

	@Override
	public Iterator<KindRoute> getKindRoutes() {return this.graphBuilder.getCurrentGraphNetwork().getKinds();}

	@Override
	public Iterator<Service> getServices() {return this.graphBuilder.getCurrentGraphNetwork().getServices();}

	@Override
	public Iterator<Station> getStations() {return this.graphBuilder.getCurrentGraphNetwork().getStations();}
	
	@Override
	public Iterator<String> getLanguages() {return lg.getLanguages().iterator();}


	
	/******************************************************************************/
	/************************SETTERS ET GETTERS ***********************************/
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
	 * @uml.property   name="eventInfoNetwork"
	 * @uml.associationEnd   inverse="iGoMaster:iGoMaster.EventInfoNetworkWatcher"
	 */
	

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
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths(){return this.pathInGraphsToRemember.getFavoritesPaths();}


	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
		return this.pathInGraphsToRemember.getRecentsPaths();}


	@Override
	public void markAsFavorite(PathInGraph pig) {this.pathInGraphsToRemember.markAsFavorite(pig);}


	@Override
	public void removeFromFavorites(PathInGraph pig) {this.pathInGraphsToRemember.removeFromFavorites(pig);}


	@Override
	public boolean isFavoritesPaths(PathInGraphCollectionBuilder path) {
		return this.pathInGraphsToRemember.isFavorite(path.getPathInGraph());
	}
	
	@Override
	public boolean isFavoritesPaths(PathInGraph path) {
		return this.pathInGraphsToRemember.isFavorite(path);
	}

}
