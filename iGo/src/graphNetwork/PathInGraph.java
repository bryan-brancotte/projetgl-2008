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
	 * Liste des stations à éviter
	 */
	protected LinkedList<Station> avoidStations;
	/**
	 * le coût total du chemin
	 */
	protected float cost;
	/**
	 * le coût que l'on va devoir payer pour commencer le trajet : le coût d'accès au premier type de ligne
	 */
	protected float entryCost;
	/**
	 * La station de destination
	 */
	protected Station destination;
	/**
	 * Liste des jonctions formant le trajet
	 */
	protected LinkedList<Junction> junctions;
	/**
	 * Critère principale pour la résolution de l'algo
	 */
	protected CriteriousForLowerPath mainCriterious;
	/**
	 * Critère secondaire pour la résolution de l'algo
	 */
	protected CriteriousForLowerPath minorCriterious;
	/**
	 * La station d'origine
	 */
	protected Station origin;
	/**
	 * Liste des type de route que l'on ne veut pas prendre
	 */
	protected LinkedList<KindRoute> refusedKindRoute;
	/**
	 * Le chemin à t'il été résolu?
	 */
	protected boolean resolved;
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
	 * la durée du chemin
	 */
	protected int time;
	/**
	 * GraphNetwork dans lequel le trajet a un sens, une existance
	 */
	protected GraphNetwork univers;

	/**
	 * constructeur d'un trajet
	 */
	private PathInGraph() {
		junctions = new LinkedList<Junction>();
		servicesAlways = new LinkedList<Service>();
		servicesOnce = new LinkedList<Service>();
		steps = new LinkedList<Station>();
		avoidStations = new LinkedList<Station>();
		refusedKindRoute = new LinkedList<KindRoute>();
		// resolved = false;
		cost = Float.NaN;
		entryCost = 1000;
		time = 0;
		destination = null;
		origin = null;
		mainCriterious = CriteriousForLowerPath.NOT_DEFINED;
		minorCriterious = CriteriousForLowerPath.NOT_DEFINED;
	}

	/**
	 * Constructeur specifiant dans quel univers le trajet est créé.
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
	 * Retourne un boolean permetant de savoir si la station est déja dans les stations à éviter.
	 * 
	 * @param s
	 *            la station
	 * @return true si la station est déja dans les stations à éviter.
	 */
	public boolean containsAvoidStation(Station s) {
		return avoidStations.contains(s);
	}

	/**
	 * Retourne un boolean permetant de savoir si la jonction est déja dans le trajet.
	 * 
	 * @param j
	 *            la jonction
	 * @return true si la jonction est déja dans le chemin
	 */
	public boolean containsJunctions(Junction j) {
		return junctions.contains(j);
	}

	/**
	 * Retourne un boolean permetant de savoir si le type de route est en 'liste noir'.
	 * 
	 * @param s
	 *            le type
	 * @return true le type y est déja.
	 */
	public boolean containsRefusedKindRoute(KindRoute k) {
		return refusedKindRoute.contains(k);
	}

	/**
	 * Retourne un boolean permetant de savoir si le service est déja marqué comme "toujours".
	 * 
	 * @param s
	 *            le service
	 * @return true si le service est déja dans le chemin
	 */
	public boolean containsServicesAlways(Service s) {
		return servicesAlways.contains(s);
	}

	/**
	 * Retourne un boolean permetant de savoir si le service est déja marqué comme "au moins une fois".
	 * 
	 * @param s
	 *            le service
	 * @return true si le service est déja dans le chemin
	 */
	public boolean containsServicesOnce(Service s) {
		return servicesOnce.contains(s);
	}

	/**
	 * Retourne un boolean permetant de savoir si la station est déja dans les étapes intermédiaires.
	 * 
	 * @param s
	 *            la station
	 * @return true si la station est déja dans les étapes intermédiaire
	 */
	public boolean containsSteps(Station s) {
		return steps.contains(s);
	}

	/**
	 * Transcrit le trajet en une chaine qui pourra ensuite être relue pour créer de nouveaux trajets
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
				sta.setAttribute("id", getId(s) + "");
				elt.appendChild(sta);
			}
			root.appendChild(elt);

			elt = document.createElement("AvoidStations");
			elt.setAttribute("size", avoidStations.size() + "");
			for (Station s : avoidStations) {
				sta = document.createElement("Station");
				sta.setAttribute("id", getId(s) + "");
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
	 * Retourne le nombre de station à éviter
	 * 
	 * @return la valeur
	 */
	public int getAvoidStationsCount() {
		return avoidStations.size();
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
	 * Retourne la station d'arrivée du trajet.
	 * 
	 * @return
	 */
	public Station getDestination() {
		return destination;
	}

	/**
	 * Retourne la première jonction a partir de laquelle on peut toujours atteindre la fin du trajet.
	 * 
	 * @return l'intersection, ou null si la destination n'est pa satteignable
	 */
	public Junction getFirstJunctionInTheLastAvaiblePart() {
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
	 * Retourne le GraphNetwork dans lequel le trajet a une existance.
	 * 
	 * @return
	 */
	public GraphNetwork getGraph() {
		return univers;
	}

	private String getId(Station s) {
		if (s == null)
			return "";
		return s.getId() + "";
	}

	/**
	 * Retourne un iterateur décrivant les jonction qui forment le chemin dans le sens départ->fin
	 * 
	 * @return retourne un iterateur. Celui ci peut être vide, mais jamais à null
	 */
	public Iterator<Junction> getJunctions() {
		return junctions.iterator();
	}

	/**
	 * Retourne le nombre de jonctions dans le trajet
	 * 
	 * @return la valeur
	 */
	public int getJunctionsCount() {
		return junctions.size();
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
	 * Retourne la station d'origine/de départ du chemin
	 * 
	 * @return
	 */
	public Station getOrigin() {
		return origin;
	}

	/**
	 * Retourne un iterateur sur les kindRoute que l'utilisateur refuse.
	 * 
	 * @return
	 */
	public KindRoute[] getRefusedKindRouteArray() {
		return refusedKindRoute.toArray(new KindRoute[0]);
	}

	/**
	 * Retourne un iterateur sur les kindRoutes que l'utilisateur refuse.
	 * 
	 * @return
	 */
	public Iterator<KindRoute> getRefusedKindRouteIter() {
		return refusedKindRoute.iterator();
	}

	/**
	 * Retourne le nombre type de route refusée
	 * 
	 * @return la valeur
	 */
	public int getRefusedKindRouteCount() {
		return refusedKindRoute.size();
	}

	/**
	 * Retourne un tableau avec tous les services requis tout au long du trajet
	 * 
	 * @return
	 */
	public Service[] getServicesAlwaysArray() {
		return servicesAlways.toArray(new Service[0]);
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
	 * Retourne le nombre service requis tout au long du trajet
	 * 
	 * @return la valeur
	 */
	public int getServicesAlwaysCount() {
		return servicesAlways.size();
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
	 * Retourne un iterateur décrivant les services requis au moins une fois sur le trajet
	 * 
	 * @return
	 */
	public Iterator<Service> getServicesOnceIter() {
		return servicesOnce.iterator();
	}

	/**
	 * Retourne le nombre service requis au moins une fois sur le trajet
	 * 
	 * @return la valeur
	 */
	public int getServicesOnceCount() {
		return servicesOnce.size();
	}

	/**
	 * Retourne un tableau contenant toutes les stations intermédiaires requises.
	 * 
	 * @return
	 */
	public Station[] getStepsArray() {
		return steps.toArray(new Station[0]);
	}

	/**
	 * Retourne un iterateur sur les stations intermédiaires requises.
	 * 
	 * @return
	 */
	public Iterator<Station> getStepsIter() {
		return steps.iterator();
	}

	/**
	 * Retourne le nombre service requis tout au long du trajet
	 * 
	 * @return la valeur
	 */
	public int getStepsCount() {
		return steps.size();
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
	 * Créé le trajet a partir d'un chaine décrivant le trajet. Si la chaine est null ou vide, on ne fait rien
	 * 
	 * @param pathInString
	 * @return true si l'import s'est bien passé
	 */
	protected boolean importPath(String pathInString) {
		try {
			return importPathWithoutCatch(pathInString);
		} catch (SAXException e) {
			e.printStackTrace();
			this.reset();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			this.reset();
			return false;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			this.reset();
			return false;
		}
	}

	/**
	 * Créé le trajet a partir d'un chaine décrivant le trajet. Si la chaine est null ou vide, on ne fait rien
	 * 
	 * @param pathInString
	 * @return true si l'import s'est bien passé
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	protected boolean importPathWithoutCatch(String pathInString) throws SAXException, IOException,
			ParserConfigurationException {
		if (pathInString == null || pathInString.compareTo("") == 0)
			return false;
		Document doc;
		int i;
		NodeList nodesPathInGraph = null;
		NodeList nodesOption = null;
		Node node = null;
		String s;
		Station station;
		Service service;

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
							s = node.getAttributes().getNamedItem("id").getNodeValue();
							if (s.compareTo("") != 0)
								origin = univers.getStation(Integer.parseInt(s));
							else
								origin = null;

						} else if (node.getNodeName().compareTo("Destination") == 0) {
							s = node.getAttributes().getNamedItem("id").getNodeValue();
							if (s.compareTo("") != 0)
								destination = univers.getStation(Integer.parseInt(s));
							else
								destination = null;

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
									service = univers.getService(Integer.parseInt(nodesOption.item(j).getAttributes()
											.getNamedItem("id").getNodeValue()));
									if (service != null)
										servicesAlways.add(service);
								}

						} else if (node.getNodeName().compareTo("SevicesOnce") == 0) {
							nodesOption = node.getChildNodes();
							for (int j = 0; j < nodesOption.getLength(); j++)
								if (nodesOption.item(j).getNodeName().compareTo("Service") == 0) {
									service = univers.getService(Integer.parseInt(nodesOption.item(j).getAttributes()
											.getNamedItem("id").getNodeValue()));
									if (service != null)
										this.servicesOnce.add(service);
								}

						} else if (node.getNodeName().compareTo("Steps") == 0) {
							nodesOption = node.getChildNodes();
							for (int j = 0; j < nodesOption.getLength(); j++)
								if (nodesOption.item(j).getNodeName().compareTo("Station") == 0) {
									station = univers.getStation(Integer.parseInt(nodesOption.item(j).getAttributes()
											.getNamedItem("id").getNodeValue()));
									if (station != null)
										steps.add(station);
								}

						} else if (node.getNodeName().compareTo("AvoidStations") == 0) {
							nodesOption = node.getChildNodes();
							for (int j = 0; j < nodesOption.getLength(); j++)
								if (nodesOption.item(j).getNodeName().compareTo("Station") == 0) {
									station = univers.getStation(Integer.parseInt(nodesOption.item(j).getAttributes()
											.getNamedItem("id").getNodeValue()));
									if (station != null)
										avoidStations.add(station);
								}
						}
					}
				}
			}
		return true;
	}

	/**
	 * Créé le trajet a partir d'un chaine décrivant le trajet. Si la chaine est null ou vide, on ne fait rien
	 * 
	 * @param pathInString
	 */
	protected boolean importPath(PathInGraph org) {
		if (org == null)
			return false;
		origin = org.getOrigin();
		destination = org.getDestination();
		cost = org.getCost();
		time = org.getTime();
		mainCriterious = org.getMainCriterious();
		minorCriterious = org.getMinorCriterious();
		servicesAlways.clear();
		servicesAlways.addAll(org.servicesAlways);
		servicesOnce.clear();
		servicesOnce.addAll(org.servicesOnce);
		avoidStations.clear();
		avoidStations.addAll(org.avoidStations);
		refusedKindRoute.clear();
		refusedKindRoute.addAll(org.refusedKindRoute);
		steps.clear();
		steps.addAll(org.steps);
		return true;
	}

	/**
	 * Accesseur permettant de savoir si le trajet à été résolu, et plus modifié ensuite
	 * 
	 * @return
	 */
	public boolean isResolved() {
		return resolved;
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
	 * Permet de savoir si on peut toujours arpenter le trajet à partir de la jonction passé en paramètre jusqu'à la fin
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

	/**
	 * Permet de savoir si le chemin est valide pour la résolution.
	 * 
	 * @return true si on peut le résoudre
	 */
	protected boolean isValideForSolving() {
		if (destination == null)
			return false;
		if (origin == null)
			return false;
		if (mainCriterious == CriteriousForLowerPath.NOT_DEFINED)
			return false;
		if (minorCriterious == CriteriousForLowerPath.NOT_DEFINED)
			return false;
		if (destination == origin)
			return false;
		return true;
	}

	/**
	 * Accesseur de coût de début du chemin
	 * 
	 * @return
	 */
	public float getEntryCost() {
		return entryCost;
	}

	protected void reset() {

		origin = null;
		destination = null;
		cost = Float.NaN;
		entryCost = 1000;
		time = 0;
		resolved = false;
		junctions.clear();
		servicesAlways.clear();
		servicesOnce.clear();
		steps.clear();
		avoidStations.clear();
		refusedKindRoute.clear();
		mainCriterious = CriteriousForLowerPath.NOT_DEFINED;
		minorCriterious = CriteriousForLowerPath.NOT_DEFINED;
	}

	public String toString() {
		String ret = "";
		ret += "PathInGraph :\n";
		ret += "From:" + origin + "\n";
		ret += "To : " + destination + "\n";
		ret += "Cost:" + cost + "\n";
		ret += "Time:" + time + "\n";
		ret += "servicesOnce     :";
		for (Service s : servicesOnce)
			ret += s + ", ";
		ret += "\nservicesAlways   :";
		for (Service s : servicesAlways)
			ret += s + ", ";
		ret += "\nsteps            :";
		for (Station s : steps)
			ret += s + ", ";
		ret += "\navoidStations    :";
		for (Station s : avoidStations)
			ret += s + ", ";
		ret += "\nrefusedKindRoute :";
		for (KindRoute s : refusedKindRoute)
			ret += s + ", ";
		return ret;
	}
}
