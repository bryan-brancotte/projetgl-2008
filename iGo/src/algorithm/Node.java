package algorithm;

import graphNetwork.Station;

import java.util.ArrayList;

public class Node {
	
	private Station from;
	private ArrayList<Station> to;
	private int interet;
	private int parcouru;

	Node(Station _from) {
		from = _from;
		interet = 0;
		parcouru = 0;
		to = new ArrayList<Station>();
	}

	public void addTo (Station _to) {
		to.add(_to);
	}

	public void removeTo (Station _to) {
		to.remove(_to);
	}
}
