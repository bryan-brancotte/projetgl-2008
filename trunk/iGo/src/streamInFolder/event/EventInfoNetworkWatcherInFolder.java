package streamInFolder.event;

import graphNetwork.GraphNetwork;
import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.KindEventInfoNetwork;
import iGoMaster.exception.ImpossibleStartingException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

/**
 * Cette classe permet la surveillance d'un dossier qui contiendra les evenements
 * 
 */
public class EventInfoNetworkWatcherInFolder extends EventInfoNetworkWatcher {

	protected EventInfoNetWorkWatcherStatus status = EventInfoNetWorkWatcherStatus.UNKNOWN_STATUS;
	protected File fichier;
	protected int version;
	protected DOMParser parser;
	protected Timer watcher;
	
	class WatcherInFolder extends TimerTask {
		public void run() {
			System.out.println("entering into timer");
			if (fichier.isFile()) {
				try {
					parser.parse(fichier.toURI().toString());
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Document doc = parser.getDocument();
				
				NodeList numVersion = doc.getElementsByTagName("VersionNumber");
				if (numVersion.getLength()==1) {
					if (Integer.parseInt(numVersion.item(0).getTextContent()) > version) {
						System.out.println("new update");
						version = Integer.parseInt(numVersion.item(0).getTextContent());

						NodeList stations = doc.getElementsByTagName("Station");
							if (stations.getLength() > 0) {
								for (int i=0; i < stations.getLength(); i++) {
									NodeList nodeChilds = stations.item(i).getChildNodes();
									if (nodeChilds != null) {
										int eisId=0;
										String eisMsg = "" ;
										String eisKeinString = "" ;
										KindEventInfoNetwork eisKein = null;
										int eisMsgId = 0;
										
										for (int j=0; j < nodeChilds.getLength() ; j++) {
											if (nodeChilds.item(j).getNodeName().compareTo("#text") != 0) {
												if (nodeChilds.item(j).getLocalName().compareTo("MSID") == 0) {
													eisMsgId = Integer.parseInt(nodeChilds.item(j).getTextContent());
												}
												else if (nodeChilds.item(j).getLocalName().compareTo("ID") == 0) {
													eisId = Integer.parseInt(nodeChilds.item(j).getTextContent());
												}
												else if (nodeChilds.item(j).getLocalName().compareTo("Kind") == 0) {
													eisKeinString = nodeChilds.item(j).getTextContent();
													if (eisKeinString.compareTo("Problem") == 0) {
														eisKein = KindEventInfoNetwork.PROBLEM;
													}
													else if (eisKeinString.compareTo("Solution") == 0) {
														eisKein = KindEventInfoNetwork.SOLUTION;
													}
													else {
														eisKein = KindEventInfoNetwork.OTHER;
													}
												}
												else if (nodeChilds.item(j).getLocalName().compareTo("WarningMsg") == 0) {
													eisMsg = nodeChilds.item(j).getTextContent();
												}
											}
										}

										eventInfosNotApplied.add(new EventInfoStation(eisId, eisMsg, eisMsgId, eisKein));
										
									}
								}
							}


							NodeList stationsRoutes = doc.getElementsByTagName("StationRoute");
								if (stationsRoutes.getLength() > 0) {
									for (int i=0; i < stationsRoutes.getLength(); i++) {
										NodeList nodeChilds = stationsRoutes.item(i).getChildNodes();
										if (nodeChilds != null) {
											int eisIds=0;
											String eisIdr="";
											String eisMsg = "" ;
											String eisKeinString = "" ;
											KindEventInfoNetwork eisKein = null;
											int eisMsgId = 0;
											
											for (int j=0; j < nodeChilds.getLength() ; j++) {
												if (nodeChilds.item(j).getNodeName().compareTo("#text") != 0) {
													if (nodeChilds.item(j).getLocalName().compareTo("MSID") == 0) {
														eisMsgId = Integer.parseInt(nodeChilds.item(j).getTextContent());
													}
													else if (nodeChilds.item(j).getLocalName().compareTo("IDS") == 0) {
														eisIds = Integer.parseInt(nodeChilds.item(j).getTextContent());	
													}
													else if (nodeChilds.item(j).getLocalName().compareTo("IDR") == 0) {
														eisIdr = nodeChilds.item(j).getTextContent();
													}
													else if (nodeChilds.item(j).getLocalName().compareTo("Kind") == 0) {
														eisKeinString = nodeChilds.item(j).getTextContent();
														if (eisKeinString.compareTo("Problem") == 0) {
															eisKein = KindEventInfoNetwork.PROBLEM;
														}
														else if (eisKeinString.compareTo("Solution") == 0) {
															eisKein = KindEventInfoNetwork.SOLUTION;
														}
														else {
															eisKein = KindEventInfoNetwork.OTHER;
														}
													}
													else if (nodeChilds.item(j).getLocalName().compareTo("WarningMsg") == 0) {
														eisMsg = nodeChilds.item(j).getTextContent();
													}
												}
											}

											eventInfosNotApplied.add(new EventInfoStationOnARoute(eisIds,eisIdr, eisMsg, eisMsgId, eisKein));
											
										}
									}
								}	
								
								NodeList routes = doc.getElementsByTagName("Route");
								if (routes.getLength() > 0) {
									for (int i=0; i < routes.getLength(); i++) {
										NodeList nodeChilds = routes.item(i).getChildNodes();
										if (nodeChilds != null) {
											String eisIdr="";
											String eisMsg = "" ;
											String eisKeinString = "" ;
											KindEventInfoNetwork eisKein = null;
											int eisMsgId = 0;
											
											for (int j=0; j < nodeChilds.getLength() ; j++) {
												if (nodeChilds.item(j).getNodeName().compareTo("#text") != 0) {
													if (nodeChilds.item(j).getLocalName().compareTo("MSID") == 0) {
														eisMsgId = Integer.parseInt(nodeChilds.item(j).getTextContent());
													}
													else if (nodeChilds.item(j).getLocalName().compareTo("IDR") == 0) {
														eisIdr = nodeChilds.item(j).getTextContent();
													}
													else if (nodeChilds.item(j).getLocalName().compareTo("Kind") == 0) {
														eisKeinString = nodeChilds.item(j).getTextContent();
														if (eisKeinString.compareTo("Problem") == 0) {
															eisKein = KindEventInfoNetwork.PROBLEM;
														}
														else if (eisKeinString.compareTo("Solution") == 0) {
															eisKein = KindEventInfoNetwork.SOLUTION;
														}
														else {
															eisKein = KindEventInfoNetwork.OTHER;
														}
													}
													else if (nodeChilds.item(j).getLocalName().compareTo("WarningMsg") == 0) {
														eisMsg = nodeChilds.item(j).getTextContent();
													}
												}
											}

											eventInfosNotApplied.add(new EventInfoRoute(eisIdr, eisMsg, eisMsgId, eisKein));
											
										}
									}
								}			
					    if (eventInfosNotApplied.size() > 0) {
					    	status = EventInfoNetWorkWatcherStatus.NEW_UPDATE;
					    }
					} 
					System.out.println("no new update");
				}
			
			} else {
				System.err.println(fichier + " : Erreur de lecture.");
			}
			
		}
	}

