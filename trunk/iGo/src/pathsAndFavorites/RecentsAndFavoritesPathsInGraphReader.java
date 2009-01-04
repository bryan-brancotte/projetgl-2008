package pathsAndFavorites;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;

import java.util.Iterator;
/**
 * Interface permettant de ne pas avoir a se soucier du moyen de sauvegarde des fichiers. On peut vouloir les sauvegarder dans un fichier, dans une
 * base de donnees, ou ailleurs.
 * 
 * La classe RecentsAndFavoritesPathsInGraphReceiver utilise cette interface, et est donc indifferente au moyen de sauvegarde
 * 
 * @author iGo
 * 
 */
public interface RecentsAndFavoritesPathsInGraphReader {

	/**
	 * Permet de preparer la lecture du repertoire contenant les chemins
	 */
	public void readPath(GraphNetworkBuilder gnb, int mrp);
	

	/**
	 * Importe les chemins presents dans le repertoire
	 */
	public void readFiles();
	
	
	/**
	 * Cette methode permet d'ajouter un itinéraire dans les derniers itinéraires utilises
	 * 
	 * @param pig
	 *            Itinéraire a ajouter
	 */
	public void addAsRecent(PathInGraphCollectionBuilder pig);

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
	 * Cette methode permet de supprimer un itineraire recent
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
	 * Cette methode permet de savoir si un itinéraire est marqué comme etant un itinéraire favori
	 * 
	 * @param pig
	 *            Itinéraire a mettre en favoris
	 * @return true s'il est favoris
	 */
	public boolean isFavorite(PathInGraph pig);
	
	
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
