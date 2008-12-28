package iGoMaster;

import graphNetwork.PathInGraph;

import java.util.Iterator;

/**
 * Interface permetant de concerver en mémoire différent trajet. On différencie 2 type : les trajet récent et favoris.
 * Les favoris sont concervés en mémoire indéfiniment, alors qu'on ne concerve que les MAX_RECENTS_PATHS chemins
 * récents.</br>
 * 
 * Les chemins récent regroupe les 50 derniers chemin favoris ou non.</br>
 * 
 * Les chemins favoris regroupe tous les chemins marqué comme tel</br>
 * 
 * Lorsqu'un chemin était favori, mais ne l'est plus, il sera supprimé s'il est antérieur au dernier chemin récent.</br>
 * 
 * @author iGo
 * 
 */
public interface RecentsAndFavoritesPathsInGraph {

	// TODO MAX_RECENTS_PATHS ok?
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
	 * //TODO valider ce commentaire
	 * 
	 * Cette methode permet d'enlever un itinéraire de la mémoire, quelque soit l'endrois où il se trouve
	 * 
	 * @param pig
	 *            Itinéraire à enlever
	 */
	// TODO la renomer en remove afin d'être pus précis.
	public void removeFromRecents(PathInGraph pig);

	/**
	 * Cette methode permet d'enlever un itinéraire des itinéraires favoris. Il n'est forcement supprimer
	 * définitivement, juste retirer des favoris
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
	public Iterator<PathInGraph> getRecentsPaths();

	/**
	 * Donne tous les itinéraires favoris
	 * 
	 * @return Iterator sur un PathInGraph
	 */
	public Iterator<PathInGraph> getFavoritesPaths();
}
