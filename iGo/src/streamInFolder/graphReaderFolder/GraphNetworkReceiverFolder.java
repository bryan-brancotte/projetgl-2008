package streamInFolder.graphReaderFolder;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.Service;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.AvailableNetwork;
import iGoMaster.GraphNetworkCostReceiver;
import iGoMaster.GraphNetworkReceiver;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import streamInFolder.graphCostReaderHardWritten.GraphNetworkCostReceiverHardWritten;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class GraphNetworkReceiverFolder implements GraphNetworkReceiver {

	private HashMap<String, AvailableNetwork> networks;
	private File folder;
	private DOMParser parser;

	public GraphNetworkReceiverFolder(String f) {
		super();
		networks = new HashMap<String, AvailableNetwork>();
		folder = new File(f);
		parser = new DOMParser();
		if (folder.isDirectory()) {
			try {
				for (File fr : folder.listFiles()) {
					if (fr.getName().contains("Network") && fr.getName().contains(".xml")) {
						AvailableNetworkInFolder nt = new AvailableNetworkInFolder(fr.getName(), fr.getAbsolutePath());
						networks.put(fr.getName(), nt);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 */
	public boolean updateGraph() {
		return false;
	}

	@Override
	public Collection<AvailableNetwork> getAvaibleNetwork() {
		if (networks != null)
			return networks.values();
		else
			return null;
	}

	@Override
	public void buildNewGraphNetwork(GraphNetworkBuilder gnb, String networkChosen, GraphNetworkCostReceiver costReceiver)
			throws GraphReceptionException, GraphConstructionException {

		if (networks.get(networkChosen) == null)
			throw new GraphReceptionException();
		else {
			try {
				System.out.println(networks.get(networkChosen));
				System.out.println(networks.get(networkChosen).getFichier());
				System.out.println(networks.get(networkChosen).getFichier().toURI());
				System.out.println(networks.get(networkChosen).getFichier().toURI().toString());
				parser.parse(networks.get(networkChosen).getFichier().toURI().toString());

				Document doc = parser.getDocument();

				NodeList servicesList = doc.getElementsByTagName("Service");
				if (servicesList.getLength() > 0) {
					for (int i = 0; i < servicesList.getLength(); i++) {
						NodeList nodeChilds = servicesList.item(i).getChildNodes();
						if (nodeChilds != null) {
							int id = 0;
							String description = "";

							for (int j = 0; j < nodeChilds.getLength(); j++) {
								if (nodeChilds.item(j).getNodeName().compareTo("#text") != 0) {
									if (nodeChilds.item(j).getLocalName().compareTo("ID") == 0) {
										id = Integer.parseInt(nodeChilds.item(j).getTextContent());
										System.out.println("Service id " + id);
									}
									else if (nodeChilds.item(j).getLocalName().compareTo("ShortDescription") == 0) {
										description = nodeChilds.item(j).getTextContent();
										System.out.println("Service description " + description);
									}
								}
							}
							gnb.addService(id, description);

						}
					}
				}

			} catch (SAXException e) {
				throw new GraphConstructionException();
			} catch (IOException e) {
				throw new GraphReceptionException();
			} catch (ViolationOfUnicityInIdentificationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {

		GraphNetworkReceiverFolder gnrf = new GraphNetworkReceiverFolder("C:/Documents and Settings/Pierrick/Bureau/2008-2008_S9/Projet GL/doc/XML/");

		try {

			gnrf.buildNewGraphNetwork(new GraphNetworkBuilder(), "NetworkGL2008.xml", new GraphNetworkCostReceiverHardWritten());

			/*
			 * test.startWatching(); System.out.println("--------------"); // System.out.println(test.getNewEventInfo().size()); try { System.in.read();
			 * 
			 * if (test.getStatus().equals(EventInfoNetWorkWatcherStatus.NEW_UPDATE)) { System.out.println("NB events : " + test.getNewEventInfo().size()); System.out.println("New
			 * UPDATE MAIN"); for (EventInfo ev : test.getNewEventInfo()) { System.out.println("Event : " + ev.getMessage()); } GraphNetworkBuilder gnb = new GraphNetworkBuilder();
			 * test.applyInfo(gnb.getInstance()); }
			 * 
			 * test.stopWatching(); System.in.read(); // test.startWatching(); // System.out.println(test.getNewEventInfo().size()); System.in.read(); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
