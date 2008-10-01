package iGoMaster;

import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraph;
import graphNetwork.StationReader;


public interface Algo {

		
		/**
		 */
		public abstract PathInGraph findPath(StationReader origine, StationReader destination, GraphNetworkReader graph);
		

}
