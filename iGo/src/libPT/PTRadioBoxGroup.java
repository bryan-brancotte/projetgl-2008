package libPT;

import java.util.LinkedList;

public class PTRadioBoxGroup {

	public static final int DEFAULT_SIZE = 3;

	// TODO vérifier le conteneur le plus léger
	protected LinkedList<PTRadioBox> radioBoxs;

	public PTRadioBoxGroup(int size) {
		radioBoxs = new LinkedList<PTRadioBox>();
	}

	public PTRadioBoxGroup() {
		this(DEFAULT_SIZE);
	}

	public void setAllNotClicked() {
		for(PTRadioBox r : radioBoxs){
			r.setClicked(false);
		}
	}

	public void add(PTRadioBox radio) {
		if (radio != null)
			radioBoxs.add(radio);
	}

}
