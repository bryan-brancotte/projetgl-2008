package iGoMaster;

import graphNetwork.PathInGraph;

import java.util.Iterator;

public interface RecentsAndFavoritesPathsInGraph {

	/**
	 * Cette methode permet d'ajouter un itineraire dans les derniers itineraires utilises
	 * @param pig Itineraire a ajouter
	 */
	public void addAsRecent(PathInGraph pig);
	
	/**
	 * Cette methode permet de marquer un itineraire comme etant un itineraire favori
	 * @param pig Itineraire a mettre en favoris
	 */
	public void markAsFavorite(PathInGraph pig);
	
	/**
	 * Cette methode permet d'enlever un itineraire des derniers itineraires utilises
	 * @param pig Itineraire a enlever
	 */
	public void removeFromRecents(PathInGraph pig);
	
	/**
	 * Cette methode permet d'enlever un itineraire des itineraires favoris
	 * @param pig Itineraire a enlever des favoris
	 */
	public void removeFromFavorites(PathInGraph pig);
	
	/**
	 * Donne tous les itineraires recents
	 * @return Iterator sur un PathInGraph
	 */
	public Iterator<PathInGraph> getRecentsPaths();
	
	/**
	 * Donne tous les itineraires favoris
	 * @return Iterator sur un PathInGraph
	 */
	public Iterator<PathInGraph> getFavoritesPaths();
}
