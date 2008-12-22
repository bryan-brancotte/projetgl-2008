package ihm.smartPhone.statePanels.component;

import graphNetwork.Station;
import ihm.smartPhone.tools.PTButton;

public class PairPTButtonStation {
	public PTButton cmd;
	public Station station;

	public PairPTButtonStation(PTButton cmd, Station station) {
		super();
		this.cmd = cmd;
		this.station = station;
	}

}
