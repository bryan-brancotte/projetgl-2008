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
import java.io.IOException;
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

public class GraphNetworkReceiverFolder implements GraphNetworkReceiver {

	private HashMap<String, AvailableNetwork> networks;
	private File folder;
	protected org.jdom.Document doc;
	protected final Lock verrou = new ReentrantLock();

	public GraphNetworkReceiverFolder(String f) {
		super();
		networks = new HashMap<String, AvailableNetwork>();
		folder = new File(f.replace("\\", "/"));
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
//	public boolean updateGraph() {
//		return false;
//	}

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

		if (networks.get(networkChosen) == null)
			throw new GraphReceptionException();
		else {
			try {
				// System.out.println(networks.get(networkChosen));
				// System.out.println(networks.get(networkChosen).getFichier());
				// System.out.println(networks.get(networkChosen).getFichier().toURI());

				SAXBuilder sxb = new SAXBuilder();

				GraphNetworkCostReceiverHardWritten giveCost;
				giveCost = new GraphNetworkCostReceiverHardWritten();

				synchronized (verrou) {

					try {
						doc = sxb.build(networks.get(networkChosen).getFichier().toURI().toString());
					} catch (JDOMException e) {
						System.err.println("Ce Fichier XML n\'est pas un fichier XML valide");
						e.printStackTrace();
					} catch (IOException e) {
						System.err.println("Erreur de lecture");
						e.printStackTrace();
					}

					Element racine;
					racine = doc.getRootElement();

					List<Element> services = racine.getChild("ServicesList").getChildren("Service");
					for (Element service : services) {
						List<Element> nodeChilds = service.getChildren();
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
//						 System.out.println("Adding Service : " + id + " : " + description);
					}

					List<Element> stations = racine.getChild("StationsList").getChildren("Station");
					for (Element station : stations) {
						List<Element> nodeChilds = station.getChildren();
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

								for (Element stationService : stationServicesList) {
									if (stationService.getName().compareTo("ID") == 0) {
										idS = Integer.parseInt(stationService.getTextTrim());
										idServicesStation.add(idS);
									}
								}

							}
						}
						if (id != 0 && !name.equals("")) {
							Station s = gnb.addStation(id, name);
//							 System.out.println("Adding Station : " + name);
							for (int l = 0; l < idServicesStation.size(); l++) {
//								 System.out.println("\t Adding service : " + gnb.getCurrentGraphNetwork().getService(idServicesStation.get(l)).getName());
								gnb.addServiceToStation(s, gnb.getCurrentGraphNetwork().getService(idServicesStation.get(l)));
							}
						}
					}

					List<Element> routes = racine.getChild("RoutesList").getChildren("Route");
					for (Element route : routes) {
						List<Element> nodeChilds = route.getChildren();
						String id = "";
						String kindR = "";
						Vector<Vector<Integer>> idSectionsStations = new Vector<Vector<Integer>>();
						Vector<Integer> timeBetweenStations = new Vector<Integer>();

						for (Element child : nodeChilds) {
							if (child.getName().compareTo("ID") == 0) {
								id = child.getTextTrim();
//								 System.out.println("id route " + id);
							}
							else if (child.getName().compareTo("Kind") == 0) {
								kindR = child.getTextTrim();
//								 System.out.println("kind route " + kindR);
							}
							else if (child.getName().compareTo("RouteSectionsList") == 0) {
								List<Element> sectionsList = child.getChildren();

								for (Element section : sectionsList) {
									timeBetweenStations.add(Integer.parseInt(section.getAttributeValue("TimeBetweenStations")));
//									 System.out.println("Time between stations " + Integer.parseInt(section.getAttributeValue("TimeBetweenStations")));

									List<Element> sectionStationsList = section.getChild("SectionStationsList").getChildren();

									Vector<Integer> idStations = new Vector<Integer>();

									for (Element station : sectionStationsList) {
										if (station.getName().compareTo("ID") == 0) {
//											 System.out.println("ID "+ Integer.parseInt(station.getTextTrim()));
											idStations.add(Integer.parseInt(station.getTextTrim()));
										}

									}
									idSectionsStations.add(idStations);
								}
							}

							if (idSectionsStations.size() > 0 && timeBetweenStations.size() > 0 && !id.equals("") && !kindR.equals("")) {
//								 System.out.println("nous avons " + idSectionsStations.size() + " sections");
								Route r = gnb.addRoute(id, kindR);
//								 System.out.println("Adding Route : " + id);
								// System.out.println();
								// System.out.println();
								gnb.defineEntryCost(KindRoute.getKindFromString(kindR), giveCost.getCost(KindRoute.getKindFromString(kindR)));

								for (int j = 0; j < idSectionsStations.size(); j++) {
//									System.out.println("J : " + j);
									for (int k = 0; k < idSectionsStations.get(j).size(); k++) {
//										System.out.println("k : " + k);
										gnb.addStationToRoute(r, gnb.getCurrentGraphNetwork().getStation(idSectionsStations.get(j).get(k)),
												timeBetweenStations.get(j));
//										System.out.println("Adding station to route " + r.getId() + " " + gnb.getCurrentGraphNetwork().getStation(idSectionsStations.get(j).get(k)) + " " + timeBetweenStations.get(j));
//										
//										Iterator iTr = gnb.getCurrentGraphNetwork().getStation(idSectionsStations.get(j).get(k)).getRoutes();
//										System.out.println("Verification station " + gnb.getCurrentGraphNetwork().getStation(idSectionsStations.get(j).get(k)));
//										while (iTr.hasNext()) {
//											Route rte = (Route) iTr.next();
//											System.out.println("\t route " + rte);
//										}
										
//										System.out.println("Adding station to route " + r.getId());
//										gnb.getCurrentGraphNetwork().getStation(1).
									}
								}
//								System.out.println();
//								System.out.println();
							}
						}
					}
//					System.out.println();
//					System.out.println();

					List<Element> interchanges = racine.getChild("InterchangesList").getChildren("Interchange");
					for (Element interchange : interchanges) {
						List<Element> startChilds = interchange.getChild("Start").getChildren();

						int idStationStart = 0;
						String idRouteStart = "";
						for (Element child : startChilds) {
							if (child.getName().compareTo("Station") == 0) {
								idStationStart = Integer.parseInt(child.getTextTrim());
//								 System.out.println("ID Station start : " + idStationStart);
							}
							else if (child.getName().compareTo("Route") == 0) {
								idRouteStart = child.getTextTrim();
//								 System.out.println("ID Route start : " + idRouteStart);
							}
						}

						List<Element> endList = interchange.getChild("Endlist").getChildren();

						for (Element end : endList) {
							List<Element> endChilds = end.getChildren();
							int idStationEnd = 0;
							String idRouteEnd = "";
							boolean freeEnd = true;
							boolean pedestrianEnd = true;
							int timeEnd = 0;
							for (Element child : endChilds) {
//								idStationEnd = 0;
//								idRouteEnd = "";
//								freeEnd = true;
//								pedestrianEnd = true;
//								timeEnd = 0;


								if (child.getName().compareTo("Station") == 0) {
//									 System.out.println("\t ID Station end : " + child.getTextTrim());
									idStationEnd = Integer.parseInt(child.getTextTrim());
								}
								else if (child.getName().compareTo("Route") == 0) {
//									 System.out.println("\t ID Route end : " + child.getTextTrim());
									idRouteEnd = child.getTextTrim();
								}
								else if (child.getName().compareTo("Free") == 0) {
//									 System.out.println("\t ID free end : " + child.getTextTrim());
									freeEnd = Boolean.parseBoolean(child.getTextTrim());
								}
								else if (child.getName().compareTo("Pedestrian") == 0) {
//									 System.out.println("\t ID pedestrian end : " + child.getTextTrim());
									pedestrianEnd = Boolean.parseBoolean(child.getTextTrim());
								}
								else if (child.getName().compareTo("Time") == 0) {
//									 System.out.println("\t ID time end : " + child.getTextTrim());
									timeEnd = Integer.parseInt(child.getTextTrim());
								}
							}

							if (idStationStart != 0 && !idRouteStart.equals("") && idStationEnd != 0 && !idRouteEnd.equals("")) {

								if (freeEnd == true) {
//									System.out.println("Free");
//									System.out.println("\t " + gnb.getCurrentGraphNetwork().getRoute(idRouteStart) + " " + gnb.getCurrentGraphNetwork().getStation(idStationStart) + " " + gnb.getCurrentGraphNetwork().getRoute(idRouteEnd) + " " + gnb.getCurrentGraphNetwork().getStation(idStationEnd) + " " + timeEnd + " " + pedestrianEnd);
									gnb.linkStationBidirectional(gnb.getCurrentGraphNetwork().getRoute(idRouteStart), gnb.getCurrentGraphNetwork().getStation(
											idStationStart), gnb.getCurrentGraphNetwork().getRoute(idRouteEnd), gnb.getCurrentGraphNetwork()
											.getStation(idStationEnd), 0, timeEnd, pedestrianEnd);
								}
								else {
//									System.out.println("NOT Free " + giveCost.getCost(gnb.getCurrentGraphNetwork().getRoute(idRouteStart).getKindRoute(), gnb.getCurrentGraphNetwork().getRoute(idRouteEnd).getKindRoute()));
//									System.out.println("\t " + gnb.getCurrentGraphNetwork().getRoute(idRouteStart) + " " + gnb.getCurrentGraphNetwork().getStation(idStationStart) + " " + gnb.getCurrentGraphNetwork().getRoute(idRouteEnd) + " " + gnb.getCurrentGraphNetwork().getStation(idStationEnd) + " " + timeEnd + " " + pedestrianEnd);
									gnb.linkStation(gnb.getCurrentGraphNetwork().getRoute(idRouteStart), gnb.getCurrentGraphNetwork().getStation(
											idStationStart), gnb.getCurrentGraphNetwork().getRoute(idRouteEnd), gnb.getCurrentGraphNetwork()
											.getStation(idStationEnd), giveCost.getCost(gnb.getCurrentGraphNetwork().getRoute(idRouteStart)
											.getKindRoute(), gnb.getCurrentGraphNetwork().getRoute(idRouteEnd).getKindRoute()), timeEnd,
											pedestrianEnd);
									
//									gnb.linkStation(routeOrigin, stationOrigin, routeDestination, stationDestination, cost, timeBetweenStations, pedestrian)
									gnb.linkStation(gnb.getCurrentGraphNetwork().getRoute(idRouteEnd), gnb.getCurrentGraphNetwork().getStation(
											idStationEnd), gnb.getCurrentGraphNetwork().getRoute(idRouteStart), gnb.getCurrentGraphNetwork()
											.getStation(idStationStart), giveCost.getCost(gnb.getCurrentGraphNetwork().getRoute(idRouteEnd)
											.getKindRoute(), gnb.getCurrentGraphNetwork().getRoute(idRouteStart).getKindRoute()), timeEnd,
											pedestrianEnd);
								}
							}
//							System.out.println("-");
						}
//						System.out.println();
//						System.out.println();

					}
				}

			} catch (ViolationOfUnicityInIdentificationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ImpossibleValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MissingResourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (StationNotOnRoadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {

		GraphNetworkReceiverFolder gnrf = new GraphNetworkReceiverFolder(System.getProperty("user.dir")+"\\ressources\\xml");

		try {

			gnrf.buildNewGraphNetwork(new GraphNetworkBuilder(), "NetworkGL2008.xml", new GraphNetworkCostReceiverHardWritten());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
