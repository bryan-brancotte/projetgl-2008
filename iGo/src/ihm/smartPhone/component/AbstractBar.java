package ihm.smartPhone.component;

import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.tools.PanelDoubleBufferingSoftwear;

public abstract class AbstractBar extends PanelDoubleBufferingSoftwear {

	protected IGoIhmSmartPhone ihm;
	

	public AbstractBar(IGoIhmSmartPhone ihm) {
		this.ihm = ihm;
	}

	public abstract void clearMessage();
}
