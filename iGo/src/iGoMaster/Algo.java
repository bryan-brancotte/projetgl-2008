package iGoMaster;

import graphNetwork.PathInGraphResultBuilder;

import java.util.Observable;


/**
 * Une classe héritant de la classe abstraite Algo se doit de notifier le master
 * de la terminaison de l'algorithme et de lui spécifier le PathInGraph qui vient
 * d'être calculé. 
 * Cf notifyObservers(Object o) avec un objet de type PathInGraph en argument.
 */

public abstract class Algo extends Observable {

	// TODO A VIRER APRES validation du pathInGraph
	protected CriteriousForLowerPath criterious1;

	public CriteriousForLowerPath getCriterious1() {
		return criterious1;
	}

	public void setCriterious1(String s) {
		criterious1 = CriteriousForLowerPath.getCriterious(s);
	}

	protected CriteriousForLowerPath criterious2;

	public CriteriousForLowerPath getCriterious2() {
		return criterious2;
	}

	public void setCriterious2(String s) {
		criterious2 = CriteriousForLowerPath.getCriterious(s);
	}

	/**
	 * Fonction pour demander la résolution d'un nouveau chemin. 
	 * Le pathInGraph calculé sera communiqué au master par l'intermédiaire
	 * de la méthode notify.
	 * 
	 * @param _pathBuilder
	 * 
	 */
	public abstract void findPath(PathInGraphResultBuilder _path);

	/**
	 */
	public void setChanged() {
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
