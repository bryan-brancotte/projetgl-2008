package streamInFolder.graphReaderFolder;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.KindRoute;
import graphNetwork.Route;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.StationNotOnRoadException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.AvailableNetwork;
import iGoMaster.GraphNetworkCostReceiver;
import iGoMaster.GraphNetworkReceiver;
import iGoMaster.exception.GraphConstructionException;
import iGoMaster.exception.GraphReceptionException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import streamInFolder.graphCostReaderHardWritten.GraphNetworkCostReceiverHardWritten;

/**
 * Interface permettant de modeliser un constructeur de reseau a partir d'un fichier
 * 
 * @author iGo
 */
public class GraphNetworkReceiverFolder implements GraphNetworkReceiver {

	/**
	 * Tableau associatif contenant les differents reseaux disponibles
	 */
	private HashMap<String, AvailableNetwork> networks;

	/**
	 * Dossier ou se trouvent les reseaux
	 */
	private File folder;

	/**
	 * Contenu XML d'un reseau
	 */
	private org.jdom.Document doc;

	/**
	 * Verrou pour empecher la lecture d'un reseau en cours de construction
	 */
	private final Lock verrou = new ReentrantLock();

	/**
	 * Dossier "iGo"
	 */
	String PATH_TO_CONFIG_HOME_DIR = "/.iGo/networks/";

	/**
	 * Chemin d'accès au dossier de travail
	 */
	String path;

