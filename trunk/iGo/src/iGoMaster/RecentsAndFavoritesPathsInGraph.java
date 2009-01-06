package iGoMaster;

import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;

import java.util.Iterator;

/**
 * Interface permetant de concerver en mémoire différents trajets. On différencie 2 type : les trajets récents et
 * favoris. Les favoris sont conservés en mémoire indéfiniement, alors qu'on ne conserve que les MAX_RECENTS_PATHS
 * chemins récents.</br>
 * 
 * Les chemins récents regroupent les 50 derniers chemins en plus de tous les favoris</br>
 * 
 * Les chemins favoris regroupent tous les chemins marqués comme tels</br>
 * 
 * Lorsqu'un chemin était favori, mais ne l'est plus, il sera ajouté en haut de la pile des chemins récents</br>
 * 
 * @author iGo
 * 
 */
public interface RecentsAndFavoritesPathsInGraph {

	/**
	 * Nombre maximum de chemins récents
	 */
	public static final int MAX_RECENTS_PATHS = 50;


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
	 * Cette methode permet d'enlever un itinéraire des itinéraires favoris. Il est alors ajouté en haut de la pile des itinéraires récents 
	 * 
	 * @param pig
	 *            Itinéraire a enlever des favoris
	 */
	public void removeFromFavorites(PathInGraph pig);

	/**
	 * Donne tous les itinéraires stockés en mémoire (récents + favoris)
	 * 
	 * @return Iterator sur un PathInGraphCollectionBuilder
	 */
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths();

	/**
	 * Donne tous les itinéraires favoris
	 * 
	 * @return Iterator sur un PathInGraphCollectionBuilder
	 */
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths();
}
