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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Cette classe permet la surveillance d'un dossier qui contiendra les evenements
 * 
 */
public class EventInfoNetworkWatcherInFolderJDOM extends EventInfoNetworkWatcher {

	protected EventInfoNetWorkWatcherStatus status = EventInfoNetWorkWatcherStatus.UNKNOWN_STATUS;
	protected File fichier;
	protected int version;
	protected org.jdom.Document doc;
	protected Timer watcher;
	protected final Lock verrou = new ReentrantLock();

	class WatcherInFolder extends TimerTask {
		@SuppressWarnings("unchecked")
		public void run() {
//			Date timeWatcher = new Date();
//			long oldTime = timeWatcher.getTime();
			if (fichier.isFile()) {

				SAXBuilder sxb = new SAXBuilder();

				try {
						doc = sxb.build(fichier);
				}  catch (JDOMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Document doc = parser.getDocument();

				Element racine;
				racine = doc.getRootElement();

				List<Element> numVersion = racine.getChildren("VersionNumber");
				if (numVersion.size() == 1) {
//					if (Integer.parseInt(numVersion.get(0).getTextTrim()) > 0) {
					if (Integer.parseInt(numVersion.get(0).getTextTrim()) != version) {
//						System.out.println("new update");
						synchronized (verrou) {

							version = Integer.parseInt(numVersion.get(0).getTextTrim());

							List<Element> stations = racine.getChild("StationsList").getChildren("Station");

							for (Element station : stations) {
								List<Element> nodeChilds = station.getChildren();

								int eisId = 0;
								String eisMsg = "";
								String eisKeinString = "";
								KindEventInfoNetwork eisKein = null;
								int eisMsgId = 0;
								
								for(Element child : nodeChilds) {
									if (child.getName().compareTo("MSID") == 0) {
										eisMsgId = Integer.parseInt(child.getTextTrim());
									}
									else if (child.getName().compareTo("ID") == 0) {
										eisId = Integer.parseInt(child.getTextTrim());
									}
									else if (child.getName().compareTo("Kind") == 0) {
										eisKeinString = child.getTextTrim();
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
									else if (child.getName().compareTo("WarningMsg") == 0) {
										eisMsg = child.getTextTrim();
									}
								}
//								System.out.println(eisId + " : " + eisMsg + " : " + eisMsgId + " : " + eisKein);
								eventInfosNotApplied.add(new EventInfoStation(eisId, eisMsg, eisMsgId, eisKein));
							}

							
							
							
							
							
							List<Element> stationsOnRoute = racine.getChild("StationRouteList").getChildren("StationRoute");

							for (Element stationOR : stationsOnRoute) {
								List<Element> nodeChilds = stationOR.getChildren();

								int eisIds = 0;
								String eisIdr = "";
								String eisMsg = "";
								String eisKeinString = "";
								KindEventInfoNetwork eisKein = null;
								int eisMsgId = 0;
								
								for(Element child : nodeChilds) {
									if (child.getName().compareTo("MSID") == 0) {
										eisMsgId = Integer.parseInt(child.getTextTrim());
									}
									else if (child.getName().compareTo("IDS") == 0) {
										eisIds = Integer.parseInt(child.getTextTrim());
									}
									else if (child.getName().compareTo("IDR") == 0) {
										eisIdr = child.getTextTrim();
									}
									else if (child.getName().compareTo("Kind") == 0) {
										eisKeinString = child.getTextTrim();
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
									else if (child.getName().compareTo("WarningMsg") == 0) {
										eisMsg = child.getTextTrim();
									}
								}
//								System.out.println(eisIds + " : " + eisIdr + " : " + eisMsg + " : " + eisMsgId + " : " + eisKein);
								eventInfosNotApplied.add(new EventInfoStationOnARoute(eisIds, eisIdr, eisMsg, eisMsgId, eisKein));
							}


							
							List<Element> routeList = racine.getChild("RouteList").getChildren("Route");

							for (Element route : routeList) {
								List<Element> nodeChilds = route.getChildren();

								String eisIdr = "";
								String eisMsg = "";
								String eisKeinString = "";
								KindEventInfoNetwork eisKein = null;
								int eisMsgId = 0;
								
								for(Element child : nodeChilds) {
									if (child.getName().compareTo("MSID") == 0) {
										eisMsgId = Integer.parseInt(child.getTextTrim());
									}
									else if (child.getName().compareTo("IDR") == 0) {
										eisIdr = child.getTextTrim();
									}
									else if (child.getName().compareTo("Kind") == 0) {
										eisKeinString = child.getTextTrim();
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
									else if (child.getName().compareTo("WarningMsg") == 0) {
										eisMsg = child.getTextTrim();
									}
								}
//								System.out.println(eisIdr + " : " + eisMsg + " : " + eisMsgId + " : " + eisKein);
								eventInfosNotApplied.add(new EventInfoRoute(eisIdr, eisMsg, eisMsgId, eisKein));
							}
							
						}
						if (eventInfosNotApplied.size() > 0) {
							status = EventInfoNetWorkWatcherStatus.NEW_UPDATE;
						}
						
//						System.out.println("----------------------------");
//						Date newTime = new Date();
//						System.out.println("Time end " + newTime.getTime());
//						System.out.println("Time : " + (newTime.getTime() - oldTime));
					}
//					System.out.println("no new update");
				}

			}
			else {
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
	public EventInfoNetworkWatcherInFolderJDOM(String path) {
		super();
		eventInfosNotApplied = new LinkedList<EventInfo>();
		fichier = new File(path);
		// parser = new DOMParser();
		version = 0;
	}

	/**
	 * Demarre la surveillance du dossier
	 */
	@Override
	public void startWatching() throws ImpossibleStartingException {
		synchronized (verrou) {
			status = EventInfoNetWorkWatcherStatus.STARTED;
			watcher = new Timer(true);
//			watcher.scheduleAtFixedRate(new WatcherInFolder(), 0, 10 * 1000);
			watcher.scheduleAtFixedRate(new WatcherInFolder(), 0, 10 * 1000);
		}
	}

	/**
	 * Arrete la surveillance du dossier
	 */
	@Override
	public void stopWatching() {
		watcher.cancel();
		status = EventInfoNetWorkWatcherStatus.STOPPED;
		if (eventInfosNotApplied.size() > 0)
			status = EventInfoNetWorkWatcherStatus.NEW_UPDATE_STOPPED;
//		System.out.println("watcher stopped");
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

		for (EventInfo ev : getNewEventInfo()) {
			ev.applyInfo(graph);
			// System.out.println("Event : " + ev.getMessage());
		}
		eventInfosNotApplied.clear();
	}

	/**
	 * Renvoie les nouveaux evenements
	 */
	@Override
	public Collection<EventInfo> getNewEventInfo() {
		if (eventInfosNotApplied == null)
			return null;
		else if (eventInfosNotApplied.size() == 0)
			return null;
		else {
			synchronized (verrou) {
				return new Vector<EventInfo>(eventInfosNotApplied);
			}
		}
	}

	public static void main(String[] args) {
		EventInfoNetworkWatcherInFolderJDOM test = new EventInfoNetworkWatcherInFolderJDOM(
				"C:/Documents and Settings/Pierrick/Bureau/2008-2008_S9/Projet GL/doc/XML/TravelAltertGL2008.xml");
		try {
			test.startWatching();
			System.out.println("--------------");
			// System.out.println(test.getNewEventInfo().size());
			try {
				System.in.read();

				if (test.getStatus().equals(EventInfoNetWorkWatcherStatus.NEW_UPDATE)) {
					System.out.println("NB events : " + test.getNewEventInfo().size());
					System.out.println("New UPDATE MAIN");
					for (EventInfo ev : test.getNewEventInfo()) {
						System.out.println("Event : " + ev.getMessage());
					}
					GraphNetworkBuilder gnb = new GraphNetworkBuilder();
					test.applyInfo(gnb.getCurrentGraphNetwork());
				}

				test.stopWatching();
				System.in.read();
				// test.startWatching();
				// System.out.println(test.getNewEventInfo().size());
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
