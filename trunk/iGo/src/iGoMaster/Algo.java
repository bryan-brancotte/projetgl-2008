package iGoMaster;

import graphNetwork.PathInGraph;
import graphNetwork.PathInGraphBuilder;

import java.util.Observable;
import java.util.Observer;

public abstract class Algo extends Observable {

	//TODO A VIRER APRES validation du pathInGraph
	protected CriteriousForLowerPath criterious1;
	public CriteriousForLowerPath getCriterious1 () { return criterious1; }
	public void setCriterious1 (String s) { criterious1=CriteriousForLowerPath.getCriterious(s); }
	protected CriteriousForLowerPath criterious2;
	public CriteriousForLowerPath getCriterious2 () { return criterious2; }
	public void setCriterious2 (String s) { criterious2=CriteriousForLowerPath.getCriterious(s); }
	/**
	 * fonction pour demander la résolution d'un nouveau chemin. On spécifie le départ et l'arrivé, on fournit le
	 * réseau, et le monteur de PathInGraph avec un PathInGraph vide à l'interrieur.
	 * 
	 * @param _pathBuilder
	 * @return
	 */
	public abstract PathInGraph findPath(PathInGraphBuilder _path);

	/**
	 */
	public void setChanged() {
	}

	/**
	 */
	public void addObserver(Observer o) {
	}

	/**
	 */
	public void notifyObserver() {
	}

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
		COST(1), TIME(2), CHANGE(3);

		protected int val;

		private CriteriousForLowerPath(int val) {
			this.val = val;
		}

		protected int getValue() {
			return this.val;
		}

		public static CriteriousForLowerPath getCriterious (String s) {
			if (s.compareToIgnoreCase("cost")==0) return COST;
			else if (s.compareToIgnoreCase("time")==0) return TIME;
			else return CHANGE;
		}
		/**
		 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
		 */
		public boolean equals(CriteriousForLowerPath ev) {
			return (this.getValue() == ev.getValue());
		}
	}

}
