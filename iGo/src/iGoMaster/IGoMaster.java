package iGoMaster;


import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.KindRoute;
import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraphCollectionBuilder;
import graphNetwork.PathInGraphConstraintBuilder;

import ihm.smartPhone.IGoIhmSmartPhone;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;
import iGoMaster.exception.ImpossibleStartingException;
import iGoMaster.exception.NoNetworkException;
import iGoMaster.exception.NoRouteForStationException;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Iterator;
import java.util.Observable;

import streamInFolder.event.EventInfoNetworkWatcherInFolderJDOM;
import streamInFolder.graphReaderFolder.AvailableNetworkInFolder;
import streamInFolder.graphReaderFolder.GraphNetworkReceiverFolder;
import streamInFolder.graphCostReaderHardWritten.GraphNetworkCostReceiverHardWritten;

import algorithm.Dijkstra;

import xmlFeature.ConfigurationXML;
import xmlFeature.LanguageXML;

/**  
 * @author iGo
 */

public class IGoMaster implements Master, Observer 
{
	
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
	
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	
	
	/******************************************************************************/
	/***************************** CONSTRUCTEUR ***********************************/
	/******************************************************************************/
	
	public IGoMaster(String network, String event)
	{
		super();
		
		this.algo = new Dijkstra();
		this.lg = new LanguageXML(); 
		this.config = new ConfigurationXML();
		this.ihm = new IGoIhmSmartPhone(this);
		this.graphBuilder = new GraphNetworkBuilder();
		this.graphReceiver = new GraphNetworkReceiverFolder(network);
		this.eventInfoNetwork = new EventInfoNetworkWatcherInFolderJDOM(event);
		this.graphNetworkCostReceiver = new GraphNetworkCostReceiverHardWritten();
		
		this.process();
	}
	
	
	/******************************************************************************/
	/********************************** THREADS ***********************************/
	/******************************************************************************/
	
	
	/**  
	 * Thread implicite qui surveillera les mises à jours du réseau tout au long de l'application
	 */
	private void watchEvent()
	{
		try {eventInfoNetwork.startWatching();} 
		catch (ImpossibleStartingException e) 
		{
			System.err.print("La surveillance des évènements n'a pas pu être activée");
		}
	}
	
	/**  
	 * Thread qui va permettre à l'algorithme de calculer un trajet
	 * Voir avec tony pour le recouvrement de demande d'algorithme
	 * Pour l'instant seule la première demande est traitée, les autres sont ignorées
	 */
	private void launchAlgo()
	{
		new Thread() 
		{
			public void run() 
			{
				threads.add(currentThread());
				
				System.out.println("elo --> Algo lancé");
				
				try 
				{
					algo.findPath(collectionBuilder.getPathInGraphResultBuilder());
					
					currentThread().interrupt();
				} 
				catch (NoRouteForStationException e) 
				{
					System.err.print("elo --> échec de l'algorithme, pas de route associée à la station");
				}
				
				ihm.returnPathAsked(null,"Echec");
			}
		}.start();
	}

	
	/******************************************************************************/
	/********************************** PRIVATE ***********************************/
	/******************************************************************************/
	
	/**  
	 * Lancement des modules essentiels au fonctionnement de l'application
	 */
	private void process()
	{
		this.initObservers();
		
		if (this.getNetwork())
		{
			this.watchEvent();
			this.launchIhm();
		}
	}
	
	/**  
	 * Lancement de l'ihm
	 */
	private void launchIhm()
	{
		System.out.println("elo --> Start Visu");
		ihm.start(false);
	}
	
	/**  
	 * Les classes observées par le master ajoutent ce dernier à leur liste d'observeurs.
	 */
	private void initObservers() 
	{
	    algo.addObserver(this);
	    eventInfoNetwork.addObserver(this);	
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
				
				System.out.println("elo --> Récupération de " + this.network.getName());
			}
			else throw new NoNetworkException("Pas de réseau disponible. Terminaison prématurée de l'application");
			
			this.graphReceiver.buildNewGraphNetwork(
					this.graphBuilder,
					this.network.getName(),
					this.graphNetworkCostReceiver
			);		
		} 
		catch (NoNetworkException e) 
		{
			System.err.print(e.getMessage());
			return false;
		}
		catch (GraphReceptionException e) 
		{
			e.printStackTrace();
			return false;
		}
		catch (GraphConstructionException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	/******************************************************************************/
	/********************************** PUBLIC ************************************/
	/******************************************************************************/
	
	@Override
	public void update(Observable o, Object arg) 
	{
		System.out.println("elo --> update");
		
		/* Redefine equals? */
		if (o.equals(algo))
		{
			if (arg.equals(collectionBuilder.getPathInGraph()))
			{	
				System.out.println("elo --> algorithme ok, on passe à l'ihm le chemin trouvé");
				
				/* Demander à tony comment savoir quelles contraintes sont relachées */
				ihm.returnPathAsked(
						collectionBuilder.getPathInGraph(),
						"Haha message qui sert à rien?"
						);
				
				threads.clear();
				
				//System.err.print("Elo --> Un observable de type algo a produit un résultat. Le master n'attend rien. Update ignoré.");
			}
			else 
			{
				System.err.print("Elo --> L'algo n'a pas retourné le pathInGraph correspondant à la collection courante");
			}
		}
		else if (o.equals(eventInfoNetwork))
		{
			eventInfoNetwork.applyInfo(graphBuilder);
			if (!ihm.updateNetwork()) System.err.print("Elo --> L'ihm n'a pas pris en compte les mises à jour");
		}
	}
	
	
	@Override
	public void stop() 
	{
		System.out.println("elo --> Fermeture de l'application");
		
		try{eventInfoNetwork.stopWatching();}
		catch (NullPointerException e)
		{System.err.print("elo --> La surveillance des événements n'était pas activée ...");}
	}

	@Override
	public boolean askForATravel(PathInGraphConstraintBuilder pathInGraphBuidable) 
	{
		System.out.println("elo --> L'ihm demande un chemin");
		try
		{
			if (threads.isEmpty()&& pathInGraphBuidable.equals(collectionBuilder.getPathInGraphConstraintBuilder()))
			{
				this.launchAlgo();
			}
		}
		catch (NullPointerException e)
		{
			System.err.println("elo --> Un builder de contrainte null est inutilisable");
			return false;
		}
		return true;
	}

	@Override
	public String lg(String key) {return lg.lg(key);}

	@Override
	public boolean setConfig(String key, String value) 
	{
		config.setValue(key, value);
		
		try{config.save();}
		catch(Exception e){return false;} 	
		
		return true; 
	}
	
	@Override
	public PathInGraphConstraintBuilder getPathInGraphConstraintBuilder() 
	{
		System.out.println("elo --> L'ihm demande un builder de contraintes");
		
		if (!threads.isEmpty())
		{
			/* Attention un update va arriver que l'on doit ignorer */
		}
			
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




}
