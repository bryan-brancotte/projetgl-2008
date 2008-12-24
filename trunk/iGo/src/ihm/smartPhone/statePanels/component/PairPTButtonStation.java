package ihm.smartPhone.statePanels.component;

import ihm.smartPhone.libPT.PTButton;
import graphNetwork.Station;

public class PairPTButtonStation {
	public PTButton cmd;
	public Station station;

	public PairPTButtonStation(PTButton cmd, Station station) {
		super();
		this.cmd = cmd;
		this.station = station;
	}

}
