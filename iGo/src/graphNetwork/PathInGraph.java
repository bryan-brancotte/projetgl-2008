package graphNetwork;

import iGoMaster.Algo.CriteriousForLowerPath;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author iGo
 */
public class PathInGraph {

	/**
	 * GraphNetwork dans lequel le trajet a un sens, une existance
	 */
	protected GraphNetwork univers;
	/**
	 * Liste des stations à éviter
	 */
	protected LinkedList<Station> avoidStations;

	/**
	 * Le chemin à t'il été résolu?
	 */
	protected boolean resolved;
	/**
	 * le coût du chemin
	 */
	protected float cost;
	/**
	 * la durée du chemin
	 */
	protected int time;
	/**
	 * La station de destination
	 */
	protected Station destination;
	/**
	 * Liste des jonctions formant le trajet
	 */
	protected LinkedList<Junction> junctions;
	/**
	 * La station d'origine
	 */
	protected Station origin;
	/**
	 * Liste des services obligatoire sur l'ensemble des stations intermédiaire et extrèmes du trajet.
	 */
	protected LinkedList<Service> servicesAlways;
	/**
	 * Liste des services à recontrer au moins une fois sur le trajet.
	 */
	protected LinkedList<Service> servicesOnce;
	/**
	 * Liste des stations intermédiaires obligatoire.
	 */
	protected LinkedList<Station> steps;
	/**
	 * Critère principale pour la résolution de l'algo
	 */
	protected CriteriousForLowerPath mainCriterious;
	/**
	 * Critère secondaire pour la résolution de l'algo
	 */
	protected CriteriousForLowerPath minorCriterious;

	public boolean isResolved() {
		return resolved;
	}

	/**
	 * Accesseur pour le critère principale dans la résolution de l'algo
	 * 
	 * @return
	 */
	public CriteriousForLowerPath getMainCriterious() {
		return mainCriterious;
	}

	/**
	 * Accesseur pour le critère secondaire dans la résolution de l'algo
	 * 
	 * @return
	 */
	public CriteriousForLowerPath getMinorCriterious() {
		return minorCriterious;
	}

	/**
	 * constructeur d'un trajet
	 */
	private PathInGraph() {
		junctions = new LinkedList<Junction>();
		servicesAlways = new LinkedList<Service>();
		servicesOnce = new LinkedList<Service>();
		steps = new LinkedList<Station>();
		avoidStations = new LinkedList<Station>();
		// resolved = false;
		cost = Float.NaN;
		time = 0;
		destination = null;
		origin = null;
		mainCriterious = CriteriousForLowerPath.NOT_DEFINED;
		minorCriterious = CriteriousForLowerPath.NOT_DEFINED;

	}

	/**
	 * Constructeur specifiant dans quel univers le trajet est cree.
	 * 
	 * @param graph
	 * @throws NullPointerException
	 *             si le paramètre est null
	 */
	protected PathInGraph(GraphNetwork graph) {
		this();
		if (graph == null)
			throw new NullPointerException();
		this.univers = graph;
	}

