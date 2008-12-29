package iGoMaster;

import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;

import java.util.Iterator;

/**
 * Interface permetant de concerver en mémoire différents trajets. On différencie 2 type : les trajets récents et favoris.
 * Les favoris sont conservés en mémoire indéfiniement, alors qu'on ne conserve que les MAX_RECENTS_PATHS chemins
 * récents.</br>
 * 
 * Les chemins récents regroupent les 50 derniers chemins favoris ou non.</br>
 * 
 * Les chemins favoris regroupent tous les chemins marqués comme tels</br>
 * 
 * Lorsqu'un chemin était favori, mais ne l'est plus, il sera supprimé s'il est antérieur au dernier chemin récent.</br>
 * 
 * @author iGo
 * 
 */
public interface RecentsAndFavoritesPathsInGraph {

	public static final int MAX_RECENTS_PATHS = 50;

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
	 * Donne tous les itinéraires recents
	 * 
	 * @return Iterator sur un PathInGraph
	 */
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths();

	/**
	 * Donne tous les itinéraires favoris
	 * 
	 * @return Iterator sur un PathInGraph
	 */
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths();
}
