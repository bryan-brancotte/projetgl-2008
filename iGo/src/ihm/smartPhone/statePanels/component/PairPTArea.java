package ihm.smartPhone.statePanels.component;

import graphNetwork.Station;
import ihm.smartPhone.libPT.PTArea;

public class PairPTArea {
	public PTArea area;
	public String name;
	public Station station;

	public PairPTArea(PTArea area, Station station) {
		super();
		this.area = area;
		this.station = station;
		this.name = station.getName();
	}

}
