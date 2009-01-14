package iGoMaster;

import graphNetwork.PathInGraphResultBuilder;
import graphNetwork.exception.StationNotOnRoadException;

import iGoMaster.exception.NoRouteForStationException;
import iGoMaster.exception.ServiceNotAccessibleException;
import iGoMaster.exception.StationNotAccessibleException;
import iGoMaster.exception.VoidPathException;

import java.util.Observable;

import algorithm.exception.NonValidDestinationException;
import algorithm.exception.NonValidOriginException;
import algorithm.exception.NullCriteriousException;
import algorithm.exception.NullStationException;



/**
 * Une classe héritant de la classe abstraite Algo se doit de notifier le master
 * de la terminaison de l'algorithme et de lui spécifier le PathInGraph qui vient
 * d'être calculé. 
 * Cf notifyObservers(Object o) avec un objet de type PathInGraph en argument.
 */

public abstract class Algo extends Observable {

	/**
	 * Fonction pour demander la résolution d'un nouveau chemin. 
	 * Le pathInGraph calculé sera communiqué au master par l'intermédiaire
	 * de la méthode notify.
	 * 
	 * @param _pathBuilder
	 * 
	 * @throws NoRouteForStationException 
	 * @throws VoidPathException 
	 * @throws ServiceNotAccessibleException 
	 * @throws StationNotAccessibleException 
	 * @throws StationNotOnRoadException 
	 * @throws NonValidDestinationException
	 * @throws NonValidOriginException
	 * @throws NoRouteForStationException
	 * 
	 */
	
	public abstract void findPath(PathInGraphResultBuilder _path) 
	throws NoRouteForStationException,
	VoidPathException,
	ServiceNotAccessibleException,
	StationNotAccessibleException,
	StationNotOnRoadException,
	NoRouteForStationException,
	VoidPathException,
	NonValidOriginException,
	NonValidDestinationException,
	NullStationException,
	NullCriteriousException;

	/**
	 * @uml.property name="iGoMaster"
	 * @uml.associationEnd inverse="algo1:iGoMaster.IGoMaster"
	 */
	private IGoMaster goMaster;

	/**
	 * Getter of the property <tt>iGoMaster</tt>
	 * 
	 * @return Returns the goMaster.
	 * @uml.property name="iGoMaster"
	 */
	public IGoMaster getIGoMaster() {
		return goMaster;
	}
	
	/**
	 * Permet au master d'arrêter proprement un algorithme en cours.
	 */
	public abstract void abort();
	

	/**
	 * Setter of the property <tt>iGoMaster</tt>
	 * 
	 * @param iGoMaster
	 *            The goMaster to set.
	 * @uml.property name="iGoMaster"
	 */
	public void setIGoMaster(IGoMaster goMaster) {
		this.goMaster = goMaster;
	}

	public enum CriteriousForLowerPath {
		NOT_DEFINED(-1), COST(0), TIME(1), CHANGE(2);

		protected int val;

		private CriteriousForLowerPath(int val) {
			this.val = val;
		}

		protected int getValue() {
			return this.val;
		}

		public static CriteriousForLowerPath getCriterious(String s) {
			if (s.compareToIgnoreCase("cost") == 0)
				return COST;
			else if (s.compareToIgnoreCase("time") == 0)
				return TIME;
			else if (s.compareToIgnoreCase("change") == 0)
				return CHANGE;
			else
				return NOT_DEFINED;
		}

		/**
		 * Surcharge de equals pour s'assurer que la comparaison sera bien faite.
		 */
		public boolean equals(CriteriousForLowerPath ev) {
			return (this.getValue() == ev.getValue());
		}
	}

}
