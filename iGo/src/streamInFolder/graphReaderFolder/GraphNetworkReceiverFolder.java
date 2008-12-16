package streamInFolder.graphReaderFolder;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.KindRoute;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
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
				// System.out.println(networks.get(networkChosen));
				// System.out.println(networks.get(networkChosen).getFichier());
				// System.out.println(networks.get(networkChosen).getFichier().toURI());
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
									}
									else if (nodeChilds.item(j).getLocalName().compareTo("ShortDescription") == 0) {
										description = nodeChilds.item(j).getTextContent();
									}
								}
							}
							gnb.addService(id, "", description);

						}
					}
				}

				NodeList stationsList = doc.getElementsByTagName("Station");
				if (stationsList.getLength() > 0) {
					for (int i = 0; i < stationsList.getLength(); i++) {
						NodeList nodeChilds = stationsList.item(i).getChildNodes();
						if (nodeChilds != null) {
							int id = 0, idS = 0;
							String name = "";
							Vector<Integer> idServicesStation = new Vector<Integer>();

							for (int j = 0; j < nodeChilds.getLength(); j++) {
								if (nodeChilds.item(j).getNodeName().compareTo("#text") != 0) {
									// System.out.println("STATION : " + nodeChilds.item(j).getLocalName());
									if (nodeChilds.item(j).getLocalName().compareTo("ID") == 0) {
										id = Integer.parseInt(nodeChilds.item(j).getTextContent());
									}
									else if (nodeChilds.item(j).getLocalName().compareTo("Name") == 0) {
										name = nodeChilds.item(j).getTextContent();
									}
									else if (nodeChilds.item(j).getLocalName().compareTo("StationServicesList") == 0) {
										NodeList stationServicesList = nodeChilds.item(j).getChildNodes();
										if (stationServicesList != null) {
											for (int k = 0; k < stationServicesList.getLength(); k++) {
												if (stationServicesList.item(k).getNodeName().compareTo("#text") != 0) {
													if (stationServicesList.item(k).getLocalName().compareTo("ID") == 0) {
														idS = Integer.parseInt(stationServicesList.item(k).getTextContent());
														idServicesStation.add(idS);
													}
												}
											}
										}
									}
								}
							}
							if (id != 0 && !name.equals("")) {
								Station s = gnb.addStation(id, name);
								for (int l = 0; l < idServicesStation.size(); l++) {
									gnb.addServiceToStation(s, gnb.getActualGraphNetwork().getService(idServicesStation.get(l)));
								}
							}
						}
					}
				}

				NodeList routesList = doc.getElementsByTagName("Route");
				if (routesList.getLength() > 0) {
					for (int i = 0; i < routesList.getLength(); i++) {
						NodeList nodeChilds = routesList.item(i).getChildNodes();
						if (nodeChilds != null) {
							String id = "";
							String kindR = "";
							Vector<Vector<Integer>> idSectionsStations = new Vector<Vector<Integer>>();
							Vector<Integer> timeBetweenStations = new Vector<Integer>();

							for (int j = 0; j < nodeChilds.getLength(); j++) {
								if (nodeChilds.item(j).getNodeName().compareTo("#text") != 0) {
									if (nodeChilds.item(j).getLocalName().compareTo("ID") == 0) {
										id = nodeChilds.item(j).getTextContent();
										// System.out.println("id route " + id);
									}
									else if (nodeChilds.item(j).getLocalName().compareTo("Kind") == 0) {
										kindR = nodeChilds.item(j).getTextContent();
										// System.out.println("kind route " + kindR);
									}
									else if (nodeChilds.item(j).getLocalName().compareTo("RouteSectionsList") == 0) {
										NodeList routeSectionsList = nodeChilds.item(j).getChildNodes();
										if (routeSectionsList != null) {
											for (int k = 0; k < routeSectionsList.getLength(); k++) {
												if (routeSectionsList.item(k).getNodeName().compareTo("#text") != 0) {
													if (routeSectionsList.item(k).getLocalName().compareTo("Section") == 0) {
														// System.out.println("section timebetweenstations = "
														// + routeSectionsList.item(k).getAttributes().getNamedItem("TimeBetweenStations")
														// .getTextContent());
														timeBetweenStations.add(Integer.parseInt(routeSectionsList.item(k).getAttributes()
																.getNamedItem("TimeBetweenStations").getTextContent()));
														NodeList idSectionsList = routeSectionsList.item(k).getChildNodes();
														if (idSectionsList != null) {
															if (idSectionsList.getLength() > 1
																	&& idSectionsList.item(1).getNodeName().compareTo("SectionStationsList") == 0) {
																NodeList idStationsList = idSectionsList.item(1).getChildNodes();
																if (idStationsList != null) {
																	// System.out.println("nb childs = " + idStationsList.getLength() / 2);
																	Vector<Integer> idStations = new Vector<Integer>();
																	for (int l = 0; l < idStationsList.getLength(); l++) {
																		if (idStationsList.item(l).getNodeName().compareTo("#text") != 0) {
																			if (idStationsList.item(l).getLocalName().compareTo("ID") == 0) {
																				// System.out.println("ID "
																				// + Integer.parseInt(idStationsList.item(l).getTextContent()));
																				idStations.add(Integer.parseInt(idStationsList.item(l)
																						.getTextContent()));
																			}
																		}
																	}
																	idSectionsStations.add(idStations);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
							if (idSectionsStations.size() > 0 && timeBetweenStations.size() > 0
									&& (timeBetweenStations.size() == idSectionsStations.size()) && !id.equals("") && !kindR.equals("")) {
								// System.out.println("nous avons " + idSectionsStations.size() + " sections");
								Route r = gnb.addRoute(id, kindR);
								for (int j = 0; j < idSectionsStations.size(); j++) {
									for (int k = 0; k < idSectionsStations.get(j).size(); k++) {
										gnb.addStationToRoute(r, gnb.getActualGraphNetwork().getStation(idSectionsStations.get(j).get(k)),
												timeBetweenStations.get(j));
									}
								}
								// DEFINE ENTRY COST
								gnb.defineEntryCost(kind, cost)
							}
						}
					}
				}

				NodeList interchangeList = doc.getElementsByTagName("Interchange");
				if (interchangeList.getLength() > 0) {
					for (int i = 0; i < interchangeList.getLength(); i++) {
						NodeList nodeChilds = interchangeList.item(i).getChildNodes();
						if (nodeChilds != null) {
							int idStationStart = 0;
							String idRouteStart = "";

							for (int j = 0; j < nodeChilds.getLength(); j++) {
								if (nodeChilds.item(j).getNodeName().compareTo("#text") != 0) {
									if (nodeChilds.item(j).getLocalName().compareTo("Start") == 0) {

										NodeList startInfos = nodeChilds.item(j).getChildNodes();
										if (startInfos != null) {
											for (int k = 0; k < startInfos.getLength(); k++) {
												if (startInfos.item(k).getNodeName().compareTo("#text") != 0) {
													if (startInfos.item(k).getLocalName().compareTo("Station") == 0) {
														idStationStart = Integer.parseInt(startInfos.item(k).getTextContent());
														System.out.println("ID Station start : " + idStationStart);
													}
													else if (startInfos.item(k).getLocalName().compareTo("Route") == 0) {
														idRouteStart = startInfos.item(k).getTextContent();
														System.out.println("ID Route start : " + idRouteStart);
													}
												}
											}
										}

									}
									else if (nodeChilds.item(j).getLocalName().compareTo("Endlist") == 0) {
										NodeList endList = nodeChilds.item(j).getChildNodes();
										System.out.println(" END LIST " + endList.getLength()/2);
										if (endList != null) {
											if (endList.getLength() > 1) {
												for (int k=0; k < endList.getLength() ; k++) {
													NodeList end = endList.item(k).getChildNodes();
													if (end != null) {
														for (int l = 0; l < end.getLength(); l++) {
															if (end.item(l).getNodeName().compareTo("#text") != 0) {
																if (end.item(l).getLocalName().compareTo("Station") == 0) {
																	System.out.println("ID Station end : " + end.item(l).getTextContent());
//																	idStations.add(Integer.parseInt(idStationsList.item(l).getTextContent()));
																}
																else if (end.item(l).getLocalName().compareTo("Route") == 0) {
																	System.out.println("ID Route end : " + end.item(l).getTextContent());
																}
																else if (end.item(l).getLocalName().compareTo("Free") == 0) {
																	System.out.println("ID free end : " + end.item(l).getTextContent());
																}
																else if (end.item(l).getLocalName().compareTo("Pedestrian") == 0) {
																	System.out.println("ID pedestrian end : " + end.item(l).getTextContent());
																}
																else if (end.item(l).getLocalName().compareTo("Time") == 0) {
																	System.out.println("ID time end : " + end.item(l).getTextContent());
																}
															}
														}
//														idSectionsStations.add(idStations);
													}
												}
											}
										}

									}
								}
							}
							System.out.println();
							System.out.println();
							gnb.linkStation(routeOrigin, stationOrigin, routeDestination, stationDestination, cost, time, pedestrian)
							// traitement

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
			} catch (ImpossibleValueException e) {
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