	/**
	 * Constructeur d'EventInfoNetworkWatcherInFolder
	 * 
	 * @param path
	 *            Chemin du dossier a surveiller
	 */
	public EventInfoNetworkWatcherInFolder(String path) {
		super();
		fichier = new File(path);
		parser = new DOMParser();
		version = 0;
		eventInfosNotApplied = new LinkedList<EventInfo>();
	}

	/**
	 * Demarre la surveillance du dossier
	 */
	@Override
	public void startWatching() throws ImpossibleStartingException {
		status = EventInfoNetWorkWatcherStatus.STARTED;
	    watcher = new Timer(true);
	    watcher.scheduleAtFixedRate(new WatcherInFolder(), 0, 10*1000);
	}

	/**
	 * Arrete la surveillance du dossier
	 */
	@Override
	public void stopWatching() {
		watcher.cancel();
		status = EventInfoNetWorkWatcherStatus.STOPPED;
		if (eventInfosNotApplied.size() > 0) status = EventInfoNetWorkWatcherStatus.NEW_UPDATE_STOPPED;
		System.out.println("watcher stopped");
	}

	/**
	 * Donne le status de la surveillance
	 */
	@Override
	public EventInfoNetWorkWatcherStatus getStatus() {
		return status;
	}

	/**
	 * Applique les informations contenues dans un événement
	 */
	@Override
	public void applyInfo(GraphNetwork graph) {

		for(EventInfo ev : getNewEventInfo()) {
			ev.applyInfo(graph);
//			System.out.println("Event : " + ev.getMessage());
		}
		eventInfosNotApplied.clear();
		
		// TODO Auto-generated method stub
//		pour chaque element de ma pile, faire applyinfo dessus
//		Penser a empecher que le master puisse faire lui meme applyinfo sur les evenements sans jeter dexception

	}

	/**
	 * Renvoie les nouveaux evenements
	 */
	@Override
	public Collection<EventInfo> getNewEventInfo() {
		return new Vector<EventInfo>(eventInfosNotApplied);
	}

	public static void main(String[] args) {
		EventInfoNetworkWatcherInFolder test = new EventInfoNetworkWatcherInFolder("C:/Documents and Settings/Pierrick/Bureau/2008-2008_S9/Projet GL/doc/XML/TravelAltertGL2008.xml");
		try {
			test.startWatching();
			System.out.println("--------------");
			System.out.println(test.getNewEventInfo().size());
			try {
				System.in.read();
				
				if (test.getStatus().equals(EventInfoNetWorkWatcherStatus.NEW_UPDATE)) {
					System.out.println("NB events : " + test.getNewEventInfo().size());
					System.out.println("New UPDATE MAIN");
					for(EventInfo ev : test.getNewEventInfo()) {
						System.out.println("Event : " + ev.getMessage());
					}
					GraphNetworkBuilder gnb = new GraphNetworkBuilder();
					test.applyInfo(gnb.getInstance());
				}
				
				test.stopWatching();
				System.in.read();
//				test.startWatching();
//				System.out.println(test.getNewEventInfo().size());
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ImpossibleStartingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
