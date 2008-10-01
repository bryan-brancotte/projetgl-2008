package graphNetwork;

public interface GraphNetworkBuilder extends GraphNetworkReader {

	/**
	 * Do a complet reset of the GraphNetworkBuilder
	 */
	public abstract void reset();

	/**
	 * Emp�che toutes modifications ult�rieures du r�seau.
	 */
	public abstract void endBuilding();

}
