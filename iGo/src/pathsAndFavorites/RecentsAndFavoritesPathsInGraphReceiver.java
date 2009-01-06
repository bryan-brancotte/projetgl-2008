package pathsAndFavorites;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import iGoMaster.RecentsAndFavoritesPathsInGraph;

import java.util.Iterator;

/**
 * Classe implémentant l'interface RecentsAndFavoritesPathsInGraph
 * 
 * Cette classe se contente de faire ce que IGoMaster lui dit de faire, peu importe la façon dont sont stockés les chemins favoris et récents, cela grâce à l'interface
 * RecentsAndFavoritesPathsInGraphReader.
 * 
 * @author iGo
 * 
 */
public class RecentsAndFavoritesPathsInGraphReceiver implements RecentsAndFavoritesPathsInGraph {

	/**
	 * Objet de type RecentsAndFavoritesPathsInGraphReader permettant d'effectuer les actions souhaitées par le master
	 */
	private RecentsAndFavoritesPathsInGraphReader rafpigr;

	/**
	 * Constructeur de RecentsAndFavoritesPathsInGraphReceiver
	 * 
	 * @param gnb
	 *            Le réseau dans lequel on travaille
	 * @param raf
	 *            L'objet qui va lire/écrire les chemins récents et favoris
	 */
	public RecentsAndFavoritesPathsInGraphReceiver(GraphNetworkBuilder gnb, RecentsAndFavoritesPathsInGraphReader raf) {
		super();
		rafpigr = raf;
		rafpigr.read(gnb, MAX_RECENTS_PATHS);
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraph#addAsRecent(PathInGraphCollectionBuilder)
	 */
	@Override
	public void addAsRecent(PathInGraphCollectionBuilder pig) {
		rafpigr.addAsRecent(pig);
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraph#getFavoritesPaths()
	 */
	@Override
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths() {
		return rafpigr.getFavoritesPaths();
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraph#getRecentsPaths()
	 */
	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
		return rafpigr.getRecentsPaths();
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraph#markAsFavorite(PathInGraph)
	 */
	@Override
	public void markAsFavorite(PathInGraph pig) {
		rafpigr.markAsFavorite(pig);
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraph#removeFromFavorites(PathInGraph)
	 */
	@Override
	public void removeFromFavorites(PathInGraph pig) {
		rafpigr.removeFromFavorites(pig);
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraph#removeFromRecents(PathInGraph)
	 */
	@Override
	public void removeFromRecents(PathInGraph pig) {
		rafpigr.removeFromRecents(pig);
	}

	/**
	 * @see RecentsAndFavoritesPathsInGraph#isFavorite(PathInGraph)
	 */
	@Override
	public boolean isFavorite(PathInGraph pig) {
		return rafpigr.isFavorite(pig);
	}

}
