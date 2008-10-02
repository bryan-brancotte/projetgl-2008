package iGoMaster;

import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.PathInGraphReader;
import graphNetwork.StationReader;

public interface Algo {

	// TODO
	/**
	 * 
	 * @param origine
	 * @param destination
	 * @param graph
	 * @param pathInGraphBuilder
	 * @return
	 */
	public abstract PathInGraphReader findPath(StationReader origine, StationReader destination,
			GraphNetworkReader graph, PathInGraphBuilder pathInGraphBuilder);

}
