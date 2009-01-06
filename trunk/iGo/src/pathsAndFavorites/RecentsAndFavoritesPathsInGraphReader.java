package pathsAndFavorites;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;

import iGoMaster.RecentsAndFavoritesPathsInGraph;

import java.util.Iterator;
/**
 * Interface permettant de ne pas avoir a se soucier du moyen de sauvegarde des fichiers. On peut vouloir les sauvegarder dans un fichier, dans une
 * base de donnees, ou ailleurs.
 * 
 * La classe RecentsAndFavoritesPathsInGraphReceiver utilise cette interface, et est donc indifferente au moyen de sauvegarde utilisé.
 * 
 * C'est IGoMaster qui choisit de quelle façon seront lus/écris les chemins récents et favoris.
 * 
 * @author iGo
 * 
 */
public interface RecentsAndFavoritesPathsInGraphReader {

	/**
	 * Permet de préparer la lecture des chemins récents/favoris
	 * @param gnb Le réseau dans lequel on travaille
	 * @param mrp Le nombre maximum de chemins récents
	 */
	public void read(GraphNetworkBuilder gnb, int mrp);
	
	
	/**
	 * @see RecentsAndFavoritesPathsInGraph#addAsRecent(PathInGraphCollectionBuilder)
	 */
	public void addAsRecent(PathInGraphCollectionBuilder pig);

	/**
	 * @see RecentsAndFavoritesPathsInGraph#markAsFavorite(PathInGraph)
	 */
	public void markAsFavorite(PathInGraph pig);

	/**
	 * @see RecentsAndFavoritesPathsInGraph#removeFromRecents(PathInGraph)
	 */
	public void removeFromRecents(PathInGraph pig);

	/**
	 * @see RecentsAndFavoritesPathsInGraph#removeFromFavorites(PathInGraph)
	 */
	public void removeFromFavorites(PathInGraph pig);
	

	/**
	 * @see RecentsAndFavoritesPathsInGraph#isFavorite(PathInGraph)
	 */
	public boolean isFavorite(PathInGraph pig);
	
	
	/**
	 * @see RecentsAndFavoritesPathsInGraph#getRecentsPaths()
	 */
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths();


	/**
	 * @see RecentsAndFavoritesPathsInGraph#getFavoritesPaths()
	 */
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths();
}