	/**
	 * Constructeur de GraphNetworkReceiverFolder
	 * 
	 * @param f
	 *            Nom du dossier ou se trouvent les reseaux
	 */
	public GraphNetworkReceiverFolder(String f) {
		super();
		if (f != null) {

			networks = new HashMap<String, AvailableNetwork>();
			path = (System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR).replace("\\", "/");
			folder = new File(path);
			if (!folder.isDirectory())
				folder.mkdir();
			if (folder.isDirectory()) {
				try {
					for (File fr : folder.listFiles()) {
						if (fr.getName().contains("Network") && fr.getName().contains(".xml")) {
							AvailableNetworkInFolder nt = new AvailableNetworkInFolder(fr.getName(), fr.getAbsolutePath());
							networks.put(fr.getName(), nt);
						}
					}

					if (networks.isEmpty()) {
						try {
							InputStream source = this.getClass().getClassLoader().getResourceAsStream("xml/NetworkGL2008.xml");
							try {
								FileOutputStream out = new FileOutputStream(path + "NetworkGL2008.xml");
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

						File destination = new File(path + "NetworkGL2008.xml");

						AvailableNetworkInFolder nt = new AvailableNetworkInFolder(destination.getName(), destination.getAbsolutePath());
						networks.put(destination.getName(), nt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @see GraphNetworkReceiver#getAvaibleNetwork()
	 */
	@Override
	public Iterator<AvailableNetwork> getAvaibleNetwork() {
		if (networks != null) {
			return networks.values().iterator();
		}
		else
			return null;
	}

	/**
	 * @see GraphNetworkReceiver#buildNewGraphNetwork(GraphNetworkBuilder, String, GraphNetworkCostReceiver)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buildNewGraphNetwork(GraphNetworkBuilder gnb, String networkChosen, GraphNetworkCostReceiver costReceiver)
			throws GraphReceptionException, GraphConstructionException {

		if (gnb != null && networkChosen != null && costReceiver != null) {
			if (networks.get(networkChosen) == null)
				throw new GraphReceptionException();
			else {
				try {
					SAXBuilder sxb = new SAXBuilder();

					GraphNetworkCostReceiverHardWritten giveCost;
					giveCost = new GraphNetworkCostReceiverHardWritten();

					synchronized (verrou) {

						try {
							doc = sxb.build(networks.get(networkChosen).getFichier().toURI().toString());
						} catch (JDOMException e) {
							throw new GraphConstructionException();
						} catch (IOException e) {
							throw new GraphConstructionException();
						}

						Element racine;
						racine = doc.getRootElement();

						Element servicesS = racine.getChild("ServicesList");
						if (servicesS != null) {
							List<Element> services = racine.getChild("ServicesList").getChildren("Service");
							if (services != null) {
								for (Element service : services) {
									List<Element> nodeChilds = service.getChildren();
									if (nodeChilds != null) {
										int id = 0;
										String description = "";

										for (Element child : nodeChilds) {
											if (child.getName().compareTo("ID") == 0) {
												id = Integer.parseInt(child.getTextTrim());
											}
											else if (child.getName().compareTo("ShortDescription") == 0) {
												description = child.getTextTrim();
											}
										}
										gnb.addService(id, description, "");
									}
								}
							}
						}

						Element stationsS = racine.getChild("StationsList");
						if (stationsS != null) {
							List<Element> stations = racine.getChild("StationsList").getChildren("Station");
							if (stations != null) {
								for (Element station : stations) {
									List<Element> nodeChilds = station.getChildren();
									if (nodeChilds != null) {
										int id = 0, idS = 0;
										String name = "";
										Vector<Integer> idServicesStation = new Vector<Integer>();

										for (Element child : nodeChilds) {
											if (child.getName().compareTo("ID") == 0) {
												id = Integer.parseInt(child.getTextTrim());
											}
											else if (child.getName().compareTo("Name") == 0) {
												name = child.getTextTrim();
											}
											else if (child.getName().compareTo("StationServicesList") == 0) {
												List<Element> stationServicesList = child.getChildren();

												if (stationServicesList != null) {
													for (Element stationService : stationServicesList) {
														if (stationService.getName().compareTo("ID") == 0) {
															idS = Integer.parseInt(stationService.getTextTrim());
															idServicesStation.add(idS);
														}
													}
												}
											}
										}
										if (id != 0 && !name.equals("")) {
											Station s = gnb.addStation(id, name);
											for (int l = 0; l < idServicesStation.size(); l++) {
												gnb.addServiceToStation(s, gnb.getCurrentGraphNetwork().getService(idServicesStation.get(l)));
											}
										}
									}
								}
							}
						}

						Element routesS = racine.getChild("RoutesList");
						if (routesS != null) {
							List<Element> routes = racine.getChild("RoutesList").getChildren("Route");
							if (routes != null) {
								for (Element route : routes) {
									List<Element> nodeChilds = route.getChildren();
									if (nodeChilds != null) {
										String id = "";
										String kindR = "";
										Vector<Vector<Integer>> idSectionsStations = new Vector<Vector<Integer>>();
										Vector<Integer> timeBetweenStations = new Vector<Integer>();

										for (Element child : nodeChilds) {
											if (child.getName().compareTo("ID") == 0) {
												id = child.getTextTrim();
											}
											else if (child.getName().compareTo("Kind") == 0) {
												kindR = child.getTextTrim();
											}
											else if (child.getName().compareTo("RouteSectionsList") == 0) {
												List<Element> sectionsList = child.getChildren();
												if (sectionsList != null) {
													for (Element section : sectionsList) {
														timeBetweenStations.add(Integer.parseInt(section.getAttributeValue("TimeBetweenStations")));

														Element sectionStationsListS = section.getChild("SectionStationsList");
														if (sectionStationsListS != null) {
															List<Element> sectionStationsList = section.getChild("SectionStationsList").getChildren();
															if (sectionStationsList != null) {
																Vector<Integer> idStations = new Vector<Integer>();

																for (Element station : sectionStationsList) {
																	if (station.getName().compareTo("ID") == 0) {
																		idStations.add(Integer.parseInt(station.getTextTrim()));
																	}

																}
																idSectionsStations.add(idStations);
															}
														}
													}
												}
											}

											if (idSectionsStations.size() > 0 && timeBetweenStations.size() > 0 && !id.equals("")
													&& !kindR.equals("")) {
												Route r = gnb.addRoute(id, kindR);
												gnb.defineEntryCost(KindRoute.getKindFromString(kindR), giveCost.getCost(KindRoute
														.getKindFromString(kindR)));

												for (int j = 0; j < idSectionsStations.size(); j++) {
													for (int k = 0; k < idSectionsStations.get(j).size(); k++) {
														gnb.addStationToRoute(r, gnb.getCurrentGraphNetwork().getStation(
																idSectionsStations.get(j).get(k)), timeBetweenStations.get(j));
													}
												}
											}
										}
									}
								}
							}
						}

						Element interchangesS = racine.getChild("InterchangesList");

						if (interchangesS != null) {
							List<Element> interchanges = racine.getChild("InterchangesList").getChildren("Interchange");
							if (interchanges != null) {
								for (Element interchange : interchanges) {
									Element startChildsS = interchange.getChild("Start");
									if (startChildsS != null) {
										List<Element> startChilds = interchange.getChild("Start").getChildren();
										if (startChilds != null) {
											int idStationStart = 0;
											String idRouteStart = "";
											for (Element child : startChilds) {
												if (child.getName().compareTo("Station") == 0) {
													idStationStart = Integer.parseInt(child.getTextTrim());
												}
												else if (child.getName().compareTo("Route") == 0) {
													idRouteStart = child.getTextTrim();
												}
											}

											Element endListS = interchange.getChild("Endlist");
											if (endListS != null) {
												List<Element> endList = interchange.getChild("Endlist").getChildren();
												if (endList != null) {
													for (Element end : endList) {
														List<Element> endChilds = end.getChildren();
														if (endChilds != null) {
															int idStationEnd = 0;
															String idRouteEnd = "";
															boolean freeEnd = true;
															boolean pedestrianEnd = true;
															int timeEnd = 0;
															for (Element child : endChilds) {

																if (child.getName().compareTo("Station") == 0) {
																	idStationEnd = Integer.parseInt(child.getTextTrim());
																}
																else if (child.getName().compareTo("Route") == 0) {
																	idRouteEnd = child.getTextTrim();
																}
																else if (child.getName().compareTo("Free") == 0) {
																	freeEnd = Boolean.parseBoolean(child.getTextTrim());
																}
																else if (child.getName().compareTo("Pedestrian") == 0) {
																	pedestrianEnd = Boolean.parseBoolean(child.getTextTrim());
																}
																else if (child.getName().compareTo("Time") == 0) {
																	timeEnd = Integer.parseInt(child.getTextTrim());
																}
															}

															if (idStationStart != 0 && !idRouteStart.equals("") && idStationEnd != 0 && !idRouteEnd.equals("")) {

																if (freeEnd == true) {
																	gnb.linkStationBidirectional(gnb.getCurrentGraphNetwork().getRoute(idRouteStart), gnb
																			.getCurrentGraphNetwork().getStation(idStationStart), gnb.getCurrentGraphNetwork()
																			.getRoute(idRouteEnd), gnb.getCurrentGraphNetwork().getStation(idStationEnd), 0,
																			timeEnd, pedestrianEnd);
																}
																else {
																	gnb.linkStation(gnb.getCurrentGraphNetwork().getRoute(idRouteStart), gnb
																			.getCurrentGraphNetwork().getStation(idStationStart), gnb.getCurrentGraphNetwork()
																			.getRoute(idRouteEnd), gnb.getCurrentGraphNetwork().getStation(idStationEnd),
																			giveCost.getCost(gnb.getCurrentGraphNetwork().getRoute(idRouteStart).getKindRoute(),
																					gnb.getCurrentGraphNetwork().getRoute(idRouteEnd).getKindRoute()), timeEnd,
																			pedestrianEnd);

																	gnb.linkStation(gnb.getCurrentGraphNetwork().getRoute(idRouteEnd), gnb
																			.getCurrentGraphNetwork().getStation(idStationEnd), gnb.getCurrentGraphNetwork()
																			.getRoute(idRouteStart), gnb.getCurrentGraphNetwork().getStation(idStationStart),
																			giveCost.getCost(gnb.getCurrentGraphNetwork().getRoute(idRouteEnd).getKindRoute(),
																					gnb.getCurrentGraphNetwork().getRoute(idRouteStart).getKindRoute()), timeEnd,
																			pedestrianEnd);
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
						}

					}

				} catch (ViolationOfUnicityInIdentificationException e) {
					throw new GraphReceptionException();
				} catch (ImpossibleValueException e) {
					throw new GraphReceptionException();
				} catch (MissingResourceException e) {
					throw new GraphReceptionException();
				} catch (StationNotOnRoadException e) {
					throw new GraphReceptionException();
				}

			}
		}
	}
}
