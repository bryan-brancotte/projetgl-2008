package streamInFolder.recentsAndFavoritesGraphs;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import iGoMaster.RecentsAndFavoritesPathsInGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pathsAndFavorites.RecentsAndFavoritesPathsInGraphReader;

/**
 * Classe interagissant avec les chemins récents/favoris, stockés dans un système de fichier
 * 
 * Cette classe implémente l'interface RecentsAndFavoritesPathsInGraphReader, ce qui garantit que toute classe de ce type (qui se charge en 'dur' d'interagir avec les chemins
 * récents/favoris), sera compatible avec le reste du programme
 * 
 * @author iGo
 */
public class RecentsAndFavoritesPathsInGraphReaderInFolder implements RecentsAndFavoritesPathsInGraphReader {

	/**
	 * Liste chaînée des chemins récents, les plus récents en premiers
	 */
	private LinkedList<PathInGraphCollectionBuilder> recents;

	/**
	 * Liste chaînée des chemins favoris, les plus récents en premiers
	 */
	private LinkedList<PathInGraphCollectionBuilder> favorites;

	/**
	 * Tableau associatif PathInGraph->File, ce qui permet de retrouver le fichier d'un chemin, par exemple lorsqu'il est ajouté dans les favoris
	 */
	private HashMap<PathInGraph, File> filesMap;

	/**
	 * Tableau associatif PathInGraph->PathInGraphCollectionBuilder, qui permet de faire l'association entre un chemin et son "constructeur", car lorsqu'on marque un chemin en tant
	 * que favori, on utilise un PathInGraph, or pour donner la liste de tous les chemins on doit renvoyer un PathInGraphCollectionBuilder
	 */
	private HashMap<PathInGraph, PathInGraphCollectionBuilder> pigMap;

	/**
	 * Dossier dans lequel on travaille
	 */
	private File folder;

	/**
	 * Chemin d'accès au dossier de travail
	 */
	private String path;

	/**
	 * Numéro du prochain chemin sauvegardé
	 */
	private int numFile = 0;

	/**
	 * Nombre de fichiers dans le répertoire de travail
	 */
	private int nbFiles = 0;

	/**
	 * Réseau dans lequel on travaille
	 */
	private GraphNetworkBuilder gnb;

	/**
	 * @see RecentsAndFavoritesPathsInGraph#MAX_RECENTS_PATHS
	 */
	private int MAX_RECENTS_PATHS;

	/**
	 * Dossier "iGo"
	 */
	public static String PATH_TO_CONFIG_HOME_DIR = "/.iGo/";

	/**
	 * Dossier contenant les chemins
	 */
	public static String PIG_DIR = "paths/";

