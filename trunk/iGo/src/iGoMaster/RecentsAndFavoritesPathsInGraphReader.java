package iGoMaster;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;

import java.util.Iterator;

public interface RecentsAndFavoritesPathsInGraphReader {

	/**
	 * Permet de lire le repertoire contenant les chemins
	 */
	public void readPath(GraphNetworkBuilder gnb);
	
	
	/**
	 * Cette methode permet d'ajouter un itinéraire dans les derniers itinéraires utilises
	 * 
	 * @param pig
	 *            Itinéraire a ajouter
	 */
	public void addAsRecent(PathInGraph pig);

	/**
	 * Cette methode permet de marquer un itinéraire comme etant un itinéraire favori
	 * 
	 * @param pig
	 *            Itinéraire a mettre en favoris
	 */
	public void markAsFavorite(PathInGraph pig);

	/**
	 * 
	 * 
	 * Cette methode permet d'enlever un itinéraire de la mémoire, quelque soit l'endroit où il se trouve
	 * 
	 * @param pig
	 *            Itinéraire à enlever
	 */
	public void removeFromRecents(PathInGraph pig);

	/**
	 * Cette methode permet d'enlever un itinéraire des itinéraires favoris. Il n'est forcement supprime
	 * définitivement, juste retire des favoris
	 * 
	 * @param pig
	 *            Itinéraire a enlever des favoris
	 */
	public void removeFromFavorites(PathInGraph pig);
	
	
	/**
	 * Donne tous les chemins recents disponibles
	 * 
	 * @return Iterator sur les differents chemins disponibles.
	 */
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths();


	/**
	 * Donne tous les chemins favoris disponibles
	 * 
	 * @return Iterator sur les differents chemins disponibles.
	 */
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths();
}
