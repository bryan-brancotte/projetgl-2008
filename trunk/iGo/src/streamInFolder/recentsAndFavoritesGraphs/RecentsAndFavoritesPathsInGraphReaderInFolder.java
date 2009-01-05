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
import java.util.LinkedList;

import pathsAndFavorites.RecentsAndFavoritesPathsInGraphReader;


public class RecentsAndFavoritesPathsInGraphReaderInFolder implements RecentsAndFavoritesPathsInGraphReader {

	private LinkedList<PathInGraphCollectionBuilder> recents;
	private LinkedList<PathInGraphCollectionBuilder> favorites;
	private HashMap<PathInGraph, File> filesMap;
	private HashMap<PathInGraph, PathInGraphCollectionBuilder> pigMap;
//	private HashMap<PathInGraph, File> recentsMap;
//	private HashMap<PathInGraph, PathInGraphCollectionBuilder> recentsMapPIGC;
//	private HashMap<PathInGraph, File> favoritesMap;
//	private HashMap<PathInGraph, PathInGraphCollectionBuilder> favoritesMapPIGC;
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
		recents = new LinkedList<PathInGraphCollectionBuilder>();
		favorites = new LinkedList<PathInGraphCollectionBuilder>();
		filesMap = new HashMap<PathInGraph, File>();
		pigMap = new HashMap<PathInGraph, PathInGraphCollectionBuilder>();
//		favoritesMap = new HashMap<PathInGraph, File>();
//		favoritesMapPIGC = new HashMap<PathInGraph, PathInGraphCollectionBuilder>();
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
//		System.out.println("READ PATH");
		if (folder.isDirectory()) {
			try {
//				favorites.clear();
//				recents.clear();
//				favoritesMap.clear();
//				favoritesMapPIGC.clear();
//				recentsMap.clear();
//				recentsMapPIGC.clear();
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
					if (fr.getName().contains("PIG") && fr.getName().contains(".xml") && !filesMap.containsValue(fr)) {
						if (fr.getName().split("\\.")[0].split("_")[1] != null  && fr.length() !=0 ) {
							int num = Integer.parseInt(fr.getName().split("\\.")[0].split("_")[1]);
							if (num > numFile) {
								numFile = num;
//								System.out.println("numfile " + numFile);
							}
							FileReader frr = new FileReader(fr);
							BufferedReader br = new BufferedReader(frr);
							String curLigne = br.readLine();
							String allLignes = curLigne;
//							 System.out.println("File " + fr);
							while (curLigne != null) {
								curLigne = br.readLine();
								allLignes += curLigne;
							}
							allLignes = allLignes.split("null")[0];
//							 System.out.println("Pierrick --> " + allLignes);
//							 System.out.println("Pierrick --> ---------------------------------");
							
							if (fr.getName().contains("fav")) {
								PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes);
								favorites.addFirst(pigcb);
								filesMap.put(pigcb.getPathInGraph(), fr);
								pigMap.put(pigcb.getPathInGraph(), pigcb);
								
//								favoritesMap.put(pigcb.getPathInGraph(), fr);
//								favoritesMapPIGC.put(pigcb.getPathInGraph(), pigcb);
								recents.addFirst(pigcb);
//								recentsMap.put(pigcb.getPathInGraph(), fr);
//								recentsMapPIGC.put(pigcb.getPathInGraph(), pigcb);
								
							}
							else {
								PathInGraphCollectionBuilder pigcb = gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes);
								recents.addFirst(pigcb);
								filesMap.put(pigcb.getPathInGraph(), fr);
								pigMap.put(pigcb.getPathInGraph(), pigcb);
//								recentsMap.put(pigcb.getPathInGraph(), fr);
//								recentsMapPIGC.put(pigcb.getPathInGraph(), pigcb);
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
	public void addAsRecent(PathInGraphCollectionBuilder pigcb) {
		boolean max=false;
		long minL = 999999999999999999L;
		int min=99999999;
		if (nbFiles > MAX_RECENTS_PATHS) {
			if (folder.isDirectory()) {
				try {
					for (File fr : folder.listFiles()) {
						if (fr.getName().contains("PIG") && fr.getName().contains(".xml")) {
							if (fr.getName().split("\\.xml")[0].split("_")[1] != null) {
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
		
		ArrayList<String> cof = new ArrayList<String>();
		for (File f : folder.listFiles()) {
			cof.add(f.getAbsolutePath().replace("\\", "/"));
			cof.add(f.getAbsolutePath().replace("\\", "/").split("\\.xml")[0] + "_fav.xml");
			cof.add(f.getAbsolutePath().replace("\\", "/").split("_fav\\.xml")[0] + ".xml");
//			System.out.println("Jai " + f.getAbsolutePath().replace("\\", "/") + " et " + f.getAbsolutePath().replace("\\", "/").split("\\.xml")[0] + "_fav.xml" + " et " +f.getAbsolutePath().replace("\\", "/").split("_fav\\.xml")[0] + ".xml");
		}
		if (cof.contains(fileName)) {
			addAsRecent(pigcb);
			return;
		}
		
		File newFile = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newFile);
			fw.write(pigcb.getPathInGraph().exportPath());
			fw.close();

			recents.addFirst(pigcb);
			filesMap.put(pigcb.getPathInGraph(), newFile);
			pigMap.put(pigcb.getPathInGraph(), pigcb);
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
		if (pigMap.containsKey(pig) && !favorites.contains(pigMap.get(pig))) {
			try {
//				System.out.println("Mark as favorite");
//				System.out.println(recentsMap.get(pig));
				File source = filesMap.get(pig);
//				System.out.println("NEW NAME " + source.getAbsolutePath());
				String name = source.getAbsolutePath().split("\\.xml")[0] + "_fav.xml";
//				System.out.println("NEW NAME " + name);
				
				
				File destination = new File(name);
				source.renameTo(destination);
				filesMap.put(pig, destination);
				favorites.addFirst(pigMap.get(pig));
			} catch(Exception e) {
				System.err.println(e);
			}
		}
	}

	@Override
	public void removeFromFavorites(PathInGraph pig) {
		if (isFavorite(pig)) {
//			System.out.println("Remove from favorites");
//			File toDelete = filesMap.get(pig);
//			toDelete.delete();
//			favorites.remove(pigMap.get(pig));
//			favoritesMap.remove(pig);
//			favoritesMapPIGC.remove(pig);
			
			File source = filesMap.get(pig);
//			System.out.println("NEW NAME " + source.getAbsolutePath());
			String name = source.getAbsolutePath().split("_fav\\.xml")[0] + ".xml";
//			System.out.println("NEW NAME " + name);
			
			
			File destination = new File(name);
			source.renameTo(destination);
			favorites.remove(pigMap.get(pig));
			filesMap.put(pig, destination);
			
//			addAsRecent(pigMap.get(pig));
		}
		// TODO Auto-generated method stub
//		addAsRecent

	}

	@Override
	public void removeFromRecents(PathInGraph pig) {
		if (pigMap.containsKey(pig) && recents.contains(pigMap.get(pig))) {
//			System.out.println("Remove from recents");
			File toDelete = filesMap.get(pig);
			toDelete.delete();
			recents.remove(pigMap.get(pig));
			filesMap.remove(pig);
			pigMap.remove(pig);
//			recentsMap.remove(pig);
//			recentsMapPIGC.remove(pig);
		}
	}

	@Override
	public boolean isFavorite(PathInGraph pig) {
		return (pigMap.containsKey(pig) && favorites.contains(pigMap.get(pig)));
	}

}
