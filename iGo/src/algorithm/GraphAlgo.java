package algorithm;

import graphNetwork.Junction;
import graphNetwork.Route;
import graphNetwork.Station;

import java.util.LinkedList;

public class GraphAlgo {
	
	public class Link {

		private Junction junction;
		private Node node;

		public Link (Junction j, Node n) {
			junction = j;
			node = n;
		}
		
		public Junction getJunction (){ return junction; }
		public Node getNode (){ return node; }
		
	}
	public class Node {
		
		private Station station;
		private Route route;
		private LinkedList<Link> to;
		private int interet;
		private int parcouru;

		Node(Station s, Route r) {
			station = s;
			route = r;
			interet = 0;
			parcouru = 0;
			to = new LinkedList<Link>();
		}

		public void addTo (Junction j, Node n) {
			to.add(new Link(j,n));
		}

		public void removeTo (Link l) {
			to.remove(l);
		}

		public Station getStation () {return station;}
		
		public int getParcouru (){ return parcouru;	}
		public void setParcouru (int _parcouru){ parcouru=_parcouru; }
		
		public int getInteret (){ return interet;	}
		public void setInteret (int _interet){ interet=_interet; }
		
	}

}
