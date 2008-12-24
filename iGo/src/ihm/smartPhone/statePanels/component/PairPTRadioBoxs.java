package ihm.smartPhone.statePanels.component;

import libPT.PTRadioBox;
import graphNetwork.Service;

public class PairPTRadioBoxs {
	public PTRadioBox[] rbs;
	public String name;
	public Service service;

	public PairPTRadioBoxs(PTRadioBox[] rbs, String name) {
		super();
		this.rbs = rbs;
		this.service = null;
		this.name = name;
	}

	public PairPTRadioBoxs(PTRadioBox[] rbs, Service service, String name) {
		super();
		this.rbs = rbs;
		this.service = service;
		this.name = name;
	}

}
