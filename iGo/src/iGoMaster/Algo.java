package iGoMaster;

import graphNetwork.CriteriousForTheLowerPath;
import graphNetwork.GraphNetworkReader;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.PathInGraphReader;
import graphNetwork.StationReader;

import java.util.Observable;
import java.util.Observer;


public abstract class Algo extends Observable {

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
