package streamInFolder.event;

import graphNetwork.GraphNetworkBuilder;
import iGoMaster.EventInfo;
import iGoMaster.EventInfoNetWorkWatcherStatus;
import iGoMaster.EventInfoNetworkWatcher;
import iGoMaster.KindEventInfoNetwork;
import iGoMaster.exception.ImpossibleStartingException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * @author iGo
 */
public class EventInfoNetworkWatcherInFolder extends EventInfoNetworkWatcher {

	/**
	 * Status de la surveillance des evenements
	 */
	protected EventInfoNetWorkWatcherStatus status = EventInfoNetWorkWatcherStatus.UNKNOWN_STATUS;

	/**
	 * Fichier contenant les evenements
	 */
	protected File fichier;

	/**
	 * Version de ce fichier
	 */
	protected int version;

	/**
	 * Contenu de ce fichier
	 */
	protected org.jdom.Document doc;

	/**
	 * Permet de relancer la surveillance periodiquement
	 */
	protected Timer watcher;

	/**
	 * Verrou pour empecher la prise en compte d'evenements en cours de construction
	 */
	protected final Lock verrou = new ReentrantLock();

	/**
	 * Dossier "iGo"
	 */
	String PATH_TO_CONFIG_HOME_DIR = "/.iGo/events/";

	/**
	 * Chemin d'accès au dossier de travail
	 */
	String path;

	class WatcherInFolder extends TimerTask {
		@SuppressWarnings("unchecked")
		public void run() {
			// Date timeWatcher = new Date();
			// long oldTime = timeWatcher.getTime();
			if (fichier.isFile()) {

				SAXBuilder sxb = new SAXBuilder();

				try {
					doc = sxb.build(fichier);
				} catch (JDOMException e) {
					System.err.println("Ce Fichier XML n\'est pas un fichier XML valide");
					e.printStackTrace();
				} catch (IOException e) {
					System.err.println("Erreur de lecture");
					e.printStackTrace();
				}

				Element racine;
				racine = doc.getRootElement();

				List<Element> numVersion = racine.getChildren("VersionNumber");
				if (numVersion.size() == 1) {
					// if (Integer.parseInt(numVersion.get(0).getTextTrim()) > 0) {
					if (Integer.parseInt(numVersion.get(0).getTextTrim()) != version) {
						// System.out.println("new update");
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

								for (Element child : nodeChilds) {
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
								// System.out.println(eisId + " : " + eisMsg + " : " + eisMsgId + " : " + eisKein);
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

								for (Element child : nodeChilds) {
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
								// System.out.println(eisIds + " : " + eisIdr + " : " + eisMsg + " : " + eisMsgId + " : " + eisKein);
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

								for (Element child : nodeChilds) {
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
								// System.out.println(eisIdr + " : " + eisMsg + " : " + eisMsgId + " : " + eisKein);
								eventInfosNotApplied.add(new EventInfoRoute(eisIdr, eisMsg, eisMsgId, eisKein));
							}

						}
						if (eventInfosNotApplied.size() > 0) {
							status = EventInfoNetWorkWatcherStatus.NEW_UPDATE;
							setChanged();
							notifyObservers(eventInfosNotApplied);
						}

						// System.out.println("----------------------------");
						// Date newTime = new Date();
						// System.out.println("Time end " + newTime.getTime());
						// System.out.println("Time : " + (newTime.getTime() - oldTime));
					}
					// System.out.println("no new update");
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
	public EventInfoNetworkWatcherInFolder(String path) {
		super();
		eventInfosNotApplied = new LinkedList<EventInfo>();
		path = (System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + "TravelAltertGL2008.xml").replace("\\", "/");
		File folder = new File((System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR).replace("\\", "/"));
		if (!folder.isDirectory()) {
			folder.mkdir();
		}
		fichier = new File(path);
		if (fichier.length() == 0) {
			try {
				InputStream source = this.getClass().getClassLoader().getResourceAsStream("xml/TravelAltertGL2008.xml");
				try {
					FileOutputStream out = new FileOutputStream(path);
					try {
						// Init

						// Lecture par segment de 0.5Mo
						byte buffer[] = new byte[512 * 1024];
						int nbLecture;

						while ((nbLecture = source.read(buffer)) != -1) {
							out.write(buffer, 0, nbLecture);
						}
					} finally {
						out.close();
					}
				} finally {
					source.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		version = 0;
	}

	/**
	 * @see EventInfoNetworkWatcher#startWatching()
	 */
	@Override
	public void startWatching() throws ImpossibleStartingException {
		synchronized (verrou) {
			status = EventInfoNetWorkWatcherStatus.STARTED;
			watcher = new Timer(true);
			// watcher.scheduleAtFixedRate(new WatcherInFolder(), 0, 10 * 1000);
			watcher.scheduleAtFixedRate(new WatcherInFolder(), 0, 10 * 1000);
		}
	}

	/**
	 * @see EventInfoNetworkWatcher#stopWatching()
	 */
	@Override
	public void stopWatching() {
		watcher.cancel();
		status = EventInfoNetWorkWatcherStatus.STOPPED;
		if (eventInfosNotApplied.size() > 0)
			status = EventInfoNetWorkWatcherStatus.NEW_UPDATE_STOPPED;
		// System.out.println("watcher stopped");
	}

	/**
	 * @see EventInfoNetworkWatcher#getStatus()
	 */
	@Override
	public EventInfoNetWorkWatcherStatus getStatus() {
		return status;
	}

	/**
	 * @see EventInfoNetworkWatcher#applyInfo(GraphNetworkBuilder)
	 */
	@Override
	public void applyInfo(GraphNetworkBuilder graph) {
		for (EventInfo ev : getNewEventInfo()) {
			ev.applyInfo(graph);
			// System.out.println("Event : " + ev.getMessage());
		}
		for (EventInfo ev : getNewEventInfo()) {
			if (ev.isApplied()) {
				eventInfosNotApplied.remove(ev);
			}
		}
	}

	/**
	 * @see EventInfoNetworkWatcher#getNewEventInfo()
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
		EventInfoNetworkWatcherInFolder test = new EventInfoNetworkWatcherInFolder(System.getProperty("user.dir")
				+ "\\ressources\\xml\\TravelAltertGL2008.xml");
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
					test.applyInfo(gnb);
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

	public String getPath() {
		return path;
	}
}