	/**
	 * Transcrit le trajet en une chaine qui pourra ensuite etre relue pour creer de nouveau trajet
	 * 
	 * @return
	 */
	public String exportPath() {
		try {
			Document document;
			ByteArrayOutputStream outStream;
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			Element root, elt, sta;

			root = document.createElement("PathInGraph");
			document.appendChild(root);

			elt = document.createElement("Origin");
			elt.setAttribute("id", getId(origin));
			root.appendChild(elt);
			elt = document.createElement("Destination");
			elt.setAttribute("id", getId(destination));
			root.appendChild(elt);
			elt = document.createElement("Cost");
			elt.setAttribute("value", cost + "");
			root.appendChild(elt);
			elt = document.createElement("Time");
			elt.setAttribute("value", time + "");
			root.appendChild(elt);
			elt = document.createElement("MainCriterious");
			elt.setAttribute("value", mainCriterious + "");
			root.appendChild(elt);
			elt = document.createElement("MinorCriterious");
			elt.setAttribute("value", minorCriterious + "");
			root.appendChild(elt);

			elt = document.createElement("SevicesAlways");
			elt.setAttribute("size", servicesAlways.size() + "");
			for (Service s : servicesAlways) {
				sta = document.createElement("Service");
				sta.setAttribute("id", s.getId() + "");
				elt.appendChild(sta);
			}
			root.appendChild(elt);

			elt = document.createElement("SevicesOnce");
			elt.setAttribute("size", servicesOnce.size() + "");
			for (Service s : servicesOnce) {
				sta = document.createElement("Service");
				sta.setAttribute("id", s.getId() + "");
				elt.appendChild(sta);
			}
			root.appendChild(elt);

			elt = document.createElement("Steps");
			elt.setAttribute("size", steps.size() + "");
			for (Station s : steps) {
				sta = document.createElement("Station");
				sta.setAttribute("id", s.getId() + "");
				elt.appendChild(sta);
			}
			root.appendChild(elt);

			elt = document.createElement("AvoidStations");
			elt.setAttribute("size", avoidStations.size() + "");
			for (Station s : avoidStations) {
				sta = document.createElement("Station");
				sta.setAttribute("id", s.getId() + "");
				elt.appendChild(sta);
			}
			root.appendChild(elt);

			Source source = new DOMSource(document);
			// Cration du fichier de sortie

			Result resultat = new StreamResult(outStream = new ByteArrayOutputStream());

			// Configuration du transformer
			TransformerFactory fabrique = TransformerFactory.newInstance();
			Transformer transformer = fabrique.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			// Transformation
			transformer.transform(source, resultat);
			// System.out.println(outStream.toString("UTF-8"));
			// return outStream.toString("UTF-8");
			String[] ret = outStream.toString("UTF-8").split("\n");
			int delta = 0;
			String retBetter = "";
			for (String s : ret) {
				s = s.substring(0, s.length() - 1);
				if (s.endsWith("/>")) {
					for (int i = 0; i < delta; i++)
						retBetter += "\t";
					retBetter += s;
				} else if (s.startsWith("</")) {
					delta--;
					for (int i = 0; i < delta; i++)
						retBetter += "\t";
					retBetter += s;
				} else if (s.startsWith("<?")) {
					retBetter += s;
				} else {
					for (int i = 0; i < delta; i++)
						retBetter += "\t";
					retBetter += s;
					delta++;
				}
				retBetter += "\n";

			}
			return retBetter;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getId(Station s) {
		if (s == null)
			return "null";
		return s.getId() + "";
	}

	/**
	 * Retourne un tableau avec l'ensemble des stations à éviter
	 * 
	 * @return le tableau avec l'ensemble des stations a éviter, ou un tableau vide s'il n'y en a pas.
	 */
	public Station[] getAvoidStationsArray() {
		return avoidStations.toArray(new Station[0]);
	}

	/**
	 * Retourne un iterateur décrivant l'ensemble des stations à éviter
	 * 
	 * @return l'iterateur, ou un iterateur terminé s'il n'y a pas de station à éviter
	 */
	public Iterator<Station> getAvoidStationsIter() {
		return avoidStations.iterator();
	}

	/**
	 * retourne le coût du trajet
	 * 
	 * @return le coût
	 */
	public float getCost() {
		return this.cost;
	}

	/**
	 * Retourne la station d'arrivé du trajet.
	 * 
	 * @return
	 */
	public Station getDestination() {
		return destination;
	}

	/**
	 * Retourne la station d'origine/de départ du chemin
	 * 
	 * @return
	 */
	public Station getOrigin() {
		return origin;
	}

	/**
	 * Retourne la première jonction a partir de laquel on peut toujours atteindre la fin du trajet.
	 * 
	 * @return l'intersection, ou null si la destination n'est pa satteignable
	 */
	public Junction getFirstJunctionInTheLastAvaiblePart() {
		// Rémi : trop long et trop couteux : O(2n) et création d'un vector (beurk)
		// Iterator<Junction> j1 = junctions.iterator();
		// Vector<Junction> jonctionInv = new Vector<Junction>();
		// Junction jonctionTrouve = null;
		// int nbJonction = junctions.size();
		//
		// while (j1.hasNext()) {
		// jonctionInv.add(--nbJonction, j1.next());
		// }
		// for (int i = 0; i < junctions.size(); i++) {
		// if (jonctionInv.elementAt(i).isEnable())
		// jonctionTrouve = jonctionInv.elementAt(i);
		// else
		// return jonctionTrouve;
		// }
		// return jonctionTrouve;
		// Voila un meilleurs solution (sans vector et avec un complexite en O(n)
		Iterator<Junction> itJ = junctions.iterator();
		Junction last = null;
		Junction current;
		while (itJ.hasNext()) {
			// on prend l'item courrant
			current = itJ.next();
			// on regarde si la dernière utilisable est définit, si ce n'est pas le cas on la définit à la station
			// courante
			if (last == null)
				last = current;
			// si la station courante n'est pas utilisable, alors la dernière station utilisable que l'on gardait en
			// mémoire n'est pas valide, on la met donc à null.
			// Cela aura pour concéquence que la prochaine station sera mise dans last, permetant ainsi de trouver la
			// solution
			if (!current.isEnable())
				current = null;
		}
		return last;
	}

	/**
	 * Retourne le GraphNetwork dans lequel le trajet à une existance.
	 * 
	 * @return
	 */
	public GraphNetwork getGraph() {
		return univers;
	}

	/**
	 * Retourne un iterateur décrivant les jonction qui forme le chemin dans le sens départ->fin
	 * 
	 * @return retourne un iterateur. Celui ci peut être vide, mais jamais à null
	 */
	public Iterator<Junction> getJunctions() {
		return junctions.iterator();
	}

	/**
	 * use getServicesAlwaysArray()
	 */
	@Deprecated
	public Service[] getSevicesAlwaysArray() {
		return servicesAlways.toArray(new Service[0]);
	}

	/**
	 * Retourne un tableau avec tout les services requis tout au long du trajet
	 * 
	 * @return
	 */
	public Service[] getServicesAlwaysArray() {
		return servicesAlways.toArray(new Service[0]);
	}

	/**
	 * use getServicesAlwaysIter()
	 */
	@Deprecated
	public Iterator<Service> getSevicesAlwaysIter() {
		return servicesAlways.iterator();
	}

	/**
	 * Retourne un iterateur décrivant les services requis tout au long du trajet
	 * 
	 * @return
	 */
	public Iterator<Service> getServicesAlwaysIter() {
		return servicesAlways.iterator();
	}

	/**
	 * use getServicesOnceArray()
	 */
	@Deprecated
	public Service[] getSevicesOnceArray() {
		return servicesOnce.toArray(new Service[0]);
	}

	/**
	 * Retourne un tableau avec tout les services requis au moins une fois sur le trajet.
	 * 
	 * @return
	 */
	public Service[] getServicesOnceArray() {
		return servicesOnce.toArray(new Service[0]);
	}

	/**
	 * use getServicesOnceIter()
	 */
	@Deprecated
	public Iterator<Service> getSevicesOnceIter() {
		return servicesOnce.iterator();
	}

	/**
	 * Retourne un iterateur décrivant les services requis au moins une fois sur le trajet
	 * 
	 * @return
	 */
	public Iterator<Service> getServicesOnceIter() {
		return servicesOnce.iterator();
	}

	/**
	 * Retourne un tableau contenant toutes les stations intermédiaire requise.
	 * 
	 * @return
	 */
	public Station[] getStepsArray() {
		return steps.toArray(new Station[0]);
	}

	/**
	 * Retourne un iterateur sur les stations intermédiaire requise.
	 * 
	 * @return
	 */
	public Iterator<Station> getStepsIter() {
		return steps.iterator();
	}

	/**
	 * Retourne la durée du trajet
	 * 
	 * @return
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * Créer le trajet a partir d'un chaine décrivant le trajet. SI la chaine est null ou vide, on ne fait rien
	 * 
	 * @param pathInString
	 */
	protected void importPath(String pathInString) {
		if (pathInString == null || pathInString.isEmpty())
			return;
		Document doc;
		int i;
		NodeList nodesPathInGraph = null;
		NodeList nodesOption = null;
		Node node = null;
		String s;
		Station station;
		Service service;

		origin = null;
		destination = null;
		cost = Float.NaN;
		time = 0;
		resolved = false;
		junctions.clear();
		servicesAlways.clear();
		servicesOnce.clear();
		steps.clear();
		avoidStations.clear();
		mainCriterious = CriteriousForLowerPath.NOT_DEFINED;
		minorCriterious = CriteriousForLowerPath.NOT_DEFINED;

		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					new ByteArrayInputStream(pathInString.getBytes()));

			i = 0;
			while ((nodesPathInGraph == null) && (i < doc.getFirstChild().getChildNodes().getLength())) {
				if (doc.getChildNodes().item(i).getNodeName().compareTo("PathInGraph") == 0) {
					nodesPathInGraph = doc.getChildNodes().item(i).getChildNodes();
				}
				i++;
			}
			if (nodesPathInGraph != null)
				for (i = 0; i < nodesPathInGraph.getLength(); i++) {
					if ((node = nodesPathInGraph.item(i)).getNodeName().compareTo("#text") != 0) {
						if (!node.getNodeName().startsWith("#")) {
							if (node.getNodeName().compareTo("Origin") == 0) {
								origin = univers.getStation(Integer.parseInt(node.getAttributes().getNamedItem("id")
										.getNodeValue()));

							} else if (node.getNodeName().compareTo("Destination") == 0) {
								destination = univers.getStation(Integer.parseInt(node.getAttributes().getNamedItem(
										"id").getNodeValue()));

							} else if (node.getNodeName().compareTo("Cost") == 0) {
								cost = Float.parseFloat(node.getAttributes().getNamedItem("value").getNodeValue());
							} else if (node.getNodeName().compareTo("Time") == 0) {
								time = Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue());

							} else if (node.getNodeName().compareTo("MainCriterious") == 0) {
								if ((s = node.getAttributes().getNamedItem("value").getNodeValue())
										.compareTo(CriteriousForLowerPath.CHANGE.toString()) == 0)
									mainCriterious = CriteriousForLowerPath.CHANGE;
								else if (s.compareTo(CriteriousForLowerPath.COST.toString()) == 0)
									mainCriterious = CriteriousForLowerPath.COST;
								else if (s.compareTo(CriteriousForLowerPath.TIME.toString()) == 0)
									mainCriterious = CriteriousForLowerPath.TIME;

							} else if (node.getNodeName().compareTo("MinorCriterious") == 0) {
								if ((s = node.getAttributes().getNamedItem("value").getNodeValue())
										.compareTo(CriteriousForLowerPath.CHANGE.toString()) == 0)
									minorCriterious = CriteriousForLowerPath.CHANGE;
								else if (s.compareTo(CriteriousForLowerPath.COST.toString()) == 0)
									minorCriterious = CriteriousForLowerPath.COST;
								else if (s.compareTo(CriteriousForLowerPath.TIME.toString()) == 0)
									minorCriterious = CriteriousForLowerPath.TIME;

							} else if (node.getNodeName().compareTo("SevicesAlways") == 0) {
								nodesOption = node.getChildNodes();
								for (int j = 0; j < nodesOption.getLength(); j++)
									if (nodesOption.item(j).getNodeName().compareTo("Service") == 0) {
										service = univers.getService(Integer.parseInt(nodesOption.item(j)
												.getAttributes().getNamedItem("id").getNodeValue()));
										if (service != null)
											servicesAlways.add(service);
									}

							} else if (node.getNodeName().compareTo("SevicesOnce") == 0) {
								nodesOption = node.getChildNodes();
								for (int j = 0; j < nodesOption.getLength(); j++)
									if (nodesOption.item(j).getNodeName().compareTo("Service") == 0) {
										service = univers.getService(Integer.parseInt(nodesOption.item(j)
												.getAttributes().getNamedItem("id").getNodeValue()));
										if (service != null)
											this.servicesOnce.add(service);
									}

							} else if (node.getNodeName().compareTo("Steps") == 0) {
								nodesOption = node.getChildNodes();
								for (int j = 0; j < nodesOption.getLength(); j++)
									if (nodesOption.item(j).getNodeName().compareTo("Station") == 0) {
										station = univers.getStation(Integer.parseInt(nodesOption.item(j)
												.getAttributes().getNamedItem("id").getNodeValue()));
										if (station != null)
											steps.add(station);
									}

							} else if (node.getNodeName().compareTo("AvoidStations") == 0) {
								nodesOption = node.getChildNodes();
								for (int j = 0; j < nodesOption.getLength(); j++)
									if (nodesOption.item(j).getNodeName().compareTo("Station") == 0) {
										station = univers.getStation(Integer.parseInt(nodesOption.item(j)
												.getAttributes().getNamedItem("id").getNodeValue()));
										if (station != null)
											avoidStations.add(station);
									}

							}
						}
						// langues.put(nodesPathInGraph.item(i).getAttributes().getNamedItem("value").getNodeValue(),
						// nodesPathInGraph.item(i).getAttributes().getNamedItem("balise").getNodeValue());
					}
				}

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Permet de savoir si on peut toujours utiliser le trajet de bout en bout.
	 * 
	 * @return
	 */
	public boolean isStillAvaible() {
		Iterator<Junction> j1 = junctions.iterator();
		while (j1.hasNext()) {
			if (!j1.next().isEnable())
				return false;
		}
		return true;
	}

	/**
	 * Permet de savoir si on peut toujours arpenter le trajet à partir de la jonction passé en paramètre jusqu'a la fin
	 * 
	 * @param junction
	 * @return
	 */
	public boolean isStillAvaible(Junction junction) {
		Iterator<Junction> j1 = junctions.iterator();
		boolean found = false;
		Junction tmp;
		while (j1.hasNext()) {
			if ((tmp = j1.next()).equals(junction))
				found = true;
			if (found && !tmp.isEnable())
				return false;
		}
		return true;
	}

	// /**
	// * Un desciptif du trajet
	// *
	// * @return
	// */
	// public String toMyString() { //
	// String retour = "<PathInGraph>" + cost + ";" + time + ";";
	// return retour;
	// retour.concat(univers.toMyString());
	//				
	// retour.concat("<stationList>");
	// Iterator<Station> it1=avoidStations.iterator();
	// while(it1.hasNext()){
	// retour.concat(it1.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</stationList>");
	// retour.concat(destination.toMyString());
	// retour.concat("<junctionList>");
	// Iterator<Station> it2=avoidStations.iterator();
	// while(it2.hasNext()){
	// retour.concat(it2.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</junctionList>");
	// retour.concat(origin.toMyString());
	// retour.concat("<serviceList>");
	// Iterator<Service> it3=sevicesAlways.iterator();
	// while(it3.hasNext()){
	// retour.concat(it3.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</junctionList>");
	// retour.concat("<serviceList>");
	// Iterator<Service> it4=sevicesOnce.iterator();
	// while(it4.hasNext()){
	// retour.concat(it4.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</junctionList>");
	// retour.concat("<stationList>");
	// Iterator<Station> it5=steps.iterator();
	// while(it5.hasNext()){
	// retour.concat(it5.next().toMyString());
	// retour.concat(";");
	// }
	// retour.concat("</stationList>");
	//				
	//				
	// retour.concat("</PathInGraph>");
	// }

}
