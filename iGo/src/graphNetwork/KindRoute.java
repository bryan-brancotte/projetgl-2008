package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */

public class KindRoute {

	protected static LinkedList<KindRoute> kinds;
	/**
	 * Le coût d'entré sur le réseaux par cette ligne
	 */
	protected float cost;
	/**
	 * Le nom du type de ligne
	 */
	protected String kindOf = "";

	/**
	 * retourne le kindRoute en fonction de son nom
	 * 
	 * @param kindOf
	 *            le nom du kind recherche
	 * @return le kindroute en question
	 */
	public static KindRoute getKindFromString(String kindOf) {
		KindRoute k = null;
		int i = 0;
		while (k == null && i < kinds.size()) {
			if (kinds.get(i).getKindOf().compareTo(kindOf) == 0)
				k = kinds.get(i);
			i++;
		}
		return k;
	}

	/**
	 * Si le kind passé en paramètre n'existe pas on le crée et le retourne. S'il existe on le retourne.
	 * 
	 * @param kindOf
	 *            nouveau kind à potentiellement ajouter
	 * @return le kindroute correspondant au kindOf.
	 */
	protected static KindRoute addKind(String kindOf) {
		if (kinds == null)
			kinds = new LinkedList<KindRoute>();
//		if (kindOf == null)
//			return null;
//		if (kindOf.isEmpty())
//			return null;
		KindRoute k = getKindFromString(kindOf);
		if (k != null)
			return k;
		else
			return new KindRoute(kindOf);

	}

	/**
	 * retourne les kindRoute connus
	 * 
	 * @return un iterateur sur les kindRoute
	 */
	public static Iterator<KindRoute> getKinds() {
		if (kinds == null)
			kinds = new LinkedList<KindRoute>();
		return kinds.iterator();
	}

	/**
	 * reset tous les kind connus
	 * 
	 * @return void
	 */
	public static void reset() {
		if (kinds != null)
			kinds.clear();
	}

	/**
	 * constructeur par defaut defini en private pour eviter des allocation non controllee de KindRoute
	 * 
	 */
	private KindRoute() {
	}

	/**
	 * cree un nouveau kindRoute et l'ajoute a la collection de kind
	 * 
	 * @param _kindOf
	 *            nouveau kind
	 * @return boolean confirmant ou non l'egalite
	 */
	private KindRoute(String _kindOf) {
		Iterator<KindRoute> it = kinds.iterator();
		boolean alreadyExist = false;
		int cpt = 0;
		while (it.hasNext()) {
			cpt++;
			if (it.next().getKindOf().equals(_kindOf))
				alreadyExist = true;
		}
		if (!alreadyExist) {
			kindOf = _kindOf;
			kinds.add(this);
		}
	}

	/**
	 * retourne le kind sous forme d'un string
	 * 
	 * @return String representant le kind
	 */
	public String getKindOf() {
		return kindOf;
	}

	/**
	 * Surcharge de equals pour s'assurer que la comparaison sera bien faite.
	 * 
	 * @param obj
	 *            objet a comparer
	 * @return boolean confirmant ou non l'egalite
	 */
	public boolean equals(Object obj) {
		if ((obj instanceof KindRoute) || (obj instanceof KindRoute)) {
			return (((KindRoute) obj).getKindOf().compareTo(this.getKindOf()) == 0);
		}
		return false;
	}

	/**
	 * setter du cout d'un kind
	 * 
	 * @param myCost
	 *            cout du kind
	 * @return void
	 */
	protected void setKindCost(float cost) {
		this.cost = cost;
	}

	// /**
	// * retourne une chaine representant le kindRoute
	// *
	// * @return la chaine
	// *
	// */
	// protected String toMyString(){
	// return "<kindRoute>"+cost+","+kindOf+"</kindRoute>";
	// }
}
