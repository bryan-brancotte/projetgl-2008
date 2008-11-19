package algorithm;

import graphNetwork.CriteriousForTheLowerPath;
import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.PathInGraphReader;
import graphNetwork.StationReader;
import iGoMaster.Algo;

import java.util.Observable;
import java.util.Observer;


public abstract class AlgoImpl extends Observable implements Algo {

	@Override
	public PathInGraphReader findPath(StationReader origine,
			StationReader destination, GraphNetworkReader graph,
			PathInGraphBuilder pathInGraphBuilder,
			CriteriousForTheLowerPath criterious) {
		// TODO Auto-generated method stub
		return null;
	}

		
		/**
		 */
		public void setChanged(){
		}


			
			/**
			 */
			public void addObserver(Observer o){
			}


				
				/**
				 */
				public void notifyObserver(){
				}

}