	/**
	 * Constructeur de RecentsAndFavoritesPathsInGraphReaderInFolder
	 */
	public RecentsAndFavoritesPathsInGraphReaderInFolder() {
		super();
		recents = new LinkedList<PathInGraphCollectionBuilder>();
		favorites = new LinkedList<PathInGraphCollectionBuilder>();
		filesMap = new HashMap<PathInGraph, File>();
		pigMap = new HashMap<PathInGraph, PathInGraphCollectionBuilder>();
		path = (System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + PIG_DIR).replace("\\", "/");
		folder = new File(path);
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#read(GraphNetworkBuilder, int)
	 */
	@Override
	public void read(GraphNetworkBuilder gn, int mrp) {
		if (gn != null) {
			gnb = gn;
			MAX_RECENTS_PATHS = mrp;
			if (folder != null && !folder.isDirectory()) {
				folder.mkdir();
			}
		}
	}

	/**
	 * Méthode lisant les fichiers du répertoire, appellée à chaque fois que le master demande les chemins récents/favoris
	 */
	@SuppressWarnings("unchecked")
	public void readFiles() {
		if (folder.isDirectory()) {
			nbFiles = 0;
			ArrayList<File> cof = new ArrayList<File>();
			ArrayList<File> toDelete = new ArrayList<File>();
			for (File f : folder.listFiles())
				cof.add(f);
			Collections.sort(cof, new Comparator() {
				public int compare(Object o1, Object o2) {
					File f1 = (File) o1;
					File f2 = (File) o2;
					if (f1.lastModified() > f2.lastModified()) {
						return -1;
					}
					else if (f1.lastModified() == f2.lastModified()) {
						return 0;
					}
					else {
						return 1;
					}
				}
			});
			for (File fr : cof) {
				if (fr.getName().contains("PIG_") && fr.getName().contains(".xml") && !filesMap.containsValue(fr)) {
					if (fr.getName().split("\\.xml") != null && fr.getName().split("\\.xml").length >= 1 && fr.length() != 0) {
						if (fr.getName().split("\\.xml")[0].split("_") != null && fr.getName().split("\\.xml")[0].split("_").length >= 1) {
							if (fr.getName().split("\\.xml")[0].split("_")[1] != null) {
								int num = Integer.parseInt(fr.getName().split("\\.")[0].split("_")[1]);
								if (num > numFile)
									numFile = num;
								try {
									FileReader frr = new FileReader(fr);
									BufferedReader br = new BufferedReader(frr);
									String curLigne = br.readLine();
									String allLignes = curLigne;
									while (curLigne != null) {
										curLigne = br.readLine();
										allLignes += curLigne;
									}
									if (allLignes != null) {
										if (allLignes.split("null") != null && allLignes.split("null").length >= 1) {
											allLignes = allLignes.replaceAll("null", "");
										}

										if (fr.getName().contains("fav")) {
											PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork()
													.getInstancePathInGraphCollectionBuilderWithoutCatch(allLignes);
											favorites.addLast(pigcb);
											filesMap.put(pigcb.getPathInGraph(), fr);
											pigMap.put(pigcb.getPathInGraph(), pigcb);
											recents.addLast(pigcb);

										}
										else {
											PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork()
													.getInstancePathInGraphCollectionBuilderWithoutCatch(allLignes);
											recents.addLast(pigcb);
											filesMap.put(pigcb.getPathInGraph(), fr);
											pigMap.put(pigcb.getPathInGraph(), pigcb);
											nbFiles++;
										}
									}
								} catch (SAXException e) {
									toDelete.add(fr);
								} catch (IOException e) {
								} catch (ParserConfigurationException e) {
								}
							}
							else {
								toDelete.add(fr);
							}
						}
						else {
							toDelete.add(fr);
						}
					}
					else {
						toDelete.add(fr);
					}
				}
				else if (!fr.getName().contains("PIG_") || !fr.getName().contains(".xml")) {
					toDelete.add(fr);
				}
			}
			for (File fr : toDelete) {
				fr.delete();
			}
		}
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#getFavoritesPaths()
	 */
	@Override
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths() {
		readFiles();
		if (favorites != null) {
			return favorites.iterator();
		}
		else
			return null;
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#getRecentsPaths()
	 */
	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
		readFiles();
		if (recents != null) {
			if (recents.size() > MAX_RECENTS_PATHS) {
				return recents.subList(0, MAX_RECENTS_PATHS).iterator();
			}
			else {
				return recents.iterator();
			}
		}
		else
			return null;
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#addAsRecent(PathInGraphCollectionBuilder)
	 */
	@Override
	public void addAsRecent(PathInGraphCollectionBuilder pigcb) {
		if (pigcb != null) {
			boolean max = false;
			long minL = 999999999999999999L;
			int min = 99999999;
			if (nbFiles > MAX_RECENTS_PATHS) {
				if (folder.isDirectory()) {
					try {
						for (File fr : folder.listFiles()) {
							if (fr.getName().contains("PIG") && fr.getName().contains(".xml")) {
								if (fr.getName().split("\\.xml") != null && fr.getName().split("\\.xml").length >= 1) {
									if (fr.getName().split("\\.xml")[0].split("_") != null && fr.getName().split("\\.xml")[0].split("_").length >= 1) {
										if (fr.getName().split("\\.xml")[0].split("_")[1] != null) {
											if (!fr.getName().contains("fav")) {
												int num = Integer.parseInt(fr.getName().split("\\.")[0].split("_")[1]);
												if (fr.lastModified() < minL) {
													min = num;
													minL = fr.lastModified();
												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					max = true;
				}
			}

			numFile++;
			String fileName;
			if (max) {
				fileName = path + "PIG_" + min + ".xml";
			}
			else {
				fileName = path + "PIG_" + numFile + ".xml";
			}

			ArrayList<String> cof = new ArrayList<String>();
			for (File f : folder.listFiles()) {
				cof.add(f.getAbsolutePath().replace("\\", "/"));
				if (f.getAbsolutePath().replace("\\", "/").split("\\.xml") != null
						&& f.getAbsolutePath().replace("\\", "/").split("\\.xml").length >= 1) {
					cof.add(f.getAbsolutePath().replace("\\", "/").split("\\.xml")[0] + "_fav.xml");
				}
				if (f.getAbsolutePath().replace("\\", "/").split("_fav\\.xml") != null
						&& f.getAbsolutePath().replace("\\", "/").split("_fav\\.xml").length >= 1) {
					cof.add(f.getAbsolutePath().replace("\\", "/").split("_fav\\.xml")[0] + ".xml");
				}
			}
			if (cof.contains(fileName) && !max) {
				addAsRecent(pigcb);
				return;
			}

			File newFile = new File(fileName);
			try {
				if (newFile != null) {
					FileWriter fw = new FileWriter(newFile);
					if (fw != null) {
						fw.write(pigcb.getPathInGraph().exportPath());
						fw.close();

						recents.addFirst(pigcb);
						filesMap.put(pigcb.getPathInGraph(), newFile);
						pigMap.put(pigcb.getPathInGraph(), pigcb);
						nbFiles++;
					}
				}

			} catch (UnsupportedEncodingException e) {
				// e.printStackTrace();
			} catch (FileNotFoundException e) {
				// e.printStackTrace();
			} catch (IOException e) {
				// e.printStackTrace();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#markAsFavorite(PathInGraph)
	 */
	@Override
	public void markAsFavorite(PathInGraph pig) {
		if (pig != null) {
			if (pigMap.containsKey(pig) && !favorites.contains(pigMap.get(pig))) {
				try {
					File source = filesMap.get(pig);
					if (source != null) {
						String name = source.getAbsolutePath().split("\\.xml")[0] + "_fav.xml";

						if (name != null) {
							File destination = new File(name);
							source.renameTo(destination);
							filesMap.put(pig, destination);
							favorites.addFirst(pigMap.get(pig));
						}
					}
				} catch (Exception e) {
					// System.err.println(e);
				}
			}
		}
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#removeFromFavorites(PathInGraph)
	 */
	@Override
	public void removeFromFavorites(PathInGraph pig) {
		if (pig != null) {
			if (isFavorite(pig)) {
				File source = filesMap.get(pig);
				String name = source.getAbsolutePath().split("_fav\\.xml")[0] + ".xml";

				if (name != null) {
					File destination = new File(name);
					source.renameTo(destination);
					favorites.remove(pigMap.get(pig));
					filesMap.put(pig, destination);
				}
			}
		}
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#removeFromRecents(PathInGraph)
	 */
	@Override
	public void removeFromRecents(PathInGraph pig) {
		if (pig != null) {
			if (pigMap.containsKey(pig) && recents.contains(pigMap.get(pig))) {
				File toDelete = filesMap.get(pig);
				if (toDelete != null) {
					toDelete.delete();
					recents.remove(pigMap.get(pig));
					if (isFavorite(pig)) {
						favorites.remove(pigMap.get(pig));
					}
					filesMap.remove(pig);
					pigMap.remove(pig);
					removeFromFavorites(pig);
				}
			}
		}
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraphReader#isFavorite(PathInGraph)
	 */
	@Override
	public boolean isFavorite(PathInGraph pig) {
		if (pig != null) {
			return (pigMap.containsKey(pig) && favorites.contains(pigMap.get(pig)));
		}
		else
			return false;
	}

}
