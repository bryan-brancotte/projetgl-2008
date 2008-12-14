package graphNetwork;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 
 * @author iGo
 */

public class KindRoute {

	//private KindRoute route;
	protected static LinkedList<KindRoute> kinds;
	protected float cost;
	private String kindOf = "";

	public static KindRoute getKindFromString(String kindOf) {//retourne le kindRoute en fonction de son nom
		KindRoute k = null;
		int i=0;
		while (k==null && i<kinds.size()) {
			if (kinds.get(i).getKindOf().compareTo(kindOf)==0) k=kinds.get(i);
			i++;
		}
		return k;
	}

	protected static KindRoute addKind(String kindOf) {//ajoute un nouveau Kind a la collection de kind. la cree si aucun kind n'existe.
        if (kinds==null) kinds = new LinkedList<KindRoute>();
		KindRoute k = getKindFromString(kindOf);
		if(k!=null) return k;
		else return new KindRoute(kindOf);

	}

	public static Iterator<KindRoute> getKinds() {//retrourne un iterator sur les kindRoute
		return kinds.iterator();
	}

	public static void reset() {//reset tous les kind connus
		kinds.clear();
	}

	private KindRoute() {//constructeur par defaut defini en private pour eviter des allocation non controllee de KindRoute
	}

	private KindRoute(String _kindOf) {//cree un nouveau kindRoute et l'ajoute a la collection de kind
		kindOf = _kindOf;
        kinds.add(this);
        

	}

	public String getKindOf() {//retourne le kind sous forme d'un string
		return kindOf;
	}
	
	public boolean equals(Object obj) {//Surcharge de equals pour s'assurer que la comparaison sera bien faite.
		if ((obj instanceof KindRoute) || (obj instanceof KindRoute)) {
			return (((KindRoute) obj).getKindOf().compareTo(this.getKindOf()) == 0);
		}
		return false;
	}
	
	public void setKindCost(float myCost){//setter du cout d'un kind
		this.cost=myCost;
	}

}
