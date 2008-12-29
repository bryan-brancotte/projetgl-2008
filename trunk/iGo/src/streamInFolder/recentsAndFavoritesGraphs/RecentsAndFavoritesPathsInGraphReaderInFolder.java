package streamInFolder.recentsAndFavoritesGraphs;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import pathsAndFavorites.RecentsAndFavoritesPathsInGraphReader;


public class RecentsAndFavoritesPathsInGraphReaderInFolder implements RecentsAndFavoritesPathsInGraphReader {

	private ArrayList<PathInGraphCollectionBuilder> recents;
	private ArrayList<PathInGraphCollectionBuilder> favorites;
	private HashMap<PathInGraph, File> recentsMap;
	private HashMap<PathInGraph, File> favoritesMap;
	private File folder;
	private String path;
	private int numFile = 0;
	private int nbFiles = 0;
	private GraphNetworkBuilder gnb;
	private int MAX_RECENTS_PATHS;

	String PATH_TO_CONFIG_HOME_DIR = "/.iGo/";
	String PIG_DIR = "paths/";

	public RecentsAndFavoritesPathsInGraphReaderInFolder() {
		super();
		recents = new ArrayList<PathInGraphCollectionBuilder>();
		favorites = new ArrayList<PathInGraphCollectionBuilder>();
		recentsMap = new HashMap<PathInGraph, File>();
		favoritesMap = new HashMap<PathInGraph, File>();
		path = (System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + PIG_DIR).replace("\\", "/");
		folder = new File(path);
	}

	@Override
	public void readPath(GraphNetworkBuilder gn, int mrp) {
		gnb = gn;
		MAX_RECENTS_PATHS = mrp;
		if (!folder.isDirectory()) {
			folder.mkdir();
		}
	}

	@Override
	public void readFiles() {
		if (folder.isDirectory()) {
			try {
				favorites.clear();
				recents.clear();
				nbFiles = 0;
				for (File fr : folder.listFiles()) {
					if (fr.getName().contains("PIG") && fr.getName().contains(".xml")) {
						if (fr.getName().split("\\.")[0].split("_")[1] != null) {
							int num = Integer.parseInt(fr.getName().split("\\.")[0].split("_")[1]);
							if (num > numFile) {
								numFile = num;
							}
							FileReader frr = new FileReader(fr);
							BufferedReader br = new BufferedReader(frr);
							String curLigne = br.readLine();
							String allLignes = curLigne;
							// System.out.println("ligne " + allLignes);
							while (curLigne != null) {
								curLigne = br.readLine();
								allLignes += curLigne;
							}
							allLignes = allLignes.split("null")[0];
							// System.out.println("Pierrick --> " + allLignes);
							// System.out.println("Pierrick --> ---------------------------------");
							if (fr.getName().contains("fav")) {
								PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes);
								favorites.add(pigcb);
								favoritesMap.put(pigcb.getPathInGraph(), fr);
							}
							else {
								PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes);
								recents.add(pigcb);
								recentsMap.put(pigcb.getPathInGraph(), fr);
								nbFiles++;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths() {
		readFiles();
		if (favorites != null) {
			return favorites.iterator();
		}
		else
			return null;
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
		readFiles();
		if (recents != null) {
			return recents.iterator();
		}
		else
			return null;
	}

	@Override
	public void addAsRecent(PathInGraph pig) {
		boolean max=false;
		long minL = 999999999999999999L;
		int min=99999999;
		if (nbFiles > MAX_RECENTS_PATHS) {
			if (folder.isDirectory()) {
				try {
					for (File fr : folder.listFiles()) {
						if (fr.getName().contains("PIG") && fr.getName().contains(".xml")) {
							if (fr.getName().split("\\.")[0].split("_")[1] != null) {
								if (!fr.getName().contains("fav")) {
//									System.out.println("Last modified : "+ fr.lastModified() + " name " + fr.getName());
									int num = Integer.parseInt(fr.getName().split("\\.")[0].split("_")[1]);
									if (fr.lastModified() < minL) {
										min = num;
										minL = fr.lastModified();
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

//				System.out.println("Pierrick -- > min " + min);
				max=true;
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
		// System.out.println("Pierrick --> filename " + fileName);
		File newFile = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newFile);
			fw.write(pig.exportPath());
			fw.close();
			nbFiles++;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void markAsFavorite(PathInGraph pig) {
		
		

	}

	@Override
	public void removeFromFavorites(PathInGraph pig) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFromRecents(PathInGraph pig) {
		// TODO Auto-generated method stub

	}

}
