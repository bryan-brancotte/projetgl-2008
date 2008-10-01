package iGoMaster;


import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraph;
import graphNetwork.StationR;

public interface Algo {

	/**
	 */
	public abstract PathInGraph findPath(StationR origine, StationR destination, GraphNetworkReader graph);

}
