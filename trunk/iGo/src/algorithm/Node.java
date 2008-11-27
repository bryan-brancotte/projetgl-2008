package algorithm;

import graphNetwork.StationReader;

import java.util.ArrayList;

public class Node {
	
	private StationReader from;
	private ArrayList<StationReader> to;
	private int interet;
	private int parcouru;

	Node(StationReader _from) {
		from = _from;
		interet = 0;
		parcouru = 0;
		to = new ArrayList<StationReader>();
	}

	public void addTo (StationReader _to) {
		to.add(_to);
	}

	public void removeTo (StationReader _to) {
		to.remove(_to);
	}
}
