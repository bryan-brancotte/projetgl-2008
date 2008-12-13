package streamInFolder.event;

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
												System.out.println(eisMsgId);
												System.out.println("_____");
											}
											else if (nodeChilds.item(j).getLocalName().compareTo("ID") == 0) {
												eisId = Integer.parseInt(nodeChilds.item(j).getTextContent());
												System.out.println(eisId);
												System.out.println("_____");
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
												System.out.println(eisKein);
												System.out.println("_____");
											}
											else if (nodeChilds.item(j).getLocalName().compareTo("WarningMsg") == 0) {
												eisMsg = nodeChilds.item(j).getTextContent();
												System.out.println(eisMsg);
												System.out.println("_____");
											}
										}
										System.out.println(nodeChilds.item(j).getLocalName());
									}

									eventInfosNotApplied.add(new EventInfoStation(eisId, eisMsg, eisMsgId, eisKein));
									
								}
								System.out.println("-----------------");
								//TODO : finir
							}
						}
				} 
			}
		
		} else {
			System.err.println(fichier + " : Erreur de lecture.");
		}

	}

	/**
	 * Arrete la surveillance du dossier
	 */
	@Override
	public void stopWatching() {
		// TODO Auto-generated method stub
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
	public void applyInfo(GraphNetworkBuilder graph) {
		// TODO Auto-generated method stub

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
		} catch (ImpossibleStartingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
