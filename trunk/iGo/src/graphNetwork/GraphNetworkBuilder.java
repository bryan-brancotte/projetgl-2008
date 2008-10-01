package graphNetwork;

public interface GraphNetworkBuilder extends GraphNetworkReader {

	/**
	 * Do a complet reset of the GraphNetworkBuilder
	 */
	public abstract void reset();

	/**
	 * Empèche toutes modifications ultérieures du réseau.
	 */
	public abstract void endBuilding();

}
