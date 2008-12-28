package iGoMaster;

import graphNetwork.PathInGraph;

import java.util.Iterator;

public interface RecentsAndFavoritesPathsInGraph {

	/**
	 * Cette methode permet d'ajouter un itineraire dans les derniers itineraires utilises
	 * 
	 * @param pig
	 *            Itinéraire a ajouter
	 */
	public void addAsRecent(PathInGraph pig);

	/**
	 * Cette methode permet de marquer un itineraire comme etant un itineraire favori
	 * 
	 * @param pig
	 *            Itinéraire a mettre en favoris
	 */
	public void markAsFavorite(PathInGraph pig);

	/**
	 * //TODO valider ce commentaire
	 * 
	 * Cette methode permet d'enlever un itineraire de la mémoire, quelque soit l'endrois où il se trouve
	 * 
	 * @param pig
	 *            Itinéraire à enlever
	 */
	// TODO la renomer en remove afin d'être pus précis.
	public void removeFromRecents(PathInGraph pig);

	/**
	 * Cette methode permet d'enlever un itineraire des itineraires favoris. Il n'est forcement supprimer
	 * définitivement, juste retirer des favoris
	 * 
	 * @param pig
	 *            Itinéraire a enlever des favoris
	 */
	public void removeFromFavorites(PathInGraph pig);

	/**
	 * Donne tous les itineraires recents
	 * 
	 * @return Iterator sur un PathInGraph
	 */
	public Iterator<PathInGraph> getRecentsPaths();

	/**
	 * Donne tous les itineraires favoris
	 * 
	 * @return Iterator sur un PathInGraph
	 */
	public Iterator<PathInGraph> getFavoritesPaths();

	// TODO un méthode pour savoir le nombre max de récent
	// TODO optionel : un méthode pour modifier le nombre max de récent
}
