package iGoMaster;

import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;

import java.util.Iterator;

/**
 * Interface permetant de concerver en mémoire différents trajets. On différencie 2 type : les trajets récents et
 * favoris. Les favoris sont conservés en mémoire indéfiniement, alors qu'on ne conserve que les MAX_RECENTS_PATHS
 * chemins récents.</br>
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
	 * Use addAsRecent(PathInGraphCollectionBuilder pigcb)
	 * 
	 * @param pig
	 *            Itinéraire a ajouter
	 */
	@Deprecated
	public void addAsRecent(PathInGraph pig);

	/**
	 * Permet de sauvegarder un nouvel itineraire
	 * @param pigcb itineraire a sauvegarder
	 */
	public void addAsRecent(PathInGraphCollectionBuilder pigcolb);

	/**
	 * Cette methode permet de marquer un itinéraire comme etant un itinéraire favori
	 * 
	 * @param pig
	 *            Itinéraire a mettre en favoris
	 */
	public void markAsFavorite(PathInGraph pig);

	/**
	 * Cette methode permet de savoir si un itinéraire est marqué comme etant un itinéraire favori
	 * 
	 * @param pig
	 *            Itinéraire a mettre en favoris
	 * @return true s'il est favoris
	 */
	public boolean isFavorite(PathInGraph pig);

	/**
	 * Cette methode permet de supprimer un itineraire recent
	 * 
	 * @param pig
	 *            Itinéraire à enlever
	 */
	public void removeFromRecents(PathInGraph pig);

	/**
	 * Cette methode permet d'enlever un itinéraire des itinéraires favoris. Il n'est forcement supprime définitivement,
	 * juste retire des favoris
	 * 
	 * @param pig
	 *            Itinéraire a enlever des favoris
	 */
	public void removeFromFavorites(PathInGraph pig);

	/**
	 * Donne tous les itinéraires stocké en mémoire (récent + favoris)
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
