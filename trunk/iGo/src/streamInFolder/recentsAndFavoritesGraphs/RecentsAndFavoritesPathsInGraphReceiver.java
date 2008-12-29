package streamInFolder.recentsAndFavoritesGraphs;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphCollectionBuilder;
import iGoMaster.RecentsAndFavoritesPathsInGraph;
import iGoMaster.RecentsAndFavoritesPathsInGraphReader;

import java.util.Iterator;

public class RecentsAndFavoritesPathsInGraphReceiver implements RecentsAndFavoritesPathsInGraph {
	
	private RecentsAndFavoritesPathsInGraphReader rafpigr;
	
	public RecentsAndFavoritesPathsInGraphReceiver(GraphNetworkBuilder gnb, RecentsAndFavoritesPathsInGraphReader raf) {
		super();
		rafpigr = raf;
		rafpigr.readPath(gnb);
	}

	@Override
	public void addAsRecent(PathInGraph pig) {
		rafpigr.addAsRecent(pig);
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getFavoritesPaths() {
		return rafpigr.getFavoritesPaths();
	}

	@Override
	public Iterator<PathInGraphCollectionBuilder> getRecentsPaths() {
		return rafpigr.getRecentsPaths();
	}

	@Override
	public void markAsFavorite(PathInGraph pig) {
		rafpigr.markAsFavorite(pig);
	}

	@Override
	public void removeFromFavorites(PathInGraph pig) {
		rafpigr.removeFromFavorites(pig);
	}

	@Override
	public void removeFromRecents(PathInGraph pig) {
		rafpigr.removeFromRecents(pig);
	}
	
	public static void main(String[] args) {

		RecentsAndFavoritesPathsInGraphReceiver gnrf = new RecentsAndFavoritesPathsInGraphReceiver(new GraphNetworkBuilder(), new RecentsAndFavoritesPathsInGraphReaderInFolder());
		
		
		
	}

}
