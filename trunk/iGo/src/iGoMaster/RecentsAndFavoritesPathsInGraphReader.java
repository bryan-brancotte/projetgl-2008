package iGoMaster;

import graphNetwork.PathInGraph;

import java.util.Iterator;

public interface RecentsAndFavoritesPathsInGraphReader {


	/**
	 * Donne tous les chemins recents disponibles
	 * 
	 * @return Iterator sur les differents chemins disponibles.
	 */
	public Iterator<PathInGraph> getRecentsPaths();


	/**
	 * Donne tous les chemins favoris disponibles
	 * 
	 * @return Iterator sur les differents chemins disponibles.
	 */
	public Iterator<PathInGraph> getFavoritesPaths();
}
