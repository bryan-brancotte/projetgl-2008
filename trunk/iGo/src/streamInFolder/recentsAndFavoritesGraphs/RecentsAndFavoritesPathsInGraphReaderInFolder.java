package streamInFolder.recentsAndFavoritesGraphs;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import iGoMaster.RecentsAndFavoritesPathsInGraphReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

public class RecentsAndFavoritesPathsInGraphReaderInFolder implements RecentsAndFavoritesPathsInGraphReader {
	
	private ArrayList<PathInGraphCollectionBuilder> recents;
	private ArrayList<PathInGraphCollectionBuilder> favorites;
	private File folder;
	private String path;
	private int numFile=0;

	String PATH_TO_CONFIG_HOME_DIR = "/.iGo/";
	String PIG_DIR = "paths/";
	
	
	public RecentsAndFavoritesPathsInGraphReaderInFolder() {
		super();
		recents = new ArrayList<PathInGraphCollectionBuilder>();
		favorites = new ArrayList<PathInGraphCollectionBuilder>();
		path = (System.getProperty("user.home") + PATH_TO_CONFIG_HOME_DIR + PIG_DIR).replace("\\", "/");
		folder = new File(path);
	}
	
	public void readPath(GraphNetworkBuilder gnb) {
		if (folder.isDirectory()) {
			try {
				for (File fr : folder.listFiles()) {
					if (fr.getName().contains("PIG") && fr.getName().contains(".xml")) {
						if(fr.getName().split("\\.")[0].split("_")[1] != null) {
							int num = Integer.parseInt(fr.getName().split("\\.")[0].split("_")[1]);
							if (num > numFile) {
								numFile = num;
							}
							FileReader frr = new FileReader(fr);
							BufferedReader br = new BufferedReader(frr);
							String curLigne = br.readLine();
							String allLignes = curLigne;
//							System.out.println("ligne " + allLignes);
							while (curLigne != null) {
								curLigne = br.readLine();
								allLignes += curLigne; 
							}
							allLignes = allLignes.split("null")[0];
							System.out.println("Pierrick --> " + allLignes);
//							System.out.println("Pierrick --> ---------------------------------");
							if (fr.getName().contains("fav")) {
								favorites.add(gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes));
							}
							else {
								recents.add(gnb.getCurrentGraphNetwork().getInstancePathInGraphCollectionBuilder(allLignes));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			folder.mkdir();
		}
	}
	
	@Override
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths() {
		if (favorites != null) {
			return favorites.iterator();
		}
		else
			return null;
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
		if (recents != null) {
			Iterator<PathInGraphCollectionBuilder> test = recents.iterator();
			while (test.hasNext()) {
				System.out.println("Pierrick --> iterator " + test.next().getPathInGraph().exportPath());
			}
			return recents.iterator();
		}
		else
			return null;
	}

	@Override
	public void addAsRecent(PathInGraph pig) {
		numFile++;
		String fileName = path + "PIG_" + numFile + ".xml";
		System.out.println("Pierrick --> filename " +  fileName);
		File newFile = new File(fileName);
		try {
			FileWriter fw = new FileWriter(newFile);
			fw.write(pig.exportPath());
			fw.close();
			
//			recents.add(pig.getGraph());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void markAsFavorite(PathInGraph pig) {
		// TODO Auto-generated method stub
		
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
