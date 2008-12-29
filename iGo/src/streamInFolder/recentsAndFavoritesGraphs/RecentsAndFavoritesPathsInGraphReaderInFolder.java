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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import pathsAndFavorites.RecentsAndFavoritesPathsInGraphReader;


public class RecentsAndFavoritesPathsInGraphReaderInFolder implements RecentsAndFavoritesPathsInGraphReader {

	private ArrayList<PathInGraphCollectionBuilder> recents;
	private ArrayList<PathInGraphCollectionBuilder> favorites;
	private HashMap<PathInGraph, File> recentsMap;
	private HashMap<PathInGraph, PathInGraphCollectionBuilder> recentsMapPIGC;
	private HashMap<PathInGraph, File> favoritesMap;
	private HashMap<PathInGraph, PathInGraphCollectionBuilder> favoritesMapPIGC;
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
		recentsMapPIGC = new HashMap<PathInGraph, PathInGraphCollectionBuilder>();
		favoritesMap = new HashMap<PathInGraph, File>();
		favoritesMapPIGC = new HashMap<PathInGraph, PathInGraphCollectionBuilder>();
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

	@SuppressWarnings("unchecked")
	@Override
	public void readFiles() {
		System.out.println("READ PATH");
		if (folder.isDirectory()) {
			try {
				favorites.clear();
				recents.clear();
				favoritesMap.clear();
				favoritesMapPIGC.clear();
				recentsMap.clear();
				recentsMapPIGC.clear();
				nbFiles = 0;
				ArrayList<File> cof = new ArrayList<File>();
				for (File f : folder.listFiles()) {
					cof.add(f);
				}
				Collections.sort(cof,
						   new Comparator() {
						      public int compare(Object o1, Object o2) {
						    	  File f1 = (File)o1;
						    	  File f2 = (File)o2;
						    	  if (f1.lastModified() > f2.lastModified()) {
						    		  return -1;
						    	  }
						    	  else if (f1.lastModified() == f2.lastModified()) {
					    			  return 0;
						    	  }
						    	  else {
						    		  return 1;
						    	  }
						    	  
//						         return f1.compareTo(f2);
						      }
						   });				
				for (File fr : cof) {
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
//							 System.out.println("Pierrick --> " + allLignes);
//							 System.out.println("Pierrick --> ---------------------------------");
							if (fr.getName().contains("fav")) {
								PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes);
								favorites.add(pigcb);
								favoritesMap.put(pigcb.getPathInGraph(), fr);
								favoritesMapPIGC.put(pigcb.getPathInGraph(), pigcb);
								recents.add(pigcb);
								recentsMap.put(pigcb.getPathInGraph(), fr);
								recentsMapPIGC.put(pigcb.getPathInGraph(), pigcb);
								
							}
							else {
								PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes);
								recents.add(pigcb);
								recentsMap.put(pigcb.getPathInGraph(), fr);
								recentsMapPIGC.put(pigcb.getPathInGraph(), pigcb);
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
//		System.out.println("get fav");
		readFiles();
		if (favorites != null) {
			return favorites.iterator();
		}
		else
			return null;
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
//		System.out.println("get recents");
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
//		 System.out.println("Pierrick --> filename " + fileName);
		File newFile = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newFile);
			fw.write(pig.exportPath());
			fw.close();
			recentsMap.put(pig, newFile);
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
		if (!favoritesMap.containsKey(pig)) {
			try {
//				System.out.println(recentsMap.get(pig));
				File source = recentsMap.get(pig);
//				System.out.println("NEW NAME " + source.getAbsolutePath());
				String name = source.getAbsolutePath().split("\\.xml")[0] + "_fav.xml";
//				System.out.println("NEW NAME " + name);
				File destination = new File(name);
				source.renameTo(destination);
//				recentsMap.remove(pig);
				recentsMap.put(pig, destination);
				favoritesMap.put(pig, destination);
				favoritesMapPIGC.put(pig, recentsMapPIGC.get(pig));
				favorites.add(favoritesMapPIGC.get(pig));
			} catch(Exception e) {
				System.err.println(e);
			}
		}
	}

	@Override
	public void removeFromFavorites(PathInGraph pig) {
		if (favoritesMap.containsKey(pig)) {
//			System.out.println("Remove from favorites");
			File toDelete = favoritesMap.get(pig);
			toDelete.delete();
			favoritesMap.remove(pig);
			favoritesMapPIGC.remove(pig);
//			addAsRecent(pig);
		}
		// TODO Auto-generated method stub
//		addAsRecent

	}

	@Override
	public void removeFromRecents(PathInGraph pig) {
		if (recentsMap.containsKey(pig)) {
//			System.out.println("Remove from recents");
			File toDelete = recentsMap.get(pig);
			toDelete.delete();
			recentsMap.remove(pig);
			recentsMapPIGC.remove(pig);
		}
	}

	@Override
	public boolean isFavorite(PathInGraph pig) {
		return favorites.contains(pig);
	}

}
